package com.tll3.Listeners;

import com.tll3.Enviroments.Worlds;
import com.tll3.Lists.CustomEntities.*;
import com.tll3.Lists.CustomEntities.CustomProjectiles.CustomEvokerFangs;
import com.tll3.Lists.CustomEntities.CustomProjectiles.CustomLlamaSpit;
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
        || entity instanceof CustomLlamaSpit || entity instanceof CustomPig)return;
        spawnWasteyard(e,loc);
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
                if (loc.getWorld().getEnvironment() == World.Environment.NORMAL) {
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
                if (loc.getWorld().getEnvironment() == World.Environment.NORMAL) {
                    if (doRandomChance(3)) {
                        summonnewmob(loc, e);
                    }
                }
            }
        }else if(getDay() >= 21 && getDay() < 28){
            if ( reason == CreatureSpawnEvent.SpawnReason.NATURAL && (entity instanceof Enemy && !(entity instanceof WaterMob))) {
                if (loc.getWorld().getEnvironment() == World.Environment.NORMAL) {
                    if (doRandomChance(4)) {
                        summonnewmob(loc, e);
                    }
                }
                if(loc.getWorld().getName().equalsIgnoreCase("world_nether")){
                    if(getTyphoonactive().equalsIgnoreCase("true")){
                        if(doRandomChance(5)){
                            spawnNetherWasteyard(e,loc);
                        }
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
                if((reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)) {
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
                if((reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)) {
                    if(getDay() >= 7 && getDay() < 21){
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
                        if(getMonsoon_active().equalsIgnoreCase("true")){
                            Spider s = (Spider) Entities.spawnMob(loc,EntityType.SPIDER);
                            chooseRandomSpider1(s,e);
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
                        if(getMonsoon_active().equalsIgnoreCase("true")){
                            Spider s = (Spider) Entities.spawnMob(loc,EntityType.SPIDER);
                            chooseRandomSpider1(s,e);
                            s.addPassenger(entity);
                        }
                        EntityHelper.setOffhand(entity,harmArrow());
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
                        reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG || reason == CreatureSpawnEvent.SpawnReason.SILVERFISH_BLOCK)) {
                    if (getDay() >= 7) {
                        Entities.silverday5((Silverfish) entity);
                    }
                }
            }
            case SPIDER -> {
                if((reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)) {
                    if(getDay() >= 7){
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
                    if(getDay() >= 7){
                        var random = getRandomValue(100);
                        if(random <= 35) {
                            Entities.revCreeper((Creeper) entity);
                        }else if(random > 35 && random <= 65 && getDay() >= 14) {
                            Entities.unstCr((Creeper) entity);
                        }else if(random > 65 && random <= 86 && getDay() >= 21) {
                            Entities.titaniumCreeper((Creeper) entity);
                        }else if(random > 86 && random <= 90){
                            Entities.creeperTower((Creeper) entity);
                        }else{
                            Entities.creChr((Creeper) entity);
                        }
                    }
                }
            }
            case ENDERMAN -> {
                if((reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)) {
                    if(getDay() >= 7){
                        var random = getRandomValue(100);
                        if(random <= 35){
                            Entities.revEnderman((Enderman) entity);
                        }else if(random > 35 && random <= 55 && getDay() >= 21){
                            Entities.cyberpunk((Enderman) entity);
                        }
                    }
                }
            }
            case PHANTOM -> {
                if((reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)) {
                    if(getDay() >= 7){
                        Entities.phanD((Phantom) entity);
                    }
                }
            }
            case DROWNED -> {
                if((reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)) {
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
                if ((reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)) {
                    if (getDay() >= 14) {
                        if(doRandomChance(35)){
                            chooserandomraider(loc,e);
                        }
                    }
                }
            }
            case PILLAGER -> {
                if ((reason == CreatureSpawnEvent.SpawnReason.RAID || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)) {
                    if (getDay() >= 14) {
                       Entities.nwPillager((Pillager) entity,true);
                    }
                }
            }
            case VINDICATOR -> {
                if ((reason == CreatureSpawnEvent.SpawnReason.RAID || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)) {
                    if (getDay() >= 14) {
                        Entities.nwVindicator((Vindicator) entity,true);
                    }
                }
            }
            case RAVAGER -> {
                if ((reason == CreatureSpawnEvent.SpawnReason.RAID || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)) {
                    if (getDay() >= 14) {
                        Entities.nwRavager((Ravager) entity,true);
                    }
                }
            }
            case EVOKER -> {
                if ((reason == CreatureSpawnEvent.SpawnReason.RAID || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)) {
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
                if(reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)Entities.huStr((Husk) entity);
            }
            }
            case STRAY -> {
                if(reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG){
                    if(getDay() >= 14)Entities.strayCom((Stray) entity);
                }
            }
            case SLIME -> {
                if(reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG){
                    if(getDay() >= 14)Entities.slimeNight((Slime) entity);
                }
            }
            case CAVE_SPIDER -> { if(getDay() >= 7){
                if(reason == CreatureSpawnEvent.SpawnReason.SPAWNER || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)Entities.csTerCol((CaveSpider) entity);
            }
            }
            case GHAST -> { if(getDay() >= 7){
                if(reason == CreatureSpawnEvent.SpawnReason.NATURAL || reason == CreatureSpawnEvent.SpawnReason.COMMAND || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG){
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
                        if(getDay() >= 21){
                            setHead(pg,new ItemBuilder(Material.NETHERITE_HELMET).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,4).build());
                            setChestplate(pg,new ItemBuilder(Material.NETHERITE_CHESTPLATE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,4).build());
                            setLeggings(pg,new ItemBuilder(Material.NETHERITE_LEGGINGS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,4).build());
                            setBoots(pg,new ItemBuilder(Material.NETHERITE_BOOTS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,4).build());
                        }
                        entity.addPassenger(pg);
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
        }else if(getDay() >= 14){
                Random random = new Random();
                int chance = random.nextInt(5);
                switch (chance){
                    case 0 ->{ Entities.skeAd(w);}
                    case 1 -> {Entities.skeFi(w);}
                    case 2 -> {Entities.skeRz(w);}
                    case 3 -> Entities.voidOver(w);
                    case 4 -> {Entities.livingSh(w);}
                }
        }
    }
    public static void chooseZombieClass1(Zombie z){
        if(getDay() >= 7) {
            Random random = new Random();
            int chance = random.nextInt(2);
            switch (chance) {
                case 0 -> Entities.zNinka(z);
                case 1 -> Entities.zArqueo(z);

            }
        }
    }
    public static void chooseBlazeType(Blaze z){
        if(getDay() >= 14) {
            Random random = new Random();
            int chance = random.nextInt(2);
            switch (chance) {
                case 0 -> Entities.windChar(z);
                case 1 -> Entities.armorBlaze(z);

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

    public static void spawnNetherWasteyard(CreatureSpawnEvent e, Location loc){
        World w = Worlds.getWasteyard();
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
        Random random = new Random();
        int chance = random.nextInt(4);
        switch (chance){
            case 0 ->{
                e.setCancelled(true);
                Pillager p = (Pillager) Entities.spawnMob(loc,EntityType.PILLAGER);
                Entities.nwPillager(p,false);
            }
            case 1 ->{
                e.setCancelled(true);
                Vindicator v = (Vindicator) Entities.spawnMob(loc,EntityType.VINDICATOR);
                Entities.nwVindicator(v,false);
            }
            case 2 -> {
                e.setCancelled(true);
                Ravager r = (Ravager) Entities.spawnMob(loc,EntityType.RAVAGER);
                Entities.nwRavager(r,false);
            }
            case 3 ->{
                e.setCancelled(true);
                WorldServer worldServer = ((CraftWorld) loc.getWorld()).getHandle();
                CustomEvoker r = new CustomEvoker(worldServer,false);
                r.a_(loc.getX(), loc.getY(), loc.getZ());
                worldServer.addFreshEntity(r, CreatureSpawnEvent.SpawnReason.CUSTOM);
            }
        }
    }

    public static void summonnewmob(Location loc,CreatureSpawnEvent e){
        if(getDay() >= 14 && getDay() < 21) {
            if(getMonsoon_active().equalsIgnoreCase("true")){
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
                        Blaze z = (Blaze) Entities.spawnMob(loc,EntityType.BLAZE);
                        chooseBlazeType(z);
                    }
                }
            }else{
            Random random = new Random();
            int chance = random.nextInt(2);
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
            }
            }
        }else if(getDay() >= 21){
            if(getMonsoon_active().equalsIgnoreCase("true")){
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
                }
            }else{
                Random random = new Random();
                int chance = random.nextInt(6);
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

