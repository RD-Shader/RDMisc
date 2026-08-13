package com.rdshader.misc.network;

import com.rdshader.misc.RDSMisc;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jspecify.annotations.NonNull;

public record RequestTeleportPacket(EntityData data) implements CustomPacketPayload {
    public static final Type<RequestTeleportPacket> TYPE = new Type<>(
            Identifier.fromNamespaceAndPath(RDSMisc.MODID, "request_teleport"));

    public static final StreamCodec<RegistryFriendlyByteBuf, RequestTeleportPacket> STREAM_CODEC = StreamCodec.composite(
            EntityData.STREAM_CODEC, RequestTeleportPacket::data, RequestTeleportPacket::new);

    public static void toServer(RequestTeleportPacket packet, IPayloadContext iPayloadContext) {
        ServerPlayer player = (ServerPlayer) iPayloadContext.player();
        EntityData data = packet.data;
        player.teleportTo(data.x(), data.y(), data.z());
    }

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
