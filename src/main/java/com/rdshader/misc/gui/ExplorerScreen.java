package com.rdshader.misc.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ItemDisplayWidget;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.NonNull;

import java.util.List;

public class ExplorerScreen extends Screen {
    public final List<ItemStack> stacks;
    public ExplorerList LIST;

    public final HeaderAndFooterLayout fullLayout = new HeaderAndFooterLayout(this, 20);

    public ExplorerScreen(List<ItemStack> stacks) {
        super(Component.translatable("item.rdsmisc.explorer"));
        this.stacks = stacks;
    }

    @Override
    protected void init() {
        LIST = new ExplorerList(minecraft);
        for (ItemStack stack : stacks) {
            LIST.addEntry(new ExplorerEntry(stack));
        }

        addWidget(LIST);

        fullLayout.visitWidgets(this::addRenderableWidget);
        fullLayout.arrangeElements();
    }

    @Override
    public void render(@NonNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        LIST.render(guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    public class ExplorerList extends ObjectSelectionList<ExplorerEntry> {
        public ExplorerList(Minecraft mc) {
            super(mc, ExplorerScreen.this.width, ExplorerScreen.this.height, 45, 20);
        }

        @Override
        public int getRowWidth() {
            return 350;
        }

        @Override
        protected int addEntry(ExplorerEntry entry) {
            return super.addEntry(entry);
        }
    }

    public class ExplorerEntry extends ObjectSelectionList.Entry<ExplorerEntry> {
        private final ItemStack stack;
        public final ItemDisplayWidget stackWidget;

        public ExplorerEntry(ItemStack stack) {
            this.stack = stack;

            stackWidget = new ItemDisplayWidget(minecraft, getContentX(), getContentY(), 9, 9,
                    Component.empty(), stack, true, true);
        }

        @Override
        public @NonNull Component getNarration() {
            return Component.literal("1919810");
        }

        @Override
        public void renderContent(@NonNull GuiGraphics guiGraphics, int i, int i1, boolean b, float v) {
            stackWidget.setX(getContentX());
            stackWidget.setY(getContentY() + 1);
            stackWidget.render(guiGraphics, i, i1, v);

            guiGraphics.drawString(ExplorerScreen.this.font, stack.getDisplayName(),
                    getContentX() + 80, getContentYMiddle() - 2, -1);

        }
    }
}
