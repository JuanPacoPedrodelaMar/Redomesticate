package com.evandev.redomesticate.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.inventory.tooltip.TooltipComponent;

public record ItemMobTooltip(CompoundTag compoundTag) implements TooltipComponent {
}
