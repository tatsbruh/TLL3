package com.tll3.Lists.CustomEntities;

import net.minecraft.util.MathHelper;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalHurtByTarget;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;
import net.minecraft.world.entity.animal.EntityPanda;
import net.minecraft.world.entity.monster.EntityMonster;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.crafting.RecipeItemStack;
import net.minecraft.world.level.IMaterial;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Blocks;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityTargetEvent;

import java.util.EnumSet;

public class CustomPanda extends EntityPanda {
    public CustomPanda(World world){
        super(EntityTypes.at,world);
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



}
