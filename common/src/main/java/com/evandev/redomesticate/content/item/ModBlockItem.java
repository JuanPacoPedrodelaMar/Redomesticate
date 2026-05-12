package com.evandev.redomesticate.content.item;

import com.evandev.redomesticate.platform.registry.RegistryObject;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
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
}