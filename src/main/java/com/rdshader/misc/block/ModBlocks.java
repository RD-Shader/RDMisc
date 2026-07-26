package com.rdshader.misc.block;

import com.rdshader.misc.RDSMisc;
import com.rdshader.misc.block.renderer.ModifiedBedType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(RDSMisc.MODID);

    public static final DeferredBlock<ModifiedBedBlock> DOUBLE_BED = registerBed(ModifiedBedType.DOUBLE);
    public static final DeferredBlock<ModifiedBedBlock> QUADRUPLE_BED = registerBed(ModifiedBedType.QUADRUPLE);
    public static final DeferredBlock<ModifiedBedBlock> TRIPLE_DEPRESSED_BED = registerBed(ModifiedBedType.TRIPLE_DEPRESSED);
    public static final DeferredBlock<ModifiedBedBlock> FOURFOLD_DEPRESSED_BED = registerBed(ModifiedBedType.FOURFOLD_DEPRESSED);
    public static final DeferredBlock<ModifiedBedBlock> FIVEFOLD_DEPRESSED_BED = registerBed(ModifiedBedType.FIVEFOLD_DEPRESSED);
    public static final DeferredBlock<ModifiedBedBlock> SIX_FOLD_DEPRESSED_BED = registerBed(ModifiedBedType.SIXFOLD_DEPRESSED);
    public static final DeferredBlock<ModifiedBedBlock> SEVEN_FOLD_DEPRESSED_BED = registerBed(ModifiedBedType.SEVENFOLD_DEPRESSED);
    public static final DeferredBlock<ModifiedBedBlock> EIGHT_FOLD_DEPRESSED_BED = registerBed(ModifiedBedType.EIGHTFOLD_DEPRESSED);

    private static DeferredBlock<ModifiedBedBlock> registerBed(ModifiedBedType type) {
        return BLOCKS.register(type.getId() + "_bed", r -> new ModifiedBedBlock(getBedProperties(r), type));
    }

    private static BlockBehaviour.Properties getBedProperties(Identifier identifier) {
        return BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, identifier)).mapColor((blockState) -> blockState.getValue(BedBlock.PART) == BedPart.FOOT ? DyeColor.RED.getMapColor() : MapColor.WOOL).sound(SoundType.WOOD).strength(0.2F).noOcclusion().ignitedByLava().pushReaction(PushReaction.DESTROY);
    }
}
