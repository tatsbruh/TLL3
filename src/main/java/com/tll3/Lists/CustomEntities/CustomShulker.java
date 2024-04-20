package com.tll3.Lists.CustomEntities;

import com.tll3.Lists.CustomEntities.CustomProjectiles.CustomShulkerBullet;
import com.tll3.Misc.ChatUtils;
import com.tll3.Misc.EntityHelper;
import com.tll3.TLL3;
import com.tll3.Task.Mobs.ZenithDashTask;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;
import net.minecraft.world.entity.ai.goal.PathfinderGoalLookAtPlayer;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomLookaround;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalHurtByTarget;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;
import net.minecraft.world.entity.monster.EntityShulker;
import net.minecraft.world.entity.monster.IMonster;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.projectile.EntityShulkerBullet;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.AxisAlignedBB;
import org.bukkit.DyeColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Shulker;
import org.bukkit.entity.Spider;
import org.bukkit.potion.PotionEffectType;

import java.util.EnumSet;

public class CustomShulker extends EntityShulker {
    public CustomShulker(World w){
        super(EntityTypes.aH,w);
        this.getBukkitEntity().setCustomName(ChatUtils.format("#4a9932Eldritch Module"));
        EntityHelper.setMobHealth((LivingEntity) this.getBukkitEntity(),65);
        EntityHelper.addPotionEffect((LivingEntity) this.getBukkitEntity(), PotionEffectType.FIRE_RESISTANCE,0);
        EntityHelper.setIdentifierString(this.getBukkitEntity(),"eldritchmodule");
        ((Shulker)this.getBukkitEntity()).setColor(DyeColor.GREEN);
        ((LivingEntity) this.getBukkitEntity()).setRemoveWhenFarAway(true);
        this.getBukkitEntity().setPersistent(false);
        this.persist = false;
        this.a(PathType.j, 0.0F);
        this.a(PathType.i, 0.0F);
        this.a(PathType.n, 0.0F);
        this.a(PathType.o, 0.0F);
    }
    @Override
    protected void B() {
        this.bO.a(1, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F, 0.02F, true));
        this.bO.a(4, new a());
        this.bO.a(7, new f());
        this.bO.a(8, new PathfinderGoalRandomLookaround(this));
        this.bP.a(1, (new PathfinderGoalHurtByTarget(this, new Class[]{this.getClass()})).a(new Class[0]));
        this.bP.a(2, new e(this));
        this.bP.a(3, new c(this));
    }

    private class a extends PathfinderGoal {
        private int b;

        public a() {
            this.a(EnumSet.of(Type.a, Type.b));
        }

        public boolean a() {
            EntityLiving entityliving = CustomShulker.this.q();
            return entityliving != null && entityliving.bx() ? CustomShulker.this.dM().ak() != EnumDifficulty.a : false;
        }

        public void c() {
            this.b = 20;
            CustomShulker.this.b(100);
        }

        public void d() {
            CustomShulker.this.b(0);
        }

        public boolean T_() {
            return true;
        }

        public void e() {
            if (CustomShulker.this.dM().ak() != EnumDifficulty.a) {
                --this.b;
                EntityLiving entityliving = CustomShulker.this.q();
                if (entityliving != null) {
                    CustomShulker.this.I().a(entityliving, 180.0F, 180.0F);
                    double d0 = CustomShulker.this.f((Entity)entityliving);
                    if (d0 < 400.0) {
                        if (this.b <= 0) {
                            this.b = 10 + CustomShulker.this.ag.a(10) * 10; //faster shooting
                            CustomShulker.this.dM().b(new CustomShulkerBullet(CustomShulker.this.dM(), CustomShulker.this, entityliving, CustomShulker.this.A().o()));
                            CustomShulker.this.a(SoundEffects.vY, 2.0F, (CustomShulker.this.ag.i() - CustomShulker.this.ag.i()) * 0.2F + 1.0F);
                        }
                    } else {
                        CustomShulker.this.h((EntityLiving)null);
                    }

                    super.e();
                }
            }

        }
    }
    private class f extends PathfinderGoal {
        private int b;

        f() {
        }

        public boolean a() {
            return CustomShulker.this.q() == null && CustomShulker.this.ag.a(b(40)) == 0 && CustomShulker.this.a(CustomShulker.this.dm(), CustomShulker.this.A());
        }

        public boolean b() {
            return CustomShulker.this.q() == null && this.b > 0;
        }

        public void c() {
            this.b = this.a(20 * (1 + CustomShulker.this.ag.a(3)));
            CustomShulker.this.b(30);
        }

        public void d() {
            if (CustomShulker.this.q() == null) {
                CustomShulker.this.b(0);
            }

        }

        public void e() {
            --this.b;
        }
    }
    private class e extends PathfinderGoalNearestAttackableTarget<EntityHuman> {
        public e(EntityShulker entityshulker) {
            super(entityshulker, EntityHuman.class, true);
        }

        public boolean a() {
            return CustomShulker.this.dM().ak() == EnumDifficulty.a ? false : super.a();
        }

        protected AxisAlignedBB a(double d0) {
            EnumDirection enumdirection = ((EntityShulker)this.e).A();
            return enumdirection.o() == EnumDirection.EnumAxis.a ? this.e.cH().c(4.0, d0, d0) : (enumdirection.o() == EnumDirection.EnumAxis.c ? this.e.cH().c(d0, d0, 4.0) : this.e.cH().c(d0, 4.0, d0));
        }
    }
    private static class c extends PathfinderGoalNearestAttackableTarget<EntityLiving> {
        public c(EntityShulker entityshulker) {
            super(entityshulker, EntityLiving.class, 10, true, false, (entityliving) -> {
                return entityliving instanceof IMonster;
            });
        }

        public boolean a() {
            return this.e.cg() == null ? false : super.a();
        }

        protected AxisAlignedBB a(double d0) {
            EnumDirection enumdirection = ((EntityShulker)this.e).A();
            return enumdirection.o() == EnumDirection.EnumAxis.a ? this.e.cH().c(4.0, d0, d0) : (enumdirection.o() == EnumDirection.EnumAxis.c ? this.e.cH().c(d0, d0, 4.0) : this.e.cH().c(d0, 4.0, d0));
        }
    }
    boolean a(BlockPosition blockposition, EnumDirection enumdirection) {
        if (this.j(blockposition)) {
            return false;
        } else {
            EnumDirection enumdirection1 = enumdirection.g();
            if (!this.dM().a(blockposition.a(enumdirection), this, enumdirection1)) {
                return false;
            } else {
                AxisAlignedBB axisalignedbb = a(enumdirection1, 1.0F).a(blockposition).h(1.0E-6);
                return this.dM().a(this, axisalignedbb);
            }
        }
    }
    private boolean j(BlockPosition blockposition) {
        IBlockData iblockdata = this.dM().a_(blockposition);
        if (iblockdata.i()) {
            return false;
        } else {
            boolean flag = iblockdata.a(Blocks.bQ) && blockposition.equals(this.dm());
            return !flag;
        }
    }
}
