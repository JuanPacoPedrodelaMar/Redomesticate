package com.evandev.redomesticate.content.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class RottenAppleItem extends Item {

    public RottenAppleItem() {
        super(new Properties().food((new FoodProperties.Builder()).nutrition(3).saturationModifier(0.3f).effect(new MobEffectInstance(MobEffects.POISON, 100, 1), 1.0F).build()));
    }
}