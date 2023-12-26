package com.tll3.Lists.CustomEntities.CustomPathfinders;

import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;
import net.minecraft.world.entity.monster.EntityCreeper;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class NewSwellGoal extends PathfinderGoal {
    private final EntityCreeper a;
    @Nullable
    private EntityLiving b;

    public NewSwellGoal(EntityCreeper var0) {
        this.a = var0;
        this.a(EnumSet.of(Type.a));
    }

    public boolean a() {
        EntityLiving var0 = this.a.q();
        return this.a.w() > 0 || var0 != null && this.a.f(var0) < 27.0; //More distance to explode
    }

    public void c() {
        this.a.N().n();
        this.b = this.a.q();
    }

    public void d() {
        this.b = null;
    }

    public boolean T_() {
        return true;
    }

    public void e() {
        if (this.b == null) {
            this.a.b(-1);
        } else if (this.a.f(this.b) > 49.0) {
            this.a.b(-1);
        } else { //Doesn't need line of sight to explode
            this.a.b(1);
        }
    }
}
