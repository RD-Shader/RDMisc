package com.rdshader.misc.gui;

import com.rdshader.misc.RDSMiscUtil;
import com.rdshader.misc.item.ModItems;
import com.rdshader.misc.network.RequestPlayersPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ItemDisplayWidget;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ExplorerScreen extends Screen {
    public List<ItemStack> displayedStacks;
    public ExplorerList LIST;
    public final PathEditBox PATH_EDIT_BOX = new PathEditBox(font, 400, 20, Component.translatable("fml.menu.mods.search"), this::pathResponder);

    public final HeaderAndFooterLayout fullLayout = new HeaderAndFooterLayout(this, height);

    public final List<ItemStack> ROOT_STACKS = new ArrayList<>();

    public ExplorerScreen(List<ItemStack> stacks) {
        super(Component.translatable("item.rdsmisc.explorer"));
        this.displayedStacks = stacks;
    }

    @Override
    protected void init() {
        initRoot();
        getUsersData();

        PATH_EDIT_BOX.setMaxLength(256);
        PATH_EDIT_BOX.setValue("/");
        LIST = new ExplorerList(minecraft);
        PATH_EDIT_BOX.refresh();
        refresh();

        addWidget(LIST);

        fullLayout.addToHeader(PATH_EDIT_BOX);
        fullLayout.visitWidgets(this::addRenderableWidget);
        fullLayout.arrangeElements();
    }

    public void refresh(List<ItemStack> stacks) {
        displayedStacks = stacks;
        refresh();
    }

    private void refresh() {
        LIST.clearEntries();
        for (ItemStack stack : displayedStacks) {
            LIST.addEntry(new ExplorerEntry(stack));
        }
    }

    private void pathResponder(String text) {
        text = text.replace('\\', '/');
        if (text.isEmpty() || text.equals("/") || text.equals("C:/")) {
            PATH_EDIT_BOX.setValue("/");
            refresh(ROOT_STACKS);
        }
        else {
            List<String> pathSlices = List.of(text.split("/"));

            if (pathSlices.getFirst().isEmpty()) {
                pathSlices = pathSlices.subList(1, pathSlices.size());
            }
            for (String pathName : pathSlices) {
                if (!pathName.isEmpty()) {
                    displayedStacks.stream()
                            .filter(stack -> pathName.equals(stack.getHoverName().getString()))
                            .findFirst().ifPresent(itemStack ->
                                    refresh(Objects.requireNonNull(itemStack.get(DataComponents.CONTAINER))
                                            .stream().filter(stack -> !stack.isEmpty()).toList()));

                }
            }
        }
    }

    private void getUsersData() {
        ClientPacketDistributor.sendToServer(RequestPlayersPacket.INSTANCE);
    }

    private boolean isValidPath(ItemStack stack) {
        return (stack.get(DataComponents.CONTAINER) != null) || isRoot(stack.getHoverName().getString());
    }

    private boolean isRoot(String name) {
        return name.equals("Users") || name.equals("Blocks");
    }

    private void initRoot() {
        ItemStack stack = ModItems.DIRECTORY.get().getDefaultInstance();
        ROOT_STACKS.add(RDSMiscUtil.namedStack(stack, "Users"));
        ROOT_STACKS.add(RDSMiscUtil.namedStack(stack, "Blocks"));
    }

    @Override
    public void render(@NonNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        LIST.render(guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    public class ExplorerList extends ObjectSelectionList<ExplorerEntry> {
        public ExplorerList(Minecraft mc) {
            super(mc, ExplorerScreen.this.width, ExplorerScreen.this.height - 60, 45, 20);
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

            stackWidget = new ItemDisplayWidget(minecraft, getContentX(), getContentY(), 8, 8,
                    Component.empty(), stack, true, true);
        }

        @Override
        public @NonNull Component getNarration() {
            return Component.literal("1919810");
        }

        @Override
        public void renderContent(@NonNull GuiGraphics guiGraphics, int i, int i1, boolean b, float v) {
            stackWidget.setX(getContentX());
            stackWidget.setY(getContentY() - 2);
            stackWidget.render(guiGraphics, i, i1, v);

            guiGraphics.drawString(ExplorerScreen.this.font, stack.getHoverName(),
                    getContentX() + 80, getContentYMiddle() - 3, -1);

        }

        @Override
        public boolean mouseClicked(@NonNull MouseButtonEvent event, boolean b) {
            if (LIST.getSelected() == this && event.button() == 0) {
                PATH_EDIT_BOX.appendPath(stack.getHoverName(), path -> isValidPath(stack));
            }
            return true;
        }
    }
}
