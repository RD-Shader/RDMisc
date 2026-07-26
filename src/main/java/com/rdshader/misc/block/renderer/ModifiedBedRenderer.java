package com.rdshader.misc.block.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.rdshader.misc.block.ModifiedBedBlock;
import com.rdshader.misc.mixin.accessor.BedRendererAccessor;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BedRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BedRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.Material;
import org.jspecify.annotations.NonNull;

public class ModifiedBedRenderer extends BedRenderer {
    public ModifiedBedRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void submit(@NonNull BedRenderState bedRenderState, @NonNull PoseStack poseStack, @NonNull SubmitNodeCollector submitNodeCollector, @NonNull CameraRenderState cameraRenderState) {
        ModifiedBedBlock block = (ModifiedBedBlock) bedRenderState.blockState.getBlock();
        Material material = new Material(Sheets.BED_SHEET, block.getType().getIdentifier());

        BedRendererAccessor pointer = (BedRendererAccessor) this;
        pointer.rdsmisc$submitPiece(poseStack, submitNodeCollector, bedRenderState.isHead ? pointer.rdsmisc$headModel() : pointer.rdsmisc$footModel(), bedRenderState.facing, material, bedRenderState.lightCoords, OverlayTexture.NO_OVERLAY, false, bedRenderState.breakProgress, 0);
    }
}
