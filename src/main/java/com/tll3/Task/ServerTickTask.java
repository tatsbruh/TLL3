package com.tll3.Task;

import com.tll3.Misc.ChatUtils;
import com.tll3.Misc.DataManager.PlayerData;
import com.tll3.Misc.GenericUtils;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageType;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BoundingBox;

import java.util.Random;
import java.util.SplittableRandom;
import java.util.stream.Collectors;

import static com.tll3.Misc.GenericUtils.getDay;
import static com.tll3.Misc.GenericUtils.*;

public class ServerTickTask extends BukkitRunnable {
    private final Player p;
    int cram_interval = 0;
    int wast_darkness = 0;
    int wast_caution = 5;
    int temperature = 0;
    int freezeticks = 0;
    private final SplittableRandom random = new SplittableRandom();

    public ServerTickTask(Player p){
        this.p = p;
    }
    @Override
    public void run() {
        double health = 20;
        double speed = 0.10000000149011612;
        int chance = GenericUtils.getRandomValue(10000);

        if(getFullSet(p,69)){
            health += 8;
            p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,100,0,true,false,true));
            p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE,100,0,true,false,true));
        }
        if(getDay() >= 35){
            health -= 12;
        }
        health += PlayerData.getExtraHealth(p);
        health -= PlayerData.getNegativeHealth(p);
        speed += PlayerData.getExtraSpeed(p);
        p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
        p.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(speed);
        if(p.getGameMode() == GameMode.SURVIVAL) {
            InventoryUtils.lockPlayerSlots(p);
            //Si el jugador tiene elytras puestas, esta volando Y tiene cooldown en las elytras, se cancela su vuelo completamente
            //Esto es parte de la habilidad del Antiair Commander
            if(p.getInventory().getChestplate() != null) {
                if (p.getInventory().getChestplate().getType() == Material.ELYTRA) {
                    if (p.hasCooldown(Material.ELYTRA)) {
                        if (p.getPose() == Pose.FALL_FLYING) {
                            p.setPose(Pose.STANDING);
                        }
                    }
                }
            }
            if(getDay() >= 7) {
                int radius = 0;
                if(getDay() >= 7 && getDay() < 21){
                    radius = 3;
                }else if(getDay() >= 21 && getDay() < 28){
                    radius = 6;
                }else if(getDay() >= 28 && getDay() < 42){
                    radius = 9;
                }else if(getDay() >= 42){
                    radius = 14;
                }
                for (Entity entity : p.getNearbyEntities(radius, radius, radius)) {
                    if (entity instanceof Enderman e) {
                        if(!e.getWorld().getName().equalsIgnoreCase("world_the_end")) {
                            if (e.getTarget() == null) {
                                e.setTarget(p);
                            }
                        }
                    }
                }
            }
            if (getDay() >= 14) {
                if (p.getWorld().getName().equals("world_wasteyard")) {
                    if (getFullSet(p, 69)) return;
                    if (wast_darkness < 2500) {
                        wast_darkness++;
                    } else {
                        if (wast_caution > 0) {
                            p.sendMessage(ChatUtils.format(ChatUtils.prefix + " &cLas nieblas oscuras cubriran la Wasteyard en " + wast_caution));
                            wast_caution--;
                            wast_darkness = 2400;
                        } else {
                            p.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS, 200, 0, false, false, false));
                            wast_caution = 5;
                            wast_darkness = 0;
                        }
                    }
                }

                if (p.isOnGround() && !p.isUnderWater() && !p.isInWater() && p.getPose() == Pose.SWIMMING) {
                    if (cram_interval < 5) {
                        cram_interval++;
                    } else {
                        cram_interval = 0;
                        if (getDay() >= 28) {
                            ((CraftPlayer) p).getHandle().a(((CraftPlayer) p).getHandle().dN().f(), 99.0F);
                        }else{
                            ((CraftPlayer) p).getHandle().a(((CraftPlayer) p).getHandle().dN().f(), 5.0F);
                        }
                    }
                }
                if (p.getWorld().getEnvironment() == World.Environment.NORMAL) {
                    if (getMonsoon_active().equalsIgnoreCase("true") && !getTyphoonactive().equalsIgnoreCase("true")) {
                        Location block = p.getWorld().getHighestBlockAt(p.getLocation().clone()).getLocation();
                        int highestY = block.getBlockY();
                        if (highestY < p.getLocation().getY() && chance <= 5) {
                            p.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS, 200, 0, false, false, false));
                        }
                    } else if ((getMonsoon_active().equalsIgnoreCase("true") && getTyphoonactive().equalsIgnoreCase("true") && getDay() < 42) || (getDay() >= 42 && getMonsoon_active().equalsIgnoreCase("true")) ) {
                        Location block = p.getWorld().getHighestBlockAt(p.getLocation().clone()).getLocation();
                        int highestY = block.getBlockY();
                        if (highestY < p.getLocation().getY() && chance <= 5) {
                            int effect = getRandomValue(4);
                            switch (effect) {
                                case 0 -> {
                                    p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 0, true, false, true));
                                }
                                case 1 -> {
                                    p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 200, 2, true, false, true));
                                }
                                case 2 -> {
                                    p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 0, true, false, true));
                                }
                                case 3 -> {
                                    p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 200, 4, true, false, true));
                                }
                            }
                        }
                    }
                }
            }
            if (getDay() >= 21) {
                if (p.getLocation().subtract(0, 1, 0).getBlock().getType() == Material.SOUL_SAND || p.getLocation().subtract(0, 1, 0).getBlock().getType() == Material.SOUL_SOIL) {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 0));
                }
                if ((getMonsoon_active().equalsIgnoreCase("true") || getDay() < 42) || getDay() >= 42) {
                    if (p.getLocation().subtract(0, 1, 0).getBlock().getType().name().toLowerCase().contains("leaves")) {
                        if(getDay() >= 42){
                            p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 2));
                            p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 0));
                            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 1));
                        }else{
                            p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 2));
                        }
                    }
                }
            }
            if(getDay() >= 28){
                if (getMonsoon_active().equalsIgnoreCase("true")) {
                    if (p.getLocation().subtract(0, 1, 0).getBlock().getType() == Material.BEDROCK) {
                        p.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 100, 9));
                    }
                    if(p.getWorld().getName().equalsIgnoreCase("world_nether")){
                        int gag = GenericUtils.getRandomValue(10000);
                        if(gag == 1){
                            p.setFireTicks(200);
                        }
                    }
                }
                if(p.getLocation().getBlock().getTemperature() >= 0.9){
                    if(temperature < 1200){
                        temperature++;
                    }else{
                        p.setFireTicks(100);
                    }
                }else if(p.getLocation().getBlock().getTemperature() <= 0.05){
                    if(temperature > -1200){
                        temperature--;
                    }else{
                        p.setFreezeTicks(200);
                    }
                }else{
                    temperature = 0;
                }
            }
            if(getDay() >= 35){
                if(p.isInWater() || p.isUnderWater()){
                    if(freezeticks < 800){
                        if(getDay() >= 42){
                            freezeticks += 2;
                        }else{
                            freezeticks++;
                        }
                    }else if(freezeticks >= 800){
                        freezeticks = 801;
                        p.setFreezeTicks(200);
                    }
                }else{
                    freezeticks = 0;
                }
                if ((getMonsoon_active().equalsIgnoreCase("true") && getDay() < 42) || getDay() >= 42) {
                    if (p.getLocation().subtract(0, 1, 0).getBlock().getType().name().toLowerCase().contains("slab")) {
                        ((CraftPlayer) p).getHandle().a(((CraftPlayer) p).getHandle().dN().f(), 99.0F);
                    }
                }
            }

            if(getDay() >= 42){
                if (p.getLocation().getBlock().getType().name().toLowerCase().contains("mushroom")) {
                    p.getWorld().createExplosion(p.getLocation(),5);
                }
            }

            //Handle mob spawn
            if(getDay() >= 35){
                if((getMonsoon_active().equalsIgnoreCase("true") && !(getDay() >= 42)) || getDay() >= 42) {
                    Location l = p.getLocation().clone();
                    if (random.nextInt(5) == 0) {
                        int pX = (random.nextBoolean() ? -1 : 1) * (random.nextInt(15)) + 15;
                        int pZ = (random.nextBoolean() ? -1 : 1) * (random.nextInt(15)) + 15;
                        int y = (int) l.getY() - 1;
                        Block block = l.getWorld().getBlockAt(l.getBlockX() + pX, y, l.getBlockZ() + pZ);
                        Block up = block.getRelative(BlockFace.UP);
                        if (block.getType() != Material.AIR && up.getType() == Material.AIR) {
                            if(
                                    ((isBlockInAWorld(block, "world", "glass", "glowstone", "obsidian")
                                    ||
                                    isBlockInAWorld(
                                            block,
                                            "world_nether",
                                            "glass", "glowstone", "obsidian", "ice", "bedrock")) && getDay() < 42) ||
                                            ((isBlock(block,"glass","glowstone","obsidian","ice","bedrock","leaves")) && getDay() >= 42)
                            ) {
                                //System.out.println("MobRain.initMobs(up.getLocation());");
                                MobRain.initMobs(up.getLocation());
                            }
                        }
                    }
                }
            }
        }
    }

    private static boolean isBlockInAWorld(Block block, String world, String... blocks){
        if(block.getLocation().getWorld().getName().equalsIgnoreCase(world))
            for(String b : blocks)
                if(block.getType().name().toLowerCase().contains(b))
                    return true;
        return false;
    }
    private static boolean isBlock(Block block, String... blocks){
            for(String b : blocks)
                if(block.getType().name().toLowerCase().contains(b))
                    return true;
        return false;
    }


    public static boolean getFullSet(Player p,int custommodeldata){
        if(p.getInventory().getHelmet() != null && p.getInventory().getChestplate() != null && p.getInventory().getLeggings() != null && p.getInventory().getBoots() != null){
            if(p.getInventory().getHelmet().hasItemMeta() && p.getInventory().getChestplate().hasItemMeta() && p.getInventory().getLeggings().hasItemMeta() && p.getInventory().getBoots().hasItemMeta()){
                if(p.getInventory().getHelmet().getItemMeta().hasCustomModelData() && p.getInventory().getChestplate().getItemMeta().hasCustomModelData() && p.getInventory().getLeggings().getItemMeta().hasCustomModelData() && p.getInventory().getBoots().getItemMeta().hasCustomModelData()){
                    return p.getInventory().getHelmet().getItemMeta().getCustomModelData() == custommodeldata && p.getInventory().getChestplate().getItemMeta().getCustomModelData() == custommodeldata && p.getInventory().getLeggings().getItemMeta().getCustomModelData() == custommodeldata && p.getInventory().getBoots().getItemMeta().getCustomModelData() == custommodeldata;
                }
            }
        }
        return false;
    }
}

