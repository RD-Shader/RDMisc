package com.rdshader.misc.datagen;

import com.rdshader.misc.RDSMisc;
import com.rdshader.misc.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class ModBlockTags extends BlockTagsProvider {
    public static TagKey<Block> BOULDERS = createKey("boulders");
    public static TagKey<Block> SAND_BOULDER_REPLACEABLE = createKey("sand_boulder_replaceable");

    public ModBlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, RDSMisc.MODID);
    }

    @Override
    protected void addTags(HolderLookup.@NonNull Provider provider) {
        tag(BOULDERS).add(ModBlocks.GRAVEL_BOULDER.get(), ModBlocks.BOULDER.get(), ModBlocks.DEEPSLATE_BOULDER.get(), ModBlocks.SAND_BOULDER.get());
        tag(SAND_BOULDER_REPLACEABLE).add(Blocks.SAND, Blocks.SANDSTONE).addTag(BlockTags.BASE_STONE_OVERWORLD);
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.BOULDER.get(), ModBlocks.DEEPSLATE_BOULDER.get());
        tag(BlockTags.MINEABLE_WITH_SHOVEL).add(ModBlocks.GRAVEL_BOULDER.get(), ModBlocks.SAND_BOULDER.get());
    }

    private static TagKey<Block> createKey(String name) {
        return TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(RDSMisc.MODID, name));
    }
}
