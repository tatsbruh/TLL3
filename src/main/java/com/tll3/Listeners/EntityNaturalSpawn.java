package com.tll3.Listeners;

import com.tll3.Enviroments.Worlds;
import com.tll3.Lists.CustomEntities.*;
import com.tll3.Lists.CustomEntities.CustomProjectiles.CustomEvokerFangs;
import com.tll3.Lists.CustomEntities.CustomProjectiles.CustomLlamaSpit;
import com.tll3.Lists.CustomEntities.Others.BetterCreeper;
import com.tll3.Lists.CustomEntities.Others.BetterEnderman;
import com.tll3.Lists.CustomEntities.Others.MiniCyclone;
import com.tll3.Lists.Entities;
import com.tll3.Misc.ChatUtils;
import com.tll3.Misc.DataManager.Data;
import com.tll3.Misc.EntityHelper;
import com.tll3.Misc.GenericUtils;
import com.tll3.Misc.ItemBuilder;
import com.tll3.TLL3;
import com.tll3.Task.Mobs.ArqBlockBreak;
import com.tll3.Task.Mobs.SpiderLungeTask;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.level.block.InfestedRotatedPillarBlock;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.craftbukkit.v1_20_R3.CraftWorld;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import static com.tll3.Misc.EntityHelper.*;
import static com.tll3.Misc.EntityHelper.setBoots;
import static com.tll3.Misc.GenericUtils.*;

