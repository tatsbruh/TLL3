package com.tll3.Lists.CustomEntities;

import com.tll3.Misc.ChatUtils;
import com.tll3.Misc.EntityHelper;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalHurtByTarget;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;
import net.minecraft.world.entity.monster.EntityBlaze;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.projectile.EntitySmallFireball;
import net.minecraft.world.level.World;
import net.minecraft.world.level.pathfinder.PathType;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.EnumSet;

public class CustomBlaze extends EntityBlaze {
    public CustomBlaze(World w) {
        super(EntityTypes.i, w);
        EntityHelper.addPotionEffect((LivingEntity) this.getBukkitEntity(), PotionEffectType.SPEED, 2);
        this.a(PathType.j, 0.0F); //No longer avoids water
        this.a(PathType.i, 0.0F); //No longer avoids lava
        this.a(PathType.n, 0.0F); //No longer avoids fire
        this.a(PathType.o, 0.0F); //No longer avoids magma blocks
        this.getBukkitEntity().setCustomName(ChatUtils.format("#4000ffZenith Hellbringer"));
        this.craftAttributes.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(100);
        EntityHelper.setMobHealth((LivingEntity) this.getBukkitEntity(), 45);
        EntityHelper.setIdentifierString(this.getBukkitEntity(), "zenithblaze");
        EntityHelper.setIdentifierString(this.getBukkitEntity(),"zenith");
        ((LivingEntity) this.getBukkitEntity()).setRemoveWhenFarAway(true);
        this.getBukkitEntity().setPersistent(false);
        this.persist = false;
    }

    @Override
    protected void B() {
        this.bO.a(4, new PathfinderGoalBlazeFireball(this));
        this.bO.a(5, new PathfinderGoalMoveTowardsRestriction(this, 1.0));
        this.bO.a(7, new PathfinderGoalRandomStrollLand(this, 1.0, 0.0F));
        this.bO.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.bO.a(8, new PathfinderGoalRandomLookaround(this));
        this.bP.a(1, (new PathfinderGoalHurtByTarget(this, new Class[0])).a(new Class[0]));
        this.bP.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, true));
    }

    @Override
    public boolean fh() {  //No longer takes damage by water
        return false;
    }

    @Override //Can fall from any distance
    public int cu() {
        return Integer.MAX_VALUE;
    }

    static class PathfinderGoalBlazeFireball extends PathfinderGoal {
        private final EntityBlaze a;
        private int b;
        private int c;
        private int d;

        public PathfinderGoalBlazeFireball(EntityBlaze var0) {
            this.a = var0;
            this.a(EnumSet.of(Type.a, Type.b));
        }

        public boolean a() {
            EntityLiving var0 = this.a.q();
            return var0 != null && var0.bx() && this.a.c(var0);
        }

        public void c() {
            this.b = 0;
        }

        public void d() {
            this.d = 0;
        }

        public boolean T_() {
            return true;
        }

        public void e() {
            --this.c;
            EntityLiving var0 = this.a.q();
            if (var0 != null) {
                //NO LINE OF SIGHT
                ++this.d;
                double var2 = this.a.f(var0);
                if (var2 < 4.0) {

                    if (this.c <= 0) {
                        this.c = 20;
                        this.a.C(var0);
                    }

                    this.a.K().a(var0.dr(), var0.dt(), var0.dx(), 1.0);
                } else if (var2 < this.h() * this.h()) {
                    double var4 = var0.dr() - this.a.dr();
                    double var6 = var0.e(0.5) - this.a.e(0.5);
                    double var8 = var0.dx() - this.a.dx();
                    if (this.c <= 0) { //ATACA MAS RAPIDO

                        this.c = 4;
                        double var10 = Math.sqrt(Math.sqrt(var2)) * 0.5;
                        for (int var12 = 0; var12 < 1; ++var12) {
                            EntitySmallFireball var13 = new EntitySmallFireball(this.a.dM(), this.a, this.a.eg().a(var4, 2.297 * var10), var6, this.a.eg().a(var8, 2.297 * var10));
                            var13.a_(var13.dr(), this.a.e(0.5) + 0.5, var13.dx());
                            this.a.dM().b(var13);
                        }
                    }
                }

                this.a.I().a(var0, 10.0F, 10.0F);
            } else if (this.d < 5) {
                this.a.K().a(var0.dr(), var0.dt(), var0.dx(), 1.0);
            }

            super.e();
        }
        private double h() {
            return this.a.b(GenericAttributes.g);
        }
    }

}
