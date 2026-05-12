package com.evandev.redomesticate.content.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DeedOfOwnershipItem extends Item {

    public DeedOfOwnershipItem() {
        super(new Properties().stacksTo(1));
    }

    public static boolean isBound(ItemStack stack) {
        CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
        return customData != null && customData.contains("HasBoundEntity") && customData.copyTag().getBoolean("HasBoundEntity");
    }

    @Override
    public boolean isFoil(@NotNull ItemStack stack) {
        return super.isFoil(stack) || isBound(stack);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
        CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
        if (isBound(stack) && customData != null && customData.contains("BoundEntityName")) {
            tooltipComponents.add(Component.translatable("item.redomesticate.deed_of_ownership.desc", customData.copyTag().getString("BoundEntityName")).withStyle(ChatFormatting.GRAY));
        } else {
            tooltipComponents.add(Component.translatable("item.redomesticate.deed_of_ownership.desc_unbound").withStyle(ChatFormatting.GRAY));
        }
    }
}