public class EntityNaturalSpawn implements Listener {
    public static void setCustomMobcap(LivingEntity entity, int maxPerDistance, double multiplier, int distance, int maxPerWorld, boolean withSameName) {
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
        || entity instanceof CustomDolphin || entity instanceof CustomLlama || entity instanceof CustomGoat
        || entity instanceof CustomBee || entity instanceof CustomEvoker || entity instanceof CustomEvokerFangs
        || entity instanceof CustomLlamaSpit || entity instanceof CustomPig
        || entity instanceof MiniCyclone || entity instanceof BetterCreeper || entity instanceof BetterEnderman)return;
        if(getDay() >= 14) spawnWasteyard(e,loc);
        if(getDay() >= 21) {
            spawnZero(e, loc);
            spawnEnd(e, loc);
        }
        if(getDay() >= 7) {
            if ( reason == CreatureSpawnEvent.SpawnReason.NATURAL && (entity instanceof Enemy && !(entity instanceof WaterMob))) {
                if(doRandomChance(35) &&  getMonsoon_active().equalsIgnoreCase("true")){
                    if(getDay() >= 7 && getDay() < 21){
                        EntityHelper.addPotionEffect(entity, PotionEffectType.SPEED,1);
                        EntityHelper.addPotionEffect(entity, PotionEffectType.DAMAGE_RESISTANCE,0);
                    }else if (getDay() >= 21 && getDay() < 28){
                        EntityHelper.addPotionEffect(entity, PotionEffectType.SPEED,2);
                        EntityHelper.addPotionEffect(entity, PotionEffectType.INCREASE_DAMAGE,3);
                        EntityHelper.addPotionEffect(entity, PotionEffectType.DAMAGE_RESISTANCE,1);
                    }else if (getDay() >= 28 && getDay() < 35){
                        EntityHelper.addPotionEffect(entity, PotionEffectType.SPEED,2);
                        EntityHelper.addPotionEffect(entity, PotionEffectType.INCREASE_DAMAGE,4);
                        EntityHelper.addPotionEffect(entity, PotionEffectType.DAMAGE_RESISTANCE,1);
                    }
                }
                if(getMonsoon_active().equalsIgnoreCase("true")){
                    if(getDay() >= 28){
                        EntityHelper.addPotionEffect(entity,PotionEffectType.FIRE_RESISTANCE,0);
                    }
                }
                if(getTyphoonactive().equalsIgnoreCase("true")){
                    EntityHelper.addPotionEffect(entity,PotionEffectType.REGENERATION,1);
                }
                if(loc.getWorld().getEnvironment() == World.Environment.NETHER){
                    EntityHelper.addPotionEffect(entity,PotionEffectType.INCREASE_DAMAGE,1);
                }

            }
        }
        if(getDay() >= 7 && getDay() < 14){
            if ( reason == CreatureSpawnEvent.SpawnReason.NATURAL && (entity instanceof Enemy && !(entity instanceof WaterMob))) {
                if (loc.getWorld().getName().equalsIgnoreCase("world")) {
                    if (doRandomChance(1)) {
                        e.setCancelled(true);
                        IronGolem ironGolem = (IronGolem) Entities.spawnMob(loc, EntityType.IRON_GOLEM);
                        Entities.enrIG(ironGolem);
                        setCustomMobcap(ironGolem, 3, 1.10, 24, 20, true);
                    }
                }
            }
        }else if(getDay() >= 14 && getDay() < 21){
            if ( reason == CreatureSpawnEvent.SpawnReason.NATURAL && (entity instanceof Enemy && !(entity instanceof WaterMob))) {
                if (loc.getWorld().getName().equalsIgnoreCase("world")) {
                    if (doRandomChance(3)) {
                        summonnewmob(loc, e);
                    }
                }
            }
        }else if(getDay() >= 21 && getDay() < 28){
            if ( reason == CreatureSpawnEvent.SpawnReason.NATURAL && (entity instanceof Enemy && !(entity instanceof WaterMob))) {
                if (loc.getWorld().getName().equalsIgnoreCase("world")) {
                    if (doRandomChance(4)) {
                        summonnewmob(loc, e);
                    }
                }
                if(loc.getWorld().getName().equalsIgnoreCase("world_nether")){
                    if(getMonsoon_active().equalsIgnoreCase("true")){
                        if(doRandomChance(5)){
                            spawnNetherWasteyard(e,loc);
                        }
                    }
                }
            }
        }else if(getDay() >= 28 && getDay() < 35){
            if ( reason == CreatureSpawnEvent.SpawnReason.NATURAL && (entity instanceof Enemy && !(entity instanceof WaterMob))) {
                if (loc.getWorld().getName().equalsIgnoreCase("world")) {
                    if (doRandomChance(4)) {
                        summonnewmob(loc, e);
                    }
                }
                if(loc.getWorld().getName().equalsIgnoreCase("world_nether")){
                    if(getMonsoon_active().equalsIgnoreCase("true")){
                        if(doRandomChance(5)){
                            spawnNetherWasteyard(e,loc);
                        }
                    }
                }
            }
        }else if(getDay() >= 35 && getDay() < 42){
            if ( reason == CreatureSpawnEvent.SpawnReason.NATURAL && (entity instanceof Enemy && !(entity instanceof WaterMob))) {
                if (loc.getWorld().getName().equalsIgnoreCase("world")) {
                    if (doRandomChance(7)) {
                        summonnewmob(loc, e);
                    }
                }
                if(loc.getWorld().getName().equalsIgnoreCase("world_nether")){
                    int random = GenericUtils.getRandomValue(100);
                    if(random <= 5){
                        spawnNetherWasteyard(e,loc);
                    }else if(random > 5 && random <= 12){
                        spawnOverworldMobs(loc,e);
                    }
                }
            }
        }
        switch (entity.getType()){
            case WITHER -> {
                if(reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.BUILD_WITHER){
                    if(loc.getWorld().getName().equalsIgnoreCase("world_wasteyard")){
                        Wither w = (Wither) entity;
                        Entities.ashenWither(w);
                    }
                }
            }
            case ZOMBIE -> {
                if((reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG || reason == CreatureSpawnEvent.SpawnReason.SPAWNER)) {
                if(getDay() >= 7) {
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
                if((reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG || reason == CreatureSpawnEvent.SpawnReason.SPAWNER)) {
                    if(getDay() >= 7 && getDay() < 21){
                        var random = getRandomValue(100);
                        if (random <= 45) {
                            chooseSkeletonClass1((Skeleton) entity);
                        } else if (random > 45 && random <= 70) {
                            Entities.revSkeleton((Skeleton) entity);
                        }else{
                            if(getMonsoon_active().equalsIgnoreCase("true")){
                                EntityHelper.setMainHand(entity,new ItemBuilder(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE,10).build());
                            }else{
                                EntityHelper.setMainHand(entity,new ItemBuilder(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE,5).build());
                            }
                        }
                        if(getMonsoon_active().equalsIgnoreCase("true") && getDay() >= 14){
                            Spider s = (Spider) Entities.spawnMob(loc,EntityType.SPIDER);
                            spiderMount(s);
                            s.addPassenger(entity);
                        }
                    }else if(getDay() >= 21){
                        var random = getRandomValue(100);
                        if (random <= 45) {
                            chooseSkeletonClass1((Skeleton) entity);
                        } else if (random > 45 && random <= 65) {
                            Entities.revSkeleton((Skeleton) entity);
                        }else if(random > 65 && random <= 80){
                            Entities.steelrailgunner((Skeleton) entity);
                        }else{
                            EntityHelper.setMainHand(entity,new ItemBuilder(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE,30).build());
                        }
                        if(getDay() < 28){
                            if(getMonsoon_active().equalsIgnoreCase("true")){
                                Spider s = (Spider) Entities.spawnMob(loc,EntityType.SPIDER);
                                spiderMount(s);
                                s.addPassenger(entity);
                            }
                        }else{
                            Spider s = (Spider) Entities.spawnMob(loc,EntityType.SPIDER);
                            spiderMount(s);
                            s.addPassenger(entity);
                        }
                        //EntityHelper.setOffhand(entity,harmArrow());
                    }
                }
                if(reason == CreatureSpawnEvent.SpawnReason.JOCKEY || reason == CreatureSpawnEvent.SpawnReason.TRAP) {
                    if (getDay() >= 7) {
                        Entities.skeW((Skeleton) entity);
                    }
                }
            }
            case SILVERFISH -> {
                if((reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND ||
                        reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG || reason == CreatureSpawnEvent.SpawnReason.SILVERFISH_BLOCK || reason == CreatureSpawnEvent.SpawnReason.SPAWNER)) {
                    if (getDay() >= 7) {
                        Entities.silverday5((Silverfish) entity);
                    }
                }
            }
            case SPIDER -> {
                if((reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG || reason == CreatureSpawnEvent.SpawnReason.SPAWNER)) {
                    if(getDay() >= 7){
                        var random = getRandomValue(100);
                        if (random <= 45) {
                            Entities.revSpider((Spider) entity);
                            new SpiderLungeTask((Spider) entity,false).runTaskTimer(TLL3.getInstance(),0L,1L);
                        } else {
                            chooseRandomSpider1((Spider) entity,e);
                        }
                    }
                }
            }
            case CREEPER -> {
                if((reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG || reason == CreatureSpawnEvent.SpawnReason.SPAWNER)) {
                    if(getDay() >= 7){
                        if(getDay() < 35) {
                            var random = getRandomValue(100);
                            if (random <= 35) {
                                Entities.revCreeper((Creeper) entity);
                            } else if (random > 35 && random <= 65 && getDay() >= 14) {
                                Entities.unstCr((Creeper) entity);
                            } else if (random > 65 && random <= 86 && getDay() >= 21) {
                                Entities.titaniumCreeper((Creeper) entity);
                            } else if (random > 86 && random <= 90) {
                                Entities.creeperTower((Creeper) entity);
                            } else {
                                Entities.creChr((Creeper) entity);
                            }
                        }else if(getDay() >= 35){
                            chooseNewCreeper((Creeper) entity,e);
                        }
                    }
                }
            }
            case ENDERMAN -> {
                if((reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG|| reason == CreatureSpawnEvent.SpawnReason.SPAWNER)) {
                    if(getDay() >= 7){
                        var random = getRandomValue(100);
                        if(random <= 35){
                            Entities.revEnderman((Enderman) entity);
                        }else if(random > 35 && random <= 55 && getDay() >= 21){
                            Entities.cyberpunk((Enderman) entity);
                        }else if(random > 55 && random <= 70 && getDay() >= 35){
                            e.setCancelled(true);
                            WorldServer worldServer = ((CraftWorld) loc.getWorld()).getHandle();
                            BetterEnderman r = new BetterEnderman(worldServer);
                            r.a_(loc.getX(), loc.getY(), loc.getZ());
                            worldServer.addFreshEntity(r, CreatureSpawnEvent.SpawnReason.CUSTOM);
                        }
                    }
                }
            }
            case PHANTOM -> {
                if((reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG|| reason == CreatureSpawnEvent.SpawnReason.SPAWNER)) {
                    if(getDay() >= 7){
                        if(getDay() >= 28){
                            if(doRandomChance(10)){
                                entity.remove();
                                Entities.entropicDem((Ghast) Entities.spawnMob(loc,EntityType.GHAST));
                            }else{
                                Entities.phanD((Phantom) entity);
                            }
                        }else{
                            Entities.phanD((Phantom) entity);
                        }
                    }
                }
            }
            case DROWNED -> {
                if((reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG|| reason == CreatureSpawnEvent.SpawnReason.SPAWNER)) {
                    if(getDay() >= 7 && getDay() < 14){
                        if(getMonsoon_active().equalsIgnoreCase("true")){
                            Entities.drowAby((Drowned) entity);
                        }
                    }else if(getDay() >= 14){
                        Entities.drowAby((Drowned) entity);
                    }
                }
            }
            case IRON_GOLEM ->{ if(getDay() >= 7)Entities.enrIG((IronGolem) entity);}
            case CHICKEN ->  {
                if((reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG || reason == CreatureSpawnEvent.SpawnReason.JOCKEY)){
                    if(getDay() >= 7){
                    e.setCancelled(true);
                    WorldServer worldServer = ((CraftWorld)loc.getWorld()).getHandle();
                    CustomChicken customChicken = new CustomChicken(worldServer);
                    customChicken.a_(loc.getX(),loc.getY(),loc.getZ());
                    worldServer.addFreshEntity(customChicken, CreatureSpawnEvent.SpawnReason.CUSTOM);
                    }
                }
            }
            case BEE -> {
                if((reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG || reason == CreatureSpawnEvent.SpawnReason.BEEHIVE)){
                    if(getDay() >= 14){
                        e.setCancelled(true);
                        WorldServer worldServer = ((CraftWorld)loc.getWorld()).getHandle();
                        CustomBee r= new CustomBee(worldServer);
                        r.a_(loc.getX(),loc.getY(),loc.getZ());
                        worldServer.addFreshEntity(r, CreatureSpawnEvent.SpawnReason.CUSTOM);
                    }
                }
            }
            case CAT,WOLF -> {
                if ((reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)) {
                    if (getDay() >= 14) {
                        e.setCancelled(true);
                        Entities.unstCr((Creeper) Entities.spawnMob(loc,EntityType.CREEPER));
                    }
                }
            }
            case ALLAY -> {
                if ((reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)) {
                    if (getDay() >= 21) {
                        e.setCancelled(true);
                        Entities.nwVex((Vex) Entities.spawnMob(loc,EntityType.VEX));
                    }
                }
            }
            case ENDERMITE -> {
                if ((reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG || reason == CreatureSpawnEvent.SpawnReason.ENDER_PEARL)) {
                    if (getDay() >= 14) {
                        Entities.quanmite((Endermite) entity);
                    }
                }
            }
            case WITCH -> {
                if ((reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG || reason == CreatureSpawnEvent.SpawnReason.SPAWNER)) {
                    if (getDay() >= 14) {
                        if(doRandomChance(35)){
                            chooserandomraider(loc,e);
                        }
                    }
                }
            }
            case PILLAGER -> {
                if ((reason == CreatureSpawnEvent.SpawnReason.RAID || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG || reason == CreatureSpawnEvent.SpawnReason.SPAWNER)) {
                    if (getDay() >= 14) {
                       Entities.nwPillager((Pillager) entity,true);
                    }
                }
            }
            case VINDICATOR -> {
                if ((reason == CreatureSpawnEvent.SpawnReason.RAID || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG || reason == CreatureSpawnEvent.SpawnReason.SPAWNER)) {
                    if (getDay() >= 14) {
                        Entities.nwVindicator((Vindicator) entity,true);
                    }
                }
            }
            case RAVAGER -> {
                if ((reason == CreatureSpawnEvent.SpawnReason.RAID || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG || reason == CreatureSpawnEvent.SpawnReason.SPAWNER)) {
                    if (getDay() >= 14) {
                        Entities.nwRavager((Ravager) entity,true);
                    }
                }
            }
            case EVOKER -> {
                if ((reason == CreatureSpawnEvent.SpawnReason.RAID || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG || reason == CreatureSpawnEvent.SpawnReason.SPAWNER)) {
                    if (getDay() >= 14) {
                        e.setCancelled(true);
                        WorldServer worldServer = ((CraftWorld) loc.getWorld()).getHandle();
                        CustomEvoker r = new CustomEvoker(worldServer,false);
                        r.a_(loc.getX(), loc.getY(), loc.getZ());
                        worldServer.addFreshEntity(r, CreatureSpawnEvent.SpawnReason.CUSTOM);
                    }
                }
            }
            case GUARDIAN -> {
                if ((reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)) {
                    if (getDay() >= 14) {
                        e.setCancelled(true);
                        WorldServer worldServer = ((CraftWorld) loc.getWorld()).getHandle();
                        CustomGuardian r = new CustomGuardian(worldServer);
                        r.a_(loc.getX(), loc.getY(), loc.getZ());
                        worldServer.addFreshEntity(r, CreatureSpawnEvent.SpawnReason.CUSTOM);
                    }
                }
            }
            case ELDER_GUARDIAN -> {
                if ((reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)) {
                    if (getDay() >= 14) {
                        e.setCancelled(true);
                        WorldServer worldServer = ((CraftWorld) loc.getWorld()).getHandle();
                        CustomElderGuardian r = new CustomElderGuardian(worldServer);
                        r.a_(loc.getX(), loc.getY(), loc.getZ());
                        worldServer.addFreshEntity(r, CreatureSpawnEvent.SpawnReason.CUSTOM);
                    }
                }
            }
            case VEX -> {
                if ((reason == CreatureSpawnEvent.SpawnReason.SPELL)) {
                    Vex v = (Vex) entity;
                    if(v.getSummoner() instanceof Evoker || v.getSummoner() instanceof CustomEvoker){
                        if(Data.has(v.getSummoner(),"evokerex", PersistentDataType.STRING)){
                            Entities.nwVex(v);
                        }
                    }
                }
            }
            case SQUID,GLOW_SQUID -> {
                if ((reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)) {
                    if (getDay() >= 14) {
                        e.setCancelled(true);
                        PufferFish f = (PufferFish) Entities.spawnMob(loc,EntityType.PUFFERFISH);
                        Entities.acidFish(f);
                    }
                }
            }
            case MULE -> {
                if(getDay() >= 7){
                SkeletonHorse h = (SkeletonHorse) Entities.spawnMob(loc,EntityType.SKELETON_HORSE);
                h.setTrapped(true);
                e.setCancelled(true);
                }
            }
            case DONKEY,HORSE -> {
                    if(getDay() >= 14){
                        SkeletonHorse h = (SkeletonHorse) Entities.spawnMob(loc,EntityType.SKELETON_HORSE);
                        h.setTrapped(true);
                        e.setCancelled(true);
                }
            }
            case WITHER_SKELETON -> {
                if(reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG){
                if(getDay() >= 7){ chooseWitherSkeletonClass1((WitherSkeleton) entity);}
                }
            }
            case BLAZE -> {
                if(reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG || reason == CreatureSpawnEvent.SpawnReason.SPAWNER){
                    if(getDay() >= 14)chooseBlazeType((Blaze) entity);
                }
            }
            case HUSK -> { if(getDay() >= 7){
                if(reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG || reason == CreatureSpawnEvent.SpawnReason.SPAWNER)Entities.huStr((Husk) entity);
            }
            }
            case STRAY -> {
                if(reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG || reason == CreatureSpawnEvent.SpawnReason.SPAWNER){
                    if(getDay() >= 14)Entities.strayCom((Stray) entity);
                }
            }
            case SLIME -> {
                if(reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG || reason == CreatureSpawnEvent.SpawnReason.SPAWNER){
                    if(getDay() >= 14)Entities.slimeNight((Slime) entity);
                }
            }
            case CAVE_SPIDER -> { if(getDay() >= 7){
                if(reason == CreatureSpawnEvent.SpawnReason.SPAWNER || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)Entities.csTerCol((CaveSpider) entity);
            }
            }
            case GHAST -> { if(getDay() >= 7){
                if(reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG || reason == CreatureSpawnEvent.SpawnReason.SPAWNER){
                    if(getDay() >= 21){
                        if(doRandomChance(35)){
                            Entities.entropicDem((Ghast) entity);
                        }else{
                            Entities.gPower((Ghast) entity);
                        }
                    }else{
                        Entities.gPower((Ghast) entity);
                    }
                }
            }
            }
            case AXOLOTL -> {
                if((reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)) {
                    if (getDay() >= 7) {
                        e.setCancelled(true);
                        WorldServer worldServer = ((CraftWorld) loc.getWorld()).getHandle();
                        CustomAxolotls r = new CustomAxolotls(worldServer);
                        r.a_(loc.getX(), loc.getY(), loc.getZ());
                        worldServer.addFreshEntity(r, CreatureSpawnEvent.SpawnReason.CUSTOM);
                    }
                }
            }
            case FOX -> {
                if((reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)){
                    if(getDay() >= 7){
                    e.setCancelled(true);
                    WorldServer worldServer = ((CraftWorld)loc.getWorld()).getHandle();
                    CustomFox r = new CustomFox(worldServer);
                    r.a_(loc.getX(),loc.getY(),loc.getZ());
                    worldServer.addFreshEntity(r, CreatureSpawnEvent.SpawnReason.CUSTOM);
                }
                }
            }
            case SNIFFER -> {
                if((reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)){
                    if(getDay() >= 7){
                    e.setCancelled(true);
                    WorldServer worldServer = ((CraftWorld)loc.getWorld()).getHandle();
                    CustomSniffer r = new CustomSniffer(worldServer);
                    r.a_(loc.getX(),loc.getY(),loc.getZ());
                    worldServer.addFreshEntity(r, CreatureSpawnEvent.SpawnReason.CUSTOM);
                }
                }
            }
            case PIG -> {
                if((reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)){
                    if(getDay() >= 21){
                        e.setCancelled(true);
                        PiglinBrute pg = (PiglinBrute) Entities.spawnMob(loc,EntityType.PIGLIN_BRUTE);
                        pg.setImmuneToZombification(true);
                        EntityHelper.addPotionEffect(pg,PotionEffectType.INCREASE_DAMAGE,3);
                        EntityHelper.addPotionEffect(pg,PotionEffectType.SPEED,2);
                        setHead(pg,new ItemBuilder(Material.NETHERITE_HELMET).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,4).build());
                        setChestplate(pg,new ItemBuilder(Material.NETHERITE_CHESTPLATE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,4).build());
                        setLeggings(pg,new ItemBuilder(Material.NETHERITE_LEGGINGS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,4).build());
                        setBoots(pg,new ItemBuilder(Material.NETHERITE_BOOTS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,4).build());
                    }
                }
            }
            case POLAR_BEAR -> {
                if((reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)) {
                    if (getDay() >= 7) {
                        e.setCancelled(true);
                        WorldServer worldServer = ((CraftWorld) loc.getWorld()).getHandle();
                        CustomPolarBear r = new CustomPolarBear(worldServer);
                        r.a_(loc.getX(), loc.getY(), loc.getZ());
                        worldServer.addFreshEntity(r, CreatureSpawnEvent.SpawnReason.CUSTOM);
                    }
                }
            }
            case PANDA -> {
                if((reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)){
                    if(getDay() >= 7){
                    e.setCancelled(true);
                    WorldServer worldServer = ((CraftWorld)loc.getWorld()).getHandle();
                    CustomPanda r = new CustomPanda(worldServer);
                    r.a_(loc.getX(),loc.getY(),loc.getZ());
                    worldServer.addFreshEntity(r, CreatureSpawnEvent.SpawnReason.CUSTOM);
                }
                }
            }
            case MUSHROOM_COW -> {
                if((reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)) {
                    if (getDay() >= 7) {
                        e.setCancelled(true);
                        WorldServer worldServer = ((CraftWorld) loc.getWorld()).getHandle();
                        CustomMooshroom r = new CustomMooshroom(worldServer);
                        r.a_(loc.getX(), loc.getY(), loc.getZ());
                        worldServer.addFreshEntity(r, CreatureSpawnEvent.SpawnReason.CUSTOM);
                    }
                }
            }
            case ZOMBIFIED_PIGLIN -> {
                if((reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)){
                    if(getDay() >= 7 && getDay() < 21){
                    Entities.enrPig((PigZombie) entity);
                    }else if(getDay() >= 21){
                        choosePigmanClass((PigZombie) entity);
                    }
                }
            }
            case PIGLIN -> {
                if((reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)){
                    if(getDay() >= 14){
                        Entities.piglGr((Piglin) entity);
                    }
                }
            }

            case DOLPHIN -> {
                if((reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)) {
                    if (getDay() >= 7) {
                        e.setCancelled(true);
                        WorldServer worldServer = ((CraftWorld) loc.getWorld()).getHandle();
                        CustomDolphin r = new CustomDolphin(worldServer);
                        r.a_(loc.getX(), loc.getY(), loc.getZ());
                        worldServer.addFreshEntity(r, CreatureSpawnEvent.SpawnReason.CUSTOM);
                    }
                }
            }
            case LLAMA -> {
                if((reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)) {
                    if (getDay() >= 14) {
                        e.setCancelled(true);
                        WorldServer worldServer = ((CraftWorld) loc.getWorld()).getHandle();
                        CustomLlama r = new CustomLlama(worldServer);
                        r.a_(loc.getX(), loc.getY(), loc.getZ());
                        worldServer.addFreshEntity(r, CreatureSpawnEvent.SpawnReason.CUSTOM);
                    }
                }
            }
            case GOAT -> {
                if((reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)) {
                    if (getDay() >= 14) {
                        e.setCancelled(true);
                        WorldServer worldServer = ((CraftWorld) loc.getWorld()).getHandle();
                        CustomGoat r = new CustomGoat(worldServer);
                        r.a_(loc.getX(), loc.getY(), loc.getZ());
                        worldServer.addFreshEntity(r, CreatureSpawnEvent.SpawnReason.CUSTOM);
                    }
                }
            }
            case PIGLIN_BRUTE -> {
                if((reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)) {
                    if (getDay() >= 14) {
                        ((PiglinBrute)entity).setImmuneToZombification(true);
                        EntityHelper.addPotionEffect(entity,PotionEffectType.INCREASE_DAMAGE,3);
                        EntityHelper.addPotionEffect(entity,PotionEffectType.SPEED,2);
                        if(getDay() >= 21){
                            setHead(entity,new ItemBuilder(Material.NETHERITE_HELMET).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,4).build());
                            setChestplate(entity,new ItemBuilder(Material.NETHERITE_CHESTPLATE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,4).build());
                            setLeggings(entity,new ItemBuilder(Material.NETHERITE_LEGGINGS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,4).build());
                            setBoots(entity,new ItemBuilder(Material.NETHERITE_BOOTS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,4).build());
                        }
                    }
                }
            }
            case MAGMA_CUBE -> {
                if((reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)) {
                    if(getDay() >= 21){
                        MagmaCube magma = (MagmaCube) entity;
                        int minsize = 10;
                        int maxsize = getRandomValue(6);
                        int result = minsize + maxsize;
                        magma.setSize(result);
                    }
                }
            }
            case STRIDER -> {
                if((reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)) {
                    if (getDay() >= 21) {
                        e.setCancelled(true);
                        Ghast g = (Ghast) Entities.spawnMob(loc,EntityType.GHAST);
                        if(doRandomChance(35)){
                            Entities.entropicDem(g);
                        }else{
                            Entities.gPower(g);
                        }
                    }
                }
            }
            case HOGLIN -> {
                if((reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)) {
                    if (getDay() >= 14) {
                        EntityHelper.addPotionEffect(entity,PotionEffectType.INCREASE_DAMAGE,4);
                        EntityHelper.addPotionEffect(entity,PotionEffectType.SPEED,1);
                        PiglinBrute pg = (PiglinBrute) Entities.spawnMob(loc,EntityType.PIGLIN_BRUTE);
                        EntityHelper.addPotionEffect(pg,PotionEffectType.INCREASE_DAMAGE,3);
                        EntityHelper.addPotionEffect(pg,PotionEffectType.SPEED,2);
                        pg.setImmuneToZombification(true);
                        if(getDay() >= 21){
                            setHead(pg,new ItemBuilder(Material.NETHERITE_HELMET).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,4).build());
                            setChestplate(pg,new ItemBuilder(Material.NETHERITE_CHESTPLATE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,4).build());
                            setLeggings(pg,new ItemBuilder(Material.NETHERITE_LEGGINGS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,4).build());
                            setBoots(pg,new ItemBuilder(Material.NETHERITE_BOOTS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,4).build());
                        }
                        ((Hoglin)entity).setImmuneToZombification(true);
                        entity.addPassenger(pg);
                    }
                }
            }
            case BREEZE -> {
                if(reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG || reason == CreatureSpawnEvent.SpawnReason.COMMAND){
                    if(getDay() >= 14) {
                        if (getDay() >= 35) {
                            chooseAirMob(loc,e);
                        } else {
                            Entities.windTyphoon((Breeze) entity);
                        }
                    }
                }
            }
            case ILLUSIONER -> {
                if(reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.RAID){
                    if(getDay() >= 28){
                        e.setCancelled(true);
                        WorldServer worldServer = ((CraftWorld) loc.getWorld()).getHandle();
                        CustomIllusioner r = new CustomIllusioner(worldServer);
                        r.a_(loc.getX(), loc.getY(), loc.getZ());
                        worldServer.addFreshEntity(r, CreatureSpawnEvent.SpawnReason.CUSTOM);
                    }
                }
            }
            case FROG,TURTLE -> {
                if(reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG || reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND) {
                    if (getDay() >= 28) {
                        e.setCancelled(true);
                        WorldServer worldServer = ((CraftWorld) loc.getWorld()).getHandle();
                        CustomAxolotls r = new CustomAxolotls(worldServer);
                        r.a_(loc.getX(), loc.getY(), loc.getZ());
                        worldServer.addFreshEntity(r, CreatureSpawnEvent.SpawnReason.CUSTOM);
                    }
                }
            }
            case WANDERING_TRADER -> {
                if(reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG || reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND) {
                    if (getDay() >= 28) {
                        e.setCancelled(true);
                        WorldServer worldServer = ((CraftWorld) loc.getWorld()).getHandle();
                        CustomEvoker r = new CustomEvoker(worldServer,false);
                        r.a_(loc.getX(), loc.getY(), loc.getZ());
                        worldServer.addFreshEntity(r, CreatureSpawnEvent.SpawnReason.CUSTOM);
                    }
                }
            }
            case TRADER_LLAMA -> {
                if(reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG || reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND) {
                    if (getDay() >= 28) {
                        e.setCancelled(true);
                        WorldServer worldServer = ((CraftWorld) loc.getWorld()).getHandle();
                        CustomLlama r = new CustomLlama(worldServer);
                        r.a_(loc.getX(), loc.getY(), loc.getZ());
                        worldServer.addFreshEntity(r, CreatureSpawnEvent.SpawnReason.CUSTOM);
                    }
                }
            }
            case RABBIT -> {
                if(reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG || reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND) {
                    if (getDay() >= 28) {
                        Entities.rabbitKiller((Rabbit) entity);
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

    public static void spawnHuskDrownedStray(CreatureSpawnEvent e, Location loc){
        int random = GenericUtils.getRandomValue(3);
        switch (random){
            case 0 ->{
                e.setCancelled(true);
                Drowned d = (Drowned) Entities.spawnMob(loc,EntityType.DROWNED);
                Entities.drowAby(d);
            }
            case 1 ->{
                e.setCancelled(true);
                Husk h = (Husk) Entities.spawnMob(loc,EntityType.HUSK);
                Entities.huStr(h);
            }
            case 2 ->{
                e.setCancelled(true);
                Stray s = (Stray) Entities.spawnMob(loc,EntityType.STRAY);
                Entities.strayCom(s);
            }
        }
    }

    public static void chooseRandomSpider1(Spider s,CreatureSpawnEvent e){
        if(getDay() >= 7 && getDay() < 14){
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
        }else if(getDay() >= 14){
            Random random = new Random();
            int chance = random.nextInt(4);
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
                case 3 -> Entities.neonSp(s);
            }
        }
    }

    public static void spiderMount(Spider s){
        if(getDay() >= 14){
            Random random = new Random();
            int chance = random.nextInt(4);
            switch (chance) {
                case 0 -> Entities.blackRev(s);
                case 1 -> Entities.adapSp(s);
                case 2 -> Entities.neonSp(s);
                case 3 -> Entities.revSpider(s);
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
        if(getDay() >= 7 && getDay() < 14){
        Random random = new Random();
        int chance = random.nextInt(3);
        switch (chance){
            case 0 -> Entities.skeAd(w);
            case 1 -> Entities.skeFi(w);
            case 2 -> Entities.skeRz(w);
        }
        }else if(getDay() >= 14 && getDay() < 35){
                Random random = new Random();
                int chance = random.nextInt(5);
                switch (chance){
                    case 0 ->{ Entities.skeAd(w);}
                    case 1 -> {Entities.skeFi(w);}
                    case 2 -> {Entities.skeRz(w);}
                    case 3 -> Entities.voidOver(w);
                    case 4 -> {Entities.livingSh(w);}
                }
        }else if(getDay() >= 35 ){
            Random random = new Random();
            int chance = random.nextInt(7);
            switch (chance){
                case 0 ->{ Entities.skeAd(w);}
                case 1 -> {Entities.skeFi(w);}
                case 2 -> {Entities.skeRz(w);}
                case 3 -> Entities.voidOver(w);
                case 4 -> {Entities.livingSh(w);}
                case 5 -> Entities.relicSkeleton(w);
                case 6 -> Entities.antiflySkeleton(w);
            }
        }
    }
    public static void chooseZombieClass1(Zombie z){
        if(getDay() >= 7 && getDay() < 28) {
            Random random = new Random();
            int chance = random.nextInt(2);
            switch (chance) {
                case 0 -> Entities.zNinka(z);
                case 1 -> Entities.zArqueo(z);

            }
        }else if(getDay() >= 28 && getDay() < 35) {
            Random random = new Random();
            int chance = random.nextInt(3);
            switch (chance) {
                case 0 -> Entities.zNinka(z);
                case 1 -> Entities.zArqueo(z);
                case 2 -> Entities.lilGhoul(z);
            }
        }else if(getDay() >= 35) {
            Random random = new Random();
            int chance = random.nextInt(4);
            switch (chance) {
                case 0 -> Entities.zNinka(z);
                case 1 -> Entities.zArqueo(z);
                case 2 -> Entities.lilGhoul(z);
                case 3 -> Entities.relicZombie(z);
            }
        }
    }
    public static void chooseBlazeType(Blaze z){
        if(getDay() >= 14 && getDay() < 28) {
            Random random = new Random();
            int chance = random.nextInt(2);
            switch (chance) {
                case 0 -> Entities.windChar(z);
                case 1 -> Entities.armorBlaze(z);

            }
        }else if(getDay() >= 28){
            Random random = new Random();
            int chance = random.nextInt(4);
            switch (chance) {
                case 0 -> Entities.windChar(z);
                case 1 -> Entities.armorBlaze(z);
                case 2 -> Entities.blazephim(z);
                case 3 -> Entities.hellSymbiote(z);

            }
        }
    }
    public static void choosePigmanClass(PigZombie z){
        if(getDay() >= 21){
            Random random = new Random();
            int chance = random.nextInt(3);
            switch (chance) {
                case 0 -> Entities.zombpigAlchemist(z);
                case 1 -> Entities.zombpigRider(z);
                case 2 -> Entities.zombpigShinobi(z);

            }
        }
    }

    public static void spawnWasteyard(CreatureSpawnEvent e, Location loc){
        World w = Worlds.getWasteyard();
        if(e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.NATURAL && loc.getWorld().getName().equalsIgnoreCase("world_wasteyard")){
            if(e.getEntity() instanceof Strider){
                e.setCancelled(true);
                return;
            }
            e.setCancelled(true);
            if(w.getLivingEntities().size() > 70)return;
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
            if(lol > 65 && lol <= 81){
                MagmaCube c = (MagmaCube) Entities.spawnMob(loc,EntityType.MAGMA_CUBE);
                Entities.toxcrawl(c);
            }
            if(lol > 81 && lol <= 84){
                Ghast g = (Ghast) Entities.spawnMob(loc,EntityType.GHAST);
                Entities.soulVg(g);
            }
            if(lol >= 85){
                Creeper c = (Creeper) Entities.spawnMob(loc,EntityType.CREEPER);
                Entities.rustwalker(c);
            }
        }
    }
    public static void spawnZero(CreatureSpawnEvent e, Location loc){
        World w = Worlds.getZero();
        if(e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.NATURAL && loc.getWorld().getName().equalsIgnoreCase("world_zero")){
            e.setCancelled(true);
            if(w.getLivingEntities().size() > 70)return;
            Random random = new Random();
            int lol = random.nextInt(101);
            if(lol <= 45){
                Spider s = (Spider) Entities.spawnMob(loc,EntityType.SPIDER);
                Entities.agileTarantula(s);
            }
            if (lol > 45 && lol <= 57) {
                IronGolem s = (IronGolem) Entities.spawnMob(loc,EntityType.IRON_GOLEM);
                Entities.zombieDestroyer(s);
            }
            if(lol > 57 && lol <= 75){
                Vindicator s = (Vindicator) Entities.spawnMob(loc,EntityType.VINDICATOR);
                Entities.killerscream(s);
            }
            if(lol > 75 && lol <= 88){
                Enderman s = (Enderman) Entities.spawnMob(loc,EntityType.ENDERMAN);
                Entities.blightedEnderman(s);
            }
            if(lol > 88 && lol <= 94){
                Skeleton s = (Skeleton) Entities.spawnMob(loc,EntityType.SKELETON);
                Entities.blightedSkeleton(s);
            }
            if(lol >= 94 && lol < 97){
                Ghast s = (Ghast) Entities.spawnMob(loc,EntityType.GHAST);
                Entities.blightedGhast(s);
            }
            if(lol >= 97){
                Creeper s = (Creeper) Entities.spawnMob(loc,EntityType.CREEPER);
                Entities.vortice(s);
            }
        }
    }
    public static void spawnEnd(CreatureSpawnEvent e, Location loc){
        World w = Worlds.getEnd();
        if(e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.NATURAL && loc.getWorld().getName().equalsIgnoreCase("world_the_end")){
            e.setCancelled(true);
            if(w.getLivingEntities().size() > 70)return;
            Random random = new Random();
            int lol = random.nextInt(101);
            if(lol <= 45){
                Enderman mob = (Enderman) Entities.spawnMob(loc,EntityType.ENDERMAN);
                Entities.starEnderman(mob);
            }
            if (lol > 45 && lol <= 60) {
                WitherSkeleton mob = (WitherSkeleton) Entities.spawnMob(loc,EntityType.WITHER_SKELETON);
                Entities.starWither(mob);
            }
            if(lol > 60 && lol <= 80){
                Pillager mob = (Pillager) Entities.spawnMob(loc,EntityType.PILLAGER);
                Entities.starPillager(mob);
            }
            if(lol > 80 && lol <= 90){
                Creeper mob = (Creeper) Entities.spawnMob(loc,EntityType.CREEPER);
                Entities.starCreeper(mob);
            }
            if(lol > 90){
                WorldServer worldServer = ((CraftWorld) loc.getWorld()).getHandle();
                MiniCyclone r = new MiniCyclone(worldServer, MiniCyclone.CycloneClass.SPACE);
                r.a_(loc.getX(), loc.getY(), loc.getZ());
                worldServer.addFreshEntity(r, CreatureSpawnEvent.SpawnReason.CUSTOM);
            }
        }
    }

    public static void spawnNetherWasteyard(CreatureSpawnEvent e, Location loc){
        if(e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.NATURAL){
            e.setCancelled(true);
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

    public static void chooserandomraider(Location loc,CreatureSpawnEvent e){
        if(getDay() < 28) {
            Random random = new Random();
            int chance = random.nextInt(4);
            switch (chance) {
                case 0 -> {
                    e.setCancelled(true);
                    Pillager p = (Pillager) Entities.spawnMob(loc, EntityType.PILLAGER);
                    Entities.nwPillager(p, false);
                }
                case 1 -> {
                    e.setCancelled(true);
                    Vindicator v = (Vindicator) Entities.spawnMob(loc, EntityType.VINDICATOR);
                    Entities.nwVindicator(v, false);
                }
                case 2 -> {
                    e.setCancelled(true);
                    Ravager r = (Ravager) Entities.spawnMob(loc, EntityType.RAVAGER);
                    Entities.nwRavager(r, false);
                }
                case 3 -> {
                    e.setCancelled(true);
                    WorldServer worldServer = ((CraftWorld) loc.getWorld()).getHandle();
                    CustomEvoker r = new CustomEvoker(worldServer, false);
                    r.a_(loc.getX(), loc.getY(), loc.getZ());
                    worldServer.addFreshEntity(r, CreatureSpawnEvent.SpawnReason.CUSTOM);
                }
            }
        }else if(getDay() >= 28){
            Random random = new Random();
            int chance = random.nextInt(5);
            switch (chance) {
                case 0 -> {
                    e.setCancelled(true);
                    Pillager p = (Pillager) Entities.spawnMob(loc, EntityType.PILLAGER);
                    Entities.nwPillager(p, false);
                }
                case 1 -> {
                    e.setCancelled(true);
                    Vindicator v = (Vindicator) Entities.spawnMob(loc, EntityType.VINDICATOR);
                    Entities.nwVindicator(v, false);
                }
                case 2 -> {
                    e.setCancelled(true);
                    Ravager r = (Ravager) Entities.spawnMob(loc, EntityType.RAVAGER);
                    Entities.nwRavager(r, false);
                }
                case 3 -> {
                    e.setCancelled(true);
                    WorldServer worldServer = ((CraftWorld) loc.getWorld()).getHandle();
                    CustomEvoker r = new CustomEvoker(worldServer, false);
                    r.a_(loc.getX(), loc.getY(), loc.getZ());
                    worldServer.addFreshEntity(r, CreatureSpawnEvent.SpawnReason.CUSTOM);
                }
                case 4 ->{
                    e.setCancelled(true);
                    WorldServer worldServer = ((CraftWorld) loc.getWorld()).getHandle();
                    CustomIllusioner r = new CustomIllusioner(worldServer);
                    r.a_(loc.getX(), loc.getY(), loc.getZ());
                    worldServer.addFreshEntity(r, CreatureSpawnEvent.SpawnReason.CUSTOM);
                }
            }
        }
    }

    public static void netherMobSpawn(Location loc,CreatureSpawnEvent e){
        e.setCancelled(true);
        Random random = new Random();
        int chance = random.nextInt(6);
        switch (chance) {
            case 0 -> { //Zombi Piglins
                PigZombie p = (PigZombie) Entities.spawnMob(loc,EntityType.ZOMBIFIED_PIGLIN);
                choosePigmanClass(p);
            }
            case 1 -> { //Ghasts
                Ghast p = (Ghast) Entities.spawnMob(loc,EntityType.GHAST);
                if(doRandomChance(35)){
                    Entities.entropicDem(p);
                }else{
                    Entities.gPower(p);
                }
            }
            case 2 -> { //Piglins
                Piglin p = (Piglin) Entities.spawnMob(loc,EntityType.PIGLIN);
                Entities.piglGr(p);
            }
            case 3 -> { //Piglin Brutes
                PiglinBrute entity = (PiglinBrute)Entities.spawnMob(loc,EntityType.PIGLIN_BRUTE);
                entity.setImmuneToZombification(true);
                EntityHelper.addPotionEffect(entity,PotionEffectType.INCREASE_DAMAGE,3);
                EntityHelper.addPotionEffect(entity,PotionEffectType.SPEED,2);
                setHead(entity,new ItemBuilder(Material.NETHERITE_HELMET).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,4).build());
                setChestplate(entity,new ItemBuilder(Material.NETHERITE_CHESTPLATE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,4).build());
                setLeggings(entity,new ItemBuilder(Material.NETHERITE_LEGGINGS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,4).build());
                setBoots(entity,new ItemBuilder(Material.NETHERITE_BOOTS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,4).build());
            }
            case 4 -> {//Hoglins
                Hoglin entity = (Hoglin)Entities.spawnMob(loc,EntityType.HOGLIN);
                EntityHelper.addPotionEffect(entity,PotionEffectType.INCREASE_DAMAGE,7);
                EntityHelper.addPotionEffect(entity,PotionEffectType.SPEED,3);
                entity.setImmuneToZombification(true);
            }
            case 5 -> {//Magmacubes
                MagmaCube magma = (MagmaCube) Entities.spawnMob(loc,EntityType.MAGMA_CUBE);
                int minsize = 10;
                int maxsize = getRandomValue(6);
                int result = minsize + maxsize;
                magma.setSize(result);
            }
        }
    }
    public static void spawnOverworldMobs(Location loc,CreatureSpawnEvent e){
            e.setCancelled(true);
            int choice = GenericUtils.getRandomValue(17);
            switch (choice){
                case 0 ->{
                    Zombie z = (Zombie) Entities.spawnMob(loc,EntityType.ZOMBIE);
                    chooseZombieClass1(z);
                }
                case 1 ->{
                    Skeleton s = (Skeleton)Entities.spawnMob(loc,EntityType.SKELETON);
                    chooseSkeletonClass1(s);
                }
                case 2 ->{
                    Spider s = (Spider) Entities.spawnMob(loc,EntityType.SPIDER);
                    spiderMount(s);
                }
                case 3 ->{
                    Creeper c = (Creeper) Entities.spawnMob(loc,EntityType.CREEPER);
                    int type = GenericUtils.getRandomValue(3);
                    switch (type){
                        case 0 ->Entities.creChr(c);
                        case 1 ->Entities.revCreeper(c);
                        case 2 ->Entities.unstCr(c);
                    }
                }
                case 4 ->{
                    Enderman en = (Enderman) Entities.spawnMob(loc,EntityType.ENDERMAN);
                    if(Math.random() < 0.5) {
                        Entities.revEnderman(en);
                    }
                }
                case 5 ->{
                    Drowned d = (Drowned) Entities.spawnMob(loc,EntityType.DROWNED);
                    Entities.drowAby(d);
                }
                case 6 ->{
                    Husk h = (Husk) Entities.spawnMob(loc,EntityType.HUSK);
                    Entities.huStr(h);
                }
                case 7 ->{
                    Stray s = (Stray) Entities.spawnMob(loc,EntityType.STRAY);
                    Entities.strayCom(s);
                }
                case 8 ->{
                    Endermite en = (Endermite) Entities.spawnMob(loc,EntityType.ENDERMITE);
                    Entities.quanmite(en);
                }
                case 9 ->{
                    Silverfish s = (Silverfish) Entities.spawnMob(loc,EntityType.SILVERFISH);
                    Entities.silverday5(s);
                }
                case 10 ->{
                    CaveSpider c = (CaveSpider) Entities.spawnMob(loc,EntityType.CAVE_SPIDER);
                    if(Math.random() < 0.5){
                        Entities.csTerCol(c);
                    }else{
                        Entities.termite(c);
                    }
                }
                case 12 ->{
                    Pillager p = (Pillager) Entities.spawnMob(loc,EntityType.PILLAGER);
                    Entities.nwPillager(p,false);
                }
                case 13 ->{
                    Vindicator v = (Vindicator) Entities.spawnMob(loc,EntityType.VINDICATOR);
                    Entities.nwVindicator(v,false);
                }
                case 14 ->{
                    Ravager r = (Ravager) Entities.spawnMob(loc,EntityType.RAVAGER);
                    Entities.nwRavager(r,false);
                }
                case 15 ->{
                    IronGolem i = (IronGolem) Entities.spawnMob(loc,EntityType.IRON_GOLEM);
                    Entities.enrIG(i);
                }
                case 16 ->{
                    Breeze z = (Breeze) Entities.spawnMob(loc,EntityType.BREEZE);
                    Entities.windTyphoon(z);
                }

            }
        }

        public static void chooseNewCreeper(Creeper c,CreatureSpawnEvent e){
            var loc = c.getLocation();
            var random = getRandomValue(100);
            if(getDay() >= 35 && getDay() < 42){
                if(random <= 30) {
                    Entities.revCreeper(c);
                }else if(random > 30 && random <= 55) {
                    Entities.unstCr(c);
                }else if(random > 55 && random <= 68) {
                    Entities.titaniumCreeper(c);
                }else if(random > 68 && random <= 74) {
                    Entities.creeperTower(c);
                }else if(random > 74 && random <= 83) {
                    Entities.doomsDay(c);
                }else if(random > 83 && random <= 90){
                    e.setCancelled(true);
                    WorldServer worldServer = ((CraftWorld) loc.getWorld()).getHandle();
                    BetterCreeper r = new BetterCreeper(worldServer);
                    r.a_(loc.getX(), loc.getY(), loc.getZ());
                    worldServer.addFreshEntity(r, CreatureSpawnEvent.SpawnReason.CUSTOM);
                }else{
                    Entities.creChr(c);
                }
            }
        }
    public static void chooseAirMob(Location loc,CreatureSpawnEvent e){
        if(getDay() >= 35 && getDay() < 42){
            e.setCancelled(true);
            Random random = new Random();
            int chance = random.nextInt(6);
            switch (chance){
                case 0 ->{
                    Breeze r = (Breeze) Entities.spawnMob(loc,EntityType.BREEZE);
                    Entities.windTyphoon(r);
                }
                case 1 ->{
                    Breeze r = (Breeze) Entities.spawnMob(loc,EntityType.BREEZE);
                    Entities.deathbringer(r);
                }
                case 2 ->{
                    WorldServer worldServer = ((CraftWorld) loc.getWorld()).getHandle();
                    MiniCyclone r = new MiniCyclone(worldServer, MiniCyclone.CycloneClass.ZOMBIE);
                    r.a_(loc.getX(), loc.getY(), loc.getZ());
                    worldServer.addFreshEntity(r, CreatureSpawnEvent.SpawnReason.CUSTOM);
                }
                case 3 ->{
                    WorldServer worldServer = ((CraftWorld) loc.getWorld()).getHandle();
                    MiniCyclone r = new MiniCyclone(worldServer, MiniCyclone.CycloneClass.NORMAL);
                    r.a_(loc.getX(), loc.getY(), loc.getZ());
                    worldServer.addFreshEntity(r, CreatureSpawnEvent.SpawnReason.CUSTOM);
                }
                case 4 ->{
                    Vex v = (Vex) Entities.spawnMob(loc,EntityType.VEX);
                    Entities.gabrielVex(v);
                }
                case 5 ->{
                    Vex v = (Vex) Entities.spawnMob(loc,EntityType.VEX);
                    Entities.relicVex(v);
                }
            }
        }
    }

    public static void summonnewmob(Location loc,CreatureSpawnEvent e){
        if(getDay() >= 14 && getDay() < 21) {
            if(getMonsoon_active().equalsIgnoreCase("true")){
                Random random = new Random();
                int chance = random.nextInt(4);
                switch (chance) {
                    case 0 -> {
                        e.setCancelled(true);
                        IronGolem ironGolem =(IronGolem)  Entities.spawnMob(loc, EntityType.IRON_GOLEM);
                        Entities.enrIG(ironGolem);
                        setCustomMobcap(ironGolem, 3, 1.10, 24, 20, true);
                    }
                    case 1 -> {
                        e.setCancelled(true);
                        WitherSkeleton w = (WitherSkeleton) Entities.spawnMob(loc,EntityType.WITHER_SKELETON);
                        chooseWitherSkeletonClass1(w);
                    }
                    case 2 -> {
                        e.setCancelled(true);
                        Blaze z = (Blaze) Entities.spawnMob(loc,EntityType.BLAZE);
                        chooseBlazeType(z);
                    }
                    case 3 -> {
                        e.setCancelled(true);
                        Breeze z = (Breeze) Entities.spawnMob(loc,EntityType.BREEZE);
                        Entities.windTyphoon(z);
                    }
                }
            }else{
            Random random = new Random();
            int chance = random.nextInt(3);
            switch (chance) {
                case 0 -> {
                    e.setCancelled(true);
                    IronGolem ironGolem =(IronGolem)  Entities.spawnMob(loc, EntityType.IRON_GOLEM);
                    Entities.enrIG(ironGolem);
                    setCustomMobcap(ironGolem, 3, 1.10, 24, 20, true);
                }
                case 1 -> {
                    e.setCancelled(true);
                    WitherSkeleton w = (WitherSkeleton) Entities.spawnMob(loc,EntityType.WITHER_SKELETON);
                    chooseWitherSkeletonClass1(w);
                }
                case 2 -> {
                    e.setCancelled(true);
                    Breeze z = (Breeze) Entities.spawnMob(loc,EntityType.BREEZE);
                    Entities.windTyphoon(z);
                }
            }
            }
        }else if(getDay() >= 21 && getDay() < 28){
            if(getMonsoon_active().equalsIgnoreCase("true")){
                Random random = new Random();
                int chance = random.nextInt(9);
                switch (chance) {
                    case 0 -> {
                        e.setCancelled(true);
                        IronGolem ironGolem =(IronGolem)  Entities.spawnMob(loc, EntityType.IRON_GOLEM);
                        Entities.enrIG(ironGolem);
                        setCustomMobcap(ironGolem, 3, 1.10, 24, 20, true);
                    }
                    case 1 -> {
                        e.setCancelled(true);
                        WitherSkeleton w = (WitherSkeleton) Entities.spawnMob(loc,EntityType.WITHER_SKELETON);
                        chooseWitherSkeletonClass1(w);
                    }
                    case 2 -> {
                        e.setCancelled(true);
                        Blaze z = (Blaze) Entities.spawnMob(loc,EntityType.BLAZE);
                        chooseBlazeType(z);
                    }
                    case 3 -> {
                        e.setCancelled(true);
                        Silverfish s = (Silverfish) Entities.spawnMob(loc,EntityType.SILVERFISH);
                        Entities.silverday5(s);
                    }
                    case 4 ->{
                        e.setCancelled(true);
                        WorldServer worldServer = ((CraftWorld)loc.getWorld()).getHandle();
                        CustomBee r= new CustomBee(worldServer);
                        r.a_(loc.getX(),loc.getY(),loc.getZ());
                        worldServer.addFreshEntity(r, CreatureSpawnEvent.SpawnReason.CUSTOM);
                    }
                    case 5 ->{
                        e.setCancelled(true);
                        Pillager p = (Pillager) Entities.spawnMob(loc,EntityType.PILLAGER);
                        Entities.nwPillager(p,false);
                    }
                    case 6 ->{
                        e.setCancelled(true);
                        Vindicator p = (Vindicator) Entities.spawnMob(loc,EntityType.VINDICATOR);
                        Entities.nwVindicator(p,false);
                    }
                    case 7 ->{
                        e.setCancelled(true);
                        WorldServer worldServer = ((CraftWorld) loc.getWorld()).getHandle();
                        CustomEvoker r = new CustomEvoker(worldServer,false);
                        r.a_(loc.getX(), loc.getY(), loc.getZ());
                        worldServer.addFreshEntity(r, CreatureSpawnEvent.SpawnReason.CUSTOM);
                    }
                    case 8 -> {
                        e.setCancelled(true);
                        Breeze z = (Breeze) Entities.spawnMob(loc,EntityType.BREEZE);
                        Entities.windTyphoon(z);
                    }
                }
            }else{
                Random random = new Random();
                int chance = random.nextInt(7);
                switch (chance) {
                    case 0 -> {
                        e.setCancelled(true);
                        IronGolem ironGolem =(IronGolem)  Entities.spawnMob(loc, EntityType.IRON_GOLEM);
                        Entities.enrIG(ironGolem);
                        setCustomMobcap(ironGolem, 3, 1.10, 24, 20, true);
                    }
                    case 1 -> {
                        e.setCancelled(true);
                        WitherSkeleton w = (WitherSkeleton) Entities.spawnMob(loc,EntityType.WITHER_SKELETON);
                        chooseWitherSkeletonClass1(w);
                    }
                    case 2 -> {
                        e.setCancelled(true);
                        Blaze z = (Blaze) Entities.spawnMob(loc,EntityType.BLAZE);
                        chooseBlazeType(z);
                    }
                    case 3 -> {
                        e.setCancelled(true);
                        Silverfish s = (Silverfish) Entities.spawnMob(loc,EntityType.SILVERFISH);
                        Entities.silverday5(s);
                    }
                    case 4 ->{
                        e.setCancelled(true);
                        WorldServer worldServer = ((CraftWorld)loc.getWorld()).getHandle();
                        CustomBee r= new CustomBee(worldServer);
                        r.a_(loc.getX(),loc.getY(),loc.getZ());
                        worldServer.addFreshEntity(r, CreatureSpawnEvent.SpawnReason.CUSTOM);
                    }
                    case 5 ->{
                        e.setCancelled(true);
                        Pillager p = (Pillager) Entities.spawnMob(loc,EntityType.PILLAGER);
                        Entities.nwPillager(p,false);
                    }
                    case 6 -> {
                        e.setCancelled(true);
                        Breeze z = (Breeze) Entities.spawnMob(loc,EntityType.BREEZE);
                        Entities.windTyphoon(z);
                    }
                }
            }
        }else if(getDay() >= 28 && getDay() < 35){
            if(getMonsoon_active().equalsIgnoreCase("true")){
                Random random = new Random();
                int chance = random.nextInt(12);
                switch (chance) {
                    case 0 -> {
                        e.setCancelled(true);
                        IronGolem ironGolem =(IronGolem)  Entities.spawnMob(loc, EntityType.IRON_GOLEM);
                        Entities.enrIG(ironGolem);
                        setCustomMobcap(ironGolem, 3, 1.10, 24, 20, true);
                    }
                    case 1 -> {
                        e.setCancelled(true);
                        WitherSkeleton w = (WitherSkeleton) Entities.spawnMob(loc,EntityType.WITHER_SKELETON);
                        chooseWitherSkeletonClass1(w);
                    }
                    case 2 -> {
                        e.setCancelled(true);
                        Blaze z = (Blaze) Entities.spawnMob(loc,EntityType.BLAZE);
                        chooseBlazeType(z);
                    }
                    case 3 -> {
                        e.setCancelled(true);
                        Silverfish s = (Silverfish) Entities.spawnMob(loc,EntityType.SILVERFISH);
                        Entities.silverday5(s);
                    }
                    case 4 ->{
                        e.setCancelled(true);
                        WorldServer worldServer = ((CraftWorld)loc.getWorld()).getHandle();
                        CustomBee r= new CustomBee(worldServer);
                        r.a_(loc.getX(),loc.getY(),loc.getZ());
                        worldServer.addFreshEntity(r, CreatureSpawnEvent.SpawnReason.CUSTOM);
                    }
                    case 5 ->{
                        e.setCancelled(true);
                        Pillager p = (Pillager) Entities.spawnMob(loc,EntityType.PILLAGER);
                        Entities.nwPillager(p,false);
                    }
                    case 6 ->{
                        e.setCancelled(true);
                        Vindicator p = (Vindicator) Entities.spawnMob(loc,EntityType.VINDICATOR);
                        Entities.nwVindicator(p,false);
                    }
                    case 7 ->{
                        e.setCancelled(true);
                        WorldServer worldServer = ((CraftWorld) loc.getWorld()).getHandle();
                        CustomEvoker r = new CustomEvoker(worldServer,false);
                        r.a_(loc.getX(), loc.getY(), loc.getZ());
                        worldServer.addFreshEntity(r, CreatureSpawnEvent.SpawnReason.CUSTOM);
                    }
                    case 8 -> {
                        e.setCancelled(true);
                        Breeze z = (Breeze) Entities.spawnMob(loc,EntityType.BREEZE);
                        Entities.windTyphoon(z);
                    }
                    case 9 ->{
                        spawnNetherWasteyard(e,loc);
                    }
                    case 10 ->{
                        spawnHuskDrownedStray(e,loc);
                    }
                    case 11 ->{
                        e.setCancelled(true);
                        WorldServer worldServer = ((CraftWorld) loc.getWorld()).getHandle();
                        CustomIllusioner r = new CustomIllusioner(worldServer);
                        r.a_(loc.getX(), loc.getY(), loc.getZ());
                        worldServer.addFreshEntity(r, CreatureSpawnEvent.SpawnReason.CUSTOM);
                    }
                }
            }else{
                Random random = new Random();
                int chance = random.nextInt(8);
                switch (chance) {
                    case 0 -> {
                        e.setCancelled(true);
                        IronGolem ironGolem =(IronGolem)  Entities.spawnMob(loc, EntityType.IRON_GOLEM);
                        Entities.enrIG(ironGolem);
                        setCustomMobcap(ironGolem, 3, 1.10, 24, 20, true);
                    }
                    case 1 -> {
                        e.setCancelled(true);
                        WitherSkeleton w = (WitherSkeleton) Entities.spawnMob(loc,EntityType.WITHER_SKELETON);
                        chooseWitherSkeletonClass1(w);
                    }
                    case 2 -> {
                        e.setCancelled(true);
                        Blaze z = (Blaze) Entities.spawnMob(loc,EntityType.BLAZE);
                        chooseBlazeType(z);
                    }
                    case 3 -> {
                        e.setCancelled(true);
                        Silverfish s = (Silverfish) Entities.spawnMob(loc,EntityType.SILVERFISH);
                        Entities.silverday5(s);
                    }
                    case 4 ->{
                        e.setCancelled(true);
                        WorldServer worldServer = ((CraftWorld)loc.getWorld()).getHandle();
                        CustomBee r= new CustomBee(worldServer);
                        r.a_(loc.getX(),loc.getY(),loc.getZ());
                        worldServer.addFreshEntity(r, CreatureSpawnEvent.SpawnReason.CUSTOM);
                    }
                    case 5 ->{
                        e.setCancelled(true);
                        Pillager p = (Pillager) Entities.spawnMob(loc,EntityType.PILLAGER);
                        Entities.nwPillager(p,false);
                    }
                    case 6 -> {
                        e.setCancelled(true);
                        Breeze z = (Breeze) Entities.spawnMob(loc,EntityType.BREEZE);
                        Entities.windTyphoon(z);
                    }
                    case 7 ->{
                        e.setCancelled(true);
                        WorldServer worldServer = ((CraftWorld) loc.getWorld()).getHandle();
                        CustomIllusioner r = new CustomIllusioner(worldServer);
                        r.a_(loc.getX(), loc.getY(), loc.getZ());
                        worldServer.addFreshEntity(r, CreatureSpawnEvent.SpawnReason.CUSTOM);
                    }
                }
            }
        }else if(getDay() >= 35 && getDay() < 42){
            if(getMonsoon_active().equalsIgnoreCase("true")){
                Random random = new Random();
                int chance = random.nextInt(15);
                switch (chance) {
                    case 0 -> {
                        e.setCancelled(true);
                        IronGolem ironGolem =(IronGolem)  Entities.spawnMob(loc, EntityType.IRON_GOLEM);
                        Entities.enrIG(ironGolem);
                        setCustomMobcap(ironGolem, 3, 1.10, 24, 20, true);
                    }
                    case 1 -> {
                        e.setCancelled(true);
                        WitherSkeleton w = (WitherSkeleton) Entities.spawnMob(loc,EntityType.WITHER_SKELETON);
                        chooseWitherSkeletonClass1(w);
                    }
                    case 2 -> {
                        e.setCancelled(true);
                        Blaze z = (Blaze) Entities.spawnMob(loc,EntityType.BLAZE);
                        chooseBlazeType(z);
                    }
                    case 3 -> {
                        e.setCancelled(true);
                        Silverfish s = (Silverfish) Entities.spawnMob(loc,EntityType.SILVERFISH);
                        Entities.silverday5(s);
                    }
                    case 4 ->{
                        e.setCancelled(true);
                        WorldServer worldServer = ((CraftWorld)loc.getWorld()).getHandle();
                        CustomBee r= new CustomBee(worldServer);
                        r.a_(loc.getX(),loc.getY(),loc.getZ());
                        worldServer.addFreshEntity(r, CreatureSpawnEvent.SpawnReason.CUSTOM);
                    }
                    case 5 ->{
                        e.setCancelled(true);
                        Pillager p = (Pillager) Entities.spawnMob(loc,EntityType.PILLAGER);
                        Entities.nwPillager(p,false);
                    }
                    case 6 ->{
                        e.setCancelled(true);
                        Vindicator p = (Vindicator) Entities.spawnMob(loc,EntityType.VINDICATOR);
                        Entities.nwVindicator(p,false);
                    }
                    case 7 ->{
                        e.setCancelled(true);
                        WorldServer worldServer = ((CraftWorld) loc.getWorld()).getHandle();
                        CustomEvoker r = new CustomEvoker(worldServer,false);
                        r.a_(loc.getX(), loc.getY(), loc.getZ());
                        worldServer.addFreshEntity(r, CreatureSpawnEvent.SpawnReason.CUSTOM);
                    }
                    case 8 -> {
                        chooseAirMob(loc,e);
                    }
                    case 9 ->{
                        spawnNetherWasteyard(e,loc);
                    }
                    case 10 ->{
                        spawnHuskDrownedStray(e,loc);
                    }
                    case 11 ->{
                        e.setCancelled(true);
                        WorldServer worldServer = ((CraftWorld) loc.getWorld()).getHandle();
                        CustomIllusioner r = new CustomIllusioner(worldServer);
                        r.a_(loc.getX(), loc.getY(), loc.getZ());
                        worldServer.addFreshEntity(r, CreatureSpawnEvent.SpawnReason.CUSTOM);
                    }
                    case 12 ->{
                        netherMobSpawn(loc,e);
                    }
                    case 13 -> {
                        if (doRandomChance(30)) {
                            e.setCancelled(true);
                            WorldServer worldServer = ((CraftWorld) loc.getWorld()).getHandle();
                            CustomGiant r = new CustomGiant(worldServer);
                            r.a_(loc.getX(), loc.getY(), loc.getZ());
                            worldServer.addFreshEntity(r, CreatureSpawnEvent.SpawnReason.CUSTOM);
                        }else{
                            netherMobSpawn(loc,e);
                        }
                    }
                    case 14 ->{
                        if(doRandomChance(35)){
                            e.setCancelled(true);
                            Warden w = (Warden) Entities.spawnMob(loc,EntityType.WARDEN);
                            EntityHelper.addPotionEffect(w,PotionEffectType.SPEED,2);
                            EntityHelper.addPotionEffect(w,PotionEffectType.INCREASE_DAMAGE,3);
                            w.setPersistent(false);
                            w.setRemoveWhenFarAway(true);
                        }else{
                            chooseAirMob(loc,e);
                        }
                    }
                }
            }else{
                Random random = new Random();
                int chance = random.nextInt(11);
                switch (chance) {
                    case 0 -> {
                        e.setCancelled(true);
                        IronGolem ironGolem =(IronGolem)  Entities.spawnMob(loc, EntityType.IRON_GOLEM);
                        Entities.enrIG(ironGolem);
                        setCustomMobcap(ironGolem, 3, 1.10, 24, 20, true);
                    }
                    case 1 -> {
                        e.setCancelled(true);
                        WitherSkeleton w = (WitherSkeleton) Entities.spawnMob(loc,EntityType.WITHER_SKELETON);
                        chooseWitherSkeletonClass1(w);
                    }
                    case 2 -> {
                        e.setCancelled(true);
                        Blaze z = (Blaze) Entities.spawnMob(loc,EntityType.BLAZE);
                        chooseBlazeType(z);
                    }
                    case 3 -> {
                        e.setCancelled(true);
                        Silverfish s = (Silverfish) Entities.spawnMob(loc,EntityType.SILVERFISH);
                        Entities.silverday5(s);
                    }
                    case 4 ->{
                        e.setCancelled(true);
                        WorldServer worldServer = ((CraftWorld)loc.getWorld()).getHandle();
                        CustomBee r= new CustomBee(worldServer);
                        r.a_(loc.getX(),loc.getY(),loc.getZ());
                        worldServer.addFreshEntity(r, CreatureSpawnEvent.SpawnReason.CUSTOM);
                    }
                    case 5 ->{
                        e.setCancelled(true);
                        Pillager p = (Pillager) Entities.spawnMob(loc,EntityType.PILLAGER);
                        Entities.nwPillager(p,false);
                    }
                    case 6 -> {
                        chooseAirMob(loc,e);
                    }
                    case 7 ->{
                        e.setCancelled(true);
                        WorldServer worldServer = ((CraftWorld) loc.getWorld()).getHandle();
                        CustomIllusioner r = new CustomIllusioner(worldServer);
                        r.a_(loc.getX(), loc.getY(), loc.getZ());
                        worldServer.addFreshEntity(r, CreatureSpawnEvent.SpawnReason.CUSTOM);
                    }
                    case 8 ->{
                        netherMobSpawn(loc,e);
                    }
                    case 9 -> {
                        if (doRandomChance(30)) {
                            e.setCancelled(true);
                            WorldServer worldServer = ((CraftWorld) loc.getWorld()).getHandle();
                            CustomGiant r = new CustomGiant(worldServer);
                            r.a_(loc.getX(), loc.getY(), loc.getZ());
                            worldServer.addFreshEntity(r, CreatureSpawnEvent.SpawnReason.CUSTOM);
                        }else{
                            netherMobSpawn(loc,e);
                        }
                    }
                    case 10 ->{
                        if(doRandomChance(15)){
                            e.setCancelled(true);
                            Warden w = (Warden) Entities.spawnMob(loc,EntityType.WARDEN);
                            EntityHelper.addPotionEffect(w,PotionEffectType.SPEED,2);
                            EntityHelper.addPotionEffect(w,PotionEffectType.INCREASE_DAMAGE,3);
                            w.setPersistent(false);
                            w.setRemoveWhenFarAway(true);
                        }else{
                            chooseAirMob(loc,e);
                        }
                    }
                }
            }
        }
    }
    public static ItemStack harmArrow() {
        ItemStack arrow = new ItemStack(Material.TIPPED_ARROW);
        PotionMeta meta = (PotionMeta) arrow.getItemMeta();
        meta.addCustomEffect(new PotionEffect(PotionEffectType.WITHER,1,1),false);
        arrow.setItemMeta(meta);
        arrow.setAmount(127);
        return arrow;
    }

 }

