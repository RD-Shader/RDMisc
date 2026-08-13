package com.rdshader.misc;

import com.rdshader.misc.block.entity.ModBlockEntityTypes;
import com.rdshader.misc.block.renderer.ModifiedBedRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.client.network.event.RegisterClientPayloadHandlersEvent;

@Mod(value = RDSMisc.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = RDSMisc.MODID, value = Dist.CLIENT)
public class RDSMiscClient {
    public RDSMiscClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    private static void onClientSetup(FMLClientSetupEvent event) {
        RDSMisc.LOGGER.info("RDShader Mod Started");
    }

    @SubscribeEvent
    public static void onRegisterClientPackets(RegisterClientPayloadHandlersEvent event) {
    }

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntityTypes.MODIFIED_BED.get(), ModifiedBedRenderer::new);
    }
}
