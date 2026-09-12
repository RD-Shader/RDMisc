package com.rdshader.misc.network;

import com.rdshader.misc.RDSMisc;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.component.ItemContainerContents;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jspecify.annotations.NonNull;

import java.util.HashMap;
import java.util.Map;

public class RequestPlayersPacket implements CustomPacketPayload {
    public static final RequestPlayersPacket INSTANCE = new RequestPlayersPacket();

    public static final CustomPacketPayload.Type<RequestPlayersPacket> TYPE = new CustomPacketPayload.Type<>(
            Identifier.fromNamespaceAndPath(RDSMisc.MODID, "request_players"));

    public static final StreamCodec<ByteBuf, RequestPlayersPacket> STREAM_CODEC = StreamCodec.unit(INSTANCE);

    private RequestPlayersPacket() {}

    public static void toServer(RequestPlayersPacket ignoredPacket, IPayloadContext iPayloadContext) {
        ServerPlayer serverPlayer = (ServerPlayer) iPayloadContext.player();
        ServerLevel level = serverPlayer.level();
        Map<String, ItemContainerContents> map = new HashMap<>();

        for (Player player: level.getPlayers(player -> true)) {
            map.put(player.getName().getString(), ItemContainerContents.fromItems(player.getInventory().getNonEquipmentItems()
                    .stream().filter(stack -> !stack.isEmpty()).toList()));
        }

        PacketDistributor.sendToPlayer(serverPlayer, new ResponsePlayersPacket(map));
    }

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
