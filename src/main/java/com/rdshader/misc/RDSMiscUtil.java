package com.rdshader.misc;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class RDSMiscUtil {
    public static boolean requireRarity(RandomSource random, int rarity) {
        if (rarity == 0) {
            return true;
        }
        return random.nextInt(rarity) == 0;
    }
    
    public static boolean requireProbability(RandomSource random, double probability) {
        if (probability > 1) {
            throw new IllegalArgumentException("Probability exceeds 1: probability=" + probability);
        }
        return requireRarity(random, (int) Math.round(1 / probability));
    }

    public static void genericBlockExplode(Level level, BlockPos pos, float power) {
        if (!level.isClientSide()) {
            Vec3 center = pos.getCenter();
            level.explode(null, center.x, center.y, center.z, power, Level.ExplosionInteraction.BLOCK);
        }
    }

    public static void genericBlockExplode(Level level, BlockPos pos) {
        genericBlockExplode(level, pos, 5.0F);
    }
}
