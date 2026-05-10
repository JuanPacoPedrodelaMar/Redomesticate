package com.evandev.redomesticate;

import com.evandev.redomesticate.network.FabricNetworking;
import com.evandev.redomesticate.registry.FabricModLoot;
import net.fabricmc.api.ModInitializer;

public class Redomesticate implements ModInitializer {

    @Override
    public void onInitialize() {
        CommonClass.init();
        FabricNetworking.initMain();
        FabricModLoot.init();
    }

}