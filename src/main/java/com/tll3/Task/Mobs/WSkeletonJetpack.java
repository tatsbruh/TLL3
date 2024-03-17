package com.tll3.Task.Mobs;

import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class WSkeletonJetpack extends BukkitRunnable {
    private final WitherSkeleton witherSkeleton;
    private int i = 0;
    public WSkeletonJetpack(WitherSkeleton w){
        this.witherSkeleton = w;
    }
    @Override
    public void run() {
        if (witherSkeleton.isDead() || !witherSkeleton.isValid()) {
            cancel();
            return;
        }
        LivingEntity target = witherSkeleton.getTarget();
        Location location = witherSkeleton.getLocation();
        if(target != null && location != null) {
            if (i < 250) {
                i++;
            } else {
                witherSkeleton.playEffect(EntityEffect.FIREWORK_EXPLODE);
                witherSkeleton.getWorld().playSound(witherSkeleton.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_SHOOT,5.0F,1.0F);
                Vector direction = target.getLocation().toVector().subtract(location.toVector()).normalize();
                witherSkeleton.setVelocity(direction.multiply(2.5));
                i = 0;
            }
        }
    }
}
