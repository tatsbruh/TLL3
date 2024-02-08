package com.tll3.Enviroments.Wasteyard;

import com.tll3.Misc.World.Fawe;
import com.tll3.TLL3;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.SplittableRandom;

public class WasteyardPopulator extends BlockPopulator {
    private final SplittableRandom random = new SplittableRandom();
    @Override
    public void populate(WorldInfo worldInfo, Random random, int chunkX, int chunkZ, LimitedRegion limitedRegion) {
        if (random.nextInt(4) == 0) {
            int x = random.nextInt(16) + chunkX * 16;
            int z = random.nextInt(16) + chunkZ * 16;
            int y = 200;
            while (limitedRegion.getType(x, y, z) == Material.AIR && y > 60) y--;
            Location location = new Location(Bukkit.getWorld("world_wasteyard"), x, y, z);
            if (limitedRegion.getType(x, y - 1, z).isSolid()) {
                Bukkit.getScheduler().runTaskLater(TLL3.getInstance(), () ->{
                    Fawe.pasteSchematic(String.valueOf((random.nextInt(13) + 1)), location.subtract(0,2,0));
                }, 15L);
            }
        }
    }

    protected Material doRandomThing() {
        return Material.FIRE;
    }
}
