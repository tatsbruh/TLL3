package com.tll3.Lists.CustomEntities;

import com.tll3.Listeners.EntityNaturalSpawn;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalHurtByTarget;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;
import net.minecraft.world.entity.animal.EntityDolphin;
import net.minecraft.world.entity.monster.EntityGuardian;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityPotionEffectEvent;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class CustomDolphin extends EntityDolphin {
    public CustomDolphin(World world){
        super(EntityTypes.w,world);
        EntityNaturalSpawn.setCustomMobcap((LivingEntity) this.getBukkitEntity(),3,1.24,60,30,true);
    }
    @Override
    protected void B() {
        this.bO.a(0, new PathfinderGoalBreath(this));
        this.bO.a(0, new PathfinderGoalWater(this));
        this.bO.a(1, new PathfinderGoalMeleeAttack(this, 1.0, true));
        this.bO.a(2, new PathfinderGoalMoveTowardsTarget(this, 0.9, 32.0F));
        this.bO.a(4, new PathfinderGoalRandomSwim(this, 1.0, 10));
        this.bO.a(4, new PathfinderGoalRandomLookaround(this));
        this.bO.a(5, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
        this.bO.a(5, new PathfinderGoalWaterJump(this, 10));
        this.bO.a(8, new PathfinderGoalFollowBoat(this));
        this.bP.a(2, new PathfinderGoalHurtByTarget(this, new Class[0]));
        this.bP.a(3,new PathfinderGoalNearestAttackableTarget<>(this, EntityHuman.class, true));
    }

}
