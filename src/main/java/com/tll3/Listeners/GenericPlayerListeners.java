package com.tll3.Listeners;

import com.tll3.Misc.ChatUtils;
import com.tll3.Misc.DataManager.Data;
import com.tll3.Misc.DataManager.PlayerData;
import com.tll3.Misc.GenericUtils;
import com.tll3.Misc.ItemBuilder;
import com.tll3.TLL3;
import com.tll3.Task.EffectDuration;
import com.tll3.Task.EffectTask;
import com.tll3.Task.ExposureTask;
import com.tll3.Task.Scoreboard;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;
import java.util.Random;

public class GenericPlayerListeners implements Listener {





    @EventHandler
    public void joinL(PlayerJoinEvent e){

        var p = e.getPlayer();
        PlayerData.addExposure(p);
        new ExposureTask(p).runTaskTimer(TLL3.getInstance(),0L,1L);
        new EffectDuration(p).runTaskTimer(TLL3.getInstance(),20L,20L); //starts the duration of the effects
        new Scoreboard(p).runTaskTimer(TLL3.getInstance(),0L,1L); //starts the scorebard task
        new EffectTask(p).runTaskTimer(TLL3.getInstance(),0L,1L);

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
                for(Player online : Bukkit.getOnlinePlayers()){

                    totemEvent(p,online,e);
                }
                new BukkitRunnable(){
                    @Override
                    public void run() {totemEffects(p);}}.runTaskLater(TLL3.getInstance(),1L);
            }
        }
    }


    public void totemEvent(Player p,Player online,EntityResurrectEvent e){
        int totem_c = PlayerData.getTotemCount(p);
        if(totem_c <= 29){
            online.sendMessage(ChatUtils.format(ChatUtils.prefix + "&7El jugador &c&l" + p.getName() + " &7ha usado un Totem de la Inmortalidad &8(Totem #" + totem_c + ") &7&lCausa: " + GenericUtils.damageCause(Objects.requireNonNull(p.getLastDamageCause()))));
            PlayerData.setTotemCount(p,totem_c + 1);
            PlayerData.addDataEffect(p,"curse",60,1);
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
                online.sendMessage(ChatUtils.format(ChatUtils.prefix + "&cEl jugador &6&l" + p.getName() + " &cno tenia suficientes totems en el inventario! &8(" + size + "/" + tot_amount + ")"));

            }else{
                PlayerData.addDataEffect(p,"curse",80,2);
                new BukkitRunnable(){
                    public void run(){
                        p.playSound(p.getLocation(), Sound.ITEM_TOTEM_USE, 10.0F, 1.0F);
                        p.playEffect(EntityEffect.TOTEM_RESURRECT);
                    }
                }.runTaskLater(TLL3.getInstance(),20L);
                p.getInventory().removeItem(new ItemStack(Material.TOTEM_OF_UNDYING, 1));
                online.sendMessage(ChatUtils.format(ChatUtils.prefix + "&7El jugador &c&l" + p.getName() + " &7ha usado 2 Totems de la Inmortalidad &8(Totem #" + totem_c + " y #" + (totem_c + 1) + ") &7&lCausa: " + GenericUtils.damageCause(Objects.requireNonNull(p.getLastDamageCause()))));
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
                online.sendMessage(ChatUtils.format(ChatUtils.prefix + "&cEl jugador &6&l" + p.getName() + " &cno tenia suficientes totems en el inventario! &8(" + size + "/" + tot_amount + ")"));
            }else{
                PlayerData.addDataEffect(p,"curse",120,3);
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
                online.sendMessage(ChatUtils.format(ChatUtils.prefix + "&7El jugador &c&l" + p.getName() + " &7ha usado 3 Totems de la Inmortalidad &8(Totem #" + totem_c + ", #" + (totem_c + 1) + " y " + (totem_c + 2) +") &7&lCausa: " + GenericUtils.damageCause(Objects.requireNonNull(p.getLastDamageCause()))));
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
                online.sendMessage(ChatUtils.format(ChatUtils.prefix + "&cEl jugador &6&l" + p.getName() + " &cno tenia suficientes totems en el inventario! &8(" + size + "/" + tot_amount + ")"));
            }else{
                PlayerData.addDataEffect(p,"curse",145,4);
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
                online.sendMessage(ChatUtils.format(ChatUtils.prefix + "&7El jugador &c&l" + p.getName() + " &7ha usado 5 Totems de la Inmortalidad &8(Totem #" + totem_c + ", #" + (totem_c + 1) + ", " + (totem_c + 2) + ", #" + (totem_c + 3) + " y #" + (totem_c + 4) +") &7&lCausa: " + GenericUtils.damageCause(Objects.requireNonNull(p.getLastDamageCause()))));
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
        if(a >= 97 ) {
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
    }

}
