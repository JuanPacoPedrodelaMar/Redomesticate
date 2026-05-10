package com.evandev.redomesticate.server.item;

import com.evandev.redomesticate.platform.registry.RegistryObject;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PetBedItem extends BlockItem {
    private final RegistryObject<Block> blockSupplier;

    public PetBedItem(RegistryObject<Block> blockSupplier, Properties props, DyeColor color) {
        super(null, props);
        this.blockSupplier = blockSupplier;
    }

    @Override
    public @NotNull Block getBlock() {
        return blockSupplier.get();
    }

    public void onDestroyed(@NotNull ItemEntity itemEntity) {

    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltips.redomesticate.substitute_pet_bed.desc").withStyle(ChatFormatting.GREEN));
    }
}