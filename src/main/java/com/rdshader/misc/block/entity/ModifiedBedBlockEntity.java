package com.rdshader.misc.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BedBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.NonNull;

public class ModifiedBedBlockEntity extends BedBlockEntity {
    public ModifiedBedBlockEntity(BlockPos pos, BlockState blockState) {
        super(pos, blockState);
    }

    @Override
    public @NonNull BlockEntityType<?> getType() {
        return ModBlockEntityTypes.MODIFIED_BED.get();
    }
}
