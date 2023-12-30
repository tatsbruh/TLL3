package com.tll3.Listeners;

import com.tll3.Enviroments.Worlds;
import com.tll3.Lists.CustomEntities.CustomAllay;
import com.tll3.Lists.CustomEntities.CustomChicken;
import com.tll3.Lists.CustomEntities.CustomCreeper;
import com.tll3.Lists.CustomEntities.CustomIronGolem;
import com.tll3.Lists.CustomEntities.CustomParrot;
import com.tll3.Lists.Entities;
import com.tll3.Misc.EntityHelper;
import com.tll3.Misc.GenericUtils;
import com.tll3.Misc.ItemBuilder;
import net.minecraft.server.level.WorldServer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_20_R3.CraftWorld;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.ArrayList;
import java.util.Objects;
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
        if(entity instanceof CustomCreeper || entity instanceof CustomIronGolem
        || entity instanceof CustomChicken || entity instanceof CustomAllay)return;
        spawnWasteyard(e,loc);
        if(getDay() >= 5){
            if(loc.getWorld().getEnvironment() == World.Environment.NORMAL && reason == CreatureSpawnEvent.SpawnReason.NATURAL && (entity instanceof Enemy && !(entity instanceof WaterMob))){
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
                    if(reason == CreatureSpawnEvent.SpawnReason.NATURAL){
                    if(doRandomChance(35)){
                        Entities.skeAd((Skeleton) entity);
                    }else{
                        EntityHelper.setMainHand(entity,new ItemBuilder(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE,4).build());
                    }
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
                    chooseRandomSpider1((Spider) entity,e);
            }
            case IRON_GOLEM -> {
                Entities.enrIG((IronGolem) entity);
            }
            case CHICKEN ->  {
                if((reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)){
                    e.setCancelled(true);
                    WorldServer worldServer = ((CraftWorld)loc.getWorld()).getHandle();
                    CustomChicken customChicken = new CustomChicken(worldServer);
                    customChicken.a_(loc.getX(),loc.getY(),loc.getZ());
                    worldServer.addFreshEntity(customChicken, CreatureSpawnEvent.SpawnReason.CUSTOM);
                }
            }
            case MULE -> {
                SkeletonHorse h = (SkeletonHorse) Entities.spawnMob(loc,EntityType.SKELETON_HORSE);
                h.setTrapped(true);
                e.setCancelled(true);
            }
            case WITHER_SKELETON -> chooseWitherSkeletonClass1((WitherSkeleton) entity);
            case HUSK -> {
                if(reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)Entities.huStr((Husk) entity);
            }
            case CAVE_SPIDER -> {
                if(reason == CreatureSpawnEvent.SpawnReason.SPAWNER || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)Entities.csTerCol((CaveSpider) entity);
            }
            case GHAST -> {
                if(reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)Entities.gPower((Ghast) entity);
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
        if(entity instanceof Horse || entity instanceof Donkey){
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

    public static void chooseRandomSpider1(Spider s,CreatureSpawnEvent e){
        Random random = new Random();
        int chance = random.nextInt(3);
        switch (chance){
            case 0 -> Entities.blackRev(s);
            case 1 -> Entities.adapSp(s);
            case 2 -> {
                e.setCancelled(true);
                CaveSpider cs = (CaveSpider) Entities.spawnMob(e.getLocation(),EntityType.CAVE_SPIDER);
                if(Objects.equals(GenericUtils.getMonsoon_active(), "true")){
                    Entities.csTerCol(cs);
                }else{
                    Entities.termite(cs);
                }
            }

        }
    }

    public static void chooseWitherSkeletonClass1(WitherSkeleton w){
        Random random = new Random();
        int chance = random.nextInt(4);
        switch (chance){
            case 0 -> Entities.wsM(w);
            case 1 -> Entities.wsR(w);
            case 2 -> Entities.wsT(w);
            case 3 -> Entities.wsW(w);
        }
    }

    public static void spawnWasteyard(CreatureSpawnEvent e, Location loc){
        World w = Worlds.getWasteyard();
        if(e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.NATURAL && loc.getWorld().getName().equalsIgnoreCase("world_wasteyard")){
            e.setCancelled(true);
            if(w.getLivingEntities().size() > 140)return;
            Random random = new Random();
            int lol = random.nextInt(101);
            if(lol <= 45){
                PiglinBrute s = (PiglinBrute) Entities.spawnMob(loc,EntityType.PIGLIN_BRUTE);
                Entities.scBrute(s);
            }
            if (lol > 45 && lol <= 50) {
                WorldServer worldServer = ((CraftWorld)loc.getWorld()).getHandle();
                CustomParrot c = new CustomParrot(worldServer);
                c.a_(loc.getX(),loc.getY(),loc.getZ());
                worldServer.addFreshEntity(c, CreatureSpawnEvent.SpawnReason.CUSTOM);
            }
            if(lol > 50 && lol <= 65){
                Pillager p = (Pillager)Entities.spawnMob(loc,EntityType.PILLAGER);
                Entities.lostScav(p);
            }
            if(lol > 65 && lol <= 80){
                MagmaCube c = (MagmaCube) Entities.spawnMob(loc,EntityType.MAGMA_CUBE);
                Entities.toxcrawl(c);
            }
            if(lol > 80 && lol <= 84){
                Ghast g = (Ghast) Entities.spawnMob(loc,EntityType.GHAST);
                Entities.soulVg(g);
            }
            if(lol >= 85){
                Creeper c = (Creeper) Entities.spawnMob(loc,EntityType.CREEPER);
                Entities.rustwalker(c);
            }
        }
    }

 }

