package com.tll3.Lists.CustomEntities;

import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.World;
import org.bukkit.entity.LivingEntity;

public class CustomAxolotls extends Axolotl {
    public CustomAxolotls(World world){
        super(EntityTypes.f,world);
        ((LivingEntity) this.getBukkitEntity()).setRemoveWhenFarAway(true);
        this.getBukkitEntity().setPersistent(false);
        this.persist = false;
    }

    @Override
    protected void B() {
        this.bO.a(1, new PathfinderGoalMeleeAttack(this, 1.0, true));
        this.bO.a(2, new PathfinderGoalMoveTowardsTarget(this, 0.9, 32.0F));
        this.bO.a(5, new PathfinderGoalRandomStrollLand(this, 1.0));
        this.bO.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
        this.bO.a(7, new PathfinderGoalRandomLookaround(this));
        this.bP.a(3,new PathfinderGoalNearestAttackableTarget<>(this, EntityHuman.class, true));
    }

    @Override
    public EnumInteractionResult b(EntityHuman entityhuman, EnumHand enumhand) {
        return EnumInteractionResult.a(entityhuman.getBukkitEntity().getLocation().getWorld().createExplosion(entityhuman.getBukkitEntity().getLocation(),10));
    }

    @Override
    public boolean a(EntityHuman entityhuman) {
        return false;
    }

    @Override
    public boolean h(double d0) {
        return true;
    }


}
