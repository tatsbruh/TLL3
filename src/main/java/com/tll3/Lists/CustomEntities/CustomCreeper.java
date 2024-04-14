package com.tll3.Lists.CustomEntities;

import com.tll3.Lists.CustomEntities.CustomPathfinders.NewSwellGoal;
import com.tll3.Misc.ChatUtils;
import com.tll3.Misc.EntityHelper;
import net.minecraft.util.datafix.fixes.MobEffectIdFix;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityTypes;

import net.minecraft.world.entity.ai.attributes.AttributeBase;
import net.minecraft.world.entity.ai.attributes.AttributeMapBase;
import net.minecraft.world.entity.ai.attributes.AttributeModifiable;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalHurtByTarget;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;
import net.minecraft.world.entity.animal.EntityCat;
import net.minecraft.world.entity.animal.EntityOcelot;
import net.minecraft.world.entity.monster.EntityCreeper;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.World;
import net.minecraft.world.level.pathfinder.PathType;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_20_R3.attribute.CraftAttribute;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.potion.PotionEffectType;

import java.lang.reflect.Field;
import java.util.Map;

public class CustomCreeper extends EntityCreeper {
    private static Field attributeMap;
    public CustomCreeper(World world) {

        super(EntityTypes.v, world);
        this.getBukkitEntity().setCustomName(ChatUtils.format("#4000ffZenith Supernova"));
        EntityHelper.addPotionEffect((LivingEntity) this.getBukkitEntity(), PotionEffectType.FIRE_RESISTANCE, 0);
        EntityHelper.addPotionEffect((LivingEntity) this.getBukkitEntity(), PotionEffectType.INVISIBILITY, 0);
        EntityHelper.addPotionEffect((LivingEntity) this.getBukkitEntity(), PotionEffectType.SPEED, 2);
        EntityHelper.setMobHealth((LivingEntity) this.getBukkitEntity(),20);
        EntityHelper.setIdentifierString(this.getBukkitEntity(),"zenithcreeper");
        EntityHelper.setIdentifierString(this.getBukkitEntity(),"zenith");
        this.setPowered(true);
        this.bV = 50; //Explosion Radius
        this.bU = 20; //Fuse ticks
        this.getBukkitEntity().setPersistent(false);
        this.persist = false;
        try { // register follow range attributes
            registerGenericAttribute(this.getBukkitEntity(), Attribute.GENERIC_FOLLOW_RANGE);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        this.craftAttributes.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(120);
        this.a(PathType.j, 0.0F);
        this.a(PathType.i, 0.0F);
        this.a(PathType.n, 0.0F);
        this.a(PathType.o, 0.0F);
    }

    @Override
    protected void B() {
        this.bO.a(1, new PathfinderGoalFloat(this));
        this.bO.a(2, new NewSwellGoal(this)); //New pathfinder (modifies Fuse Distance, Line of Sight)
        //No avoiding ocelots or cats
        this.bO.a(4, new PathfinderGoalMeleeAttack(this, 1.0, false));
        this.bO.a(5, new PathfinderGoalRandomStrollLand(this, 0.8));
        this.bO.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.bO.a(6, new PathfinderGoalRandomLookaround(this));
        this.bP.a(1, new PathfinderGoalNearestAttackableTarget<>(this, EntityHuman.class, true));
        this.bP.a(2, new PathfinderGoalHurtByTarget(this, new Class[0]));
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
