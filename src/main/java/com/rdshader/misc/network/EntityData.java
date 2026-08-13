package com.rdshader.misc.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;

public record EntityData(String uuid, EntityType<?> entityType, double x, double y, double z, ItemStack stack) {
    public static final StreamCodec<RegistryFriendlyByteBuf, EntityData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, EntityData::uuid,
            EntityType.STREAM_CODEC, EntityData::entityType,
            ByteBufCodecs.DOUBLE, EntityData::x,
            ByteBufCodecs.DOUBLE, EntityData::y,
            ByteBufCodecs.DOUBLE, EntityData::z,
            ItemStack.OPTIONAL_STREAM_CODEC, EntityData::stack, EntityData::new);

    public String getEntityTypeId() {
        return EntityType.getKey(entityType).getPath();
    }
}
