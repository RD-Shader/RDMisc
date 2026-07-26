package com.rdshader.misc.datagen;

import com.rdshader.misc.block.ModBlocks;
import com.rdshader.misc.gamerule.ModGameRules;
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
        add(ModGameRules.RANDOM_EXPLODE.get().getDescriptionId(), "主世界也要床床爆");

        add(ModBlocks.DOUBLE_BED.get(), "！？床床？！");
        add(ModBlocks.QUADRUPLE_BED.get(), "！？床床床床？！");
        add(ModBlocks.TRIPLE_DEPRESSED_BED.get(), "！？床缩压重三重压缩床？！");
        add(ModBlocks.FOURFOLD_DEPRESSED_BED.get(), "！？床缩压重四重压缩床？！");
        add(ModBlocks.FIVEFOLD_DEPRESSED_BED.get(), "！？床缩压重五重压缩床？！");
        add(ModBlocks.SIX_FOLD_DEPRESSED_BED.get(), "！？床缩压重六重压缩床？！");
        add(ModBlocks.SEVEN_FOLD_DEPRESSED_BED.get(), "！？床缩压重七重压缩床？！");
        add(ModBlocks.EIGHT_FOLD_DEPRESSED_BED.get(), "！？床缩压重八重压缩床？！");

        add("itemGroup." + RDSMisc.MODID + ".rdmisc_tab", "<RDMisc>");
    }
}
