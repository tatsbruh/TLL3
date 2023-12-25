package com.tll3.Lists.CustomEntities;

import net.minecraft.world.entity.EntityTypes;

import net.minecraft.world.entity.monster.EntityCreeper;
import net.minecraft.world.level.World;

public class CustomCreeper extends EntityCreeper {
    public CustomCreeper(World world) {
        super(EntityTypes.v, world);
    }

    @Override
    public int cu(){
        return Integer.MAX_VALUE;
    }
}
