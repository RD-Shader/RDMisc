package com.rdshader.misc.datagen.worldgen;

import com.rdshader.misc.RDSMisc;
import com.rdshader.misc.mixin.accessor.OrePlacementsAccessor;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ModPlacedFeatures {
    public static ResourceKey<PlacedFeature> GRAVEL_BOULDER = createKey("gravel_boulder");
    public static ResourceKey<PlacedFeature> BOULDER = createKey("boulder");
    public static ResourceKey<PlacedFeature> DEEPSLATE_BOULDER = createKey("deepslate_boulder");
    public static ResourceKey<PlacedFeature> SAND_BOULDER = createKey("sand_boulder");

    public static void submitPlacedFeatures() {
        RDSMisc.BUILDER.add(Registries.PLACED_FEATURE, bootstrap -> {
            HolderGetter<ConfiguredFeature<?, ?>> otherRegistry = bootstrap.lookup(Registries.CONFIGURED_FEATURE);
            bootstrap.register(GRAVEL_BOULDER, new PlacedFeature(otherRegistry.getOrThrow(ModConfiguredFeatures.GRAVEL_BOULDER),
                    OrePlacementsAccessor.rdsmisc$commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(80)))));
            bootstrap.register(BOULDER, new PlacedFeature(otherRegistry.getOrThrow(ModConfiguredFeatures.BOULDER),
                    OrePlacementsAccessor.rdsmisc$commonOrePlacement(12, HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(80)))));
            bootstrap.register(DEEPSLATE_BOULDER, new PlacedFeature(otherRegistry.getOrThrow(ModConfiguredFeatures.DEEPSLATE_BOULDER),
                    OrePlacementsAccessor.rdsmisc$commonOrePlacement(12, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(2)))));
            bootstrap.register(SAND_BOULDER, new PlacedFeature(otherRegistry.getOrThrow(ModConfiguredFeatures.SAND_BOULDER),
                    OrePlacementsAccessor.rdsmisc$commonOrePlacement(9, HeightRangePlacement.uniform(VerticalAnchor.absolute(32), VerticalAnchor.absolute(80)))));
                });
    }
    private static ResourceKey<PlacedFeature> createKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, Identifier.fromNamespaceAndPath(RDSMisc.MODID, name));
    }
}
