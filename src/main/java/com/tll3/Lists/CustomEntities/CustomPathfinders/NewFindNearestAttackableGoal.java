package com.tll3.Lists.CustomEntities.CustomPathfinders;

import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalTarget;
import net.minecraft.world.entity.ai.targeting.PathfinderTargetCondition;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.phys.AxisAlignedBB;
import org.bukkit.event.entity.EntityTargetEvent;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.function.Predicate;

public class NewFindNearestAttackableGoal<T extends EntityLiving> extends PathfinderGoalTarget {
    private static final int i = 10;
    protected final Class<T> a;
    protected final int b;
    @Nullable
    protected EntityLiving c;
    protected PathfinderTargetCondition d;

    public NewFindNearestAttackableGoal(EntityInsentient entityinsentient, Class<T> oclass, boolean flag) {
        this(entityinsentient, oclass, 10, flag, false, (Predicate)null);
    }

    public NewFindNearestAttackableGoal(EntityInsentient entityinsentient, Class<T> oclass, boolean flag, Predicate<EntityLiving> predicate) {
        this(entityinsentient, oclass, 10, flag, false, predicate);
    }

    public NewFindNearestAttackableGoal(EntityInsentient entityinsentient, Class<T> oclass, boolean flag, boolean flag1) {
        this(entityinsentient, oclass, 10, flag, flag1, (Predicate)null);
    }

    public NewFindNearestAttackableGoal(EntityInsentient entityinsentient, Class<T> oclass, int i, boolean flag, boolean flag1, @Nullable Predicate<EntityLiving> predicate) {
        super(entityinsentient, flag, flag1);
        this.a = oclass;
        this.b = b(i);
        this.a(EnumSet.of(Type.d));
        this.d = PathfinderTargetCondition.a().a(this.l()).a(predicate);
    }

    public boolean a() {
        if (this.b > 0 && this.e.eg().a(this.b) != 0) {
            return false;
        } else {
            this.h();
            return this.c != null;
        }
    }

    protected AxisAlignedBB a(double d0) {
        return this.e.cH().c(d0, 4.0, d0);
    }

    protected void h() {
        if (this.a != EntityHuman.class && this.a != EntityPlayer.class) {
            this.c = this.e.dM().a(this.e.dM().a(this.a, this.a(this.l()), (entityliving) -> {
                return true;
            }), this.d, this.e, this.e.dr(), this.e.dv(), this.e.dx());
        } else {
            this.c = this.e.dM().a(this.d, this.e, this.e.dr(), this.e.dv(), this.e.dx());
        }

    }

    public void c() {
        this.e.setTarget(this.c, this.c instanceof EntityPlayer ? EntityTargetEvent.TargetReason.CLOSEST_PLAYER : EntityTargetEvent.TargetReason.CLOSEST_ENTITY, true);
        super.c();
    }

    public void a(@Nullable EntityLiving entityliving) {
        this.c = entityliving;
    }
}
