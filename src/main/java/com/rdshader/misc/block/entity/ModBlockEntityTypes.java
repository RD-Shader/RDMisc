package com.rdshader.misc.block.entity;

import com.rdshader.misc.RDSMisc;
import com.rdshader.misc.block.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntityTypes {
    public static final DeferredRegister<BlockEntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, RDSMisc.MODID);

    public static final Supplier<BlockEntityType<ModifiedBedBlockEntity>> MODIFIED_BED =
            ENTITY_TYPES.register("modified_bed", registryName ->
                    new BlockEntityType<>(ModifiedBedBlockEntity::new,
                            ModBlocks.DOUBLE_BED.get(), ModBlocks.QUADRUPLE_BED.get(), ModBlocks.TRIPLE_DEPRESSED_BED.get(), ModBlocks.FOURFOLD_DEPRESSED_BED.get(), ModBlocks.FIVEFOLD_DEPRESSED_BED.get(), ModBlocks.SIX_FOLD_DEPRESSED_BED.get(), ModBlocks.SEVEN_FOLD_DEPRESSED_BED.get(), ModBlocks.EIGHT_FOLD_DEPRESSED_BED.get()));
}
