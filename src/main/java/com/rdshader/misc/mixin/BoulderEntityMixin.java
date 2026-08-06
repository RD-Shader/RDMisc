package com.rdshader.misc.mixin;

import com.rdshader.misc.datagen.ModBlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FallingBlockEntity.class)
public abstract class BoulderEntityMixin extends Entity {
    @Shadow
    private BlockState blockState;

    public BoulderEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "getDefaultGravity", at = @At("RETURN"), cancellable = true)
    public void getDefaultGravity(CallbackInfoReturnable<Double> cir) {
        if (blockState.is(ModBlockTags.BOULDERS)) {
            cir.setReturnValue(0.15);
        }
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z", shift = At.Shift.AFTER))
    public void tick(CallbackInfo ci) {
        if (blockState.is(ModBlockTags.BOULDERS)) {
            level().destroyBlock(blockPosition(), false);
        }
    }
}
