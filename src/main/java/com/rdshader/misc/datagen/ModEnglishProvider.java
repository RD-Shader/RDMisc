package com.rdshader.misc.datagen;

import com.rdshader.misc.RDSMisc;
import com.rdshader.misc.block.ModBlocks;
import com.rdshader.misc.gamerule.ModGameRules;
import com.rdshader.misc.item.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModEnglishProvider extends LanguageProvider {
    public ModEnglishProvider(PackOutput output) {
        super(output, RDSMisc.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add(ModGameRules.DROP_INVENTORY.get().getDescriptionId(), "Drop Inventory");
        add(ModGameRules.DROP_FREQUENCY.get().getDescriptionId(), "Drop Frequency");
        add(ModGameRules.RANDOM_EXPLODE.get().getDescriptionId(), "Bed Randomly Explodes");
        add(ModGameRules.FURNACE_EXPLODE.get().getDescriptionId(), "Furnace Automatically Explodes");

        add(ModBlocks.DOUBLE_BED.get(), "Bed*2");
        add(ModBlocks.QUADRUPLE_BED.get(), "Bed*4");
        add(ModBlocks.TRIPLE_DEPRESSED_BED.get(), "Triple Depressed Bed");
        add(ModBlocks.FOURFOLD_DEPRESSED_BED.get(), "Fourfold Depressed Bed");
        add(ModBlocks.FIVEFOLD_DEPRESSED_BED.get(), "Fivefold Depressed Bed");
        add(ModBlocks.SIX_FOLD_DEPRESSED_BED.get(), "Sixfold Depressed Bed");
        add(ModBlocks.SEVEN_FOLD_DEPRESSED_BED.get(), "Sevenfold Depressed Bed");
        add(ModBlocks.EIGHT_FOLD_DEPRESSED_BED.get(), "Eightfold Depressed Bed");

        add(ModBlocks.GRAVEL_BOULDER.get(), "Gravel Boulder");
        add(ModBlocks.BOULDER.get(), "Boulder");
        add(ModBlocks.DEEPSLATE_BOULDER.get(), "Deepslate Boulder");
        add(ModBlocks.SAND_BOULDER.get(), "Sand Boulder");

        add(ModItems.TASK_MANAGER.get(), "taskmgr.exe");

        add(RDSMisc.MODID + ".gui.TaskManagerScreen", "Task Manager");
        add(RDSMisc.MODID + ".gui.copy_position", "Copy Position (C)");
        add(RDSMisc.MODID + ".gui.copy_uuid", "Copy UUID (C)");
        add(RDSMisc.MODID + ".gui.teleport", "Teleport (T)");
        add(RDSMisc.MODID + ".gui.terminate", "Terminate (E)");
        add(RDSMisc.MODID + ".gui.terminate_all", "Terminate All Selected (E)");
        add(RDSMisc.MODID + ".gui.entity_count", "%d in total");

        add("itemGroup." + RDSMisc.MODID + ".rdmisc_tab", "RDMisc");
    }
}
