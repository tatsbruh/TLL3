package com.tll3.Lists.CustomEntities;

import com.tll3.Misc.ChatUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.tags.TagsBlock;
import net.minecraft.util.MathHelper;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.attributes.AttributeBase;
import net.minecraft.world.entity.ai.attributes.AttributeMapBase;
import net.minecraft.world.entity.ai.attributes.AttributeModifiable;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.animal.EntityParrot;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.BlockLeaves;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_20_R3.attribute.CraftAttribute;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;

public class CustomParrot extends EntityParrot {
    private static Field attributeMap;

    public CustomParrot(World world) {
        super(EntityTypes.au, world);
        this.getBukkitEntity().setCustomName(ChatUtils.format("#70503eDustseeker"));
        ((LivingEntity) this.getBukkitEntity()).setRemoveWhenFarAway(true);
        this.getBukkitEntity().setPersistent(false);
        this.persist = false;
        try { // register follow range attributes
            registerGenericAttribute(this.getBukkitEntity(), Attribute.GENERIC_ATTACK_DAMAGE);
            registerGenericAttribute(this.getBukkitEntity(), Attribute.GENERIC_ATTACK_KNOCKBACK);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        this.craftAttributes.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(25);
        ((LivingEntity) this.getBukkitEntity()).setHealth(25);
        this.craftAttributes.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(20);
        this.craftAttributes.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.5);
        this.craftAttributes.getAttribute(Attribute.GENERIC_ATTACK_KNOCKBACK).setBaseValue(5);
    }

    @Override
    protected void B() {
        this.bO.a(0, new PathfinderGoalPanic(this, 1.25));
        this.bO.a(0, new PathfinderGoalFloat(this));
        this.bO.a(1, new PathfinderGoalMeleeAttack(this, 1.0, true));
        this.bO.a(2, new PathfinderGoalMoveTowardsTarget(this, 0.9, 32.0F));
        this.bO.a(5, new PathfinderGoalRandomStrollLand(this, 1.0));
        this.bO.a(7, new PathfinderGoalRandomLookaround(this));
        this.bP.a(3,new PathfinderGoalNearestAttackableTarget<>(this, EntityHuman.class, true));
        this.bO.a(1, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.bO.a(2, new a(this, 1.0));
        this.bO.a(3, new PathfinderGoalFollowEntity(this, 1.0, 3.0F, 7.0F));
    }

    static {
        try {
            attributeMap = AttributeMapBase.class.getDeclaredField("b");
            attributeMap.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public void registerGenericAttribute(org.bukkit.entity.Entity entity, Attribute attribute) throws IllegalAccessException {
        AttributeMapBase attributeMapBase = ((CraftLivingEntity) entity).getHandle().eR();
        Map<AttributeBase, AttributeModifiable> map = (Map<AttributeBase, AttributeModifiable>) attributeMap.get(attributeMapBase);
        AttributeBase attributeBase = CraftAttribute.bukkitToMinecraft(attribute);
        AttributeModifiable attributeModifiable = new AttributeModifiable(attributeBase, AttributeModifiable::e);
        map.put(attributeBase, attributeModifiable);
    }
    private static class a extends PathfinderGoalRandomFly {
        public a(EntityCreature entitycreature, double d0) {
            super(entitycreature, d0);
        }

        protected @Nullable Vec3D h() {
            Vec3D vec3d = null;
            if (this.b.aZ()) {
                vec3d = LandRandomPos.a(this.b, 15, 15);
            }

            if (this.b.eg().i() >= this.j) {
                vec3d = this.k();
            }

            return vec3d == null ? super.h() : vec3d;
        }

        private @Nullable Vec3D k() {
            BlockPosition blockposition = this.b.dm();
            BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();
            BlockPosition.MutableBlockPosition blockposition_mutableblockposition1 = new BlockPosition.MutableBlockPosition();
            Iterable<BlockPosition> iterable = BlockPosition.b(MathHelper.a(this.b.dr() - 3.0), MathHelper.a(this.b.dt() - 6.0), MathHelper.a(this.b.dx() - 3.0), MathHelper.a(this.b.dr() + 3.0), MathHelper.a(this.b.dt() + 6.0), MathHelper.a(this.b.dx() + 3.0));
            Iterator iterator = iterable.iterator();

            BlockPosition blockposition1;
            boolean flag;
            do {
                do {
                    if (!iterator.hasNext()) {
                        return null;
                    }

                    blockposition1 = (BlockPosition)iterator.next();
                } while(blockposition.equals(blockposition1));

                IBlockData iblockdata = this.b.dM().a_(blockposition_mutableblockposition1.a(blockposition1, EnumDirection.a));
                flag = iblockdata.b() instanceof BlockLeaves || iblockdata.a(TagsBlock.t);
            } while(!flag || !this.b.dM().u(blockposition1) || !this.b.dM().u(blockposition_mutableblockposition.a(blockposition1, EnumDirection.b)));

            return Vec3D.c(blockposition1);
        }
    }

}
