package com.tll3.Enviroments.Zero;

import com.google.common.collect.Lists;
import com.tll3.Enviroments.Wasteyard.Wasteyard;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;
import org.bukkit.util.noise.SimplexOctaveGenerator;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.SplittableRandom;

public class ZeroHorizon extends ChunkGenerator{
        private final SplittableRandom random = new SplittableRandom();

    @Override
    public void generateBedrock(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, @NotNull ChunkGenerator.ChunkData chunkData) {
        SimplexOctaveGenerator generator = new SimplexOctaveGenerator(worldInfo.getSeed(), 8);
        generator.setScale(0.02D);
        for (int X = 0; X < 16; X++) {
            for (int Z = 0; Z < 16; Z++) {
                double noise = (generator.noise(chunkX * 16 + X, chunkZ * 16 + Z, 0.5D, 0.5D));
                int height = (int) (noise * 38);
                int y = 85;
                for (int i = 0; i < height / 2; i++) {
                    this.setBlock(chunkData, X, y + i, Z, this.getRandomMaterial());
                }
                for (int i = 0; i < height; i++) {
                    this.setBlock(chunkData, X, y - i, Z, this.getUnderMaterial());
                }
            }
        }
    }
    @Nullable
    @Override
    public BiomeProvider getDefaultBiomeProvider(@NotNull WorldInfo worldInfo) {
        return new ZeroBiome();
    }
    protected void setBlock(ChunkGenerator.ChunkData data, int X, int Y, int Z, Material material) {
        data.setBlock(X, Y, Z, material);
    }
    protected Material getRandomMaterial() {
        return Material.GRASS_BLOCK;
    }

    protected Material getUnderMaterial() {
        return Material.DIRT;
    }
    public static class ZeroBiome extends BiomeProvider{
        @NotNull
        @Override
        public Biome getBiome(@NotNull WorldInfo worldInfo, int x, int y, int z) {
            return Biome.END_BARRENS;
        }

        @NotNull
        @Override
        public List<Biome> getBiomes(@NotNull WorldInfo worldInfo) {
            return Lists.newArrayList(Biome.END_BARRENS);
        }
    }
}
