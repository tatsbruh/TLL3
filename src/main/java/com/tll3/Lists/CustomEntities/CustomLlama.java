package com.tll3.Lists.CustomEntities;

import com.tll3.Lists.CustomEntities.CustomProjectiles.CustomLlamaSpit;
import com.tll3.Misc.ChatUtils;
import com.tll3.Misc.GenericUtils;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalHurtByTarget;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;
import net.minecraft.world.entity.animal.horse.EntityLlama;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.World;
import org.bukkit.entity.LivingEntity;

import java.lang.reflect.Field;

public class CustomLlama extends EntityLlama {
    private Field ca;

    public CustomLlama(World w){
        super(EntityTypes.ak,w);
        this.getBukkitEntity().setCustomName(ChatUtils.format("#bf000aLlama Imposible"));
        ((LivingEntity) this.getBukkitEntity()).setRemoveWhenFarAway(true);
        this.getBukkitEntity().setPersistent(false);
        this.persist = false;
        try {
            this.ca = EntityLlama.class.getDeclaredField("ca");
            this.ca.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void B() {
        this.bO.a(0, new PathfinderGoalFloat(this));
        this.bO.a(2, new PathfinderGoalLlamaFollow(this, 2.0999999046325684));
        this.bO.a(3, new PathfinderGoalArrowAttack(this, 1.25, 40, 20.0F));
        this.bO.a(4, new PathfinderGoalBreed(this, 1.0));
        this.bO.a(6, new PathfinderGoalFollowParent(this, 1.0));
        this.bO.a(7, new PathfinderGoalRandomStrollLand(this, 0.7));
        this.bO.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
        this.bO.a(9, new PathfinderGoalRandomLookaround(this));
        this.bP.a(2, new PathfinderGoalHurtByTarget(this, new Class[0]));
        this.bP.a(3, new PathfinderGoalNearestAttackableTarget<>(this, EntityHuman.class, true));
    }

    @Override
    public void a(EntityLiving entityliving, float f) {
        try {
            this.caca(entityliving);
        } catch (IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
    }
    public void caca(EntityLiving entityliving) throws IllegalAccessException {
        float dmg = 45.0F;
        if(GenericUtils.getDay() >= 28){
            dmg = 125.0F;
        }
        CustomLlamaSpit customLlamaSpit = new CustomLlamaSpit(this.dM(),this,dmg);
        double d0 = entityliving.dr() - this.dr();
        double d1 = entityliving.e(0.3333333333333333) - customLlamaSpit.dt();
        double d2 = entityliving.dx() - this.dx();
        double d3 = Math.sqrt(d0 * d0 + d2 * d2) * 0.20000000298023224;
        customLlamaSpit.c(d0, d1 + d3, d2, 1.5F, 10.0F);
        if (!this.aU()) {
            this.dM().a((EntityHuman)null, this.dr(), this.dt(), this.dx(), SoundEffects.nq, this.db(), 1.0F, 1.0F + (this.ag.i() - this.ag.i()) * 0.2F);
        }
        this.dM().b(customLlamaSpit);
        ca.setBoolean(this,true);
    }
}
