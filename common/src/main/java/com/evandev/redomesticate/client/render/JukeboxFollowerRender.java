package com.evandev.redomesticate.client.render;

import com.evandev.redomesticate.content.entity.FollowingJukeboxEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

public class JukeboxFollowerRender extends EntityRenderer<FollowingJukeboxEntity> {

    private final ItemStack jukebox = new ItemStack(Items.JUKEBOX);

    public JukeboxFollowerRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn);
        jukebox.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true);
    }

    @Override
    public void render(@NotNull FollowingJukeboxEntity entity, float yaw, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int light) {
        super.render(entity, yaw, partialTicks, poseStack, buffer, light);
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entity.yRotO, entity.getYRot())));
        poseStack.translate(0, 0.1F, 0);
        poseStack.scale(1.8F, 1.8F, 1.8F);
        Minecraft.getInstance().getItemRenderer().renderStatic(jukebox, ItemDisplayContext.GROUND, light, OverlayTexture.NO_OVERLAY, poseStack, buffer, entity.level(), entity.getId());
        poseStack.popPose();

    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull FollowingJukeboxEntity entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}