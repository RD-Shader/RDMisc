package com.rdshader.misc.datagen;

import com.rdshader.misc.ModGameRules;
import com.rdshader.misc.RDSMisc;
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
    }
}
