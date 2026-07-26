package com.rdshader.misc.handler;

import com.rdshader.misc.RDSMisc;
import com.rdshader.misc.block.ModifiedBedBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.ExplosionEvent;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@EventBusSubscriber(modid = RDSMisc.MODID)
public class EventHandlers {
    private static final ScheduledExecutorService SCHEDULER = Executors.newSingleThreadScheduledExecutor();

    @SubscribeEvent
    public static void bedExplodeHandler(ExplosionEvent.Detonate event) {
        Level level = event.getLevel();
        if (level instanceof ServerLevel) {
            for (BlockPos pos : event.getAffectedBlocks()) {
                BlockState blockState = level.getBlockState(pos);
                if (blockState.getBlock() instanceof BedBlock bedBlock) {
                    SCHEDULER.schedule(() -> level.getServer().execute(() -> {
                        int intensity = 1;
                        if (bedBlock instanceof ModifiedBedBlock block) {
                            intensity = block.getIntensity();
                        }
                        ModifiedBedBlock.modifiedBedExplode(blockState, level, pos, intensity);
                        }), 400, TimeUnit.MILLISECONDS);
                }
            }
        }
    }
}
