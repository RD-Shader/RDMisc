package com.rdshader.misc.block;

import com.rdshader.misc.block.entity.ModifiedBedBlockEntity;
import com.rdshader.misc.block.renderer.ModifiedBedType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;

public class ModifiedBedBlock extends BedBlock {
    private final ModifiedBedType type;
    private final int intensity;

    public ModifiedBedBlock(Properties properties, ModifiedBedType type) {
        super(DyeColor.RED, properties);

        this.type = type;
        this.intensity = type.getIntensity();
    }

    @Override
    protected @NonNull InteractionResult useWithoutItem(@NonNull BlockState blockState, Level level, @NonNull BlockPos blockPos, @NonNull Player player, @NonNull BlockHitResult blockHitResult) {
        if (!level.isClientSide()) {
            modifiedBedExplode(blockState, level, blockPos, intensity);
            return InteractionResult.SUCCESS_SERVER;

        }
        return InteractionResult.SUCCESS_SERVER;
    }

    public static void modifiedBedExplode(BlockState blockState, Level level, BlockPos blockPos, int intensity) {
        if (blockState.getBlock() instanceof BedBlock) {
            level.removeBlock(blockPos, false);
            BlockPos blockpos = blockPos.relative((blockState.getValue(FACING)).getOpposite());
            if (level.getBlockState(blockpos).getBlock() instanceof BedBlock) {
                level.removeBlock(blockpos, false);
            }

            Vec3 center = blockPos.getCenter();
            level.explode(null, level.damageSources().badRespawnPointExplosion(center), null, center, (float) (5 * Math.sqrt(intensity)), true, Level.ExplosionInteraction.BLOCK);
        }
    }

    public ModifiedBedType getType() {
        return type;
    }

    public int getIntensity() {
        return intensity;
    }

    @Override
    public @NonNull BlockEntity newBlockEntity(@NonNull BlockPos blockPos, @NonNull BlockState blockState) {
        return new ModifiedBedBlockEntity(blockPos, blockState);
    }
}
