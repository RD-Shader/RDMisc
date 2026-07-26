package com.rdshader.misc.item;

import com.rdshader.misc.RDSMisc;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public class ModTabs {
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB, RDSMisc.MODID);

    public static final Supplier<CreativeModeTab> RDMISC_TAB = TABS.register("rdmisc_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + RDSMisc.MODID + ".rdmisc_tab"))
            .icon(() -> new ItemStack(Items.NAME_TAG))
            .displayItems((params, output) -> {
                output.accept(ModItems.DOUBLE_BED.get());
                output.accept(ModItems.QUADRUPLE_BED.get());
                output.accept(ModItems.TRIPLE_DEPRESSED_BED.get());
                output.accept(ModItems.FOURFOLD_DEPRESSED_BED.get());
                output.accept(ModItems.FIVEFOLD_DEPRESSED_BED.get());
                output.accept(ModItems.SIX_FOLD_DEPRESSED_BED.get());
                output.accept(ModItems.SEVEN_FOLD_DEPRESSED_BED.get());
                output.accept(ModItems.EIGHT_FOLD_DEPRESSED_BED.get());
            })
            .build()
    );
}
