package com.tll3.Lists.CustomEntities.CustomProjectiles;

import com.google.common.base.MoreObjects;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.particles.Particles;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.projectile.EntityShulkerBullet;
import net.minecraft.world.level.World;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.MovingObjectPositionEntity;
import org.bukkit.event.entity.EntityPotionEffectEvent;

public class CustomShulkerBullet extends EntityShulkerBullet {
    public CustomShulkerBullet(World world, EntityLiving entityliving, Entity entity, EnumDirection.EnumAxis enumdirection_enumaxis) {
        super(world, entityliving, entity, enumdirection_enumaxis);
    }

    @Override
    protected void a(MovingObjectPositionEntity movingobjectpositionentity) {
        super.a(movingobjectpositionentity);
        Entity entity = movingobjectpositionentity.a();
        Entity entity1 = this.w();
        EntityLiving entityliving = entity1 instanceof EntityLiving ? (EntityLiving)entity1 : null;
        boolean flag = entity.a(this.dN().a(this, entityliving), 4.0F);
        if (flag) {
            this.a(entityliving, entity);
            if (entity instanceof EntityLiving) {
                EntityLiving entityliving1 = (EntityLiving)entity;
                entityliving1.addEffect(new MobEffect(MobEffects.y, 100,4), (Entity) MoreObjects.firstNonNull(entity1, this), EntityPotionEffectEvent.Cause.ATTACK); //Aplican Levitacion 5 por 5 seg
                entityliving1.addEffect(new MobEffect(MobEffects.z, 400), (Entity) MoreObjects.firstNonNull(entity1, this), EntityPotionEffectEvent.Cause.ATTACK); //Aplican Maldicion por 20 seg
            }
        }

    }

    @Override
    protected void a(MovingObjectPositionBlock movingobjectpositionblock) {
        super.a(movingobjectpositionblock);
        this.dM().a(this, this.dr(), this.dt(), this.dx(), 2.5F, false, World.a.c);
        this.a(SoundEffects.vR, 1.0F, 1.0F);
    }
}
