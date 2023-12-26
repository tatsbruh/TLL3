package com.tll3.Listeners;

import com.tll3.Misc.ChatUtils;
import com.tll3.Misc.DataManager.Data;
import com.tll3.Misc.DataManager.PlayerData;
import com.tll3.Misc.GenericUtils;
import com.tll3.TLL3;
import com.tll3.Task.EffectDuration;
import com.tll3.Task.EffectTask;
import com.tll3.Task.Scoreboard;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public class GenericPlayerListeners implements Listener {





    @EventHandler
    public void joinL(PlayerJoinEvent e){
        var p = e.getPlayer();
        new EffectDuration(p).runTaskTimer(TLL3.getInstance(),20L,20L); //starts the duration of the effects
        new Scoreboard(p).runTaskTimer(TLL3.getInstance(),0L,1L); //starts the scorebard task
        new EffectTask(p).runTaskTimer(TLL3.getInstance(),0L,1L);

    }


    @EventHandler
    public void totemE(EntityResurrectEvent e){
        var entity = e.getEntity();
        if(entity instanceof Player p){
            if(p.getInventory().getItemInMainHand().getType() == Material.TOTEM_OF_UNDYING || p.getInventory().getItemInOffHand().getType() == Material.TOTEM_OF_UNDYING){
                for(Player online : Bukkit.getOnlinePlayers()){
                    totemEvent(p,online,e);
                }
            }
        }
    }


    public void totemEvent(Player p,Player online,EntityResurrectEvent e){
        int totem_c = PlayerData.getTotemCount(p);
        if(totem_c <= 19){
            online.sendMessage(ChatUtils.format(ChatUtils.prefix + "&7El jugador &c&l" + p.getName() + " &7a usado un Totem de la Inmortalidad &8(Totem #" + totem_c + ") &7&lCausa: " + GenericUtils.damageCause(Objects.requireNonNull(p.getLastDamageCause()))));
            PlayerData.setTotemCount(p,totem_c + 1);
        }
        if(totem_c >= 20 && totem_c <= 34){
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
                online.sendMessage(ChatUtils.format(ChatUtils.prefix + "&cEl jugador &6&l" + p.getName() + " &cno tenia suficientes totems en el inventario!"));
            }else{
                new BukkitRunnable(){
                    public void run(){
                        p.playSound(p.getLocation(), Sound.ITEM_TOTEM_USE, 5.0F, 1.0F);
                        p.playEffect(EntityEffect.TOTEM_RESURRECT);
                    }
                }.runTaskLater(TLL3.getInstance(),20L);
                p.getInventory().removeItem(new ItemStack(Material.TOTEM_OF_UNDYING, 1));
                online.sendMessage(ChatUtils.format(ChatUtils.prefix + "&7El jugador &c&l" + p.getName() + " &7a usado 2 Totems de la Inmortalidad &8(Totem #" + totem_c + " y #" + (totem_c + 1) + ") &7&lCausa: " + GenericUtils.damageCause(Objects.requireNonNull(p.getLastDamageCause()))));
                PlayerData.setTotemCount(p,totem_c + 2);
            }
        }
        if(totem_c >= 35 && totem_c <= 68){
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
                online.sendMessage(ChatUtils.format(ChatUtils.prefix + "&cEl jugador &6&l" + p.getName() + " &cno tenia suficientes totems en el inventario!"));
            }else{
                new BukkitRunnable(){
                    public void run(){
                        p.playSound(p.getLocation(), Sound.ITEM_TOTEM_USE, 5.0F, 1.0F);
                        p.playEffect(EntityEffect.TOTEM_RESURRECT);
                    }
                }.runTaskLater(TLL3.getInstance(),20L);
                new BukkitRunnable(){
                    public void run(){
                        p.playSound(p.getLocation(), Sound.ITEM_TOTEM_USE, 5.0F, 1.0F);
                        p.playEffect(EntityEffect.TOTEM_RESURRECT);
                    }
                }.runTaskLater(TLL3.getInstance(),40L);
                p.getInventory().removeItem(new ItemStack(Material.TOTEM_OF_UNDYING, 2));
                online.sendMessage(ChatUtils.format(ChatUtils.prefix + "&7El jugador &c&l" + p.getName() + " &7a usado 3 Totems de la Inmortalidad &8(Totem #" + totem_c + ", #" + (totem_c + 1) + " y " + (totem_c + 2) +") &7&lCausa: " + GenericUtils.damageCause(Objects.requireNonNull(p.getLastDamageCause()))));
                PlayerData.setTotemCount(p,totem_c + 3);
            }
        }
        if(totem_c >= 69){
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
                online.sendMessage(ChatUtils.format(ChatUtils.prefix + "&cEl jugador &6&l" + p.getName() + " &cno tenia suficientes totems en el inventario!"));
            }else{
                new BukkitRunnable(){
                    public void run(){
                        p.playSound(p.getLocation(), Sound.ITEM_TOTEM_USE, 5.0F, 1.0F);
                        p.playEffect(EntityEffect.TOTEM_RESURRECT);
                    }
                }.runTaskLater(TLL3.getInstance(),20L);
                new BukkitRunnable(){
                    public void run(){
                        p.playSound(p.getLocation(), Sound.ITEM_TOTEM_USE, 5.0F, 1.0F);
                        p.playEffect(EntityEffect.TOTEM_RESURRECT);
                    }
                }.runTaskLater(TLL3.getInstance(),40L);
                new BukkitRunnable(){
                    public void run(){
                        p.playSound(p.getLocation(), Sound.ITEM_TOTEM_USE, 5.0F, 1.0F);
                        p.playEffect(EntityEffect.TOTEM_RESURRECT);
                    }
                }.runTaskLater(TLL3.getInstance(),60L);
                new BukkitRunnable(){
                    public void run(){
                        p.playSound(p.getLocation(), Sound.ITEM_TOTEM_USE, 5.0F, 1.0F);
                        p.playEffect(EntityEffect.TOTEM_RESURRECT);
                    }
                }.runTaskLater(TLL3.getInstance(),80L);
                p.getInventory().removeItem(new ItemStack(Material.TOTEM_OF_UNDYING, 4));
                online.sendMessage(ChatUtils.format(ChatUtils.prefix + "&7El jugador &c&l" + p.getName() + " &7a usado 5 Totems de la Inmortalidad &8(Totem #" + totem_c + ", #" + (totem_c + 1) + ", " + (totem_c + 2) + ", #" + (totem_c + 3) + "y #" + (totem_c + 4) +") &7&lCausa: " + GenericUtils.damageCause(Objects.requireNonNull(p.getLastDamageCause()))));
                PlayerData.setTotemCount(p,totem_c + 5);
            }
        }

    }



}
