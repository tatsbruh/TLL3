package com.tll3.Lists.CustomEntities.CustomPathfinders;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;
import net.minecraft.world.entity.monster.EntityMonster;
import net.minecraft.world.entity.monster.IRangedEntity;
import net.minecraft.world.entity.projectile.ProjectileHelper;
import net.minecraft.world.item.ItemBow;
import net.minecraft.world.item.Items;

import java.util.EnumSet;

public class NewShootBow <T extends EntityMonster & IRangedEntity> extends PathfinderGoal {
    private final T a;
    private final double b;
    private int c;
    private final float d;
    private int e = -1;
    private int f;
    private boolean g;
    private boolean h;
    private int i = -1;

    public NewShootBow(T var0, double var1, int var3, float var4) {
        this.a = var0;
        this.b = var1;
        this.c = var3;
        this.d = var4 * var4;
        this.a(EnumSet.of(Type.a, Type.b));
    }

    public void c(int var0) {
        this.c = var0;
    }

    public boolean a() {
        return this.a.q() == null ? false : this.h();
    }

    protected boolean h() {
        return this.a.b(Items.or);
    }

    public boolean b() {
        return (this.a() || !this.a.N().l()) && this.h();
    }

    public void c() {
        super.c();
        this.a.v(true);
    }

    public void d() {
        super.d();
        this.a.v(false);
        this.f = 0;
        this.e = -1;
        this.a.ft();
    }

    public boolean T_() {
        return true;
    }

    public void e() {
        EntityLiving var0 = this.a.q();
        if (var0 != null) {
            double var1 = this.a.i(var0.dr(), var0.dt(), var0.dx());
            ++this.f;
            if (!(var1 > (double)this.d) && this.f >= 20) {
                this.a.N().n();
                ++this.i;
            } else {
                this.a.N().a(var0, this.b);
                this.i = -1;
            }

            if (this.i >= 20) {
                if ((double)this.a.eg().i() < 0.3) {
                    this.g = !this.g;
                }

                if ((double)this.a.eg().i() < 0.3) {
                    this.h = !this.h;
                }

                this.i = 0;
            }

            if (this.i > -1) {
                if (var1 > (double)(this.d * 0.75F)) {
                    this.h = false;
                } else if (var1 < (double)(this.d * 0.25F)) {
                    this.h = true;
                }

                this.a.K().a(this.h ? -0.5F : 0.5F, this.g ? 0.5F : -0.5F);
                Entity var7 = this.a.da();
                if (var7 instanceof EntityInsentient) {
                    EntityInsentient var5 = (EntityInsentient)var7;
                    var5.a(var0, 30.0F, 30.0F);
                }

                this.a.a(var0, 30.0F, 30.0F);
            } else {
                this.a.I().a(var0, 30.0F, 30.0F);
            }

            if (this.a.fn()) {
                this.a.ft();
                ((IRangedEntity)this.a).a(var0, ItemBow.a(20));
                this.e = this.c;
            } else if (--this.e <= 0 && this.f >= -60) {
                this.a.c(ProjectileHelper.a(this.a, Items.or));
            }

        }
    }
}
