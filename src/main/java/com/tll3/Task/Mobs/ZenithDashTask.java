package com.tll3.Task.Mobs;

import com.tll3.Lists.Entities;
import com.tll3.Misc.GenericUtils;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Silverfish;
import org.bukkit.entity.Spider;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class ZenithDashTask extends BukkitRunnable {
    private static final int LUNGE_THRESHOLD = 100;

    private final Spider s;
    private int i = 0;

    public ZenithDashTask(Spider spider) {
        this.s = spider;
    }
    @Override
    public void run() {
        if (s.isDead() || !s.isValid()) {
            cancel();
            return;
        }
        LivingEntity target = s.getTarget();
        Location location = s.getLocation();
        if (i < LUNGE_THRESHOLD && target != null && location != null) {
            i++;
        } else if (target != null && location != null) {
            Vector direction = target.getLocation().toVector().subtract(location.toVector()).normalize();
            s.setVelocity(direction.multiply(2.2));
            s.playEffect(EntityEffect.WITCH_MAGIC);
            s.getWorld().playSound(s.getLocation(),Sound.ITEM_FIRECHARGE_USE,10.0F,2.0F);
            s.getLocation().getWorld().spawnParticle(Particle.GUST_EMITTER,s.getLocation(),1,0,0,0,0);
            s.getLocation().getWorld().spawnParticle(Particle.FLASH,s.getLocation(),1,0,0,0,0);
            i = 0;
        }
    }
}
