package com.tll3.Listeners;

import com.tll3.Lists.CustomEntities.CustomCreeper;
import com.tll3.Lists.Entities;
import com.tll3.Misc.EntityHelper;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.monster.EntityCreeper;
import org.bukkit.craftbukkit.v1_20_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftCreeper;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

import static com.tll3.Misc.GenericUtils.*;

public class EntityNaturalSpawn implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void mainSpawnEv(CreatureSpawnEvent e){
        var entity = e.getEntity();
        var reason = e.getSpawnReason();
        var loc = e.getLocation();

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

        if(entity instanceof CustomCreeper)return;
        switch (entity.getType()){
            case CREEPER -> {
                if(getDay() >= 5 && (reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.EGG)){
                    e.setCancelled(true);
                    WorldServer worldServer = ((CraftWorld)loc.getWorld()).getHandle();
                    CustomCreeper cC = new CustomCreeper(worldServer);
                    cC.a_(loc.getX(),loc.getY(),loc.getZ());
                    worldServer.addFreshEntity(cC, CreatureSpawnEvent.SpawnReason.CUSTOM);
                }
            }
            case SKELETON -> {
                if(getDay() >= 5 && (reason == CreatureSpawnEvent.SpawnReason.TRAP || reason == CreatureSpawnEvent.SpawnReason.JOCKEY)){
                    Entities.skeW((Skeleton) entity);
                }
            }
            case ZOMBIE -> {
                if(getDay() >= 5){
                    if(doRandomChance(20)){
                       Entities.zNinka((Zombie) entity);
                    }
                }
            }

        }


    }
    public static boolean doRandomChance(int chance){
            Random random = new Random();
            int chancemax = random.nextInt(100);
            return chancemax <= chance;
        }
 }

