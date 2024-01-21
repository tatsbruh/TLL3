package com.tll3.Listeners;

import com.tll3.Lists.Entities;
import com.tll3.Misc.ChatUtils;
import com.tll3.Misc.DataManager.Data;
import com.tll3.Misc.DataManager.PlayerData;
import com.tll3.Misc.GenericUtils;
import com.tll3.Misc.ItemBuilder;
import com.tll3.TLL3;
import com.tll3.Task.*;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EnumMoveType;
import net.minecraft.world.item.ItemTrident;
import net.minecraft.world.level.block.BlockTrapdoor;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Pose;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityPoseChangeEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.event.entity.EntityToggleSwimEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Objects;
import java.util.Random;

import static com.tll3.Misc.GenericUtils.getDay;
import static com.tll3.Misc.GenericUtils.*;

public class GenericPlayerListeners implements Listener {





    @EventHandler
    public void joinL(PlayerJoinEvent e){
        var p = e.getPlayer();
        if(PlayerData.getItemCooldown(p,"inv_tome") > 0){
            p.setCooldown(Material.BOOK,PlayerData.getItemCooldown(p,"inv_tome"));
        }

        PlayerData.addExposure(p);
        if(getDay() >= 7) {
            new ExposureTask(p).runTaskTimer(TLL3.getInstance(), 0L, 1L);
        }
        new EffectDuration(p).runTaskTimer(TLL3.getInstance(),20L,20L); //starts the duration of the effects
        new TabManager(p).runTaskTimer(TLL3.getInstance(),0L,1L);
        new Scoreboard(p).runTaskTimer(TLL3.getInstance(),0L,1L); //starts the scorebard task
        new EffectTask(p).runTaskTimer(TLL3.getInstance(),0L,1L);
        new ServerTickTask(p).runTaskTimer(TLL3.getInstance(),0L,1L);

    }

    @EventHandler
    public void quitE(PlayerQuitEvent e){
        var p = e.getPlayer();
        if(p.hasCooldown(Material.BOOK)){
            PlayerData.setItemCooldown(p,"inv_tome",p.getCooldown(Material.BOOK));
        }
    }


