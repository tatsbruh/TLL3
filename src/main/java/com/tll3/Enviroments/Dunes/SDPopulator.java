package com.tll3.Enviroments.Dunes;

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

public class SDPopulator extends BlockPopulator {
    private final SplittableRandom random = new SplittableRandom();
    @Override
    public void populate(WorldInfo worldInfo, Random random, int chunkX, int chunkZ, LimitedRegion limitedRegion) {
        if(random.nextInt(4) == 0){
            int x = random.nextInt(16) + chunkX * 16;
            int z = random.nextInt(16) + chunkZ * 16;
            int y = 200;
            while(limitedRegion.getType(x, y, z).isAir() && y > -64) y--;
            Location location = new Location(Bukkit.getWorld("world_dunes"), x, y, z);
            if(limitedRegion.getType(x, y, z).isSolid()) {
                if(limitedRegion.getType(x,y,z) == Material.RED_SANDSTONE || limitedRegion.getType(x,y,z) == Material.RED_SAND){
                    if(random.nextInt(100) > 50) {
                        if(random.nextInt(100) > 50) {
                            limitedRegion.setType(x, y + 1, z, Material.CACTUS);
                            limitedRegion.setType(x, y + 2, z, Material.CACTUS);
                            limitedRegion.setType(x, y + 3, z, Material.CACTUS);
                        }else {
                            limitedRegion.setType(x, y + 1, z, Material.DEAD_BUSH);
                        }
                        }else{
                        Bukkit.getScheduler().runTaskLater(TLL3.getInstance(), () ->{
                            Fawe.pasteSchematic("dunes" + (random.nextInt(8) + 1), location.subtract(0,2,0));
                        }, 15L);
                    }
                }
            }
        }
    }
}
