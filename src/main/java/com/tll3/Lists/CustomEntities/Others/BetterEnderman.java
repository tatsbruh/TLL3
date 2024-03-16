package com.tll3.Lists.CustomEntities.Others;

import com.tll3.Misc.ChatUtils;
import com.tll3.Misc.EntityHelper;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.monster.EntityEnderman;
import net.minecraft.world.level.World;
import net.minecraft.world.level.pathfinder.PathType;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

public class BetterEnderman extends EntityEnderman {
    public BetterEnderman(World w){
        super(EntityTypes.F,w);
        EntityHelper.addPotionEffect((LivingEntity) this.getBukkitEntity(), PotionEffectType.FIRE_RESISTANCE, 0);
        this.a(PathType.j, 0.0F); //No longer avoids water
        this.a(PathType.i, 0.0F); //No longer avoids lava
        this.a(PathType.n, 0.0F); //No longer avoids fire
        this.a(PathType.o, 0.0F); //No longer avoids magma blocks

        //Relic Enderman shit
        this.getBukkitEntity().setCustomName(ChatUtils.format("&2&lPrimeval Watcher"));
        this.craftAttributes.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(20);
        EntityHelper.setMobHealth((LivingEntity) this.getBukkitEntity(), 65);
        EntityHelper.setMainHand((LivingEntity) this.getBukkitEntity(), new ItemStack(Material.AIR));
        EntityHelper.setMobHealth((LivingEntity) this.getBukkitEntity(), 45);
        EntityHelper.setIdentifierString(this.getBukkitEntity(), "relicenderman");
        EntityHelper.setIdentifierString(this.getBukkitEntity(), "relicmob");
        ((LivingEntity) this.getBukkitEntity()).setRemoveWhenFarAway(true);
        this.getBukkitEntity().setPersistent(false);
        this.persist = false;
    }
    @Override
    public boolean fh() {  //No longer takes damage by water
        return false;
    }
    @Override //Can fall from any distance
    public int cu(){
        return Integer.MAX_VALUE;
    }
}
