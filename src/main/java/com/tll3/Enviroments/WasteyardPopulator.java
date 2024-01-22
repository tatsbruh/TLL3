package com.tll3.Enviroments;

import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.SplittableRandom;

public class WasteyardPopulator extends BlockPopulator {
    private final SplittableRandom random = new SplittableRandom();
    @Override
    public void populate(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, @NotNull LimitedRegion limitedRegion) {
        for(int iteration = 0; iteration < 3; iteration++) {
            int x = random.nextInt(16) + chunkX * 16;
            int z = random.nextInt(16) + chunkZ * 16;
            int y = 82;
            while(limitedRegion.getType(x, y, z).isAir() && y > -64) y--;
            if(limitedRegion.getType(x, y, z).isSolid() && limitedRegion.getType(x,y + 1,z).isAir()) {
                limitedRegion.setType(x, y + 1, z, doRandomThing());
            }

        }
    }

    protected Material doRandomThing() {
        return Material.FIRE;
    }
}
