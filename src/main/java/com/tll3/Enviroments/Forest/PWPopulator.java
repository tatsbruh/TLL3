package com.tll3.Enviroments.Forest;

import com.tll3.Misc.World.Fawe;
import com.tll3.TLL3;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.generator.WorldInfo;

import java.util.Random;
import java.util.SplittableRandom;

public class PWPopulator extends BlockPopulator {
    private final SplittableRandom random = new SplittableRandom();
    @Override
    public void populate(WorldInfo worldInfo, Random random, int chunkX, int chunkZ, LimitedRegion limitedRegion) {
        for(int iteration = 0; iteration < 10; iteration++) {
            int x = random.nextInt(16) + chunkX * 16;
            int z = random.nextInt(16) + chunkZ * 16;
            int y = 319;
            while(limitedRegion.getType(x, y, z).isAir() && y > -64) y--;
            Location location = new Location(Bukkit.getWorld("world_primeval"), x, y, z);
            if(limitedRegion.getType(x, y, z).isSolid()) {
                if(limitedRegion.getType(x,y,z) == Material.GRASS_BLOCK){
                if(random.nextInt(100) > 50) {
                    limitedRegion.setType(x, y + 1, z, getRandomMaterial());
                }else {
                    limitedRegion.generateTree(location.add(0, 1, 0), random, TreeType.DARK_OAK);
                }
            }
            }else if(limitedRegion.getType(x,y,z).isSolid() && limitedRegion.getType(x,y + 1,z) == Material.WATER){
                limitedRegion.setType(x, y + 1, z, Material.SEAGRASS);
            }
        }
    }
    protected Material getRandomMaterial() {
        int r = random.nextInt(100);
        if(r >= 70){return Material.POPPY;
        }else if(r < 70 && r >= 55){
            return Material.BLUE_ORCHID;
        }else if(r < 55 && r >= 25){
            return Material.WHITE_TULIP;
        }
        return Material.SHORT_GRASS;
    }
}
