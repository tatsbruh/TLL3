package com.tll3.Lists.CustomEntities;

import com.tll3.Misc.ChatUtils;
import com.tll3.Misc.EntityHelper;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.goal.PathfinderGoalBowShoot;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFloat;
import net.minecraft.world.entity.ai.goal.PathfinderGoalLookAtPlayer;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomStroll;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalHurtByTarget;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;
import net.minecraft.world.entity.animal.EntityIronGolem;
import net.minecraft.world.entity.monster.EntityIllagerIllusioner;
import net.minecraft.world.entity.monster.EntityIllagerWizard;
import net.minecraft.world.entity.npc.EntityVillagerAbstract;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.raid.EntityRaider;
import net.minecraft.world.level.World;
import net.minecraft.world.level.pathfinder.PathType;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Evoker;
import org.bukkit.entity.Illusioner;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

public class CustomIllusioner extends EntityIllagerIllusioner {
    public CustomIllusioner(World w){
        super(EntityTypes.ab,w);
        this.getBukkitEntity().setCustomName(ChatUtils.format("#0e7b99Everyname"));
        EntityHelper.setIdentifierString(this.getBukkitEntity(),"illusionerex");
        EntityHelper.setMainHand((LivingEntity) this.getBukkitEntity(),new ItemStack(Material.BOW));
        this.craftAttributes.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(55);
        ((LivingEntity) this.getBukkitEntity()).setHealth(55);
        ((LivingEntity) this.getBukkitEntity()).setRemoveWhenFarAway(true);
        ((Illusioner)this.getBukkitEntity()).setCanJoinRaid(false);
        this.getBukkitEntity().setPersistent(false);
        this.persist = false;
        //No longer avoids water, fire, lava, magmablocks
        this.a(PathType.j, 0.0F);
        this.a(PathType.i, 0.0F);
        this.a(PathType.n, 0.0F);
        this.a(PathType.o, 0.0F);
    }

    @Override
    protected void B() {
        this.bO.a(1, new EntityRaider.b<>(this));
        //this.bO.a(4, new b());
        //this.bO.a(5, new a());
        this.bO.a(0, new PathfinderGoalFloat(this));
        this.bO.a(6, new PathfinderGoalBowShoot<>(this, 0.5, 20, 15.0F));
        this.bO.a(8, new PathfinderGoalRandomStroll(this, 0.6));
        this.bO.a(9, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 3.0F, 1.0F));
        this.bO.a(10, new PathfinderGoalLookAtPlayer(this, EntityInsentient.class, 8.0F));
        this.bP.a(1, (new PathfinderGoalHurtByTarget(this, new Class[]{EntityRaider.class})).a(new Class[0]));
        this.bP.a(2, (new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, true)).c(300));
    }
    private class b extends EntityIllagerWizard.PathfinderGoalCastSpell {
        b() {
            super();
        }

        public boolean a() {
            return !super.a() ? false : !CustomIllusioner.this.a(MobEffects.n);
        }

        protected int h() {
            return 20;
        }

        protected int i() {
            return 300;
        }

        protected void k() {
            CustomIllusioner.this.addEffect(new MobEffect(MobEffects.G, 600), EntityPotionEffectEvent.Cause.ILLUSION);
        }

        protected @Nullable SoundEffect l() {
            return SoundEffects.mw;
        }

        protected EntityIllagerWizard.Spell m() {
            return Spell.e;
        }
    }
    private class a extends EntityIllagerWizard.PathfinderGoalCastSpell {
        private int e;

        a() {
            super();
        }

        public boolean a() {
            return !super.a() ? false : (CustomIllusioner.this.q() == null ? false : (CustomIllusioner.this.q().aj() == this.e ? false : CustomIllusioner.this.dM().d_(CustomIllusioner.this.dm()).a((float) EnumDifficulty.c.ordinal())));
        }

        public void c() {
            super.c();
            EntityLiving entityliving = CustomIllusioner.this.q();
            if (entityliving != null) {
                this.e = entityliving.aj();
            }

        }

        protected int h() {
            return 20;
        }

        protected int i() {
            return 180;
        }

        protected void k() {
            CustomIllusioner.this.q().addEffect(new MobEffect(MobEffects.G, 400), CustomIllusioner.this, EntityPotionEffectEvent.Cause.ATTACK);
        }

        protected SoundEffect l() {
            return SoundEffects.mv;
        }

        protected EntityIllagerWizard.Spell m() {
            return Spell.f;
        }
    }
}
