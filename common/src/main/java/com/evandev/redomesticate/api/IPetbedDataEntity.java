package com.evandev.redomesticate.api;

import net.minecraft.nbt.CompoundTag;

public interface IPetbedDataEntity {

    CompoundTag redomesticate$getCitadelEntityData();

    void redomesticate$setCitadelEntityData(CompoundTag nbt);
}
