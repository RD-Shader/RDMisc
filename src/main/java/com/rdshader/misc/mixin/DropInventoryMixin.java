package com.rdshader.misc.mixin;

import com.mojang.authlib.GameProfile;
import com.rdshader.misc.gamerule.ModGameRules;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.CommonHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public abstract class DropInventoryMixin extends Player {
    public DropInventoryMixin(Level level, GameProfile gameProfile) {
        super(level, gameProfile);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    public void tick(CallbackInfo ci) {
        if (level() instanceof ServerLevel serverLevel) {
            if (serverLevel.getGameRules().get(ModGameRules.DROP_INVENTORY.get())) {
                Inventory inventory = getInventory();
                int frequency = serverLevel.getGameRules().get(ModGameRules.DROP_FREQUENCY.get());

                if (inventory.isEmpty() && onGround()) {
                    if (random.nextInt(50000 / frequency + 1) == 0) {
                        addDeltaMovement(new Vec3(0, 4, 0));
                        hurtMarked = true;
                    }
                }
                else {
                    ItemStack target = ItemStack.EMPTY;
                    if (random.nextInt(5000 / frequency + 1) == 0) {
                        target = inventory.getSelectedItem();
                    }
                    else if (random.nextInt(300 / frequency + 1) == 0) {
                        target = inventory.getItem(random.nextInt(inventory.getMaxStackSize()));
                    }

                    if (!target.isEmpty()) {
                        inventory.removeItem(target);
                        CommonHooks.onPlayerTossEvent(this, target, false, true);
                    }
                }
            }
        }
    }
}
