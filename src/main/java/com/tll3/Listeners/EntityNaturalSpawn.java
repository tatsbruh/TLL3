package com.tll3.Listeners;

import com.tll3.Lists.Entities;
import com.tll3.Misc.EntityHelper;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.potion.PotionEffectType;
import org.eclipse.sisu.Priority;

import java.util.PriorityQueue;

public class EntityNaturalSpawn implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void mainSpawnEv(CreatureSpawnEvent e){
        var entity = e.getEntity();
        var reason = e.getSpawnReason();
        var loc = e.getLocation();
        if(entity instanceof Pig){
            PiglinBrute pg = (PiglinBrute) Entities.spawnMob(loc, EntityType.PIGLIN_BRUTE);
            pg.setImmuneToZombification(true);
            e.setCancelled(true);
        }
        if(entity instanceof Cow){
            Ravager r = (Ravager) Entities.spawnMob(loc,EntityType.RAVAGER);
            r.setCanJoinRaid(false);
            e.setCancelled(true);
        }
        if(entity instanceof Sheep){
            Blaze c = (Blaze) Entities.spawnMob(loc,EntityType.BLAZE);
            e.setCancelled(true);
        }
        if(entity instanceof Chicken){
            Silverfish c = (Silverfish)Entities.spawnMob(loc,EntityType.SILVERFISH);
            EntityHelper.addPotionEffect(c, PotionEffectType.SPEED,2);
            e.setCancelled(true);
        }
        if(entity instanceof Horse || entity instanceof Mule || entity instanceof Donkey){
            SkeletonHorse h = (SkeletonHorse) Entities.spawnMob(loc,EntityType.SKELETON_HORSE);
            h.setTrapped(true);
        }
    }

}
