package com.tll3.Lists.CustomEntities;

import net.minecraft.tags.TagKey;
import net.minecraft.tags.TagsFluid;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.World;
import org.bukkit.entity.LivingEntity;

import java.util.EnumSet;

public class CustomFox extends EntityFox {
    private PathfinderGoalNearestAttackableTarget ck;

    public CustomFox(World world){
        super(EntityTypes.O,world);
        ((LivingEntity) this.getBukkitEntity()).setRemoveWhenFarAway(true);
        this.getBukkitEntity().setPersistent(false);
        this.persist = false;
    }

    @Override
    protected void B() {
        this.ck = new PathfinderGoalNearestAttackableTarget<>(this, EntityHuman.class, true);
        this.bO.a(0, new PathfinderGoalFloat(this));
        this.bO.a(0, new ClimbOnTopOfPowderSnowGoal(this, this.dM()));
        this.bO.a(6, new o());
        this.bO.a(1, new PathfinderGoalMeleeAttack(this, 1.2000000476837158, true));
        this.bO.a(10, new PathfinderGoalLeapAtTarget(this, 0.4F));
        this.bO.a(11, new PathfinderGoalRandomStrollLand(this, 1.0));
        this.bO.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 24.0F));
    }

 }
