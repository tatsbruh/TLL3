package com.tll3.Task;

import com.tll3.Enviroments.Worlds;
import com.tll3.Listeners.EntityNaturalSpawn;
import com.tll3.Lists.CustomEntities.CustomLlama;
import com.tll3.Lists.CustomEntities.CustomParrot;
import com.tll3.Lists.CustomEntities.Others.MiniCyclone;
import com.tll3.Lists.Entities;
import com.tll3.Misc.EntityHelper;
import com.tll3.Misc.GenericUtils;
import com.tll3.Misc.ItemBuilder;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_20_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftEntity;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

import static com.tll3.Misc.EntityHelper.*;
import static com.tll3.Misc.EntityHelper.setBoots;
import static com.tll3.Misc.GenericUtils.getDay;


//Vortex Outbreak task, IDK why i called it MobRain but deal with it
public class MobRain extends BukkitRunnable {

    @Override
    public void run() {
        World w = GenericUtils.getWorld();
        for(Player online : Bukkit.getOnlinePlayers()){
            if(online.getWorld().getEnvironment() == World.Environment.NORMAL && GenericUtils.getTyphoonactive().equalsIgnoreCase("true")) {
                if (online.getGameMode() == GameMode.SURVIVAL) {
                    int random = GenericUtils.getRandomValue(5000) + 1;
                    int maxchance1 = GenericUtils.getDay() >= 42 ? 30 : 10;
                    if (random <= maxchance1) {
                        Location ploc = online.getLocation().clone();
                        ArrayList<Location> spawns = new ArrayList<>();
                        spawns.add(ploc.clone().add(GenericUtils.getRandomValue(30) + 5, 55, GenericUtils.getRandomValue(-25) - 5));
                        spawns.add(ploc.clone().add(GenericUtils.getRandomValue(25) + 5, 55, GenericUtils.getRandomValue(25) + 5));
                        spawns.add(ploc.clone().add(GenericUtils.getRandomValue(-25) - 5, 55, GenericUtils.getRandomValue(30) + 5));
                        for (Location l : spawns) {
                            if (w.getBlockAt(l).getType() == Material.AIR && w.getBlockAt(l.clone().add(0, 1, 0)).getType() == Material.AIR) {
                                initMobs(l);
                            }
                        }
                    }
                    int random2 = GenericUtils.getRandomValue(3000) + 1;
                    int maxchance2 = GenericUtils.getDay() >= 35 ? 25 : 10;
                    if (random2 <= maxchance2) {
                        Location ploc = online.getLocation().clone();
                        ArrayList<Location> spawns = new ArrayList<>();
                        spawns.add(ploc.clone().add(GenericUtils.getRandomValue(35), ploc.getBlockY(), GenericUtils.getRandomValue(-35)));
                        for (Location l : spawns) {
                            w.strikeLightning(l);
                        }
                    }
                }
            }
        }
    }

