package com.tll3.Lists.CustomEntities;

import com.tll3.Lists.CustomEntities.CustomPathfinders.NewShootBow;
import com.tll3.Misc.ChatUtils;
import com.tll3.Misc.EntityHelper;
import com.tll3.Misc.ItemBuilder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalHurtByTarget;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;
import net.minecraft.world.entity.animal.EntityIronGolem;
import net.minecraft.world.entity.animal.EntityTurtle;
import net.minecraft.world.entity.animal.EntityWolf;
import net.minecraft.world.entity.monster.EntitySkeleton;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.World;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityPotionEffectEvent;

public class CustomSkeleton extends EntitySkeleton {
    public CustomSkeleton(World world){
        super(EntityTypes.aK,world);
        this.getBukkitEntity().setCustomName(ChatUtils.format("#4000ffZenith Skeleton"));
        EntityHelper.setMainHand((LivingEntity) this.getBukkitEntity(),new ItemBuilder(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE,49).build());
        this.addEffect(new MobEffect(MobEffects.l,Integer.MAX_VALUE,0), EntityPotionEffectEvent.Cause.PLUGIN);
        ((LivingEntity) this.getBukkitEntity()).setRemoveWhenFarAway(true);
        this.getBukkitEntity().setPersistent(false);
        this.persist = false;

    }

    @Override
    protected void B() {
        this.bO.a(4,new NewShootBow<>(this, 1.0, 20, 15.0F));
        this.bO.a(5, new PathfinderGoalRandomStrollLand(this, 1.0));
        this.bO.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.bO.a(6, new PathfinderGoalRandomLookaround(this));
        this.bP.a(1, new PathfinderGoalHurtByTarget(this, new Class[0]));
        this.bP.a(2, new PathfinderGoalNearestAttackableTarget<>(this, EntityHuman.class, true));
    }

    @Override //Can fall from any distance
    public int cu(){
        return Integer.MAX_VALUE;
    }

}
