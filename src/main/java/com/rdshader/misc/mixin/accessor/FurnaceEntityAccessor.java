package com.rdshader.misc.mixin.accessor;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(AbstractFurnaceBlockEntity.class)
public interface FurnaceEntityAccessor {
    @Accessor("items")
    NonNullList<ItemStack> rdsmisc$items();

    @Accessor("litTimeRemaining")
    int rdsmisc$litTimeRemaining();

    @Invoker("isLit")
    boolean rdsmisc$isLit();
}
