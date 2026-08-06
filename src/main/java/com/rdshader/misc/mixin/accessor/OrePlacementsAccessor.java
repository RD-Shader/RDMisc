package com.rdshader.misc.mixin.accessor;

import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;

@Mixin(OrePlacements.class)
public interface OrePlacementsAccessor {
    @Invoker("commonOrePlacement")
    static List<PlacementModifier> rdsmisc$commonOrePlacement(int count, PlacementModifier heightRange) {
        throw new AssertionError("关注RDShader谢谢喵");
    }
}
