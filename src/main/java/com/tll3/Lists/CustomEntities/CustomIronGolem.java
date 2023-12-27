package com.tll3.Lists.CustomEntities;

import com.tll3.Misc.ChatUtils;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalDefendVillage;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalHurtByTarget;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalUniversalAngerReset;
import net.minecraft.world.entity.animal.EntityIronGolem;
import net.minecraft.world.entity.monster.EntityCreeper;
import net.minecraft.world.entity.monster.IMonster;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.World;

/*
* Sin usar por ahora, no puedo encontrar una manera de que despawneen naturalmente porque son iron golems y no aceptan el removeWhenFarAway();
* */
public class CustomIronGolem extends EntityIronGolem {
    public CustomIronGolem(World world){
        super(EntityTypes.ad,world);
        this.getBukkitEntity().setCustomName(ChatUtils.format("&4Enraged Iron Golem"));
        this.persist = false;
    }
    @Override
    protected void B() {
        this.bO.a(1, new PathfinderGoalMeleeAttack(this, 1.0, true));
        this.bO.a(2, new PathfinderGoalMoveTowardsTarget(this, 0.9, 32.0F));
        this.bO.a(7, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
        this.bO.a(8, new PathfinderGoalRandomLookaround(this));
        this.bP.a(2, new PathfinderGoalHurtByTarget(this, new Class[0]));
        this.bP.a(3,new PathfinderGoalNearestAttackableTarget<>(this, EntityHuman.class, true));
        this.bP.a(4, new PathfinderGoalUniversalAngerReset(this, false));
    }
}
