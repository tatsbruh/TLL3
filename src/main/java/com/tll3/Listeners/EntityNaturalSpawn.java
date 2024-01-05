package com.tll3.Listeners;

import com.tll3.Enviroments.Worlds;
import com.tll3.Lists.CustomEntities.*;
import com.tll3.Lists.Entities;
import com.tll3.Misc.EntityHelper;
import com.tll3.Misc.GenericUtils;
import com.tll3.Misc.ItemBuilder;
import com.tll3.TLL3;
import com.tll3.Task.Mobs.ArqBlockBreak;
import com.tll3.Task.Mobs.SpiderLungeTask;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.level.block.InfestedRotatedPillarBlock;
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
import org.bukkit.potion.PotionEffectType;

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
        || entity instanceof CustomChicken || entity instanceof CustomAllay
        || entity instanceof CustomFox || entity instanceof CustomMooshroom || entity instanceof CustomPanda
        || entity instanceof CustomPolarBear || entity instanceof CustomSniffer || entity instanceof CustomAxolotls
        )return;
        spawnWasteyard(e,loc);
        if(getDay() >= 5) {
            if ( reason == CreatureSpawnEvent.SpawnReason.NATURAL && (entity instanceof Enemy && !(entity instanceof WaterMob))) {
                if(doRandomChance(50) && getMonsoon_active().equalsIgnoreCase("true")){
                    EntityHelper.addPotionEffect(entity, PotionEffectType.SPEED,1);
                    EntityHelper.addPotionEffect(entity, PotionEffectType.DAMAGE_RESISTANCE,1);
                }

                if(loc.getWorld().getEnvironment() == World.Environment.NORMAL){
                if (doRandomChance(1)) {
                    e.setCancelled(true);
                    Entities.enrIG((IronGolem) Entities.spawnMob(loc, EntityType.IRON_GOLEM));
                }
                }

            }

        }
        switch (entity.getType()){
            case ZOMBIE -> {
                if((reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)) {
                if(getDay() >= 5) {
                    var random = getRandomValue(100);
                    if (random <= 45) {
                        chooseZombieClass1((Zombie) entity);
                    } else if (random > 45 && random <= 70) {
                        Entities.revZombie((Zombie) entity);
                    }
                }
                }
            }
            case SKELETON -> {
                if((reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)) {
                    if(getDay() >= 5){
                        var random = getRandomValue(100);
                        if (random <= 45) {
                            chooseSkeletonClass1((Skeleton) entity);
                        } else if (random > 45 && random <= 70) {
                            Entities.revSkeleton((Skeleton) entity);
                        }else{
                            if(getMonsoon_active().equalsIgnoreCase("true")){
                                EntityHelper.setMainHand(entity,new ItemBuilder(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE,9).build());
                            }else{
                                EntityHelper.setMainHand(entity,new ItemBuilder(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE,4).build());
                            }
                        }
                    }
                }
                if(reason == CreatureSpawnEvent.SpawnReason.JOCKEY || reason == CreatureSpawnEvent.SpawnReason.TRAP){
                    if(getDay() >= 5){
                    Entities.skeW((Skeleton) entity);
                }
                }
            }
            case SILVERFISH -> {
                if((reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND ||
                        reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG || reason == CreatureSpawnEvent.SpawnReason.SILVERFISH_BLOCK)) {
                    Entities.silverday5((Silverfish) entity);
                }
            }
            case SPIDER -> {
                if((reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)) {
                    if(getDay() >= 5){
                        var random = getRandomValue(100);
                        if (random <= 45) {
                            Entities.revSpider((Spider) entity);
                            new SpiderLungeTask((Spider) entity).runTaskTimer(TLL3.getInstance(),0L,1L);
                        } else {
                            chooseRandomSpider1((Spider) entity,e);
                        }
                    }
                }
            }
            case CREEPER -> {
                if((reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)) {
                    if(getDay() >= 5){
                        var random = getRandomValue(100);
                        if(random <= 35){
                            Entities.revCreeper((Creeper) entity);
                        }else{
                            Entities.creChr((Creeper) entity);
                        }
                    }
                }
            }
            case ENDERMAN -> {
                if((reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)) {
                    if(getDay() >= 5){
                        var random = getRandomValue(100);
                        if(random <= 35){
                            Entities.revEnderman((Enderman) entity);
                        }
                    }
                }
            }
            case PHANTOM -> {
                if((reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)) {
                    if(getDay() >= 5){
                        Entities.phanD((Phantom) entity);
                    }
                }
            }
            case DROWNED -> {
                if((reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)) {
                    if(getDay() >= 5){
                        if(getMonsoon_active().equalsIgnoreCase("true")){
                            Entities.drowAby((Drowned) entity);
                        }
                    }
                }
            }
            case IRON_GOLEM ->{ if(getDay() >= 5)Entities.enrIG((IronGolem) entity);}
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
            case AXOLOTL -> {
                if((reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)){
                    e.setCancelled(true);
                    WorldServer worldServer = ((CraftWorld)loc.getWorld()).getHandle();
                    CustomAxolotls r = new CustomAxolotls(worldServer);
                    r.a_(loc.getX(),loc.getY(),loc.getZ());
                    worldServer.addFreshEntity(r, CreatureSpawnEvent.SpawnReason.CUSTOM);
                }
            }
            case FOX -> {
                if((reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)){
                    e.setCancelled(true);
                    WorldServer worldServer = ((CraftWorld)loc.getWorld()).getHandle();
                    CustomFox r = new CustomFox(worldServer);
                    r.a_(loc.getX(),loc.getY(),loc.getZ());
                    worldServer.addFreshEntity(r, CreatureSpawnEvent.SpawnReason.CUSTOM);
                }
            }
            case SNIFFER -> {
                if((reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)){
                    e.setCancelled(true);
                    WorldServer worldServer = ((CraftWorld)loc.getWorld()).getHandle();
                    CustomSniffer r = new CustomSniffer(worldServer);
                    r.a_(loc.getX(),loc.getY(),loc.getZ());
                    worldServer.addFreshEntity(r, CreatureSpawnEvent.SpawnReason.CUSTOM);
                }
            }
            case POLAR_BEAR -> {
                if((reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)){
                    e.setCancelled(true);
                    WorldServer worldServer = ((CraftWorld)loc.getWorld()).getHandle();
                    CustomPolarBear r = new CustomPolarBear(worldServer);
                    r.a_(loc.getX(),loc.getY(),loc.getZ());
                    worldServer.addFreshEntity(r, CreatureSpawnEvent.SpawnReason.CUSTOM);
                }
            }
            case PANDA -> {
                if((reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)){
                    e.setCancelled(true);
                    WorldServer worldServer = ((CraftWorld)loc.getWorld()).getHandle();
                    CustomPanda r = new CustomPanda(worldServer);
                    r.a_(loc.getX(),loc.getY(),loc.getZ());
                    worldServer.addFreshEntity(r, CreatureSpawnEvent.SpawnReason.CUSTOM);
                }
            }
            case MUSHROOM_COW -> {
                if((reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)){
                    e.setCancelled(true);
                    WorldServer worldServer = ((CraftWorld)loc.getWorld()).getHandle();
                    CustomMooshroom r = new CustomMooshroom(worldServer);
                    r.a_(loc.getX(),loc.getY(),loc.getZ());
                    worldServer.addFreshEntity(r, CreatureSpawnEvent.SpawnReason.CUSTOM);
                }
            }
            case ZOMBIFIED_PIGLIN -> {
                if((reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)){
                    Entities.enrPig((PigZombie) entity);
                }
            }
        }
    }

    public static boolean doRandomChance(int chance){
            Random random = new Random();
            int chancemax = random.nextInt(100);
            return chancemax <= chance;
    }

    public static void chooseRandomSpider1(Spider s,CreatureSpawnEvent e){
        if(getDay() >= 5){
        Random random = new Random();
        int chance = random.nextInt(3);
        switch (chance) {
            case 0 -> Entities.blackRev(s);
            case 1 -> Entities.adapSp(s);
            case 2 -> {
                e.setCancelled(true);
                CaveSpider cs = (CaveSpider) Entities.spawnMob(e.getLocation(), EntityType.CAVE_SPIDER);
                if (Objects.equals(GenericUtils.getMonsoon_active(), "true")) {
                    Entities.csTerCol(cs);
                } else {
                    Entities.termite(cs);
                }
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
    public static void chooseSkeletonClass1(Skeleton w){
        if(getDay() >= 5){
        Random random = new Random();
        int chance = random.nextInt(3);
        switch (chance){
            case 0 -> Entities.skeAd(w);
            case 1 -> Entities.skeFi(w);
            case 2 -> Entities.skeRz(w);
        }
        }
    }
    public static void chooseZombieClass1(Zombie z){
        if(getDay() >= 5) {
            Random random = new Random();
            int chance = random.nextInt(2);
            switch (chance) {
                case 0 -> Entities.zNinka(z);
                case 1 -> {
                    Entities.zArqueo(z);
                    new ArqBlockBreak(z).runTaskTimer(TLL3.getInstance(), 20L, 35L);
                }
            }
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

