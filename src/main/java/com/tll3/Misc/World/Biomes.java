package com.tll3.Misc.World;

import me.outspending.biomesapi.BiomeSettings;
import me.outspending.biomesapi.BiomeTempModifier;
import me.outspending.biomesapi.biome.CustomBiome;
import me.outspending.biomesapi.registry.BiomeRegistry;
import me.outspending.biomesapi.registry.BiomeResourceKey;
import me.outspending.biomesapi.renderer.AmbientParticle;
import me.outspending.biomesapi.renderer.ParticleRenderer;
import net.minecraft.world.level.biome.BiomeSettingsMobs;
import org.bukkit.block.Biome;

public class Biomes {
    public static void registerAllBiomes() {
        BiomeRegistry.newRegistry().register(primeval_woods);
        BiomeRegistry.newRegistry().register(savage_dunes);
        BiomeRegistry.newRegistry().register(scorched_plateau);
    }

    static CustomBiome primeval_woods = CustomBiome.builder() // Creates a new biome builder
            .resourceKey(BiomeResourceKey.of("afterlife", "primeval_woods")) // Resource key for the biome aka "test:custombiome" (These cannot be registered twice, the resource keys MUST be different than other biomes.)
            .settings(BiomeSettings.builder() // Biome settings (Required, default settings are used if not provided)
                    .depth(1F) // Depth of the biome (Required, default is 0.1F)
                    .scale(0.2F) // Scale of the biome (Required, default is 0.2F)
                    .temperature(0.3F) // Temperature of the biome (Required, default of 0.3F)
                    .downfall(0.4F) // Downfall of the biome (Required, default is 0.4F)
                    .modifier(BiomeTempModifier.NONE) // Temperature modifier of the biome (Required, default is NONE)
                    .build())
            .fogColor("#1f5378") // Fog color of the biome (Required)
            .foliageColor("#0f402a") // Foliage color of the biome (Not Required)
            .skyColor("#81b8cc") // Sky color of the biome (Required)
            .waterColor("#1b5f94") // Water color of the biome (Required)
            .waterFogColor("#1b7c94") // Water fog color of the biome (Required)
            .grassColor("#045929") // Grass color of the biome (Not Required)
            .build();

    static CustomBiome savage_dunes = CustomBiome.builder() // Creates a new biome builder
            .resourceKey(BiomeResourceKey.of("afterlife", "savage_dunes")) // Resource key for the biome aka "test:custombiome" (These cannot be registered twice, the resource keys MUST be different than other biomes.)
            .settings(BiomeSettings.builder() // Biome settings (Required, default settings are used if not provided)
                    .depth(0.1F) // Depth of the biome (Required, default is 0.1F)
                    .scale(0.2F) // Scale of the biome (Required, default is 0.2F)
                    .temperature(1F) // Temperature of the biome (Required, default of 0.3F)
                    .downfall(0.4F) // Downfall of the biome (Required, default is 0.4F)
                    .modifier(BiomeTempModifier.NONE) // Temperature modifier of the biome (Required, default is NONE)
                    .build())
            .fogColor("#fcc96f") // Fog color of the biome (Required)
            .foliageColor("#0f402a") // Foliage color of the biome (Not Required)
            .skyColor("#f7e2bc") // Sky color of the biome (Required)
            .waterColor("#7384c9") // Water color of the biome (Required)
            .waterFogColor("#aab8f2") // Water fog color of the biome (Required)
            .grassColor("#045929") // Grass color of the biome (Not Required)
            .build();
    static CustomBiome scorched_plateau = CustomBiome.builder() // Creates a new biome builder
            .resourceKey(BiomeResourceKey.of("afterlife", "scorched_plateau")) // Resource key for the biome aka "test:custombiome" (These cannot be registered twice, the resource keys MUST be different than other biomes.)
            .settings(BiomeSettings.builder() // Biome settings (Required, default settings are used if not provided)
                    .depth(0.5F) // Depth of the biome (Required, default is 0.1F)
                    .scale(0.6F) // Scale of the biome (Required, default is 0.2F)
                    .temperature(1F) // Temperature of the biome (Required, default of 0.3F)
                    .downfall(0.4F) // Downfall of the biome (Required, default is 0.4F)
                    .modifier(BiomeTempModifier.NONE) // Temperature modifier of the biome (Required, default is NONE)
                    .build())
            .fogColor("#c24d1f") // Fog color of the biome (Required)
            .foliageColor("#0f402a") // Foliage color of the biome (Not Required)
            .skyColor("#db8e70") // Sky color of the biome (Required)
            .waterColor("#7384c9") // Water color of the biome (Required)
            .waterFogColor("#aab8f2") // Water fog color of the biome (Required)
            .grassColor("#045929") // Grass color of the biome (Not Required)
            .build();
}

