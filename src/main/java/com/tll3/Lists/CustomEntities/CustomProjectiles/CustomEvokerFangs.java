package com.tll3.Lists.CustomEntities.CustomProjectiles;

import net.minecraft.core.particles.Particles;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.projectile.EntityEvokerFangs;
import net.minecraft.world.level.World;
import org.bukkit.craftbukkit.v1_20_R3.event.CraftEventFactory;
import org.bukkit.entity.EvokerFangs;

import java.util.Iterator;
import java.util.List;

public class CustomEvokerFangs extends EntityEvokerFangs {

    private boolean h;
    private int g;
    private boolean f;
    private final double damage;

    public CustomEvokerFangs(World world, double d0, double d1, double d2, float f, int i, EntityLiving entityliving,double damage) {
        super(world, d0, d1, d2, f, i, entityliving);
        this.damage = damage;
    }

    @Override
    public void l() {
        super.l();
        if (this.dM().B) {
            if (this.h) {
                --this.g;
                if (this.g == 14) {
                    for(int i = 0; i < 12; ++i) {
                        double d0 = this.dr() + (this.ag.j() * 2.0 - 1.0) * (double)this.dg() * 0.5;
                        double d1 = this.dt() + 0.05 + this.ag.j();
                        double d2 = this.dx() + (this.ag.j() * 2.0 - 1.0) * (double)this.dg() * 0.5;
                        double d3 = (this.ag.j() * 2.0 - 1.0) * 0.3;
                        double d4 = 0.3 + this.ag.j() * 0.3;
                        double d5 = (this.ag.j() * 2.0 - 1.0) * 0.3;
                        this.dM().a(Particles.g, d0, d1 + 1.0, d2, d3, d4, d5);
                    }
                }
            }
        } else if (--this.e < 0) {
            if (this.e == -8) {
                List<EntityLiving> list = this.dM().a(EntityLiving.class, this.cH().c(0.2, 0.0, 0.2));
                Iterator iterator = list.iterator();

                while(iterator.hasNext()) {
                    EntityLiving entityliving = (EntityLiving)iterator.next();
                    this.fuckyou(entityliving);
                }
            }

            if (!this.f) {
                this.dM().a(this, (byte)4);
                this.f = true;
            }

            if (--this.g < 0) {
                this.am();
            }
        }

    }

    private void fuckyou(EntityLiving entityliving) {
        EntityLiving entityliving1 = this.q();
        if (entityliving.bx() && !entityliving.cr() && entityliving != entityliving1) {
            if (entityliving1 == null) {
                // TODO: Error de compilación aquí
//                CraftEventFactory.entityDamage = this;
                entityliving.a(this.dN().o(), 6.0F);
//                CraftEventFactory.entityDamage = null;
            } else {
                if (entityliving1.s(entityliving)) {
                    return;
                }

                entityliving.a(this.dN().c(this, entityliving1), (float) damage);
            }
        }

    }
}
