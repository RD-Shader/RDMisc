package com.rdshader.misc.datagen;

import com.rdshader.misc.block.ModBlocks;
import com.rdshader.misc.gamerule.ModGameRules;
import com.rdshader.misc.RDSMisc;
import com.rdshader.misc.item.ModItems;
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
        add(ModGameRules.FURNACE_EXPLODE.get().getDescriptionId(), "熔炉自爆");

        add(ModBlocks.DOUBLE_BED.get(), "！？床床？！");
        add(ModBlocks.QUADRUPLE_BED.get(), "！？床床床床？！");
        add(ModBlocks.TRIPLE_DEPRESSED_BED.get(), "！？床缩压重三重压缩床？！");
        add(ModBlocks.FOURFOLD_DEPRESSED_BED.get(), "！？床缩压重四重压缩床？！");
        add(ModBlocks.FIVEFOLD_DEPRESSED_BED.get(), "！？床缩压重五重压缩床？！");
        add(ModBlocks.SIX_FOLD_DEPRESSED_BED.get(), "！？床缩压重六重压缩床？！");
        add(ModBlocks.SEVEN_FOLD_DEPRESSED_BED.get(), "！？床缩压重七重压缩床？！");
        add(ModBlocks.EIGHT_FOLD_DEPRESSED_BED.get(), "！？床缩压重八重压缩床？！");

        add(ModBlocks.GRAVEL_BOULDER.get(), "沙砾巨石");
        add(ModBlocks.BOULDER.get(), "正版巨石");
        add(ModBlocks.DEEPSLATE_BOULDER.get(), "深板岩巨石");
        add(ModBlocks.SAND_BOULDER.get(), "沙子巨石");

        add(ModItems.TASK_MANAGER.get(), "任务管理器");

        add(RDSMisc.MODID + ".gui.TaskManagerScreen", "任务管理器");
        add(RDSMisc.MODID + ".gui.copy_position", "复制坐标 (C)");
        add(RDSMisc.MODID + ".gui.copy_uuid", "复制UUID (C)");
        add(RDSMisc.MODID + ".gui.teleport", "传送 (T)");
        add(RDSMisc.MODID + ".gui.terminate", "结束任务 (E)");
        add(RDSMisc.MODID + ".gui.terminate_all", "结束所有任务 (E)");
        add(RDSMisc.MODID + ".gui.entity_count", "共有 %d 项");

        add("itemGroup." + RDSMisc.MODID + ".rdmisc_tab", "<RDMisc>");
    }
}
