package com.rdshader.misc.item;

import com.rdshader.misc.gui.TaskManagerScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;

public class TaskManagerItem extends Item {
    public TaskManagerItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NonNull InteractionResult use(@NonNull Level level, @NonNull Player player0, @NonNull InteractionHand hand) {
        if (level.isClientSide()) {
            Minecraft.getInstance().setScreen(new TaskManagerScreen());
        }

        return InteractionResult.SUCCESS;
    }
}
