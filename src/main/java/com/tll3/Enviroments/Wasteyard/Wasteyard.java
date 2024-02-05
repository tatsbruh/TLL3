package com.tll3.Enviroments.Wasteyard;


import com.tll3.Misc.World.FastNoiseLite;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;
import org.bukkit.util.noise.SimplexOctaveGenerator;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.*;
import java.util.List;

public class Wasteyard extends ChunkGenerator {
    private final SplittableRandom random = new SplittableRandom();
    private final FastNoiseLite terrainNoise = new FastNoiseLite();
    private final FastNoiseLite detailNoise = new FastNoiseLite();

    protected Material getRandomMaterial() {
        int r = random.nextInt(100);
        if(r >= 60){
            return Material.GRAY_CONCRETE_POWDER;
        }else if(r < 60 && r >= 40){
            return Material.LIGHT_GRAY_CONCRETE;
        }
        return Material.GRAY_GLAZED_TERRACOTTA;
    }
    protected Material getUnderMaterial() {
        int r = random.nextInt(100);
        if(r >= 70){return Material.TUFF;
        }else if(r < 70 && r >= 55){
            return Material.SMOOTH_BASALT;
        }else if(r < 55 && r >= 25){
            return Material.BLACKSTONE;
        }
        return Material.DEEPSLATE;
    }

    private final HashMap<Integer, List<Material>> layers = new HashMap<>() {{
        put(0, Arrays.asList(getRandomMaterial()));
        put(1, Arrays.asList(getUnderMaterial()));
        put(2, Arrays.asList(getUnderMaterial(), Material.BEDROCK));
        put(3, Arrays.asList(Material.BEDROCK));
    }};
    private final WasteyardPopulator populator;
    public Wasteyard() {
        terrainNoise.SetFrequency(0.006f);
        detailNoise.SetFrequency(0.03f);
        terrainNoise.SetFractalType(FastNoiseLite.FractalType.FBm);
        terrainNoise.SetFractalOctaves(5);
        this.populator = new WasteyardPopulator();
    }

    @Override
    public void generateNoise(WorldInfo worldInfo, Random random, int chunkX, int chunkZ, ChunkData chunkData) {
        for (int y = chunkData.getMinHeight(); y < 130 && y < chunkData.getMaxHeight(); y++) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    float noise2 = (terrainNoise.GetNoise(x + (chunkX * 16), z + (chunkZ * 16)) * 2) + (detailNoise.GetNoise(x + (chunkX * 16), z + (chunkZ * 16)) / 10);
                    float noise3 = detailNoise.GetNoise(x + (chunkX * 16), y, z + (chunkZ * 16));
                    float currentY = (47 + (noise2 * 30));
                    if (y < 1) {
                        chunkData.setBlock(x, y, z, layers.get(3).get(random.nextInt(layers.get(3).size())));
                    } else if (y < currentY) {
                        float distanceToSurface = Math.abs(y - currentY);
                        double function = .1 * Math.pow(distanceToSurface, 2) - 1;
                        if (noise3 > Math.min(function, -.3)) {
                            if (distanceToSurface < 1 && y > 63) {
                                chunkData.setBlock(x, y, z, getRandomMaterial());
                            } else if (distanceToSurface < 5) {
                                chunkData.setBlock(x, y, z, getUnderMaterial());
                            } else {
                                Material neighbour = getUnderMaterial();
                                List<Material> neighbourBlocks = new ArrayList<Material>(Arrays.asList(chunkData.getType(Math.max(x - 1, 0), y, z), chunkData.getType(x, Math.max(y - 1, 0), z), chunkData.getType(x, y, Math.max(z - 1, 0))));
                                if (random.nextFloat() < 0.002) {
                                    neighbour = layers.get(2).get(Math.min(random.nextInt(layers.get(2).size()), random.nextInt(layers.get(2).size())));
                                }
                                if ((!Collections.disjoint(neighbourBlocks, layers.get(2)))) {
                                    for (Material neighbourBlock : neighbourBlocks) {
                                        if (layers.get(2).contains(neighbourBlock) && random.nextFloat() < -0.01 * layers.get(2).indexOf(neighbourBlock) + 0.4) {
                                            neighbour = neighbourBlock;
                                        }
                                    }
                                }
                                chunkData.setBlock(x, y, z, neighbour);
                            }
                        }
                    } else if (y < 62) {
                        chunkData.setBlock(x, y, z, Material.WATER);
                    }
                }
            }
        }
    }



    @Nullable
    @Override
    public BiomeProvider getDefaultBiomeProvider(@NotNull WorldInfo worldInfo) {
        return new WasteyardBiome();
    }

    @Override
    public @NotNull List<BlockPopulator> getDefaultPopulators(@NotNull World world) {
        return List.of(populator);
    }



    public static class WasteyardBiome extends BiomeProvider {
        @NotNull
        @Override
        public Biome getBiome(@NotNull WorldInfo worldInfo, int i, int i1, int i2) {
            return Biome.BASALT_DELTAS;
        }
        @NotNull
        @Override
        public List<Biome> getBiomes(@NotNull WorldInfo worldInfo) {
            return List.of(Biome.BASALT_DELTAS);
        }
    }
}
