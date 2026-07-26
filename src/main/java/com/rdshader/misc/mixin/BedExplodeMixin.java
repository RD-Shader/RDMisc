package com.rdshader.misc.mixin;

import com.rdshader.misc.block.ModifiedBedBlock;
import com.rdshader.misc.gamerule.ModGameRules;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BedBlock.class)
public abstract class BedExplodeMixin extends HorizontalDirectionalBlock {
    public BedExplodeMixin(Properties p_54120_) {
        super(p_54120_);
    }

    @Inject(method = "useWithoutItem", at = @At("HEAD"), cancellable = true)
    public void useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult, CallbackInfoReturnable<InteractionResult> cir) {
        if (level instanceof ServerLevel serverLevel) {
            if (serverLevel.getGameRules().get(ModGameRules.RANDOM_EXPLODE.get())) {
                if (level.random.nextInt(5) == 0) {
                    ModifiedBedBlock.modifiedBedExplode(blockState, level, blockPos, 1);
                    cir.setReturnValue(InteractionResult.SUCCESS_SERVER);
                }
            }
        }
    }
}