    public static void initMobs(Location loc){
        if(GenericUtils.getDay() < 28){
            int choice = GenericUtils.getRandomValue(17);
            switch (choice){
                case 0 ->{
                    Zombie z = (Zombie) Entities.spawnMob(loc,EntityType.ZOMBIE);
                    EntityNaturalSpawn.chooseZombieClass1(z);
                }
                case 1 ->{
                    Skeleton s = (Skeleton)Entities.spawnMob(loc,EntityType.SKELETON);
                    EntityNaturalSpawn.chooseSkeletonClass1(s);
                }
                case 2 ->{
                    Spider s = (Spider) Entities.spawnMob(loc,EntityType.SPIDER);
                    int type = GenericUtils.getRandomValue(4);
                    switch (type){
                        case 0 ->Entities.blackRev(s);
                        case 1 ->Entities.adapSp(s);
                        case 2 ->Entities.revSpider(s);
                        case 3 ->Entities.neonSp(s);
                    }
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
                    Enderman e = (Enderman) Entities.spawnMob(loc,EntityType.ENDERMAN);
                    if(Math.random() < 0.5) {
                        Entities.revEnderman(e);
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
                    Endermite e = (Endermite) Entities.spawnMob(loc,EntityType.ENDERMITE);
                    Entities.quanmite(e);
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
                case 11 ->{
                    Slime s = (Slime) Entities.spawnMob(loc,EntityType.SLIME);
                    Entities.slimeNight(s);
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
                    WorldServer worldServer = ((CraftWorld) loc.getWorld()).getHandle();
                    CustomLlama r = new CustomLlama(worldServer);
                    r.a_(loc.getX(), loc.getY(), loc.getZ());
                    worldServer.addFreshEntity(r, CreatureSpawnEvent.SpawnReason.CUSTOM);
                }
            }
        }else if(GenericUtils.getDay() >= 28 && GenericUtils.getDay() < 35){
            int choice = GenericUtils.getRandomValue(24);
            switch (choice){
                case 0 ->{
                    Zombie z = (Zombie) Entities.spawnMob(loc,EntityType.ZOMBIE);
                    EntityNaturalSpawn.chooseZombieClass1(z);
                }
                case 1 ->{
                    Skeleton s = (Skeleton)Entities.spawnMob(loc,EntityType.SKELETON);
                    EntityNaturalSpawn.chooseSkeletonClass1(s);
                }
                case 2 ->{
                    Spider s = (Spider) Entities.spawnMob(loc,EntityType.SPIDER);
                    int type = GenericUtils.getRandomValue(4);
                    switch (type){
                        case 0 ->Entities.blackRev(s);
                        case 1 ->Entities.adapSp(s);
                        case 2 ->Entities.revSpider(s);
                        case 3 ->Entities.neonSp(s);
                    }
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
                    Enderman e = (Enderman) Entities.spawnMob(loc,EntityType.ENDERMAN);
                    if(Math.random() < 0.5) {
                        Entities.revEnderman(e);
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
                    Endermite e = (Endermite) Entities.spawnMob(loc,EntityType.ENDERMITE);
                    Entities.quanmite(e);
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
                case 11 ->{
                    Slime s = (Slime) Entities.spawnMob(loc,EntityType.SLIME);
                    Entities.slimeNight(s);
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
                    WorldServer worldServer = ((CraftWorld) loc.getWorld()).getHandle();
                    CustomLlama r = new CustomLlama(worldServer);
                    r.a_(loc.getX(), loc.getY(), loc.getZ());
                    worldServer.addFreshEntity(r, CreatureSpawnEvent.SpawnReason.CUSTOM);
                }
                case 17 ->{
                    waste(loc);
                }
                case 18 ->{
                    PiglinBrute pg = (PiglinBrute) Entities.spawnMob(loc,EntityType.PIGLIN_BRUTE);
                    EntityHelper.addPotionEffect(pg, PotionEffectType.INCREASE_DAMAGE,3);
                    EntityHelper.addPotionEffect(pg,PotionEffectType.SPEED,2);
                    pg.setImmuneToZombification(true);
                    setHead(pg,new ItemBuilder(Material.NETHERITE_HELMET).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,4).build());
                    setChestplate(pg,new ItemBuilder(Material.NETHERITE_CHESTPLATE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,4).build());
                    setLeggings(pg,new ItemBuilder(Material.NETHERITE_LEGGINGS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,4).build());
                    setBoots(pg,new ItemBuilder(Material.NETHERITE_BOOTS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,4).build());
                }
                case 19 ->{
                    Skeleton s = (Skeleton) Entities.spawnMob(loc,EntityType.SKELETON);
                    Entities.steelrailgunner(s);
                }
                case 20 ->{
                    Creeper c = (Creeper) Entities.spawnMob(loc,EntityType.CREEPER);
                    Entities.titaniumCreeper(c);
                }
                case 21 ->{
                    PigZombie z = (PigZombie) Entities.spawnMob(loc,EntityType.ZOMBIFIED_PIGLIN);
                    Entities.zombpigAlchemist(z);
                }
                case 22 ->{
                    Breeze z = (Breeze) Entities.spawnMob(loc,EntityType.BREEZE);
                    Entities.windTyphoon(z);
                }
                case 23 ->{
                    Piglin p = (Piglin) Entities.spawnMob(loc,EntityType.PIGLIN);
                    Entities.piglGr(p);
                }

            }
        }else if(GenericUtils.getDay() >= 35 && GenericUtils.getDay() < 42){
        int choice = GenericUtils.getRandomValue(26);
        switch (choice){
            case 0 ->{
                Zombie z = (Zombie) Entities.spawnMob(loc,EntityType.ZOMBIE);
                EntityNaturalSpawn.chooseZombieClass1(z);
            }
            case 1 ->{
                Skeleton s = (Skeleton)Entities.spawnMob(loc,EntityType.SKELETON);
                EntityNaturalSpawn.chooseSkeletonClass1(s);
            }
            case 2 ->{
                Spider s = (Spider) Entities.spawnMob(loc,EntityType.SPIDER);
                int type = GenericUtils.getRandomValue(4);
                switch (type){
                    case 0 ->Entities.blackRev(s);
                    case 1 ->Entities.adapSp(s);
                    case 2 ->Entities.revSpider(s);
                    case 3 ->Entities.neonSp(s);
                }
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
                Enderman e = (Enderman) Entities.spawnMob(loc,EntityType.ENDERMAN);
                if(Math.random() < 0.5) {
                    Entities.revEnderman(e);
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
                Endermite e = (Endermite) Entities.spawnMob(loc,EntityType.ENDERMITE);
                Entities.quanmite(e);
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
            case 11 ->{
                Slime s = (Slime) Entities.spawnMob(loc,EntityType.SLIME);
                Entities.slimeNight(s);
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
                WorldServer worldServer = ((CraftWorld) loc.getWorld()).getHandle();
                CustomLlama r = new CustomLlama(worldServer);
                r.a_(loc.getX(), loc.getY(), loc.getZ());
                worldServer.addFreshEntity(r, CreatureSpawnEvent.SpawnReason.CUSTOM);
            }
            case 17 ->{
                waste(loc);
            }
            case 18 ->{
                PiglinBrute pg = (PiglinBrute) Entities.spawnMob(loc,EntityType.PIGLIN_BRUTE);
                EntityHelper.addPotionEffect(pg, PotionEffectType.INCREASE_DAMAGE,3);
                EntityHelper.addPotionEffect(pg,PotionEffectType.SPEED,2);
                pg.setImmuneToZombification(true);
                setHead(pg,new ItemBuilder(Material.NETHERITE_HELMET).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,4).build());
                setChestplate(pg,new ItemBuilder(Material.NETHERITE_CHESTPLATE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,4).build());
                setLeggings(pg,new ItemBuilder(Material.NETHERITE_LEGGINGS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,4).build());
                setBoots(pg,new ItemBuilder(Material.NETHERITE_BOOTS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,4).build());
            }
            case 19 ->{
                Skeleton s = (Skeleton) Entities.spawnMob(loc,EntityType.SKELETON);
                Entities.steelrailgunner(s);
            }
            case 20 ->{
                Creeper c = (Creeper) Entities.spawnMob(loc,EntityType.CREEPER);
                Entities.titaniumCreeper(c);
            }
            case 21 ->{
                PigZombie z = (PigZombie) Entities.spawnMob(loc,EntityType.ZOMBIFIED_PIGLIN);
                Entities.zombpigAlchemist(z);
            }
            case 22 ->{
                Breeze z = (Breeze) Entities.spawnMob(loc,EntityType.BREEZE);
                Entities.windTyphoon(z);
            }
            case 23 ->{
                Piglin p = (Piglin) Entities.spawnMob(loc,EntityType.PIGLIN);
                Entities.piglGr(p);
            }
            case 24 ->end(loc);
            case 25 ->zero(loc);

        }
    }else if(GenericUtils.getDay() >= 42){
            int choice = GenericUtils.getRandomValue(26);
            switch (choice){
                case 0 ->{
                    Zombie z = (Zombie) Entities.spawnMob(loc,EntityType.ZOMBIE);
                    EntityNaturalSpawn.chooseZombieClass1(z);
                }
                case 1 ->{
                    Skeleton s = (Skeleton)Entities.spawnMob(loc,EntityType.SKELETON);
                    EntityNaturalSpawn.chooseSkeletonClass1(s);
                }
                case 2 ->{
                    Spider s = (Spider) Entities.spawnMob(loc,EntityType.SPIDER);
                    int type = GenericUtils.getRandomValue(4);
                    switch (type){
                        case 0 ->Entities.blackRev(s);
                        case 1 ->Entities.adapSp(s);
                        case 2 ->Entities.revSpider(s);
                        case 3 ->Entities.neonSp(s);
                    }
                }
                case 3 ->{
                    Creeper c = (Creeper) Entities.spawnMob(loc,EntityType.CREEPER);
                    Entities.chooseCreeperType(c);
                }
                case 4 ->{
                    Enderman e = (Enderman) Entities.spawnMob(loc,EntityType.ENDERMAN);
                    if(Math.random() < 0.5) {
                        Entities.revEnderman(e);
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
                    Endermite e = (Endermite) Entities.spawnMob(loc,EntityType.ENDERMITE);
                    Entities.quanmite(e);
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
                case 11 ->{
                    Slime s = (Slime) Entities.spawnMob(loc,EntityType.SLIME);
                    Entities.slimeNight(s);
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
                    WorldServer worldServer = ((CraftWorld) loc.getWorld()).getHandle();
                    CustomLlama r = new CustomLlama(worldServer);
                    r.a_(loc.getX(), loc.getY(), loc.getZ());
                    worldServer.addFreshEntity(r, CreatureSpawnEvent.SpawnReason.CUSTOM);
                }
                case 17 ->{
                    waste(loc);
                }
                case 18 ->{
                    PiglinBrute pg = (PiglinBrute) Entities.spawnMob(loc,EntityType.PIGLIN_BRUTE);
                    EntityHelper.addPotionEffect(pg, PotionEffectType.INCREASE_DAMAGE,3);
                    EntityHelper.addPotionEffect(pg,PotionEffectType.SPEED,2);
                    pg.setImmuneToZombification(true);
                    setHead(pg,new ItemBuilder(Material.NETHERITE_HELMET).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,4).build());
                    setChestplate(pg,new ItemBuilder(Material.NETHERITE_CHESTPLATE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,4).build());
                    setLeggings(pg,new ItemBuilder(Material.NETHERITE_LEGGINGS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,4).build());
                    setBoots(pg,new ItemBuilder(Material.NETHERITE_BOOTS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,4).build());
                }
                case 19 ->{
                    Skeleton s = (Skeleton) Entities.spawnMob(loc,EntityType.SKELETON);
                    Entities.steelrailgunner(s);
                }
                case 20 ->{
                    Creeper c = (Creeper) Entities.spawnMob(loc,EntityType.CREEPER);
                    Entities.titaniumCreeper(c);
                }
                case 21 ->{
                    PigZombie z = (PigZombie) Entities.spawnMob(loc,EntityType.ZOMBIFIED_PIGLIN);
                    Entities.zombpigAlchemist(z);
                }
                case 22 ->{
                    Breeze z = (Breeze) Entities.spawnMob(loc,EntityType.BREEZE);
                    Entities.windTyphoon(z);
                }
                case 23 ->{
                    Piglin p = (Piglin) Entities.spawnMob(loc,EntityType.PIGLIN);
                    Entities.piglGr(p);
                }
                case 24 ->end(loc);
                case 25 ->zero(loc);
                case 26 ->cyclone(loc);

            }
        }

    }


    public static void waste(Location loc){
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
    public static void zero(Location loc){
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
    public static void end(Location loc){
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

    public static void cyclone(Location loc){
        if(getDay() >= 42){
            Random random = new Random();
            int chance = random.nextInt(5);
            switch (chance){
                case 0 ->{
                    WorldServer worldServer = ((CraftWorld) loc.getWorld()).getHandle();
                    MiniCyclone r = new MiniCyclone(worldServer, MiniCyclone.CycloneClass.ZOMBIE);
                    r.a_(loc.getX(), loc.getY(), loc.getZ());
                    worldServer.addFreshEntity(r, CreatureSpawnEvent.SpawnReason.CUSTOM);
                }
                case 1 ->{
                    WorldServer worldServer = ((CraftWorld) loc.getWorld()).getHandle();
                    MiniCyclone r = new MiniCyclone(worldServer, MiniCyclone.CycloneClass.SPACE);
                    r.a_(loc.getX(), loc.getY(), loc.getZ());
                    worldServer.addFreshEntity(r, CreatureSpawnEvent.SpawnReason.CUSTOM);
                }
                case 2 ->{
                    WorldServer worldServer = ((CraftWorld) loc.getWorld()).getHandle();
                    MiniCyclone r = new MiniCyclone(worldServer, MiniCyclone.CycloneClass.NORMAL);
                    r.a_(loc.getX(), loc.getY(), loc.getZ());
                    worldServer.addFreshEntity(r, CreatureSpawnEvent.SpawnReason.CUSTOM);
                }
                case 3 ->{
                    WorldServer worldServer = ((CraftWorld) loc.getWorld()).getHandle();
                    MiniCyclone r = new MiniCyclone(worldServer, MiniCyclone.CycloneClass.METAL);
                    r.a_(loc.getX(), loc.getY(), loc.getZ());
                    worldServer.addFreshEntity(r, CreatureSpawnEvent.SpawnReason.CUSTOM);
                }
                case 4 ->{
                    WorldServer worldServer = ((CraftWorld) loc.getWorld()).getHandle();
                    MiniCyclone r = new MiniCyclone(worldServer, MiniCyclone.CycloneClass.RELIC);
                    r.a_(loc.getX(), loc.getY(), loc.getZ());
                    worldServer.addFreshEntity(r, CreatureSpawnEvent.SpawnReason.CUSTOM);
                }
            }
        }
    }

}
