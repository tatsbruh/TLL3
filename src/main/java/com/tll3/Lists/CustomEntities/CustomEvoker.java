package com.tll3.Lists.CustomEntities;

import com.tll3.Lists.CustomEntities.CustomProjectiles.CustomEvokerFangs;
import com.tll3.Misc.ChatUtils;
import com.tll3.Misc.EntityHelper;
import com.tll3.Misc.GenericUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.util.MathHelper;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.PathfinderGoalAvoidTarget;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFloat;
import net.minecraft.world.entity.ai.goal.PathfinderGoalLookAtPlayer;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomStroll;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalHurtByTarget;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;
import net.minecraft.world.entity.ai.targeting.PathfinderTargetCondition;
import net.minecraft.world.entity.animal.EntityIronGolem;
import net.minecraft.world.entity.animal.EntitySheep;
import net.minecraft.world.entity.monster.EntityEvoker;
import net.minecraft.world.entity.monster.EntityIllagerWizard;
import net.minecraft.world.entity.monster.EntityVex;
import net.minecraft.world.entity.npc.EntityVillagerAbstract;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.projectile.EntityEvokerFangs;
import net.minecraft.world.entity.raid.EntityRaider;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3D;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.scores.ScoreboardTeam;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Evoker;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.CreatureSpawnEvent;

import javax.annotation.Nullable;

public class CustomEvoker extends EntityEvoker {

    private EntitySheep e;

    public CustomEvoker(World w,boolean canjoinraid){
        super(EntityTypes.H,w);
        this.getBukkitEntity().setCustomName(ChatUtils.format("#0e7b99Ultravoker"));
        EntityHelper.setIdentifierString(this.getBukkitEntity(),"evokerex");
        this.craftAttributes.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(55);
        ((LivingEntity) this.getBukkitEntity()).setHealth(55);
        ((LivingEntity) this.getBukkitEntity()).setRemoveWhenFarAway(true);
        ((Evoker)this.getBukkitEntity()).setCanJoinRaid(canjoinraid);
        this.getBukkitEntity().setPersistent(false);
        this.persist = false;
    }

