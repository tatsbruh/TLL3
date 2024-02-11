package com.tll3.Misc.World;

import me.outspending.biomesapi.BiomeSettings;
import me.outspending.biomesapi.BiomeTempModifier;
import me.outspending.biomesapi.biome.CustomBiome;
import me.outspending.biomesapi.registry.BiomeRegistry;
import me.outspending.biomesapi.registry.BiomeResourceKey;
import me.outspending.biomesapi.renderer.AmbientParticle;
import me.outspending.biomesapi.renderer.ParticleRenderer;

public class Biomes {
    public static void registerAllBiomes(){
        BiomeRegistry.newRegistry().register(primeval_woods);
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

}
