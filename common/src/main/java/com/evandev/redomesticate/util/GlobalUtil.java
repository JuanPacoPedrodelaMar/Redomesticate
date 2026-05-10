package com.evandev.redomesticate.util;

import com.evandev.redomesticate.Constants;
import net.minecraft.resources.ResourceLocation;

public class GlobalUtil {
    public static ResourceLocation res(String name) {
        return ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name);
    }
}
