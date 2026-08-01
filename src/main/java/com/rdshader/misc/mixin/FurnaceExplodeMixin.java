package com.rdshader.misc.mixin;

import com.rdshader.misc.RDSMiscUtil;
import com.rdshader.misc.gamerule.ModGameRules;
import com.rdshader.misc.mixin.accessor.FurnaceEntityAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class FurnaceExplodeMixin extends BaseContainerBlockEntity {

    protected FurnaceExplodeMixin(BlockEntityType<?> p_155076_, BlockPos p_155077_, BlockState p_155078_) {
        super(p_155076_, p_155077_, p_155078_);
    }

    @Inject(method = "serverTick", at = @At("RETURN"), cancellable = true)
    private static void serverTick(ServerLevel level, BlockPos pos, BlockState state, AbstractFurnaceBlockEntity furnace, CallbackInfo ci) {
        if (level.getGameRules().get(ModGameRules.FURNACE_EXPLODE.get())) {
            FurnaceEntityAccessor furnaceReference = (FurnaceEntityAccessor) furnace;
            if (furnaceReference.rdsmisc$isLit()) {
                int time = furnaceReference.rdsmisc$litTimeRemaining();
                if (RDSMiscUtil.requireRarity(level.random, Math.min(time, 100000) * 100)) {
                    RDSMiscUtil.genericBlockExplode(level, pos);
                    ci.cancel();
                }

                ItemStack burnedStack = furnaceReference.rdsmisc$items().getFirst();
                int burnedAmount = burnedStack.getCount();

                if (burnedAmount > 0 && !burnedStack.isEmpty()) {
                    if (RDSMiscUtil.requireProbability(level.random, Math.pow((double) burnedAmount / burnedStack.getMaxStackSize(), 4) * 0.1)) {
                        RDSMiscUtil.genericBlockExplode(level, pos);
                        ci.cancel();
                    }
                }

                ItemStack resultStack = furnaceReference.rdsmisc$items().get(1);
                int resultAmount = burnedStack.getCount();

                if (resultAmount > 0 && !resultStack.isEmpty()) {
                    if (RDSMiscUtil.requireProbability(level.random, Math.pow((double) resultAmount / resultStack.getMaxStackSize(), 4) * 0.1)) {
                        RDSMiscUtil.genericBlockExplode(level, pos);
                        ci.cancel();
                    }
                }
            }
        }
    }
}
