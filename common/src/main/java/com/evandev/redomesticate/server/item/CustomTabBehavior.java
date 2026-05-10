package com.evandev.redomesticate.server.item;

import net.minecraft.world.item.CreativeModeTab;

public interface CustomTabBehavior {
    void fillItemCategory(CreativeModeTab.Output contents);
}
