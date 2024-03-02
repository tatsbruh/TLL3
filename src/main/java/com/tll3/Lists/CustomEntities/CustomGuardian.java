package com.tll3.Lists.CustomEntities;

import com.tll3.Misc.GenericUtils;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;
import net.minecraft.world.entity.animal.EntitySquid;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.monster.EntityGuardian;
import net.minecraft.world.entity.monster.EntityGuardianElder;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityPotionEffectEvent;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.function.Predicate;

public class CustomGuardian extends EntityGuardian {
    public CustomGuardian(World world) {
        super(EntityTypes.W, world);
        ((LivingEntity) this.getBukkitEntity()).setRemoveWhenFarAway(true);
        this.addEffect(new MobEffect(MobEffects.a,Integer.MAX_VALUE,1), EntityPotionEffectEvent.Cause.PLUGIN);
        this.getBukkitEntity().setPersistent(false);
        this.persist = false;
    }

    public PathfinderGoalGuardianAttack guardianAttack;


    @Override
    protected void B() {
        PathfinderGoalMoveTowardsRestriction pathfindergoalmovetowardsrestriction = new PathfinderGoalMoveTowardsRestriction(this, 1.0);
        this.d = new PathfinderGoalRandomStroll(this, 1.0, 80);
        this.bO.a(4, this.guardianAttack = new PathfinderGoalGuardianAttack(this));
        this.bO.a(5, pathfindergoalmovetowardsrestriction);
        this.bO.a(7, this.d);
        this.bO.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.bO.a(8, new PathfinderGoalLookAtPlayer(this, EntityGuardian.class, 12.0F, 0.01F));
        this.bO.a(9, new PathfinderGoalRandomLookaround(this));
        this.d.a(EnumSet.of(PathfinderGoal.Type.a, PathfinderGoal.Type.b));
        pathfindergoalmovetowardsrestriction.a(EnumSet.of(PathfinderGoal.Type.a, PathfinderGoal.Type.b));
        this.bP.a(1, new PathfinderGoalNearestAttackableTarget(this, EntityLiving.class, 10, true, false, new EntitySelectorGuardianTargetHumanSquid(this)));
    }


    private static class EntitySelectorGuardianTargetHumanSquid implements Predicate<EntityLiving> {
        private final EntityGuardian a;

        public EntitySelectorGuardianTargetHumanSquid(EntityGuardian entityguardian) {
            this.a = entityguardian;
        }

        public boolean test(@Nullable EntityLiving entityliving) {
            return (entityliving instanceof EntityHuman || entityliving instanceof EntitySquid || entityliving instanceof Axolotl) && entityliving.f(this.a) > 9.0;
        }
    }
    public static class PathfinderGoalGuardianAttack extends PathfinderGoal {
        private final EntityGuardian a;
        public int b;
        private final boolean c;

        public PathfinderGoalGuardianAttack(EntityGuardian entityguardian) {
            this.a = entityguardian;
            this.c = entityguardian instanceof EntityGuardianElder;
            this.a(EnumSet.of(Type.a, Type.b));
        }

        public boolean a() {
            EntityLiving entityliving = this.a.q();
            return entityliving != null && entityliving.bx();
        }

        public boolean b() {
            return super.b() && (this.c || this.a.q() != null && this.a.f(this.a.q()) > 9.0);
        }

        public void c() {
            this.b = -10;
            this.a.N().n();
            EntityLiving entityliving = this.a.q();
            if (entityliving != null) {
                this.a.I().a(entityliving, 90.0F, 90.0F);
            }

            this.a.au = true;
        }

        public void d() {
            this.a.b(0);
            this.a.h((EntityLiving)null);
            this.a.d.i();
        }

        public boolean T_() {
            return true;
        }

        public void e() {
            EntityLiving entityliving = this.a.q();
            if (entityliving != null) {
                this.a.N().n();
                this.a.I().a(entityliving, 90.0F, 90.0F);
                if (!this.a.E(entityliving)) {
                    this.a.h((EntityLiving)null);
                } else {
                    this.b += 2;
                    if (this.b == 0) {
                        this.a.b(entityliving.aj());
                        if (!this.a.aU()) {
                            this.a.dM().a(this.a, (byte)21);
                        }
                    } else if (this.b >= this.a.w()) {
                        float f = 1.0F;
                        if (this.a.dM().ak() == EnumDifficulty.d) {
                            f += 2.0F;
                        }

                        if (this.c) {
                            f += 2.0F;
                        }

                        if(GenericUtils.getDay() >= 28){
                            entityliving.a(this.a.dN().c(this.a, this.a), 20.0F);
                        }else{
                            entityliving.a(this.a.dN().c(this.a, this.a), 12.0F);
                        }
                        entityliving.a(this.a.dN().b(this.a), (float)this.a.b(GenericAttributes.c));
                        this.a.h((EntityLiving)null);
                    }

                    super.e();
                }
            }

        }
    }


}
