package com.rdshader.misc.block;

import com.rdshader.misc.RDSMisc;
import com.rdshader.misc.block.renderer.ModifiedBedType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.ColorRGBA;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
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

    public static final DeferredBlock<BoulderBlock> GRAVEL_BOULDER = BLOCKS.register("gravel_boulder",
            r -> new BoulderBlock(new ColorRGBA(-8356741), 40.0F, BlockBehaviour.Properties.of().setId(id(r)).mapColor(MapColor.DIRT).strength(1.25F).sound(SoundType.GRAVEL)));
    public static final DeferredBlock<BoulderBlock> BOULDER = BLOCKS.register("boulder",
            r -> new BoulderBlock(new ColorRGBA(-8356741), 60.0F, BlockBehaviour.Properties.of().setId(id(r)).mapColor(MapColor.STONE).strength(3.75F, 6.0F)));
    public static final DeferredBlock<BoulderBlock> DEEPSLATE_BOULDER = BLOCKS.register("deepslate_boulder",
            r -> new BoulderBlock(new ColorRGBA(-8356741), 80.0F, BlockBehaviour.Properties.of().setId(id(r)).mapColor(MapColor.DEEPSLATE).strength(7.5F, 6.0F).sound(SoundType.DEEPSLATE)));
    public static final DeferredBlock<BoulderBlock> SAND_BOULDER = BLOCKS.register("sand_boulder",
            r -> new BoulderBlock(new ColorRGBA(14406560), 40.0F, BlockBehaviour.Properties.of().setId(id(r)).mapColor(MapColor.SAND).strength(1.25F).sound(SoundType.SAND)));

    private static ResourceKey<Block> id(Identifier id) {
        return ResourceKey.create(Registries.BLOCK, id);
    }

    private static DeferredBlock<ModifiedBedBlock> registerBed(ModifiedBedType type) {
        return BLOCKS.register(type.getId() + "_bed", r -> new ModifiedBedBlock(getBedProperties(r), type));
    }

    private static BlockBehaviour.Properties getBedProperties(Identifier identifier) {
        return BlockBehaviour.Properties.of().setId(id(identifier)).mapColor((blockState) -> blockState.getValue(BedBlock.PART) == BedPart.FOOT ? DyeColor.RED.getMapColor() : MapColor.WOOL).sound(SoundType.WOOD).strength(0.2F).noOcclusion().ignitedByLava().pushReaction(PushReaction.DESTROY);
    }
}
