package com.tll3.Lists.CustomEntities.CustomProjectiles;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.animal.horse.EntityLlama;
import net.minecraft.world.entity.projectile.EntityLlamaSpit;
import net.minecraft.world.level.World;
import net.minecraft.world.phys.MovingObjectPositionEntity;

public class CustomLlamaSpit extends EntityLlamaSpit {
    private double damage;
    public CustomLlamaSpit(World w, EntityLlama entityLlama,double damage){
        super(w,entityLlama);
        this.damage = damage;
    }
    @Override
    protected void a(MovingObjectPositionEntity movingobjectpositionentity) {
        super.a(movingobjectpositionentity);
        Entity entity = this.w();
        if (entity instanceof EntityLiving entityliving) {
            movingobjectpositionentity.a().a(this.dN().a(this, entityliving), (float) damage);
        }

    }
}
