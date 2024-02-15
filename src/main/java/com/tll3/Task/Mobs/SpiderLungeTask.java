package com.tll3.Task.Mobs;

import com.tll3.Lists.Entities;
import com.tll3.Misc.GenericUtils;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Silverfish;
import org.bukkit.entity.Spider;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class SpiderLungeTask extends BukkitRunnable {
    private static final int LUNGE_THRESHOLD = 200;

    private final Spider s;
    private boolean PRIMITIVE = false;
    private int i = 0;

    public SpiderLungeTask(Spider spider, boolean primitive) {
        this.s = spider;
        this.PRIMITIVE = primitive;
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
            if(PRIMITIVE){
                s.setVelocity(direction.multiply(-1.5));
                if(GenericUtils.getRandomValue(100) >= 85){
                    for(int i = 0; i < 4;i++){
                        Silverfish r = (Silverfish) Entities.spawnMob(s.getLocation().add(GenericUtils.getRandomValue(5),0,GenericUtils.getRandomValue(5)),EntityType.SILVERFISH);
                        Entities.primSilv(r);
                    }
                }
            }else{
                s.setVelocity(direction.multiply(1.5));
            }
            i = 0;
        }
    }
}

