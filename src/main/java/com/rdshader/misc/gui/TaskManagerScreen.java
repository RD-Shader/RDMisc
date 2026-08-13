package com.rdshader.misc.gui;

import com.rdshader.misc.network.EntityData;
import com.rdshader.misc.network.RequestEntitiesPacket;
import com.rdshader.misc.network.RequestKillPacket;
import com.rdshader.misc.network.RequestTeleportPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.ItemDisplayWidget;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.DefaultTooltipPositioner;
import net.minecraft.client.input.InputWithModifiers;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SpawnEggItem;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import org.jspecify.annotations.NonNull;
import org.lwjgl.glfw.GLFW;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TaskManagerScreen extends Screen {
    private int ticks = 0;
    private boolean doRefresh = true;
    public List<EntityData> dataList;
    public List<EntityData> shownList;
    public TaskList list;
    public EditBox searchBox = new EditBox(font, 400, 20, Component.translatable("fml.menu.mods.search"));

    private final Button COPY = new CopyButton(0, 0, 100, 20, Component.translatable("rdsmisc.gui.copy_position"));

    private final Button TELEPORT = new Button.Builder(Component.translatable("rdsmisc.gui.teleport"), b -> {
        TaskEntry entry = list.getSelected();
        if (entry != null) {
            ClientPacketDistributor.sendToServer(new RequestTeleportPacket(entry.data));
            if (minecraft.player != null) {
                minecraft.player.playSound(SoundEvents.AMBIENT_CAVE.value(), 3.0F, 1.5F);
            }
            onClose();
        }
    }).width(100).build();

    private final Button TERMINATE = new TerminateButton(0, 0, 100, 20, Component.translatable("rdsmisc.gui.terminate"));

    public final HeaderAndFooterLayout fullLayout = new HeaderAndFooterLayout(this, 20);
    public final LinearLayout headerLayout = fullLayout.addToHeader(new LinearLayout(width, 20, LinearLayout.Orientation.HORIZONTAL));
    public final LinearLayout footerLayout = fullLayout.addToFooter(new LinearLayout(width, 20, LinearLayout.Orientation.HORIZONTAL));

    public TaskManagerScreen() {
        super(Component.translatable("rdsmisc.gui.TaskManagerScreen"));
    }

    @Override
    protected void init() {
        ClientPacketDistributor.sendToServer(RequestEntitiesPacket.INSTANCE);

        list = new TaskList(minecraft);
        addWidget(list);

        headerLayout.addChild(searchBox);
        addButtons();

        fullLayout.visitWidgets(this::addRenderableWidget);
        fullLayout.arrangeElements();

        headerLayout.visitWidgets(this::addRenderableWidget);
        headerLayout.arrangeElements();

        footerLayout.visitWidgets(this::addRenderableWidget);
        footerLayout.arrangeElements();

        searchBox.setResponder(this::inputResponder);

        searchBox.setMaxLength(100);
    }

    @Override
    public void tick() {
        ticks++;
        if (ticks == 40) {
            ticks = 0;
            if (list.getSelected() == null && doRefresh) {
                ClientPacketDistributor.sendToServer(RequestEntitiesPacket.INSTANCE);
            }
        }
    }

    private void inputResponder(String text) {
        list.setFocused(false);
        if (!text.isEmpty()) {
            List<EntityData> newList = List.of();
            if (text.contains("-")) {
                newList = searchBy(data -> data.uuid().equals(text));
                list.setScrollAmount(0);
            }
            else {
                Matcher matcher = Pattern.compile("\\bd([<>])(\\d+)(\\.\\d*)?\\b").matcher(text);
                if (matcher.find()) {
                    String comparator = matcher.group(1);
                    String after = matcher.group(3);
                    double squaredDistance = Math.pow(Double.parseDouble(matcher.group(2) + (after == null ? "" : after)), 2);
                    if (comparator.equals("<")) {
                        newList = searchAndSort(data -> getSquaredDistance(data) < squaredDistance, Comparator.comparing(this::getSquaredDistance));
                    }
                    else if (comparator.equals(">")) {
                        newList = searchAndSort(data -> getSquaredDistance(data) > squaredDistance, Comparator.comparing(this::getSquaredDistance));
                    }
                }
                else {
                    newList = searchBy(data -> text.contains(data.entityType().getDescription().getString()));
                }
            }

            if (!newList.isEmpty()) {
                shownList = newList;
                refresh();
                doRefresh = false;
            }
            if (text.contains("/rev")) {
                Collections.reverse(shownList);
                refresh();
                doRefresh = false;
            }
        }
        else {
            doRefresh = true;
            refresh();
        }
    }

    private List<EntityData> searchBy(Predicate<? super EntityData> predicate) {
        return dataList.stream().filter(predicate).collect(Collectors.toList());
    }

    private List<EntityData> searchAndSort(Predicate<? super EntityData> predicate, Comparator<? super EntityData> comparator) {
        return dataList.stream().filter(predicate).sorted(comparator).collect(Collectors.toList());
    }

    private double getSquaredDistance(EntityData data) {
        Player player = minecraft.player;
        if (player != null) {
            return Math.pow(player.getX() - data.x(), 2) + Math.pow(player.getY() - data.y(), 2) + Math.pow(player.getZ() - data.z(), 2);
        }
        return Double.MAX_VALUE;
    }

    private void addButtons() {
        footerLayout.addChild(COPY);
        footerLayout.addChild(TELEPORT);
        footerLayout.addChild(TERMINATE);
    }

    public void refresh() {
        list.clearEntries();
        for (EntityData entityData : shownList) {
            list.addEntry(new TaskEntry(entityData));
        }
    }

    @Override
    public void render(@NonNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.drawString(minecraft.font, Component.translatable("rdsmisc.gui.entity_count", list.size()), 0, 30, -1);
        list.render(guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean keyPressed(@NonNull KeyEvent event) {
        if (event.hasAltDown()) {
            if (event.key() == GLFW.GLFW_KEY_C) {
                COPY.onPress(event);
            }
            else if (event.key() == GLFW.GLFW_KEY_T) {
                TELEPORT.onPress(event);
            }
            else if (event.key() == GLFW.GLFW_KEY_E) {
                TERMINATE.onPress(event);
            }
        }
        return super.keyPressed(event);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    public class TaskList extends ObjectSelectionList<TaskEntry> {
        public TaskList(Minecraft mc) {
            super(mc, TaskManagerScreen.this.width, fullLayout.getContentHeight(), 45, 23);
            setY(29);
        }

        @Override
        public int getRowWidth() {
            return 350;
        }

        @Override
        protected int addEntry(TaskEntry entry) {
            return super.addEntry(entry);
        }

        protected int size() {
            return getItemCount();
        }
    }

    public class TaskEntry extends ObjectSelectionList.Entry<TaskEntry> {
        private final EntityType<?> type;
        private final EntityData data;
        private final String position;
        private boolean displayUUID = false;
        public final ItemDisplayWidget itemWidget;

        public TaskEntry(EntityData data) {
            this.data = data;
            position = String.format("(%.3f, %.3f, %.3f)", data.x(), data.y(), data.z());

            type = data.entityType();
            Item item = SpawnEggItem.byId(type);
            ItemStack stack = data.stack();

            if (item != null) {
                stack = item.getDefaultInstance();
            }
            if (stack.isEmpty()) {
                stack = Items.BARRIER.getDefaultInstance();
            }

            itemWidget = new ItemDisplayWidget(minecraft, getContentX(), getContentY(), 18, 18,
                    Component.empty(), stack, true, true);

            addWidget(itemWidget);
        }

        @Override
        public @NonNull Component getNarration() {
            return Component.literal("114514");
        }

        @Override
        public void renderContent(@NonNull GuiGraphics guiGraphics, int i, int i1, boolean b, float v) {
            itemWidget.setX(getContentX());
            itemWidget.setY(getContentY() + 1);
            itemWidget.render(guiGraphics, i, i1, v);

            guiGraphics.drawString(TaskManagerScreen.this.font, type.getDescription(),
                    getContentX() + 80, getContentYMiddle() - 2, -1);
            guiGraphics.drawString(TaskManagerScreen.this.font, position,
                    getContentX() + 160, getContentYMiddle() - 2, -1);

            if (!isFocused()) {
                displayUUID = false;
            }

            if (isFocused() && displayUUID) {
                guiGraphics.renderTooltip(TaskManagerScreen.this.font, List.of(ClientTooltipComponent.create(
                                FormattedCharSequence.forward(data.uuid(), Style.EMPTY))), getContentX(), getContentY(),
                        DefaultTooltipPositioner.INSTANCE, null);
            }
        }

        @Override
        public boolean mouseClicked(@NonNull MouseButtonEvent event, boolean b) {
            if (event.input() == 1) {
                displayUUID = !displayUUID;
                return false;
            }
            return true;
        }
    }


    public class TerminateButton extends Button.Plain {
        boolean terminateUnique = true;
        @SuppressWarnings("DataFlowIssue")
        protected TerminateButton(int x, int y, int width, int height, Component component) {
            super(x, y, width, height, component, null, Button.DEFAULT_NARRATION);
        }

        @Override
        public boolean mouseClicked(@NonNull MouseButtonEvent event, boolean p_434606_) {
            if (this.isActive()) {
                if (isMouseOver(event.x(), event.y())) {
                    playDownSound(Minecraft.getInstance().getSoundManager());

                    if (event.input() == 0) {
                        onPress(event);
                    }
                    else if (event.input() == 1) {
                        terminateUnique = !terminateUnique;
                    }

                    if (terminateUnique) {
                        message = Component.translatable("rdsmisc.gui.terminate");
                    }
                    else {
                        message = Component.translatable("rdsmisc.gui.terminate_all");
                    }
                }
            }
            return false;
        }

        @Override
        public void onPress(@NonNull InputWithModifiers i) {
            TaskEntry entry = list.getSelected();
            if (entry != null) {
                if (terminateUnique) {
                    ClientPacketDistributor.sendToServer(new RequestKillPacket(entry.data.uuid()));
                    ClientPacketDistributor.sendToServer(RequestEntitiesPacket.INSTANCE);
                    if (minecraft.player != null) {
                        minecraft.player.playSound(SoundEvents.AMBIENT_CAVE.value(), 3.0F, 1.5F);
                    }
                }
            }
            if (!terminateUnique) {
                for (EntityData data: shownList) {
                    ClientPacketDistributor.sendToServer(new RequestKillPacket(data.uuid()));
                }
                ClientPacketDistributor.sendToServer(RequestEntitiesPacket.INSTANCE);
                if (minecraft.player != null) {
                    minecraft.player.playSound(SoundEvents.AMBIENT_CAVE.value(), 3.0F, 1.5F);
                }
            }
        }

        @Override
        public int getFGColor() {
            return terminateUnique ? -1 : 0xFFFF0000;
        }
    }


    public class CopyButton extends Button.Plain {
        boolean copyPosition = true;
        @SuppressWarnings("DataFlowIssue")
        protected CopyButton(int x, int y, int width, int height, Component component) {
            super(x, y, width, height, component, null, Button.DEFAULT_NARRATION);
        }

        @Override
        public boolean mouseClicked(@NonNull MouseButtonEvent event, boolean p_434606_) {
            if (this.isActive()) {
                if (isMouseOver(event.x(), event.y())) {
                    playDownSound(Minecraft.getInstance().getSoundManager());

                    if (event.input() == 0) {
                        onPress(event);
                    }
                    else if (event.input() == 1) {
                        copyPosition = !copyPosition;
                    }

                    if (copyPosition) {
                        message = Component.translatable("rdsmisc.gui.copy_position");
                    }
                    else {
                        message = Component.translatable("rdsmisc.gui.copy_uuid");
                    }
                }
            }
            return false;
        }

        @Override
        public void onPress(@NonNull InputWithModifiers i) {
            TaskEntry entry = list.getSelected();
            if (entry != null) {
                if (copyPosition) {
                    minecraft.keyboardHandler.setClipboard(String.format("%f %f %f", entry.data.x(), entry.data.y(), entry.data.z()));
                }
                else {
                    minecraft.keyboardHandler.setClipboard(entry.data.uuid());
                }
            }
        }
    }
}
