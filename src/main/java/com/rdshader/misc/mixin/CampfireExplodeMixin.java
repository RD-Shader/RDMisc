package com.rdshader.misc.mixin;

import com.rdshader.misc.RDSMiscUtil;
import com.rdshader.misc.mixin.accessor.CampfireEntityAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CampfireBlockEntity.class)
public abstract class CampfireExplodeMixin extends BlockEntity {
    public CampfireExplodeMixin(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    @Inject(method = "cookTick", at = @At("RETURN"), cancellable = true)
    private static void cookTick(ServerLevel level, BlockPos pos, BlockState state, CampfireBlockEntity campfire, RecipeManager.CachedCheck<SingleRecipeInput, CampfireCookingRecipe> check, CallbackInfo ci) {
        CampfireEntityAccessor reference = (CampfireEntityAccessor) campfire;
        int stackCount = 0;
        for (ItemStack stack: reference.rdsmisc$items()) {
            if (!stack.isEmpty()) {
                stackCount++;
            }
        }

        if (stackCount == 0) {
            ci.cancel();
        }
        else if (RDSMiscUtil.requireProbability(level.random, Math.pow((stackCount / 4.0), 4) * 0.03)) {
            if (state.is(Blocks.CAMPFIRE)) {
                RDSMiscUtil.genericBlockExplode(level, pos);
            }
            else {
                RDSMiscUtil.genericBlockExplode(level, pos, 10.0F);
            }
        }
    }
}
