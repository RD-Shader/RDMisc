package com.rdshader.misc.datagen.worldgen;

import com.rdshader.misc.RDSMisc;
import com.rdshader.misc.block.ModBlocks;
import com.rdshader.misc.datagen.ModBlockTags;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

public class ModConfiguredFeatures {
    public static ResourceKey<ConfiguredFeature<?, ?>> GRAVEL_BOULDER = createKey("gravel_boulder");
    public static ResourceKey<ConfiguredFeature<?, ?>> BOULDER = createKey("boulder");
    public static ResourceKey<ConfiguredFeature<?, ?>> DEEPSLATE_BOULDER = createKey("deepslate_boulder");
    public static ResourceKey<ConfiguredFeature<?, ?>> SAND_BOULDER = createKey("sand_boulder");

    public static void submitConfiguredFeatures() {
        RDSMisc.BUILDER.add(Registries.CONFIGURED_FEATURE, bootstrap -> {
            HolderGetter<Block> blocks = bootstrap.lookup(Registries.BLOCK);
            bootstrap.register(GRAVEL_BOULDER, new ConfiguredFeature<>(Feature.ORE,
                            new OreConfiguration(new TagMatchTest(BlockTags.BASE_STONE_OVERWORLD),
                                    blocks.getOrThrow(ModBlocks.GRAVEL_BOULDER.getKey()).value().defaultBlockState(), 28)));
            bootstrap.register(BOULDER, new ConfiguredFeature<>(Feature.ORE,
                    new OreConfiguration(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES),
                            blocks.getOrThrow(ModBlocks.BOULDER.getKey()).value().defaultBlockState(), 22)));
            bootstrap.register(DEEPSLATE_BOULDER, new ConfiguredFeature<>(Feature.ORE,
                    new OreConfiguration(new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES),
                            blocks.getOrThrow(ModBlocks.DEEPSLATE_BOULDER.getKey()).value().defaultBlockState(), 33)));
            bootstrap.register(SAND_BOULDER, new ConfiguredFeature<>(Feature.ORE,
                    new OreConfiguration(new TagMatchTest(ModBlockTags.SAND_BOULDER_REPLACEABLE),
                            blocks.getOrThrow(ModBlocks.SAND_BOULDER.getKey()).value().defaultBlockState(), 25)));
        });
    }

    private static ResourceKey<ConfiguredFeature<?, ?>> createKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, Identifier.fromNamespaceAndPath(RDSMisc.MODID, name));
    }
}
