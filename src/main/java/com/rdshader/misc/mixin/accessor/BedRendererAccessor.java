package com.rdshader.misc.mixin.accessor;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BedRenderer;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BedRenderer.class)
public interface BedRendererAccessor {
    @Invoker("submitPiece")
    void rdsmisc$submitPiece(PoseStack poseStack, SubmitNodeCollector nodeCollector, Model.Simple model, Direction direction, Material material, int packedLight, int packedOverlay, boolean isFeet, ModelFeatureRenderer.@Nullable CrumblingOverlay crumblingOverlay, int outlineColor);

    @Accessor("headModel")
    Model.Simple rdsmisc$headModel();

    @Accessor("footModel")
    Model.Simple rdsmisc$footModel();
}
