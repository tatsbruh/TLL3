package com.tll3.Lists.CustomEntities.Others;

import com.tll3.Lists.CustomEntities.CustomPathfinders.NewShootBow;
import com.tll3.Misc.ChatUtils;
import com.tll3.Misc.EntityHelper;
import com.tll3.Misc.ItemBuilder;
import com.tll3.TLL3;
import com.tll3.Task.Mobs.MiniCycloneTask;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalHurtByTarget;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;
import net.minecraft.world.entity.monster.EntitySkeleton;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.World;
import net.minecraft.world.level.pathfinder.PathType;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

public class MiniCyclone extends EntitySkeleton {
    private final CycloneClass clase;
    public enum CycloneClass{
        SPACE,
        METAL,
        NORMAL,
        ZOMBIE
    }
    public MiniCyclone(World world,CycloneClass clas){
        super(EntityTypes.aK,world);
        clase = clas;
        if(clase == CycloneClass.SPACE) {
            this.getBukkitEntity().setCustomName(ChatUtils.format("#c552fbM#be5dfbe#b767fbs#b072fbo#a97dfcc#a288fco#9b92fcs#949dfcm#8ca8fco#85b3fcc#7ebdfcy#77c8fcc#70d3fdl#69defdo#62e8fdn#5bf3fde"));
            EntityHelper.setMainHand((LivingEntity) this.getBukkitEntity(), new ItemStack(Material.AIR));
            EntityHelper.setMobHealth((LivingEntity) this.getBukkitEntity(), 45);
            EntityHelper.setIdentifierString(this.getBukkitEntity(), "minicyclone_space");
            EntityHelper.addPotionEffect((LivingEntity) this.getBukkitEntity(), PotionEffectType.SPEED, 2);
        }else if(clase == CycloneClass.ZOMBIE){
            this.getBukkitEntity().setCustomName(ChatUtils.format("#B668D7Z#B15DD7y#AD52D7c#A847D7l#A33BD6o#9F30D6n#9A25D6e"));
            EntityHelper.setMainHand((LivingEntity) this.getBukkitEntity(), new ItemStack(Material.AIR));
            EntityHelper.setMobHealth((LivingEntity) this.getBukkitEntity(), 30);
            EntityHelper.setIdentifierString(this.getBukkitEntity(), "minicyclone_zombie");
            EntityHelper.addPotionEffect((LivingEntity) this.getBukkitEntity(), PotionEffectType.SPEED, 1);
        }else if(clase == CycloneClass.NORMAL){
            this.getBukkitEntity().setCustomName(ChatUtils.format("&f&lTermacyclone"));
            EntityHelper.setMainHand((LivingEntity) this.getBukkitEntity(), new ItemStack(Material.AIR));
            EntityHelper.setMobHealth((LivingEntity) this.getBukkitEntity(), 60);
            EntityHelper.setIdentifierString(this.getBukkitEntity(), "minicyclone_normal");
            EntityHelper.addPotionEffect((LivingEntity) this.getBukkitEntity(), PotionEffectType.SPEED, 0);
        }
        EntityHelper.setMainHand((LivingEntity) this.getBukkitEntity(),new ItemStack(Material.AIR));
        new MiniCycloneTask((Skeleton) this.getBukkitEntity()).runTaskTimer(TLL3.getInstance(),0L,1L);
        this.craftAttributes.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(0);
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
        this.bO.a(1, new PathfinderGoalMeleeAttack(this, 1.0, true));
        this.bO.a(2, new PathfinderGoalMoveTowardsTarget(this, 0.9, 32.0F));
        this.bO.a(5, new PathfinderGoalRandomStrollLand(this, 1.0));
        this.bO.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.bO.a(6, new PathfinderGoalRandomLookaround(this));
        this.bP.a(1, new PathfinderGoalHurtByTarget(this, new Class[0]));
        this.bP.a(2, new PathfinderGoalNearestAttackableTarget<>(this, EntityHuman.class, true));
    }
    @Override
    public void l() {
        super.l();

    }

    @Override
    protected SoundEffect y() {
        return SoundEffects.cl;
    }
    @Override
    protected SoundEffect d(DamageSource damagesource) {
        return SoundEffects.cs;
    }
    @Override
    protected SoundEffect n_() {
        return SoundEffects.cr;
    }

    SoundEffect w() {
        return SoundEffects.cq;
    }
}
