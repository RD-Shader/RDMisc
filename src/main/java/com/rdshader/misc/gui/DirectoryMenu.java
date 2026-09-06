package com.rdshader.misc.gui;

import com.rdshader.misc.item.ModItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import org.jspecify.annotations.NonNull;

public class DirectoryMenu extends ChestMenu {
    private final SimpleContainer container;
    private final ItemStack stack;

    public DirectoryMenu(int containerId, Inventory playerInventory, SimpleContainer container, ItemStack stack) {
        super(MenuType.GENERIC_9x6, containerId, playerInventory, container, 6);
        this.container = container;
        this.stack = stack;
    }

    @Override
    public void removed(@NonNull Player player) {
        super.removed(player);
        if (stack.is(ModItems.DIRECTORY.get())) {
            stack.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(container.getItems()));
        }
    }
}
