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
        terrainNoise.SetFrequency(0.001f);
        detailNoise.SetFrequency(0.05f);
        terrainNoise.SetFractalType(FastNoiseLite.FractalType.FBm);
        terrainNoise.SetFractalOctaves(5);
        this.populator = new WasteyardPopulator();
    }

    @Override
    public void generateNoise(WorldInfo worldInfo, Random random, int chunkX, int chunkZ, ChunkData chunkData) {
        for(int y = chunkData.getMinHeight(); y < 130 && y < chunkData.getMaxHeight(); y++) {
            for(int x = 0; x < 16; x++) {
                for(int z = 0; z < 16; z++) {
                    float noise2 = (terrainNoise.GetNoise(x + (chunkX * 16), z + (chunkZ * 16)) * 2) + (detailNoise.GetNoise(x + (chunkX * 16), z + (chunkZ * 16)) / 10);

                    if(65 + (30 * noise2) > y) {
                        chunkData.setBlock(x, y, z, getRandomMaterial());
                    }
                    else if(y < 62) {
                        chunkData.setBlock(x, y, z, Material.LAVA);
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
