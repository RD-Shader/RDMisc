package com.rdshader.misc;

import com.mojang.logging.LogUtils;
import com.rdshader.misc.datagen.ModChineseProvider;
import com.rdshader.misc.datagen.ModEnglishProvider;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import org.slf4j.Logger;

@Mod(RDSMisc.MODID)
public class RDSMisc {
    public static final String MODID = "rdsmisc";
    public static final Logger LOGGER = LogUtils.getLogger();

    public RDSMisc(IEventBus modEventBus, ModContainer modContainer) {
        ModGameRules.GAME_RULES.register(modEventBus);
        modEventBus.register(this);
        modEventBus.addListener(this::gatherData);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void gatherData(GatherDataEvent.Client event) {
        event.createProvider(ModEnglishProvider::new);
        event.createProvider(ModChineseProvider::new);
    }

    @SubscribeEvent
    private void register(RegisterEvent event) {
        LOGGER.info("滚木");
    }
}
