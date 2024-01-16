package com.tll3.Enviroments;


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
import java.awt.*;
import java.util.*;
import java.util.List;

public class Wasteyard extends ChunkGenerator {
    private final SplittableRandom random = new SplittableRandom();
    private final WasteyardPopulator populator;
    public Wasteyard() {
        this.populator = new WasteyardPopulator();
    }

    @Override
    public void generateNoise(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, @NotNull ChunkGenerator.ChunkData chunkData) {
        SimplexOctaveGenerator generator = new SimplexOctaveGenerator(worldInfo.getSeed(), 8);
        generator.setScale(0.012D);
        for (int X = 0; X < 16; X++) {
            for (int Z = 0; Z < 16; Z++) {
                double noise = generator.noise(chunkX * 16 + X, chunkZ * 16 + Z, 0.32D, 0.17D);
                int height = (int) (noise * 38);
                int y = 95;
                if (height <= 40) {
                    for(int y2 = height; y2 <= 40; y2++) {
                        this.setBlock(chunkData,X,y2,Z,Material.LAVA);
                    }
                }
                for (int i = 0; i < height / 2; i++) {
                    this.setBlock(chunkData, X, y + i, Z, this.getRandomMaterial2());
                }
                for (int i = 0; i < height; i++) {
                    this.setBlock(chunkData, X, y - i, Z, this.getUnderMaterial());
                }

            }
        }
    }


    @Override
    public void generateSurface(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, @NotNull ChunkGenerator.ChunkData chunkData) {
        SimplexOctaveGenerator generator = new SimplexOctaveGenerator(new Random(worldInfo.getSeed()), 6);
        generator.setScale(0.008);
        int worldX = chunkX * 16;
        int worldZ = chunkZ * 16;
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                double noise = generator.noise(worldX + x, worldZ + z, 1, 1, true);
                int height = (int) (noise * 40);
                height += 84;
                if (height > chunkData.getMaxHeight()) {
                    height = chunkData.getMaxHeight();
                }

                for (int y = 0; y < height / 2; y++) {
                    chunkData.setBlock(x, y, z, getRandomMaterial());
                }
            }
        }
    }

    @Override
    public void generateBedrock(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, @NotNull ChunkGenerator.ChunkData chunkData) {
        if (chunkData.getMinHeight() == worldInfo.getMinHeight()) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    chunkData.setBlock(x, 30, z, Material.BEDROCK);
                }
            }
        }
    }
    protected void setBlock(ChunkData data, int X, int Y, int Z, Material material) {
        data.setBlock(X, Y, Z, material);
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
    protected Material getRandomMaterial() {
        int r = random.nextInt(100);
        if(r >= 70){return Material.LIGHT_GRAY_CONCRETE_POWDER;
        }else if(r < 70 && r >= 55){
            return Material.GRAY_GLAZED_TERRACOTTA;
        }else if(r < 55 && r >= 25){
            return Material.BLACKSTONE;
        }
        return Material.GRAY_CONCRETE_POWDER;
    }
    protected Material getRandomMaterial2() {
        int r = random.nextInt(100);
        if(r >= 70){return Material.LIGHT_GRAY_CONCRETE_POWDER;
        }else if(r < 70 && r >= 35){
            return Material.GRAY_GLAZED_TERRACOTTA;
        }else if(r < 35 && r >= 25){
            return Material.MAGMA_BLOCK;
        }
        return Material.GRAY_CONCRETE_POWDER;
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
    public boolean shouldGenerateSurface() { return true; }

}
