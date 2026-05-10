package com.evandev.redomesticate.client.render;

import com.evandev.redomesticate.Constants;
import com.evandev.redomesticate.mixin.accessor.SpriteContentsAccessor;
import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.level.block.state.BlockState;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class OreColorRegistry {

    public static Map<String, Integer> TEXTURES_TO_COLOR = new HashMap<>();

    public static int getBlockColor(BlockState stack) {
        String blockName = stack.toString();
        if (TEXTURES_TO_COLOR.containsKey(blockName)) {
            return TEXTURES_TO_COLOR.get(blockName);
        } else {
            int colorizer = -1;
            try {
                colorizer = Minecraft.getInstance().getBlockColors().getColor(stack, null, null, 0);
            } catch (Exception e) {
                Constants.LOG.warn("Another mod did not use block colorizers correctly for: {}", blockName);
            }
            int color = 0XFFFFFF;
            if (colorizer == -1) {
                try {
                    Color texColour = getAverageColour(getTextureAtlas(stack));
                    color = texColour.getRGB();
                } catch (Exception e) {
                    Constants.LOG.error("Failed to parse average color for block: {}", blockName, e);
                }
            } else {
                color = colorizer;
            }
            TEXTURES_TO_COLOR.put(blockName, color);
            return color;
        }
    }

    private static Color getAverageColour(TextureAtlasSprite image) {
        float red = 0;
        float green = 0;
        float blue = 0;
        float count = 0;
        int uMax = image.contents().width();
        int vMax = image.contents().height();

        NativeImage nativeImage = ((SpriteContentsAccessor) image.contents()).redomesticate$getOriginalImage();

        for (float i = 0; i < uMax; i++) {
            for (float j = 0; j < vMax; j++) {
                int pixel = nativeImage.getPixelRGBA((int) i, (int) j);

                int alpha = (pixel >> 24) & 0xFF;
                if (alpha == 0) {
                    continue;
                }

                float localRed = pixel & 0xFF;
                float localGreen = (pixel >> 8) & 0xFF;
                float localBlue = (pixel >> 16) & 0xFF;

                if (Math.abs(Math.max(localRed, Math.max(localGreen, localBlue)) - Math.min(localRed, Math.min(localGreen, localBlue))) < 10) {
                    continue;
                }

                red += localRed;
                green += localGreen;
                blue += localBlue;
                count++;
            }
        }

        if (count == 0) return Color.WHITE;

        return new Color((int) (red / count), (int) (green / count), (int) (blue / count));
    }

    private static TextureAtlasSprite getTextureAtlas(BlockState state) {
        return Minecraft.getInstance().getBlockRenderer().getBlockModelShaper().getBlockModel(state).getParticleIcon();
    }
}