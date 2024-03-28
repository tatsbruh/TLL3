package com.tll3.Task.Mobs;

import com.tll3.Listeners.EntityNaturalSpawn;
import com.tll3.Lists.CustomEntities.Others.MiniCyclone;
import com.tll3.Misc.DataManager.Data;
import com.tll3.Misc.EntityHelper;
import com.tll3.Misc.GenericUtils;
import com.tll3.TLL3;
import org.bukkit.Bukkit;
import org.bukkit.EntityEffect;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

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
                    nearby.playSound(nearby.getLocation(), Sound.ENTITY_WITHER_BREAK_BLOCK, 3.0F, -1.0F);
                    Bukkit.getScheduler().runTaskLaterAsynchronously(TLL3.getInstance(), () -> {
                        nearby.setNoDamageTicks(0);
                        nearby.setLastDamage(Integer.MAX_VALUE);
                    }, 2L);
                    DAMAGE_COOLDOWN = 5;
                    if(Data.has(l,"minicyclone_space", PersistentDataType.STRING)){
                        nearby.damage(7, l);
                        if(EntityNaturalSpawn.doRandomChance(3)){
                            var list = l.getNearbyEntities(15, 15, 15);
                            var mob = list.get(new Random().nextInt(list.size()));
                            if (GenericUtils.isHostileMob(mob.getType())) {
                                var le = l.getLocation().clone();
                                nearby.playSound(nearby.getLocation(), Sound.ENTITY_SHULKER_TELEPORT, 10.0F, -1.0F);
                                mob.teleport(le);
                                mob.playEffect(EntityEffect.TELEPORT_ENDER);
                            }
                        }
                    }
                    if(Data.has(l,"minicyclone_zombie", PersistentDataType.STRING)){
                        if(l.hasAI()) {
                            nearby.damage(11, l);
                            nearby.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 100, 0, true, false, true));
                        }
                    }
                    if(Data.has(l,"minicyclone_normal", PersistentDataType.STRING)){
                        nearby.damage(25, l);
                    }
                }
            }
        }
    }
}
