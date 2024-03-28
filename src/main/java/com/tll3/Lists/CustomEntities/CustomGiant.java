package com.tll3.Lists.CustomEntities;

import com.tll3.Misc.EntityHelper;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalHurtByTarget;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;
import net.minecraft.world.entity.animal.EntityIronGolem;
import net.minecraft.world.entity.animal.EntityTurtle;
import net.minecraft.world.entity.monster.EntityGiantZombie;
import net.minecraft.world.entity.monster.EntityPigZombie;
import net.minecraft.world.entity.monster.EntityZombie;
import net.minecraft.world.entity.npc.EntityVillagerAbstract;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.World;
import org.bukkit.entity.LivingEntity;

public class CustomGiant extends EntityGiantZombie {
    public CustomGiant(World w){
        super(EntityTypes.S,w);
        EntityHelper.setMobDamage((LivingEntity) this.getBukkitEntity(),60);
        EntityHelper.setMobHealth((LivingEntity) this.getBukkitEntity(),600);
        EntityHelper.setIdentifierString(this.getBukkitEntity(),"giant");
        ((LivingEntity)this.getBukkitEntity()).setPersistent(false);
        ((LivingEntity)this.getBukkitEntity()).setRemoveWhenFarAway(true);
    }
    @Override
    protected void B() {
        this.bO.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.bO.a(8, new PathfinderGoalRandomLookaround(this));
        this.bO.a(7, new PathfinderGoalRandomStrollLand(this, 1.0));

        this.bP.a(0,new PathfinderGoalMeleeAttack(this,1.0,true));
        this.bP.a(1, new PathfinderGoalNearestAttackableTarget<>(this, EntityHuman.class,true));
    }

    protected SoundEffect y() {
        return SoundEffects.Cc;
    }

    protected SoundEffect d(DamageSource damagesource) {
        return SoundEffects.Cm;
    }

    protected SoundEffect n_() {
        return SoundEffects.Ch;
    }

}
