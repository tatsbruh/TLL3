package com.tll3.Lists.CustomEntities;

import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalHurtByTarget;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalUniversalAngerReset;
import net.minecraft.world.entity.animal.EntityFox;
import net.minecraft.world.entity.animal.EntityPolarBear;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.World;
import org.bukkit.entity.LivingEntity;

import java.util.function.Predicate;

public class CustomPolarBear extends EntityPolarBear {
    public CustomPolarBear(World world){
        super(EntityTypes.aA,world);
        ((LivingEntity) this.getBukkitEntity()).setRemoveWhenFarAway(true);
        this.getBukkitEntity().setPersistent(false);
        this.persist = false;
    }

    @Override
    protected void B() {
        super.B();
        this.bO.a(0, new PathfinderGoalFloat(this));
        this.bO.a(1, new PathfinderGoalMeleeAttack(this, 1.0, true));
        this.bO.a(4, new PathfinderGoalFollowParent(this, 1.25));
        this.bO.a(5, new PathfinderGoalRandomStroll(this, 1.0));
        this.bO.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
        this.bO.a(7, new PathfinderGoalRandomLookaround(this));
        this.bP.a(2, new PathfinderGoalHurtByTarget(this, new Class[0]));
        this.bP.a(3, new PathfinderGoalNearestAttackableTarget<>(this, EntityHuman.class, true));
        this.bP.a(5, new PathfinderGoalUniversalAngerReset<>(this, false));
    }


}
