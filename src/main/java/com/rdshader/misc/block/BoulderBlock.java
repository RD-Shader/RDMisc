package com.rdshader.misc.block;

import com.rdshader.misc.datagen.ModBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ColorRGBA;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ColoredFallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NonNull;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public class BoulderBlock extends ColoredFallingBlock {
    private static boolean affectFlag = true;
    private static final VoxelShape COLLISION_SHAPE = Block.box(1, 1, 1, 15, 15, 15);

    private final float damage;

    public BoulderBlock(ColorRGBA dustColor, float damage, Properties properties) {
        super(dustColor, properties);
        this.damage = damage;
    }

    @Override
    protected void falling(@NonNull FallingBlockEntity entity) {
        entity.setHurtsEntities(damage, 1000);
    }

    @Override
    public void entityInside(@NonNull BlockState state, @NonNull Level level, @NonNull BlockPos pos, @NonNull Entity entity, @NonNull InsideBlockEffectApplier applier, boolean intersects) {
        if (level instanceof ServerLevel serverLevel) {
            entity.hurtServer(serverLevel, serverLevel.damageSources().fall(), damage);
            serverLevel.destroyBlock(pos, false);
        }
    }

    @Override
    public void affectNeighborsAfterRemoval(@NonNull BlockState startState, @NonNull ServerLevel level, @NonNull BlockPos startPos, boolean movedByPiston) {
        if (affectFlag) {
            affectFlag = false;

            Set<BlockPos> toProcessPositions = new HashSet<>();
            Queue<BlockPos> queue = new ArrayDeque<>();
            queue.add(startPos);

            while (toProcessPositions.size() <= 200 && !queue.isEmpty()) {
                BlockPos pos = queue.poll();

                if (toProcessPositions.add(pos)) {
                    for (int x = -1; x <= 1; x++) {
                        for (int y = -1; y <= 1; y++) {
                            for (int z = -1; z <= 1; z++) {
                                if (!(x == 0 && y == 0 && z == 0)) {
                                    BlockPos relativePos = pos.offset(x, y, z);
                                    BlockState relativeState = level.getBlockState(relativePos);
                                    if (!toProcessPositions.contains(relativePos) && relativeState.is(ModBlockTags.BOULDERS)) {
                                        queue.add(relativePos);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            for (BlockPos pos : toProcessPositions) {
                level.destroyBlock(pos, false);
            }

            affectFlag = true;
        }
    }

    @Override
    protected @NonNull VoxelShape getCollisionShape(@NonNull BlockState state, @NonNull BlockGetter level, @NonNull BlockPos pos, @NonNull CollisionContext context) {
        return COLLISION_SHAPE;
    }
}
