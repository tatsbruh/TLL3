package com.tll3.Lists.CustomEntities.Others;

import com.tll3.Misc.ChatUtils;
import com.tll3.Misc.EntityHelper;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.monster.EntityCreeper;
import net.minecraft.world.level.World;
import net.minecraft.world.level.pathfinder.PathType;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_20_R3.event.CraftEventFactory;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

public class BetterCreeper extends EntityCreeper {
    public BetterCreeper(World w){
        super(EntityTypes.v,w);
        EntityHelper.addPotionEffect((LivingEntity) this.getBukkitEntity(), PotionEffectType.FIRE_RESISTANCE, 0);
        EntityHelper.addPotionEffect((LivingEntity) this.getBukkitEntity(), PotionEffectType.SPEED, 1);
        this.getBukkitEntity().setCustomName(ChatUtils.format("&2&lElder Vortex"));
        EntityHelper.setMobHealth((LivingEntity) this.getBukkitEntity(), 45);
        EntityHelper.setIdentifierString(this.getBukkitEntity(), "reliccreeper");
        EntityHelper.setIdentifierString(this.getBukkitEntity(), "relicmob");
        this.setPowered(true);
        this.bV = 6;
        ((LivingEntity) this.getBukkitEntity()).setRemoveWhenFarAway(true);
        this.getBukkitEntity().setPersistent(false);
        this.persist = false;
    }
    @Override //Can fall from any distance
    public int cu(){
        return Integer.MAX_VALUE;
    }

    @Override
    public void gh() {
        if (!this.dM().B) {
            float f = this.a() ? 2.0F : 1.0F;
            ExplosionPrimeEvent event = CraftEventFactory.callExplosionPrimeEvent(this, (float)this.bV * f, false);
            this.ba = false;
            this.dM().a(this, this.dr(), this.dt(), this.dx(), event.getRadius(), event.getFire(), World.a.c);
            this.bT = 0;

        }

    }
}
