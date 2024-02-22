package com.tll3.Task.Mobs;

import com.tll3.Listeners.EntityNaturalSpawn;
import com.tll3.Lists.CustomEntities.Others.MiniCyclone;
import com.tll3.Misc.DataManager.Data;
import com.tll3.Misc.EntityHelper;
import com.tll3.TLL3;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

public class MiniCycloneTask extends BukkitRunnable {
    private final Skeleton l;
    int DAMAGE_COOLDOWN = 5;
    public MiniCycloneTask(Skeleton l){
        this.l = l;
    }
    @Override
    public void run() {
        if (l.isDead() || !l.isValid()) {
            cancel();
            return;
        }
        if (l.getTarget() != null) {
            for (Player nearby : l.getLocation().getNearbyPlayers(2, 2, 2)) {
                if (nearby.isDead() || !nearby.isOnline() || nearby == null || nearby.getGameMode() != GameMode.SURVIVAL)
                    return;
                if (DAMAGE_COOLDOWN > 0) {
                    DAMAGE_COOLDOWN--;
                } else {
                    nearby.damage(7, l);
                    nearby.playSound(nearby.getLocation(), Sound.ENTITY_WITHER_BREAK_BLOCK, 3.0F, -1.0F);
                    Bukkit.getScheduler().runTaskLaterAsynchronously(TLL3.getInstance(), () -> {
                        nearby.setNoDamageTicks(0);
                        nearby.setLastDamage(Integer.MAX_VALUE);
                    }, 2L);
                    DAMAGE_COOLDOWN = 5;
                    if(Data.has(l,"minicyclone_space", PersistentDataType.STRING)){
                        if(EntityNaturalSpawn.doRandomChance(5)){
                            EntityHelper.teleportEnderman(nearby,nearby.getLocation().getBlockX(),nearby.getLocation().getBlockY(),nearby.getLocation().getBlockZ(),nearby.getWorld(),40.0D);
                        }
                    }
                }
            }
        }
    }
}
