package com.tll3.Lists.CustomEntities;

import com.tll3.Listeners.EntityNaturalSpawn;
import com.tll3.Misc.ChatUtils;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.attributes.AttributeBase;
import net.minecraft.world.entity.ai.attributes.AttributeMapBase;
import net.minecraft.world.entity.ai.attributes.AttributeModifiable;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;
import net.minecraft.world.entity.animal.EntityChicken;
import net.minecraft.world.entity.monster.EntityEvoker;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.World;
import net.minecraft.world.level.pathfinder.PathType;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_20_R3.attribute.CraftAttribute;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityRegainHealthEvent;

import java.lang.reflect.Field;
import java.util.Map;

import static com.tll3.Misc.GenericUtils.getDay;

public class CustomChicken extends EntityChicken {
    private static Field attributeMap;
    public CustomChicken(World world){
        super(EntityTypes.r,world);
        this.getBukkitEntity().setCustomName(ChatUtils.format("#ebb434Cockatrice"));
        this.getBukkitEntity().setPersistent(false);
        this.persist = false;
        try { // register follow range attributes
            registerGenericAttribute(this.getBukkitEntity(), Attribute.GENERIC_ATTACK_DAMAGE);
            registerGenericAttribute(this.getBukkitEntity(),Attribute.GENERIC_ATTACK_KNOCKBACK);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        this.craftAttributes.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(15);
        ((LivingEntity)this.getBukkitEntity()).setHealth(15);
        if(getDay() >= 14){
            this.craftAttributes.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(45);
        }else{
            this.craftAttributes.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(10);
        }
        this.craftAttributes.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.5);
        EntityNaturalSpawn.setCustomMobcap((LivingEntity) this.getBukkitEntity(),3,1.24,60,30,true);
    }
    @Override
    protected void B() {
        this.bO.a(0, new PathfinderGoalFloat(this));
        this.bO.a(1, new PathfinderGoalMeleeAttack(this, 1.0, true));
        this.bO.a(2, new PathfinderGoalMoveTowardsTarget(this, 0.9, 32.0F));
        this.bO.a(5, new PathfinderGoalRandomStrollLand(this, 1.0));
        this.bO.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
        this.bO.a(7, new PathfinderGoalRandomLookaround(this));
        this.bP.a(3,new PathfinderGoalNearestAttackableTarget<>(this, EntityHuman.class, true));
    }
    static {
        try {
            attributeMap = AttributeMapBase.class.getDeclaredField("b");
            attributeMap.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
    @Override //Can fall from any distance
    public int cu(){
        return Integer.MAX_VALUE;
    }
    public void registerGenericAttribute(org.bukkit.entity.Entity entity, Attribute attribute) throws IllegalAccessException {
        AttributeMapBase attributeMapBase = ((CraftLivingEntity)entity).getHandle().eR();
        Map<AttributeBase, AttributeModifiable> map = (Map<AttributeBase, AttributeModifiable>)attributeMap.get(attributeMapBase);
        AttributeBase attributeBase = CraftAttribute.bukkitToMinecraft(attribute);
        AttributeModifiable attributeModifiable = new AttributeModifiable(attributeBase, AttributeModifiable::e);
        map.put(attributeBase, attributeModifiable);
    }
}
