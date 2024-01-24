package com.tll3.Task;

import com.tll3.Listeners.EntityNaturalSpawn;
import com.tll3.Lists.CustomEntities.CustomLlama;
import com.tll3.Lists.Entities;
import com.tll3.Misc.GenericUtils;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_20_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftEntity;
import org.bukkit.entity.*;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MobRain extends BukkitRunnable {

    @Override
    public void run() {
        World w = GenericUtils.getWorld();
        for(Player online : Bukkit.getOnlinePlayers()){
            if(online.getWorld().getEnvironment() == World.Environment.NORMAL && GenericUtils.getTyphoonactive().equalsIgnoreCase("true")) {
                if (online.getGameMode() == GameMode.SURVIVAL) {
                    int random = GenericUtils.getRandomValue(4500) + 1;
                    if (random <= 10) {
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
                    int random2 = GenericUtils.getRandomValue(2000) + 1;
                    if (random2 <= 10) {
                        Location ploc = online.getLocation().clone();
                        ArrayList<Location> spawns = new ArrayList<>();
                        spawns.add(ploc.clone().add(GenericUtils.getRandomValue(5), ploc.getBlockY(), GenericUtils.getRandomValue(-5)));
                        spawns.add(ploc.clone().add(GenericUtils.getRandomValue(-5), ploc.getBlockY(), GenericUtils.getRandomValue(5)));
                        for (Location l : spawns) {
                                w.strikeLightning(l);
                        }
                    }
                }
            }
        }
    }

    public static void initMobs(Location loc){
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

    }


}
