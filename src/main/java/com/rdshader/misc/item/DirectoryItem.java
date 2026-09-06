package com.rdshader.misc.item;

import com.rdshader.misc.gui.DirectoryMenu;
import com.rdshader.misc.inventory.DirectoryContainer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;

public class DirectoryItem extends Item {
    private static final Component CONTAINER_TITLE = Component.translatable("item.rdsmisc.directory");

    public DirectoryItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NonNull InteractionResult use(@NonNull Level level, @NonNull Player player, @NonNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        Component name = getName();
        SimpleContainer container = new DirectoryContainer();
        ItemContainerContents contents = stack.get(DataComponents.CONTAINER);

        if (contents != null) {
            for (ItemStack itemStack: contents.nonEmptyItems()) {
                container.addItem(itemStack);
            }
        }

        player.openMenu(new SimpleMenuProvider((id, inventory, player1) -> new DirectoryMenu(id, inventory, container, stack)
                , name == CommonComponents.EMPTY ? CONTAINER_TITLE : name));

        return InteractionResult.SUCCESS;
    }
}
