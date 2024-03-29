package com.tll3.Lists.CustomEntities;

import com.tll3.Listeners.EntityNaturalSpawn;
import com.tll3.Lists.Entities;
import com.tll3.Misc.ChatUtils;
import com.tll3.Misc.GenericUtils;
import net.minecraft.core.particles.Particles;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.TagsItem;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.attributes.AttributeBase;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalHurtByTarget;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalUniversalAngerReset;
import net.minecraft.world.entity.animal.EntityBee;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.crafting.RecipeItemStack;
import net.minecraft.world.level.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.entity.EntityPotionEffectEvent;

public class CustomBee extends EntityBee {
    public CustomBee(World world){
        super(EntityTypes.h,world);
        this.getBukkitEntity().setCustomName(ChatUtils.format("#2d8a06Langosta"));
        EntityNaturalSpawn.setCustomMobcap((LivingEntity) this.getBukkitEntity(),3,1.24,60,30,true);
        ((LivingEntity) this.getBukkitEntity()).setRemoveWhenFarAway(true);
        this.getBukkitEntity().setPersistent(false);
        this.persist = false;
        this.craftAttributes.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
        ((LivingEntity) this.getBukkitEntity()).setHealth(20);
        if(GenericUtils.getDay() >= 21){
            this.craftAttributes.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(20);
        }else{
            this.craftAttributes.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(5);
        }

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

    @Override
    public void l() {
        super.l();
        if(this.gp()){
            this.x(false);
        }
    }
    @Override
    public boolean C(Entity entity) {
        boolean flag = entity.a(this.dN().a(this), (float)((int)this.b((AttributeBase) GenericAttributes.c)));
        if (flag) {
            this.a((EntityLiving)this, (Entity)entity);
            if (entity instanceof EntityLiving) {
                ((EntityLiving)entity).q(((EntityLiving)entity).eP() + 1);
                byte b0 = 0;
                if (this.dM().ak() == EnumDifficulty.c) {
                    b0 = 10;
                } else if (this.dM().ak() == EnumDifficulty.d) {
                    b0 = 18;
                }
                if (b0 > 0) {
                    ((EntityLiving)entity).addEffect(new MobEffect(MobEffects.s, b0 * 20, 4), this, EntityPotionEffectEvent.Cause.ATTACK);
                    ((EntityLiving)entity).addEffect(new MobEffect(MobEffects.r, b0 * 20, 1), this, EntityPotionEffectEvent.Cause.ATTACK);
                    ((EntityLiving)entity).addEffect(new MobEffect(MobEffects.b, b0 * 20, 2), this, EntityPotionEffectEvent.Cause.ATTACK);
                }
            }

            this.x(true);
            this.X_();
            this.a(SoundEffects.bD, 1.0F, 1.0F);
        }

        return flag;
    }

}
