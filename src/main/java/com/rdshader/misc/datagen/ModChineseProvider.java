package com.rdshader.misc.datagen;

import com.rdshader.misc.ModGameRules;
import com.rdshader.misc.RDSMisc;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModChineseProvider extends LanguageProvider {
    public ModChineseProvider(PackOutput output) {
        super(output, RDSMisc.MODID, "zh_cn");
    }

    @Override
    protected void addTranslations() {
        add(ModGameRules.DROP_INVENTORY.get().getDescriptionId(), "不死亡掉落");
        add(ModGameRules.DROP_FREQUENCY.get().getDescriptionId(), "掉落频率");
    }
}
