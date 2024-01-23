package com.tll3.Lists.CustomEntities;

import com.tll3.Misc.ChatUtils;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.attributes.AttributeBase;
import net.minecraft.world.entity.ai.attributes.AttributeMapBase;
import net.minecraft.world.entity.ai.attributes.AttributeModifiable;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;
import net.minecraft.world.entity.animal.EntityPig;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_20_R3.attribute.CraftAttribute;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityPotionEffectEvent;

import java.lang.reflect.Field;
import java.util.Map;

import static com.tll3.Misc.GenericUtils.getDay;

public class CustomPig extends EntityPig {
    private static Field attributeMap;
    public CustomPig(World w){
        super(EntityTypes.aw,w);
        this.getBukkitEntity().setCustomName(ChatUtils.format("#7a3d5bCerdo Rabioso"));
        this.getBukkitEntity().setPersistent(false);
        this.addEffect(new MobEffect(MobEffects.a,Integer.MAX_VALUE,1), EntityPotionEffectEvent.Cause.PLUGIN);
        this.persist = false;
        try {
            registerGenericAttribute(this.getBukkitEntity(), Attribute.GENERIC_ATTACK_DAMAGE);
            registerGenericAttribute(this.getBukkitEntity(),Attribute.GENERIC_ATTACK_KNOCKBACK);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        this.craftAttributes.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(35);
        ((LivingEntity)this.getBukkitEntity()).setHealth(35);
        this.craftAttributes.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(50);
        this.craftAttributes.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.5);
    }
    @Override
    protected void B(){
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
    @Override
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
