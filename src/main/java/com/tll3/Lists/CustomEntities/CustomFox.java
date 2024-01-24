package com.tll3.Lists.CustomEntities;

import com.tll3.Misc.GenericUtils;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.TagsFluid;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;

import java.util.EnumSet;

public class CustomFox extends EntityFox {


    public CustomFox(World world){
        super(EntityTypes.O,world);
        ((LivingEntity) this.getBukkitEntity()).setRemoveWhenFarAway(true);
        this.getBukkitEntity().setPersistent(false);
        this.persist = false;
        if(GenericUtils.getDay() >= 21){
            this.craftAttributes.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(40);
        }
    }

    @Override
    protected void B() {
        this.bO.a(0, new PathfinderGoalFloat(this));
        this.bO.a(0, new ClimbOnTopOfPowderSnowGoal(this, this.dM()));
        this.bO.a(2, new PathfinderGoalMoveTowardsTarget(this, 0.9, 32.0F));
        this.bO.a(6, new o());
        this.bO.a(1, new PathfinderGoalMeleeAttack(this, 1.0, true));
        this.bO.a(10, new PathfinderGoalLeapAtTarget(this, 0.4F));
        this.bO.a(11, new PathfinderGoalRandomStrollLand(this, 1.0));
        this.bO.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 24.0F));
        this.bP.a(3,new PathfinderGoalNearestAttackableTarget<>(this, EntityHuman.class, true));
    }

 }
