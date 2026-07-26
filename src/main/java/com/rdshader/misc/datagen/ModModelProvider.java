package com.rdshader.misc.datagen;

import com.rdshader.misc.RDSMisc;
import com.rdshader.misc.block.ModBlocks;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import org.jspecify.annotations.NonNull;

public class ModModelProvider extends ModelProvider {
    public ModModelProvider(PackOutput output) {
        super(output, RDSMisc.MODID);
    }

    private void registerBlockModels(BlockModelGenerators blockModels) {
        createModifiedBed(blockModels, ModBlocks.DOUBLE_BED.get());
        createModifiedBed(blockModels, ModBlocks.QUADRUPLE_BED.get());
        createModifiedBed(blockModels, ModBlocks.TRIPLE_DEPRESSED_BED.get());
        createModifiedBed(blockModels, ModBlocks.FOURFOLD_DEPRESSED_BED.get());
        createModifiedBed(blockModels, ModBlocks.FIVEFOLD_DEPRESSED_BED.get());
        createModifiedBed(blockModels, ModBlocks.SIX_FOLD_DEPRESSED_BED.get());
        createModifiedBed(blockModels, ModBlocks.SEVEN_FOLD_DEPRESSED_BED.get());
        createModifiedBed(blockModels, ModBlocks.EIGHT_FOLD_DEPRESSED_BED.get());
    }

    private void registerItemModels(ItemModelGenerators ignoredItemModels) {
    }

    @SuppressWarnings("deprecation")
    private void createModifiedBed(BlockModelGenerators blockModels, Block block) {
        MultiVariant multivariant = BlockModelGenerators.plainVariant(ModelLocationUtils.decorateBlockModelLocation("bed"));
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, multivariant));
        blockModels.registerSimpleFlatItemModel(block.asItem());
    }

    @Override
    protected void registerModels(@NonNull BlockModelGenerators blockModels, @NonNull ItemModelGenerators itemModels) {
        registerBlockModels(blockModels);
        registerItemModels(itemModels);
    }
}
