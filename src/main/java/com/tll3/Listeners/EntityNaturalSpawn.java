package com.tll3.Listeners;

import com.tll3.Lists.CustomEntities.CustomCreeper;
import com.tll3.Lists.CustomEntities.CustomIronGolem;
import com.tll3.Lists.Entities;
import com.tll3.Misc.EntityHelper;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.monster.EntityCreeper;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_20_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftCreeper;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Random;

import static com.tll3.Misc.GenericUtils.*;

public class EntityNaturalSpawn implements Listener {
    public void setCustomMobcap(LivingEntity entity, int maxPerDistance, double multiplier, int distance, int maxPerWorld, boolean withSameName) {
        ArrayList<LivingEntity> nearbyEntities = new ArrayList<>();
        maxPerDistance *= multiplier;
        maxPerWorld *= multiplier;
        if (withSameName) {
            for (LivingEntity nearbyEntity : entity.getLocation().getNearbyEntitiesByType(entity.getClass(), distance, distance, distance)) {

                if (nearbyEntity.getName().equalsIgnoreCase(entity.getName())) nearbyEntities.add(nearbyEntity);
            }
            if (nearbyEntities.size() >= maxPerDistance || entity.getWorld().getEntitiesByClass(entity.getClass()).size() > maxPerWorld) {
                entity.remove();
            }
        } else {
            for (LivingEntity nearbyEntity : entity.getLocation().getNearbyEntitiesByType(entity.getClass(), distance, distance, distance)) {
                nearbyEntities.add(nearbyEntity);
            }
            if (nearbyEntities.size() >= maxPerDistance || entity.getWorld().getEntitiesByClass(entity.getClass()).size() > maxPerWorld) {
                entity.remove();
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void mainSpawnEv(CreatureSpawnEvent e){
        var entity = e.getEntity();
        var reason = e.getSpawnReason();
        var loc = e.getLocation();


        if(entity instanceof CustomCreeper || entity instanceof CustomIronGolem)return;
        if(getDay() >= 5){
            if(loc.getWorld().getEnvironment() == World.Environment.NORMAL && reason == CreatureSpawnEvent.SpawnReason.NATURAL && entity instanceof Enemy){
                if(doRandomChance(1)){
                    e.setCancelled(true);
                    Entities.enrIG((IronGolem) Entities.spawnMob(loc,EntityType.IRON_GOLEM));
                }
            }

        switch (entity.getType()){
            case CREEPER -> {
                if((reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)){
                    Entities.creChr((Creeper) entity);
                }
            }
            case SKELETON -> {
                    if((reason == CreatureSpawnEvent.SpawnReason.TRAP || reason == CreatureSpawnEvent.SpawnReason.JOCKEY))Entities.skeW((Skeleton) entity);
                    if(doRandomChance(35) && reason == CreatureSpawnEvent.SpawnReason.NATURAL){
                        Entities.skeAd((Skeleton) entity);
                    }
            }
            case ZOMBIE -> {
                if(getDay() >= 5){
                    if(doRandomChance(35)){
                        Entities.zNinka((Zombie) entity);
                    }
                }
            }
            case SPIDER -> {
                    chooseRandomSpider1(getDay(), (Spider) entity,e);
            }
            case IRON_GOLEM -> {

                Entities.enrIG((IronGolem) entity);
            }

        }
        }

        if(getDay() >= 15){
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
            e.setCancelled(true);
        }
        }




    }
    public static boolean doRandomChance(int chance){
            Random random = new Random();
            int chancemax = random.nextInt(100);
            return chancemax <= chance;
    }

    public static void chooseRandomSpider1(int day,Spider s,CreatureSpawnEvent e){
        Random random = new Random();
        int chance = random.nextInt(3);
        switch (chance){
            case 0 -> Entities.blackRev(s);
            case 1 -> Entities.adapSp(s);
            case 2 -> {
                e.setCancelled(true);
                CaveSpider cs = (CaveSpider) Entities.spawnMob(e.getLocation(),EntityType.CAVE_SPIDER);
                Entities.termite(cs);
            }
        }
    }

 }