    @Override
    protected void B() {
        super.B();
        this.bO.a(0, new PathfinderGoalFloat(this));
        this.bO.a(1, new b());
        this.bO.a(2, new PathfinderGoalAvoidTarget(this, EntityHuman.class, 8.0F, 0.6, 1.0));
        this.bO.a(4, new c());
        this.bO.a(5, new a());
        this.bO.a(6, new d());
        this.bO.a(8, new PathfinderGoalRandomStroll(this, 0.6));
        this.bO.a(9, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 3.0F, 1.0F));
        this.bO.a(10, new PathfinderGoalLookAtPlayer(this, EntityInsentient.class, 8.0F));
        this.bP.a(1, (new PathfinderGoalHurtByTarget(this, new Class[]{EntityRaider.class})).a(new Class[0]));
        this.bP.a(2, (new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, true)).c(300));
        this.bP.a(3, (new PathfinderGoalNearestAttackableTarget(this, EntityVillagerAbstract.class, false)).c(300));
    }



    private class b extends EntityIllagerWizard.b {
        b() {
            super();
        }

        public void e() {
            if (CustomEvoker.this.q() != null) {
                CustomEvoker.this.I().a(CustomEvoker.this.q(), (float)CustomEvoker.this.ab(), (float)CustomEvoker.this.aa());
            } else if (sheep() != null) {
                CustomEvoker.this.I().a(CustomEvoker.this.sheep(), (float)CustomEvoker.this.ab(), (float)CustomEvoker.this.aa());
            }

        }
    }
    private class a extends EntityIllagerWizard.PathfinderGoalCastSpell {
        a() {
            super();
        }

        @Override
        protected int h() {
            if(GenericUtils.getDay() >= 28){
                return 5;
            }
            return 20;
        }

        @Override
        protected int i() {
            if(GenericUtils.getDay() >= 28){
                return 50;
            }
            return 85;
        }

        @Override
        protected void k() {
            EntityLiving entityliving = CustomEvoker.this.q();
            double d0 = Math.min(entityliving.dt(), CustomEvoker.this.dt());
            double d1 = Math.max(entityliving.dt(), CustomEvoker.this.dt()) + 1.0;
            float f = (float)MathHelper.d(entityliving.dx() - CustomEvoker.this.dx(), entityliving.dr() - CustomEvoker.this.dr());
            int i;
            if (CustomEvoker.this.f((Entity) entityliving) < 9.0) {
                float f1;
                for(i = 0; i < 5; ++i) {
                    f1 = f + (float)i * 3.1415927F * 0.4F;
                    this.a(CustomEvoker.this.dr() + (double)MathHelper.b(f1) * 1.5, CustomEvoker.this.dx() + (double)MathHelper.a(f1) * 1.5, d0, d1, f1, 0);
                }

                for(i = 0; i < 8; ++i) {
                    f1 = f + (float)i * 3.1415927F * 2.0F / 8.0F + 1.2566371F;
                    this.a(CustomEvoker.this.dr() + (double)MathHelper.b(f1) * 2.5, CustomEvoker.this.dx() + (double)MathHelper.a(f1) * 2.5, d0, d1, f1, 3);
                }
            } else {
                for(i = 0; i < 16; ++i) {
                    double d2 = 1.25 * (double)(i + 1);
                    int j = 1 * i;
                    this.a(CustomEvoker.this.dr() + (double)MathHelper.b(f) * d2, CustomEvoker.this.dx() + (double)MathHelper.a(f) * d2, d0, d1, f, j);
                }
            }

        }

        private void a(double d0, double d1, double d2, double d3, float f, int i) {
            BlockPosition blockposition = BlockPosition.a(d0, d3, d1);
            boolean flag = false;
            double d4 = 0.0;

            do {
                BlockPosition blockposition1 = blockposition.d();
                IBlockData iblockdata = CustomEvoker.this.dM().a_(blockposition1);
                if (iblockdata.d(CustomEvoker.this.dM(), blockposition1, EnumDirection.b)) {
                    if (!CustomEvoker.this.dM().u(blockposition)) {
                        IBlockData iblockdata1 = CustomEvoker.this.dM().a_(blockposition);
                        VoxelShape voxelshape = iblockdata1.k(CustomEvoker.this.dM(), blockposition);
                        if (!voxelshape.c()) {
                            d4 = voxelshape.c(EnumDirection.EnumAxis.b);
                        }
                    }

                    flag = true;
                    break;
                }

                blockposition = blockposition.d();
            } while(blockposition.v() >= MathHelper.a(d2) - 1);

            if (flag) {
                CustomEvoker.this.dM().b(new CustomEvokerFangs(CustomEvoker.this.dM(), d0, (double)blockposition.v() + d4, d1, f, i, CustomEvoker.this,40.0F));
            }

        }

        protected SoundEffect l() {
            return SoundEffects.hZ;
        }

        protected EntityIllagerWizard.Spell m() {
            return Spell.c;
        }
    }

    private class c extends EntityIllagerWizard.PathfinderGoalCastSpell {
        private final PathfinderTargetCondition e = PathfinderTargetCondition.b().a(16.0).d().e();

        c() {
            super();
        }

        public boolean a() {
            if (!super.a()) {
                return false;
            } else {
                int i = CustomEvoker.this.dM().a(EntityVex.class, this.e, CustomEvoker.this, CustomEvoker.this.cH().g(16.0)).size();
                return CustomEvoker.this.ag.a(12) + 1 > i;
            }
        }

        @Override
        protected int h() {
            if(GenericUtils.getDay() >= 28){
                return 55;
            }
            return 80;
        }

        @Override
        protected int i() {
            if(GenericUtils.getDay() >= 28){
                return 200;
            }
            return 350;
        }

        @Override
        protected void k() {
            WorldServer worldserver = (WorldServer)CustomEvoker.this.dM();
            ScoreboardTeam scoreboardteam = CustomEvoker.this.cg();

            for(int i = 0; i < 6; ++i) {
                BlockPosition blockposition = CustomEvoker.this.dm().b(-2 + CustomEvoker.this.ag.a(5), 1, -2 + CustomEvoker.this.ag.a(5));
                EntityVex entityvex = (EntityVex)EntityTypes.bf.a(CustomEvoker.this.dM());
                if (entityvex != null) {
                    entityvex.a(blockposition, 0.0F, 0.0F);
                    entityvex.a(worldserver, CustomEvoker.this.dM().d_(blockposition), EnumMobSpawn.f, (GroupDataEntity)null, (NBTTagCompound)null);
                    entityvex.a(CustomEvoker.this);
                    entityvex.i(blockposition);
                    entityvex.b(20 * (30 + CustomEvoker.this.ag.a(90)));
                    worldserver.addFreshEntityWithPassengers(entityvex, CreatureSpawnEvent.SpawnReason.SPELL);
                }
            }

        }

        protected SoundEffect l() {
            return SoundEffects.ia;
        }

        protected EntityIllagerWizard.Spell m() {
            return Spell.b;
        }
    }
    @Nullable EntitySheep sheep() {
        return this.e;
    }
}
