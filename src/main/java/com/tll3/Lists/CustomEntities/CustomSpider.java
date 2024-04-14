package com.tll3.Lists.CustomEntities;

import com.tll3.Misc.ChatUtils;
import com.tll3.Misc.EntityHelper;
import com.tll3.Misc.ItemBuilder;
import com.tll3.TLL3;
import com.tll3.Task.Mobs.ZenithDashTask;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.monster.EntitySpider;
import net.minecraft.world.level.World;
import net.minecraft.world.level.pathfinder.PathType;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Spider;
import org.bukkit.potion.PotionEffectType;

public class CustomSpider extends EntitySpider {
    public CustomSpider(World w){
        super(EntityTypes.aT,w);
        this.getBukkitEntity().setCustomName(ChatUtils.format("#4000ffZenith Broodmancer"));
        EntityHelper.setMobHealth((LivingEntity) this.getBukkitEntity(),55);
        EntityHelper.setMobDamage((LivingEntity) this.getBukkitEntity(),20);
        EntityHelper.addPotionEffect((LivingEntity) this.getBukkitEntity(), PotionEffectType.FIRE_RESISTANCE,0);
        EntityHelper.addPotionEffect((LivingEntity) this.getBukkitEntity(), PotionEffectType.SPEED,2);
        new ZenithDashTask((Spider) this.getBukkitEntity()).runTaskTimer(TLL3.getInstance(),0L,1L);
        EntityHelper.setIdentifierString(this.getBukkitEntity(),"zenithspider");
        EntityHelper.setIdentifierString(this.getBukkitEntity(),"zenith");
        ((LivingEntity) this.getBukkitEntity()).setRemoveWhenFarAway(true);
        this.getBukkitEntity().setPersistent(false);
        this.persist = false;
        this.a(PathType.j, 0.0F);
        this.a(PathType.i, 0.0F);
        this.a(PathType.n, 0.0F);
        this.a(PathType.o, 0.0F);
    }
    @Override //Can fall from any distance
    public int cu(){
        return Integer.MAX_VALUE;
    }
}
