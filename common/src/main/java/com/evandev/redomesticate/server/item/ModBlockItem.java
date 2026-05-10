package com.evandev.redomesticate.server.item;

import com.evandev.redomesticate.platform.registry.RegistryObject;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import org.jetbrains.annotations.NotNull;

public class ModBlockItem extends BlockItem {

    private final RegistryObject<Block> blockSupplier;

    public ModBlockItem(RegistryObject<Block> blockSupplier, Properties props) {
        super(null, props);
        this.blockSupplier = blockSupplier;
    }

    @Override
    public @NotNull Block getBlock() {
        return blockSupplier.get();
    }

    @Override
    public boolean canFitInsideContainerItems() {
        return !(blockSupplier.get() instanceof ShulkerBoxBlock);
    }

    @Override
    public void onDestroyed(@NotNull ItemEntity p_150700_) {

    }
}
