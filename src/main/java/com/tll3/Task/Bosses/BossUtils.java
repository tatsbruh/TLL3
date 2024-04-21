package com.tll3.Task.Bosses;

import com.tll3.Misc.ChatUtils;
import com.tll3.TLL3;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Creature;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class BossUtils {

    public static void freezeboss(LivingEntity l){
        l.setAI(false);
        ((Creature)l).setAware(false);
        l.setInvulnerable(true);
        l.setCollidable(false);
        l.setGravity(false);
    }
    public static void unfreezeboss(LivingEntity l){
        l.setAI(true);
        ((Creature)l).setAware(true);
        l.setInvulnerable(false);
        l.setCollidable(true);
        l.setGravity(true);
    }
    public static void sendUniversalMessage(String message){
        for(Player online : Bukkit.getOnlinePlayers()){
            online.sendMessage(ChatUtils.format(message));
        }
    }

    public static Player ValidPlayer(){
        for(Player onl : Bukkit.getOnlinePlayers()){
            if(onl.getGameMode() == GameMode.SURVIVAL) {
                return onl;
            }
        }
        return null;
    }

    public static void runEventLater(Runnable run,long delay){
        new BukkitRunnable() {
            @Override
            public void run() {
                run.run();
            }
        }.runTaskLater(TLL3.getInstance(),delay);
    }


}
