package com.evandev.redomesticate.api;

import net.minecraft.nbt.CompoundTag;

public interface IPetbedDataEntity {

    CompoundTag redomesticate$getEntityData();

    void redomesticate$setEntityData(CompoundTag nbt);
}
