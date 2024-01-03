package com.tll3.Task.Mobs;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Spider;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class SpiderLungeTask extends BukkitRunnable {
    private static final int LUNGE_THRESHOLD = 200;

    private final Spider s;
    private int i = 0;

    public SpiderLungeTask(Spider spider) {
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
            s.setVelocity(direction.multiply(1.5));
            i = 0;
        }
    }
}

