package com.tll3.Task.Mobs;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

//This is from @Duckelekuuk
public class HomingTask extends BukkitRunnable {

    private Entity target;
    private Entity arrow;
    public HomingTask(Entity arrow){
        this.arrow = arrow;
    }
    @Override
    public void run() {
        if (target == null) seekTarget();
        if (arrow.isDead() || target.isDead() || arrow.isOnGround()) {cancel();return;}
        Vector newVector = target.getBoundingBox().getCenter().subtract(arrow.getLocation().toVector()).normalize();
        arrow.setVelocity(newVector);
    }
    private void seekTarget() {
        List<Entity> nearbyEntities = arrow.getNearbyEntities(20, 20, 20);
        if (nearbyEntities.size() == 0)
            cancel();
        Optional<Entity> optionalEntity = nearbyEntities.stream()
                .filter(entity -> entity instanceof Player)
                .min(Comparator.comparing(entity -> entity.getLocation().distanceSquared(arrow.getLocation())));

        if (!optionalEntity.isPresent())
            cancel();
        target = optionalEntity.get();
    }
}
