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
        this.bO.a(0, new PathfinderGoalFloat(this));
        this.bO.a(2, new PathfinderGoalMoveTowardsTarget(this, 0.9, 32.0F));
        this.bO.a(3, new b(this, 1.2000000476837158, true));
        this.bO.a(9, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 24.0F));
        this.bO.a(10, new PathfinderGoalRandomLookaround(this));
        this.bO.a(12, new j(this));
        this.bO.a(14, new PathfinderGoalRandomStrollLand(this, 1.0));
        this.bP.a(1, (new e(this, new Class[0])).a(new Class[0]));
        this.bP.a(3,new PathfinderGoalNearestAttackableTarget<>(this, EntityHuman.class, true));
    }


    private static class b extends PathfinderGoalMeleeAttack {
        private final EntityPanda b;

        public b(EntityPanda entitypanda, double d0, boolean flag) {
            super(entitypanda, d0, flag);
            this.b = entitypanda;
        }

        public boolean a() {
            return this.b.gy() && super.a();
        }
    }
    private static class j extends PathfinderGoal {
        private final EntityPanda a;

        public j(EntityPanda entitypanda) {
            this.a = entitypanda;
            this.a(EnumSet.of(Type.a, Type.b, Type.c));
        }

        public boolean a() {
            if ((this.a.o_() || this.a.gt()) && this.a.aC()) {
                if (!this.a.gy()) {
                    return false;
                } else {
                    float f = this.a.dC() * 0.017453292F;
                    float f1 = -MathHelper.a(f);
                    float f2 = MathHelper.b(f);
                    int i = (double)Math.abs(f1) > 0.5 ? MathHelper.j((double)f1) : 0;
                    int j = (double)Math.abs(f2) > 0.5 ? MathHelper.j((double)f2) : 0;
                    return this.a.dM().a_(this.a.dm().b(i, -1, j)).i() ? true : (this.a.gt() && this.a.ag.a(b(60)) == 1 ? true : this.a.ag.a(b(500)) == 1);
                }
            } else {
                return false;
            }
        }

        public boolean b() {
            return false;
        }

        public void c() {
            this.a.A(true);
        }

        public boolean S_() {
            return false;
        }
    }
    private static class e extends PathfinderGoalHurtByTarget {
        private final EntityPanda a;

        public e(EntityPanda entitypanda, Class<?>... aclass) {
            super(entitypanda, aclass);
            this.a = entitypanda;
        }

        public boolean b() {
                return super.b();
        }

        protected void a(EntityInsentient entityinsentient, EntityLiving entityliving) {
            if (entityinsentient instanceof EntityPanda && entityinsentient.fW()) {
                entityinsentient.setTarget(entityliving, EntityTargetEvent.TargetReason.TARGET_ATTACKED_ENTITY, true);
            }

        }
    }

}
