package com.evandev.redomesticate.util;

import com.evandev.redomesticate.api.IPetbedDataEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;

public class ModEntityData {
    public static CompoundTag getOrCreateEntityTag(LivingEntity entity) {
        CompoundTag tag = getEntityTag(entity);
        return tag == null ? new CompoundTag() : tag;
    }

    public static CompoundTag getEntityTag(LivingEntity entity) {
        return entity instanceof IPetbedDataEntity ? ((IPetbedDataEntity) entity).redomesticate$getCitadelEntityData() : new CompoundTag();
    }

    public static void setEntityTag(LivingEntity entity, CompoundTag tag) {
        if (entity instanceof IPetbedDataEntity) {
            ((IPetbedDataEntity) entity).redomesticate$setCitadelEntityData(tag);
        }
    }
}
