package com.evandev.redomesticate.util;

import net.minecraft.world.inventory.tooltip.TooltipComponent;

public record ItemMobTooltip(net.minecraft.nbt.CompoundTag compoundTag) implements TooltipComponent {
}
