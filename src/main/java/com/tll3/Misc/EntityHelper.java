package com.tll3.Misc;

import com.tll3.Misc.DataManager.Data;
import net.minecraft.world.entity.ai.attributes.AttributeMapBase;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftLivingEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.Random;

public class EntityHelper {
    //A easier way to setup entities, instead of going in annoying ways
    public static void setName(LivingEntity entity, String name){
        entity.setCustomName(ChatUtils.format(name));
    }

    public static void setIdentifierString(PersistentDataHolder entity, String id){
        Data.set(entity, id, PersistentDataType.STRING, id);
    }
    public static void setIdentifierInt(PersistentDataHolder entity, String id,int i){
        Data.set(entity, id, PersistentDataType.INTEGER, i);
    }

    public static boolean entityHasIdentifier(PersistentDataHolder entity, String id){
        return Data.has(entity, id, PersistentDataType.STRING);
    }

    public static void addPotionEffect(LivingEntity entity, PotionEffectType type, int amplifier) {
        entity.addPotionEffect(new PotionEffect(type, Integer.MAX_VALUE, amplifier,false,false,false));
    }

    public static void setHead(LivingEntity entity, ItemStack itemStack){
        Objects.requireNonNull(entity.getEquipment()).setHelmet(itemStack,true);
        entity.getEquipment().setHelmetDropChance(0);
    }

    public static void setChestplate(LivingEntity entity, ItemStack itemStack){
        Objects.requireNonNull(entity.getEquipment()).setChestplate(itemStack,true);
        entity.getEquipment().setChestplateDropChance(0);
    }

    public static void setLeggings(LivingEntity entity, ItemStack itemStack){
        Objects.requireNonNull(entity.getEquipment()).setLeggings(itemStack,true);
        entity.getEquipment().setLeggingsDropChance(0);
    }

    public static void setBoots(LivingEntity entity, ItemStack itemStack){
        Objects.requireNonNull(entity.getEquipment()).setBoots(itemStack,true);
        entity.getEquipment().setBootsDropChance(0);
    }

    public static void setMainHand(LivingEntity entity, ItemStack itemStack){
        Objects.requireNonNull(entity.getEquipment()).setItemInMainHand(itemStack,true);
        entity.getEquipment().setItemInMainHandDropChance(0);
    }

    public static void setOffhand(LivingEntity entity, ItemStack itemStack){
        Objects.requireNonNull(entity.getEquipment()).setItemInOffHand(itemStack,true);
        entity.getEquipment().setItemInOffHandDropChance(0);
    }

    public static void setMobHealth(LivingEntity entity,int health){
        Objects.requireNonNull(entity.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(health);
        entity.setHealth(health);
    }

    public static void setMobDamage(LivingEntity entity,int damage){
        Objects.requireNonNull(entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE)).setBaseValue(damage);
    }
    public static void setMobRange(LivingEntity entity,int range){
        Objects.requireNonNull(entity.getAttribute(Attribute.GENERIC_FOLLOW_RANGE)).setBaseValue(range);
    }

    public static void setKnockresist(LivingEntity entity,int amount){
        Objects.requireNonNull(entity.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE)).setBaseValue(amount);
    }

    public static void teleportEnderman(Entity e, int locX, int locY, int locZ, World world, double range) {
        for (int i = 0; i < 64; ++i) {
            if (eq(e, locX, locY, locZ, world,range)) {
                return;
            }
        }
    }
    private static boolean eq(Entity e, int locX, int locY, int locZ, World world,double range) {
        Random random = new Random();
        //For reference, basic radius is 64.0D
        double x = locX + (random.nextDouble() - 0.5D) * range;
        double y = locY + (double) (random.nextInt(64) - 32);
        double z = locZ + (random.nextDouble() - 0.5D) * range;

        Block b = world.getBlockAt((int)x, (int)y, (int)z);

        while (b.getY() > 0 && b.getType().isAir()) b = b.getRelative(BlockFace.DOWN);

        if (b.getY() <= 0) return false;
        if(b.isSolid()) return false;
        return e.teleport(new Location(world, x, b.getY() + 1, z));
    }


}
