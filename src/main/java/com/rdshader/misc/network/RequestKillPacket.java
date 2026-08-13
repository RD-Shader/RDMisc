package com.rdshader.misc.network;

import com.rdshader.misc.RDSMisc;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jspecify.annotations.NonNull;

import java.util.UUID;

public record RequestKillPacket(String uuid) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<RequestKillPacket> TYPE = new CustomPacketPayload.Type<>(
            Identifier.fromNamespaceAndPath(RDSMisc.MODID, "request_kill"));

    public static final StreamCodec<ByteBuf, RequestKillPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, RequestKillPacket::uuid, RequestKillPacket::new);

    public static void toServer(RequestKillPacket packet, IPayloadContext iPayloadContext) {
        ServerPlayer player = (ServerPlayer) iPayloadContext.player();
        ServerLevel level = player.level();
        Entity entity = level.getEntity(UUID.fromString(packet.uuid()));
        if (entity != null) {
            entity.kill(level);
        }
    }

    @Override
    public CustomPacketPayload.@NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
