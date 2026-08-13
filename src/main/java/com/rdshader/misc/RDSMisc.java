package com.rdshader.misc;

import com.mojang.logging.LogUtils;
import com.rdshader.misc.block.ModBlocks;
import com.rdshader.misc.block.entity.ModBlockEntityTypes;
import com.rdshader.misc.datagen.*;
import com.rdshader.misc.datagen.worldgen.ModConfiguredFeatures;
import com.rdshader.misc.datagen.worldgen.ModPlacedFeatures;
import com.rdshader.misc.gamerule.ModGameRules;
import com.rdshader.misc.item.ModItems;
import com.rdshader.misc.item.ModTabs;
import com.rdshader.misc.network.PayloadRegistry;
import net.minecraft.core.RegistrySetBuilder;
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
    public static RegistrySetBuilder BUILDER = new RegistrySetBuilder();

    public RDSMisc(IEventBus modEventBus, ModContainer modContainer) {
        generalRegister(modEventBus);
        modEventBus.register(this);
        addEvents(modEventBus);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void generalRegister(IEventBus modEventBus) {
        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModBlockEntityTypes.ENTITY_TYPES.register(modEventBus);
        ModTabs.TABS.register(modEventBus);
        ModGameRules.GAME_RULES.register(modEventBus);
    }

    private void gatherData(GatherDataEvent.Client event) {
        event.createProvider(ModModelProvider::new);
        event.createProvider(ModRecipeProvider.Runner::new);
        event.createProvider(ModBlockTags::new);
        event.createProvider(ModEnglishProvider::new);
        event.createProvider(ModChineseProvider::new);

        ModConfiguredFeatures.submitConfiguredFeatures();
        ModPlacedFeatures.submitPlacedFeatures();

        event.createDatapackRegistryObjects(BUILDER);
    }

    private void addEvents(IEventBus modEventBus) {
        modEventBus.addListener(this::gatherData);
        modEventBus.addListener(PayloadRegistry::registerPayloads);
    }

    @SubscribeEvent
    private void register(RegisterEvent event) {
        LOGGER.info("滚木");
    }
}
