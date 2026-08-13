package com.rdshader.misc.item;

import com.rdshader.misc.RDSMisc;
import com.rdshader.misc.block.ModBlocks;
import com.rdshader.misc.block.ModifiedBedBlock;
import com.rdshader.misc.block.renderer.ModifiedBedType;
import net.minecraft.world.item.BedItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(RDSMisc.MODID);

    public static final DeferredItem<BlockItem> DOUBLE_BED = registerBed(ModifiedBedType.DOUBLE, ModBlocks.DOUBLE_BED);
    public static final DeferredItem<BlockItem> QUADRUPLE_BED = registerBed(ModifiedBedType.QUADRUPLE, ModBlocks.QUADRUPLE_BED);
    public static final DeferredItem<BlockItem> TRIPLE_DEPRESSED_BED = registerBed(ModifiedBedType.TRIPLE_DEPRESSED, ModBlocks.TRIPLE_DEPRESSED_BED);
    public static final DeferredItem<BlockItem> FOURFOLD_DEPRESSED_BED = registerBed(ModifiedBedType.FOURFOLD_DEPRESSED, ModBlocks.FOURFOLD_DEPRESSED_BED);
    public static final DeferredItem<BlockItem> FIVEFOLD_DEPRESSED_BED = registerBed(ModifiedBedType.FIVEFOLD_DEPRESSED, ModBlocks.FIVEFOLD_DEPRESSED_BED);
    public static final DeferredItem<BlockItem> SIX_FOLD_DEPRESSED_BED = registerBed(ModifiedBedType.SIXFOLD_DEPRESSED, ModBlocks.SIX_FOLD_DEPRESSED_BED);
    public static final DeferredItem<BlockItem> SEVEN_FOLD_DEPRESSED_BED = registerBed(ModifiedBedType.SEVENFOLD_DEPRESSED, ModBlocks.SEVEN_FOLD_DEPRESSED_BED);
    public static final DeferredItem<BlockItem> EIGHT_FOLD_DEPRESSED_BED = registerBed(ModifiedBedType.EIGHTFOLD_DEPRESSED, ModBlocks.EIGHT_FOLD_DEPRESSED_BED);

    public static final DeferredItem<BlockItem> GRAVEL_BOULDER = ITEMS.registerSimpleBlockItem("gravel_boulder", ModBlocks.GRAVEL_BOULDER);
    public static final DeferredItem<BlockItem> BOULDER = ITEMS.registerSimpleBlockItem("boulder", ModBlocks.BOULDER);
    public static final DeferredItem<BlockItem> DEEPSLATE_BOULDER = ITEMS.registerSimpleBlockItem("deepslate_boulder", ModBlocks.DEEPSLATE_BOULDER);
    public static final DeferredItem<BlockItem> SAND_BOULDER = ITEMS.registerSimpleBlockItem("sand_boulder", ModBlocks.SAND_BOULDER);

    public static final DeferredItem<Item> TASK_MANAGER = ITEMS.registerItem("taskmgr", TaskManagerItem::new, p -> p.stacksTo(1));

    private static DeferredItem<BlockItem> registerBed(ModifiedBedType type, DeferredBlock<ModifiedBedBlock> block) {
        return ITEMS.registerItem(type.getId() + "_bed", props -> new BedItem(block.get(), props), (() -> (new Item.Properties().stacksTo(1)).useBlockDescriptionPrefix()));
    }
}