    @EventHandler
    public void rightclickE(PlayerInteractEvent e){
        var p = e.getPlayer();
        var item = e.getItem();
        if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
            if(item == null)return;
            if(!item.hasItemMeta())return;
            if(checkItemId(item,"invulnerable_tome")){
                if(p.hasCooldown(Material.BOOK)){
                    p.sendMessage(ChatUtils.format(ChatUtils.prefix + "&c¡No puedes usar este item aun!"));
                    p.getWorld().playSound(p.getLocation(),Sound.BLOCK_NOTE_BLOCK_PLING,10.0F,-1.0F);
                }else{
                    p.sendMessage(ChatUtils.format(ChatUtils.prefix + "&eHas usado tu &8&lTomo de la Inmortalidad"));
                    p.getWorld().playSound(p.getLocation(),Sound.BLOCK_ENCHANTMENT_TABLE_USE,10.0F,2.0F);
                    p.setCooldown(Material.BOOK,6000);
                    PlayerData.addDataEffect(p,"invulnerable",15,1);
                }
            }

            if(checkItemId(item,"brimstonetrident")){
                if(p.isInLava() || (p.getWorld().getName().equals("world_nether") && getMonsoon_active().equalsIgnoreCase("true"))){
                    if(p.hasCooldown(Material.TRIDENT))return;
                    EntityPlayer papa = ((CraftPlayer)p).getHandle();
                    papa.t(25);
                    p.playSound(p.getLocation(),Sound.ITEM_TRIDENT_RIPTIDE_3,10.0F,2.0F);
                    double speed = 6.55;
                    Vector direction = p.getLocation().getDirection().multiply(speed);
                    p.setVelocity(direction);
                    p.setCooldown(Material.TRIDENT,30);
                }
            }

        }
    }




    @EventHandler
    public void itemduraE(PlayerItemDamageEvent e){
        var player = e.getPlayer();
        if(getDay() >= 7){
        if(Objects.equals(GenericUtils.getMonsoon_active(), "true") && player.getGameMode() == GameMode.SURVIVAL){
            Location block = player.getWorld().getHighestBlockAt(player.getLocation().clone()).getLocation();
            int highestY = block.getBlockY();
            if (highestY < player.getLocation().getY()) {
                if(getDay() >= 14){
                    e.setDamage(e.getDamage() * 4);
                }else{
                e.setDamage(e.getDamage() * 2);
            }
            }
        }
        }
    }

    @EventHandler
    public void regenerateE(EntityRegainHealthEvent e){
        if(e.getEntity() instanceof Player p){
            if(e.getRegainReason() == EntityRegainHealthEvent.RegainReason.SATIATED){
                if(Data.has(p,"bleed", PersistentDataType.STRING)){
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void cubetaE(PlayerBucketFillEvent e){
        if(getMonsoon_active().equalsIgnoreCase("true") && getDay() >= 14){
            e.setCancelled(true);
        }
    }




    @EventHandler
    public void eatitemE(PlayerItemConsumeEvent e){
        var player = e.getPlayer();
        var item = e.getItem();
        if(new ItemBuilder(item).hasID("miraclefruit")){
            PlayerData.restExp(player,30);
        }
    }




    @EventHandler
    public void totemE(EntityResurrectEvent e){
        var entity = e.getEntity();
        if(entity instanceof Player p){
            if(p.getInventory().getItemInMainHand().getType() == Material.TOTEM_OF_UNDYING || p.getInventory().getItemInOffHand().getType() == Material.TOTEM_OF_UNDYING){
                if(e.isCancelled())return;
                totemEvent(p,e);
                if(getDay() >= 14)exposureJump(p,PlayerData.getTotemCount(p));
                new BukkitRunnable(){
                    @Override
                    public void run() {totemEffects(p);}}.runTaskLater(TLL3.getInstance(),1L);
            }
        }
    }



    public void totemEvent(Player p,EntityResurrectEvent e){
        int totem_c = PlayerData.getTotemCount(p);
        if(totem_c <= 29){
            for(Player online : Bukkit.getOnlinePlayers()) {
                online.sendMessage(ChatUtils.format(ChatUtils.prefix + "&7El jugador &c&l" + p.getName() + " &7ha usado un Totem de la Inmortalidad &8(Totem #" + totem_c + ") &7&lCausa: " + GenericUtils.damageCause(Objects.requireNonNull(p.getLastDamageCause()))));
            }
            PlayerData.setTotemCount(p,totem_c + 1);

            if(getDay() >= 7)PlayerData.addDataEffect(p,"curse",60,1);
        }
        if(totem_c > 29 && totem_c <= 54){
            int tot_amount = 2;
            int main = 0;
            int off = 0;
            if (p.getInventory().getItemInMainHand().getType().equals(Material.TOTEM_OF_UNDYING)) {
                main = 1;
            }
            if (p.getInventory().getItemInOffHand().getType().equals(Material.TOTEM_OF_UNDYING)) {
                off = 1;
            }
            int result = main + off;
            int size = p.getInventory().all(Material.TOTEM_OF_UNDYING).size() + result;
            if(tot_amount > size){
                e.setCancelled(true);
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(ChatUtils.format(ChatUtils.prefix + "&cEl jugador &6&l" + p.getName() + " &cno tenia suficientes totems en el inventario! &8(" + size + "/" + tot_amount + ")"));
                }
            }else{
                if(getDay() >= 7)PlayerData.addDataEffect(p,"curse",80,2);
                new BukkitRunnable(){
                    public void run(){
                        p.playSound(p.getLocation(), Sound.ITEM_TOTEM_USE, 10.0F, 1.0F);
                        p.playEffect(EntityEffect.TOTEM_RESURRECT);
                    }
                }.runTaskLater(TLL3.getInstance(),20L);
                p.getInventory().removeItem(new ItemStack(Material.TOTEM_OF_UNDYING, 1));
                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(ChatUtils.format(ChatUtils.prefix + "&7El jugador &c&l" + p.getName() + " &7ha usado 2 Totems de la Inmortalidad &8(Totem #" + totem_c + " y #" + (totem_c + 1) + ") &7&lCausa: " + GenericUtils.damageCause(Objects.requireNonNull(p.getLastDamageCause()))));
                }
                PlayerData.setTotemCount(p,totem_c + 2);
            }
        }
        if(totem_c >= 55 && totem_c <= 84){
            int tot_amount = 3;
            int main = 0;
            int off = 0;
            if (p.getInventory().getItemInMainHand().getType().equals(Material.TOTEM_OF_UNDYING)) {
                main = 1;
            }
            if (p.getInventory().getItemInOffHand().getType().equals(Material.TOTEM_OF_UNDYING)) {
                off = 1;
            }
            int result = main + off;
            int size = p.getInventory().all(Material.TOTEM_OF_UNDYING).size() + result;
            if(tot_amount > size){
                e.setCancelled(true);
                for(Player online : Bukkit.getOnlinePlayers()) {

                    online.sendMessage(ChatUtils.format(ChatUtils.prefix + "&cEl jugador &6&l" + p.getName() + " &cno tenia suficientes totems en el inventario! &8(" + size + "/" + tot_amount + ")"));
                }
                }else{
                if(getDay() >= 7)PlayerData.addDataEffect(p,"curse",120,3);
                new BukkitRunnable(){
                    public void run(){
                        p.playSound(p.getLocation(), Sound.ITEM_TOTEM_USE, 10.0F, 1.0F);
                        p.playEffect(EntityEffect.TOTEM_RESURRECT);
                    }
                }.runTaskLater(TLL3.getInstance(),20L);
                new BukkitRunnable(){
                    public void run(){
                        p.playSound(p.getLocation(), Sound.ITEM_TOTEM_USE, 10.0F, 1.0F);
                        p.playEffect(EntityEffect.TOTEM_RESURRECT);
                    }
                }.runTaskLater(TLL3.getInstance(),40L);
                p.getInventory().removeItem(new ItemStack(Material.TOTEM_OF_UNDYING, 2));
                for(Player online : Bukkit.getOnlinePlayers()) {

                    online.sendMessage(ChatUtils.format(ChatUtils.prefix + "&7El jugador &c&l" + p.getName() + " &7ha usado 3 Totems de la Inmortalidad &8(Totem #" + totem_c + ", #" + (totem_c + 1) + " y " + (totem_c + 2) + ") &7&lCausa: " + GenericUtils.damageCause(Objects.requireNonNull(p.getLastDamageCause()))));
                }
                PlayerData.setTotemCount(p,totem_c + 3);
            }
        }
        if(totem_c >= 85){
            int tot_amount = 5;
            int main = 0;
            int off = 0;
            if (p.getInventory().getItemInMainHand().getType().equals(Material.TOTEM_OF_UNDYING)) {
                main = 1;
            }
            if (p.getInventory().getItemInOffHand().getType().equals(Material.TOTEM_OF_UNDYING)) {
                off = 1;
            }
            int result = main + off;
            int size = p.getInventory().all(Material.TOTEM_OF_UNDYING).size() + result;
            if(tot_amount > size){
                e.setCancelled(true);
                for(Player online : Bukkit.getOnlinePlayers()) {

                    online.sendMessage(ChatUtils.format(ChatUtils.prefix + "&cEl jugador &6&l" + p.getName() + " &cno tenia suficientes totems en el inventario! &8(" + size + "/" + tot_amount + ")"));

                }
            }else{
                if(getDay() >= 7)PlayerData.addDataEffect(p,"curse",145,4);
                new BukkitRunnable(){
                    public void run(){
                        p.playSound(p.getLocation(), Sound.ITEM_TOTEM_USE, 10.0F, 1.0F);
                        p.playEffect(EntityEffect.TOTEM_RESURRECT);
                    }
                }.runTaskLater(TLL3.getInstance(),20L);
                new BukkitRunnable(){
                    public void run(){
                        p.playSound(p.getLocation(), Sound.ITEM_TOTEM_USE, 10.0F, 1.0F);
                        p.playEffect(EntityEffect.TOTEM_RESURRECT);
                    }
                }.runTaskLater(TLL3.getInstance(),40L);
                new BukkitRunnable(){
                    public void run(){
                        p.playSound(p.getLocation(), Sound.ITEM_TOTEM_USE, 10.0F, 1.0F);
                        p.playEffect(EntityEffect.TOTEM_RESURRECT);
                    }
                }.runTaskLater(TLL3.getInstance(),60L);
                new BukkitRunnable(){
                    public void run(){
                        p.playSound(p.getLocation(), Sound.ITEM_TOTEM_USE, 10.0F, 1.0F);
                        p.playEffect(EntityEffect.TOTEM_RESURRECT);
                    }
                }.runTaskLater(TLL3.getInstance(),80L);
                p.getInventory().removeItem(new ItemStack(Material.TOTEM_OF_UNDYING, 4));
                for(Player online : Bukkit.getOnlinePlayers()) {

                    online.sendMessage(ChatUtils.format(ChatUtils.prefix + "&7El jugador &c&l" + p.getName() + " &7ha usado 5 Totems de la Inmortalidad &8(Totem #" + totem_c + ", #" + (totem_c + 1) + ", " + (totem_c + 2) + ", #" + (totem_c + 3) + " y #" + (totem_c + 4) + ") &7&lCausa: " + GenericUtils.damageCause(Objects.requireNonNull(p.getLastDamageCause()))));

                }
                PlayerData.setTotemCount(p,totem_c + 5);
            }
        }

    }
    public void totemEffects(Player p){
        int a = PlayerData.getTotemCount(p);

        if(a >= 5 && a <= 12){
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,350,1,true, false,true));
        }
        if(a >= 13 && a <= 20){
            p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS,350,0,true, false,true));
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,350,1,true, false,true));
        }
        if(a >= 21 && a <= 32){
            p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS,500,0,true, false,true));
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,350,1,true, false,true));
            p.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
        }
        if(a >= 33 && a <= 43) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS,350,0,true, false,true));
            p.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER,350,0,true, false,true));
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,350,1,true, false,true));
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING,350,1,true, false,true));
            p.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
        }
        if(a >= 44 && a <= 54) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS,350,0,true, false,true));
            p.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER,350,1,true, false,true));
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,350,1,true, false,true));
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING,350,2,true, false,true));
            p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION,350,0,true, false,true));
            p.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
        }
        if(a >= 55 && a <= 62) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS,350,0,true, false,true));
            p.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER,350,1,true, false,true));
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,350,1,true, false,true));
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING,350,2,true, false,true));
            p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION,350,0,true, false,true));
            p.removePotionEffect(PotionEffectType.ABSORPTION);
            p.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);

        }
        if(a >= 63 && a <= 78) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS,350,0,true, false,true));
            p.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER,350,2,true, false,true));
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,350,2,true, false,true));
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING,350,3,true, false,true));
            p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION,350,0,true, false,true));
            p.removePotionEffect(PotionEffectType.ABSORPTION);
            p.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
            p.setFoodLevel(p.getFoodLevel() - 2);
        }
        if(a >= 79 && a <= 87) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS,350,0,true, false,true));
            p.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER,350,2,true, false,true));
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,350,2,true, false,true));
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING,350,3,true, false,true));
            p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION,350,0,true, false,true));
            p.removePotionEffect(PotionEffectType.REGENERATION);
            p.removePotionEffect(PotionEffectType.ABSORPTION);
            p.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
            p.setFoodLevel(p.getFoodLevel() - 5);
        }
        if(a >= 88 && a <= 96) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS,350,0,true, false,true));
            p.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER,350,2,true, false,true));
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,350,2,true, false,true));
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING,350,3,true, false,true));
            p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION,350,0,true, false,true));
            p.removePotionEffect(PotionEffectType.REGENERATION);
            p.removePotionEffect(PotionEffectType.ABSORPTION);
            p.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
            p.setFoodLevel(p.getFoodLevel() - 15);

        }
        if(a >= 97  && a <= 119) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER,350,9,true, false,true));
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,350,4,true, false,true));
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING,350,6,true, false,true));
            p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION,350,0,true, false,true));
            p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS,350,2,true, false,true));
            p.addPotionEffect(new PotionEffect(PotionEffectType.BAD_OMEN,Integer.MAX_VALUE,4,true,false,true));
            p.removePotionEffect(PotionEffectType.REGENERATION);
            p.removePotionEffect(PotionEffectType.ABSORPTION);
            p.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
            p.setFoodLevel(0);
        }
        if(a >= 120){
            p.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER,350,9,true, false,true));
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,350,9,true, false,true));
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING,350,9,true, false,true));
            p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION,350,0,true, false,true));
            p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS,350,9,true, false,true));
            p.addPotionEffect(new PotionEffect(PotionEffectType.BAD_OMEN,Integer.MAX_VALUE,4,true,false,true));
            p.removePotionEffect(PotionEffectType.REGENERATION);
            p.removePotionEffect(PotionEffectType.ABSORPTION);
            p.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
            p.setFoodLevel(0);
            if(EntityNaturalSpawn.doRandomChance(50)){
                for(Player online : Bukkit.getOnlinePlayers())online.sendMessage(ChatUtils.format(ChatUtils.prefix + "&c&lEl Totem de " + p.getName() + " ha invocado un &8&lWITHER BOSS"));
                Entities.spawnMob(p.getLocation(), EntityType.WITHER);
            }
        }
    }

    public static void exposureJump(Player p,int totem_c){
        if(Bukkit.getOnlinePlayers().size() <= 1){
            p.sendMessage(ChatUtils.format(ChatUtils.prefix + "&eNo hay jugadores conectados"));
            return;
        }
        Player randomplayer = (Player) Bukkit.getOnlinePlayers().toArray()[new Random().nextInt(Bukkit.getOnlinePlayers().size())];
        if(randomplayer.getGameMode() == GameMode.SPECTATOR || randomplayer.getGameMode() == GameMode.CREATIVE || !randomplayer.getName().equals(p.getName()))return;
        int r = GenericUtils.getRandomValue(100);
        if(r < 20){
            for(Player online : Bukkit.getOnlinePlayers()){
                online.sendMessage(ChatUtils.format(ChatUtils.prefix + "&cEl Totem de la Inmortalidad de " + p.getName() + " aplico su &4Pánico en el jugador " + randomplayer.getName() + " &8(20 > " + r + ")"));
            }
            if(totem_c <= 29){
                PlayerData.addDataEffect(randomplayer,"curse",60,1);
            }
            if(totem_c > 29 && totem_c <= 54){
                PlayerData.addDataEffect(randomplayer,"curse",80,2);
            }
            if(totem_c >= 55 && totem_c <= 84){
                PlayerData.addDataEffect(randomplayer,"curse",120,3);
            }
            if(totem_c >= 85){
                PlayerData.addDataEffect(randomplayer,"curse",145,4);
            }
        }else{
            p.sendMessage(ChatUtils.format(ChatUtils.prefix + "&7Tu Totem de la Inmortalidad no aplico el pánico en nadie. (20 < " + r + ")"));
        }
    }


    public static boolean checkItemId(ItemStack item,String id){
        if(item.hasItemMeta()){
            if (item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(TLL3.getInstance(),"id"),PersistentDataType.STRING)){
                if(item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(TLL3.getInstance(),"id"),PersistentDataType.STRING).equalsIgnoreCase(id)){
                  return true;
                }
            }
        }
        return false;
    }

}
