package com.tll3.Lists.CustomEntities;

import com.tll3.Misc.ChatUtils;
import net.minecraft.world.entity.EntityTypes;

import net.minecraft.world.entity.monster.EntityCreeper;
import net.minecraft.world.level.World;

public class CustomCreeper extends EntityCreeper {
    public CustomCreeper(World world) {
        super(EntityTypes.v, world);
        this.getBukkitEntity().setCustomName(ChatUtils.format("&bSupercharged Creeper"));
        this.setPowered(true);
        this.getBukkitEntity().setPersistent(false);
        this.persist = false;
    }

    @Override
    public int cu(){
        return Integer.MAX_VALUE;
    }

}
