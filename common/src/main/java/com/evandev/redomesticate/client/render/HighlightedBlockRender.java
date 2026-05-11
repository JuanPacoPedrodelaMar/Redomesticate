package com.evandev.redomesticate.client.render;

import com.evandev.redomesticate.Constants;
import com.evandev.redomesticate.client.model.HighlightedBlockModel;
import com.evandev.redomesticate.content.entity.HighlightedBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class HighlightedBlockRender extends EntityRenderer<HighlightedBlockEntity> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "textures/highlighted_block.png");
    private final HighlightedBlockModel highlightedBlockModel = new HighlightedBlockModel();

    public HighlightedBlockRender(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.0F;
    }

    public void render(@NotNull HighlightedBlockEntity entity, float f1, float f2, PoseStack stack, MultiBufferSource source, int packedLight) {
        stack.pushPose();
        stack.translate(0, 0.5F, 0);
        VertexConsumer vertexconsumer = source.getBuffer(RenderType.outline(this.getTextureLocation(entity)));
        this.highlightedBlockModel.renderToBuffer(stack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY, 1);
        stack.popPose();
    }

    public @NotNull ResourceLocation getTextureLocation(@NotNull HighlightedBlockEntity block) {
        return TEXTURE;
    }
}