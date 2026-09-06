package com.rdshader.misc.item;

import com.rdshader.misc.gui.ExplorerScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

public class ExplorerItem extends Item {
    public ExplorerItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NonNull InteractionResult use(@NonNull Level level, @NonNull Player player, @NonNull InteractionHand hand) {
        if (level.isClientSide()) {
            Inventory inventory = player.getInventory();
            List<ItemStack> stacks = new ArrayList<>();

            for (ItemStack stack : inventory) {
                if (!stack.isEmpty()) {
                    stacks.add(stack);
                }
            }

            Minecraft.getInstance().setScreen(new ExplorerScreen(stacks));
        }
        return InteractionResult.SUCCESS;
    }
}
