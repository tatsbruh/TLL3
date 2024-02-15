package com.tll3.Enviroments.Volcano;

import com.tll3.Enviroments.Forest.PWPopulator;
import com.tll3.Misc.World.FastNoiseLite;
import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class ScorchedPlateau extends ChunkGenerator {
    private final FastNoiseLite terrainNoise = new FastNoiseLite();
    private final FastNoiseLite detailNoise = new FastNoiseLite();
    private final HashMap<Integer, List<Material>> layers = new HashMap<Integer, List<Material>>() {{
        put(0, Arrays.asList(Material.NETHERRACK));
        put(1, Arrays.asList(Material.NETHERRACK));
        put(2, Arrays.asList(Material.COAL_ORE, Material.IRON_ORE, Material.REDSTONE_ORE, Material.LAPIS_ORE, Material.GOLD_ORE, Material.DIAMOND_ORE));
        put(3, Arrays.asList(Material.BEDROCK));
    }};
    public ScorchedPlateau() {
        terrainNoise.SetFrequency(0.001f);
        detailNoise.SetFrequency(0.0008f);
        detailNoise.SetNoiseType(FastNoiseLite.NoiseType.Perlin);
        terrainNoise.SetFractalType(FastNoiseLite.FractalType.FBm);
        terrainNoise.SetFractalOctaves(9);

    }

    @Override
    public void generateNoise(WorldInfo worldInfo, Random random, int chunkX, int chunkZ, ChunkData chunkData) {
        for(int y = chunkData.getMinHeight(); y < 130 && y < chunkData.getMaxHeight(); y++) {
            for(int x = 0; x < 16; x++) {
                for(int z = 0; z < 16; z++) {
                    float noise2 = (terrainNoise.GetNoise(x + (chunkX * 16), z + (chunkZ * 16)) * 2) + (detailNoise.GetNoise(x + (chunkX * 16), z + (chunkZ * 16)) / 10);
                    float currentY = (65 + (noise2 * 30));

                    if(y < 1) {
                        chunkData.setBlock(x, y, z, layers.get(3).get(random.nextInt(layers.get(3).size())));
                    }
                    else if(y < currentY) {
                        float distanceToSurface = Math.abs(y - currentY); // The absolute y distance to the world surface.
                        // Set grass if the block closest to the surface.
                        if(distanceToSurface < 1 && y > 63) {
                            chunkData.setBlock(x, y, z, layers.get(0).get(0));
                        }
                        else if(distanceToSurface < 5) {
                            chunkData.setBlock(x, y, z, layers.get(1).get(random.nextInt(layers.get(1).size())));
                        }
                        else {
                            chunkData.setBlock(x, y, z, Material.BLACKSTONE);
                        }
                    }
                    else if(y < 65) {
                        chunkData.setBlock(x, y, z, Material.LAVA);
                    }
                }
            }
        }
    }
}
