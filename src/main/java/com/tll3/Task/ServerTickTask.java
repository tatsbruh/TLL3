package com.tll3.Task;

import com.tll3.Misc.DataManager.PlayerData;
import com.tll3.Misc.GenericUtils;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageType;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.entity.Pose;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BoundingBox;

import static com.tll3.Misc.GenericUtils.getDay;
import static com.tll3.Misc.GenericUtils.*;

public class ServerTickTask extends BukkitRunnable {
    private final Player p;
    int cram_interval = 0;

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
        health += PlayerData.getExtraHealth(p);
        speed += PlayerData.getExtraSpeed(p);
        p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
        p.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(speed);
        if(p.getGameMode() == GameMode.SURVIVAL){
        if(getDay() >= 14){
            if(p.isOnGround() && !p.isUnderWater() && !p.isInWater() && p.getPose() == Pose.SWIMMING){
                if(cram_interval < 5){
                    cram_interval++;
                }else{
                    cram_interval = 0;
                    ((CraftPlayer)p).getHandle().a(((CraftPlayer)p).getHandle().dN().f(),5.0F);
                }
            }
            if(getMonsoon_active().equalsIgnoreCase("true")){
            Location block = p.getWorld().getHighestBlockAt(p.getLocation().clone()).getLocation();
            int highestY = block.getBlockY();
            if (highestY < p.getLocation().getY() && chance <= 5) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS,200,0,false,false,false));
            }
            }
        }
        }

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

    public boolean Ok(Player player) {
        BoundingBox boundingBox = player.getBoundingBox();
        double width = boundingBox.getWidthX();
        double height = boundingBox.getHeight();
        double length = boundingBox.getWidthZ();
        double epsilon = 0.1;
        return Math.abs(width - 1.0) < epsilon && Math.abs(height - 1.0) < epsilon && Math.abs(length - 1.0) < epsilon;
    }
}

