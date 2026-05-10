package com.evandev.redomesticate.client;

import net.minecraft.world.entity.Entity;

import java.util.HashMap;
import java.util.Map;

public class CommonClientData {
    public static final Map<Entity, int[]> shadowPunchRenderData = new HashMap<>();

    public static void updateVisualDataForMob(Entity entity, int[] arr) {
        shadowPunchRenderData.put(entity, arr);
    }
}