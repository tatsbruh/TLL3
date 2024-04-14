package com.tll3.Lists.CustomEntities;

import com.tll3.Misc.ChatUtils;
import com.tll3.Misc.EntityHelper;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.control.ControllerMove;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;
import net.minecraft.world.entity.monster.EntityGhast;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.projectile.EntityLargeFireball;
import net.minecraft.world.level.World;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffectType;

import java.util.EnumSet;

public class CustomGhast extends EntityGhast {
    public CustomGhast(World world){
        super(EntityTypes.R,world);
        this.getBukkitEntity().setCustomName(ChatUtils.format("#4000ffZenith Dreadnought"));
        EntityHelper.addPotionEffect((LivingEntity) this.getBukkitEntity(), PotionEffectType.FIRE_RESISTANCE, 0);
        EntityHelper.setMobHealth((LivingEntity) this.getBukkitEntity(),35);
        EntityHelper.setIdentifierString(this.getBukkitEntity(),"zenithghast");
        EntityHelper.setIdentifierString(this.getBukkitEntity(),"zenith");
        ((LivingEntity) this.getBukkitEntity()).setRemoveWhenFarAway(true);
        this.getBukkitEntity().setPersistent(false);
        this.persist = false;
        this.a(PathType.j, 0.0F);
        this.a(PathType.i, 0.0F);
        this.a(PathType.n, 0.0F);
        this.a(PathType.o, 0.0F);
    }

    @Override
    public int w() {
        return 5;
    }
    @Override
    protected void B() {
        this.bO.a(5, new PathfinderGoalGhastIdleMove(this));
        this.bO.a(7, new PathfinderGoalGhastMoveTowardsTarget(this));
        this.bO.a(7, new PathfinderGoalGhastAttackTarget(this));
        this.bP.a(1, new PathfinderGoalNearestAttackableTarget<>(this, EntityHuman.class, true));
    }
    private static class PathfinderGoalGhastAttackTarget extends PathfinderGoal {
        private final EntityGhast b;
        public int a;

        public PathfinderGoalGhastAttackTarget(EntityGhast entityghast) {
            this.b = entityghast;
        }

        public boolean a() {
            return this.b.q() != null;
        }

        public void c() {
            this.a = 0;
        }

        public void d() {
            this.b.w(false);
        }

        public boolean T_() {
            return true;
        }

        public void e() {
            EntityLiving entityliving = this.b.q();
            if (entityliving != null) {
                double d0 = 64.0;
                if (entityliving.f(this.b) < 6000.0D) { //El ghast ahora mira mucho mas lejos y encima no requiere de linea de vision para atacar
                    World world = this.b.dM();
                    ++this.a;
                    if (this.a == 10 && !this.b.aU()) {
                        world.a((EntityHuman)null, 1015, this.b.dm(), 0);
                    }
                    this.b.w(this.a > 2);

                    if (this.a == 5) {
                        double d1 = 4.0;
                        Vec3D vec3d = this.b.f(1.0F);
                        double d2 = entityliving.dr() - (this.b.dr() + vec3d.c * 4.0);
                        double d3 = entityliving.e(0.5) - (0.5 + this.b.e(0.5));
                        double d4 = entityliving.dx() - (this.b.dx() + vec3d.e * 4.0);
                        if (!this.b.aU()) {
                            world.a((EntityHuman)null, 1016, this.b.dm(), 0);
                        }
                        EntityLargeFireball entitylargefireball = new EntityLargeFireball(world, this.b, d2, d3, d4, this.b.w());
                        entitylargefireball.bukkitYield = (float)(entitylargefireball.e = this.b.w());
                        entitylargefireball.a_(this.b.dr() + vec3d.c * 4.0, this.b.e(0.5) + 0.5, entitylargefireball.dx() + vec3d.e * 4.0);
                        world.b(entitylargefireball);
                        this.a = 0;
                    }
                } else if (this.a > 0) {
                    --this.a;
                }

            }

        }
    }

    private static class PathfinderGoalGhastIdleMove extends PathfinderGoal {
        private final EntityGhast a;

        public PathfinderGoalGhastIdleMove(EntityGhast entityghast) {
            this.a = entityghast;
            this.a(EnumSet.of(Type.a));
        }

        public boolean a() {
            ControllerMove controllermove = this.a.K();
            if (!controllermove.b()) {
                return true;
            } else {
                double d0 = controllermove.d() - this.a.dr();
                double d1 = controllermove.e() - this.a.dt();
                double d2 = controllermove.f() - this.a.dx();
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                return d3 < 1.0 || d3 > 3600.0;
            }
        }

        public boolean b() {
            return false;
        }

        public void c() {
            RandomSource randomsource = this.a.eg();
            double d0 = this.a.dr() + (double)((randomsource.i() * 2.0F - 1.0F) * 16.0F);
            double d1 = this.a.dt() + (double)((randomsource.i() * 2.0F - 1.0F) * 16.0F);
            double d2 = this.a.dx() + (double)((randomsource.i() * 2.0F - 1.0F) * 16.0F);
            this.a.K().a(d0, d1, d2, 1.0);
        }
    }

    private static class PathfinderGoalGhastMoveTowardsTarget extends PathfinderGoal {
        private final EntityGhast a;

        public PathfinderGoalGhastMoveTowardsTarget(EntityGhast entityghast) {
            this.a = entityghast;
            this.a(EnumSet.of(Type.b));
        }

        public boolean a() {
            return true;
        }

        public boolean T_() {
            return true;
        }

        public void e() {
            if (this.a.q() == null) {
                Vec3D vec3d = this.a.dp();
                this.a.r(-((float) MathHelper.d(vec3d.c, vec3d.e)) * 57.295776F);
                this.a.aU = this.a.dC();
            } else {
                EntityLiving entityliving = this.a.q();
                double d0 = 64.0;
                if (entityliving.f(this.a) < 4096.0) {
                    double d1 = entityliving.dr() - this.a.dr();
                    double d2 = entityliving.dx() - this.a.dx();
                    this.a.r(-((float)MathHelper.d(d1, d2)) * 57.295776F);
                    this.a.aU = this.a.dC();
                }
            }

        }
    }
}
