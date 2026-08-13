package com.rdshader.misc.network;

import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class PayloadRegistry {
    public static void registerPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");

        registrar.playToServer(RequestEntitiesPacket.TYPE, RequestEntitiesPacket.STREAM_CODEC, RequestEntitiesPacket::toServer);

        registrar.playToServer(RequestKillPacket.TYPE, RequestKillPacket.STREAM_CODEC, RequestKillPacket::toServer);

        registrar.playToServer(RequestTeleportPacket.TYPE, RequestTeleportPacket.STREAM_CODEC, RequestTeleportPacket::toServer);

        registrar.playToClient(ResponseEntitiesPacket.TYPE, ResponseEntitiesPacket.STREAM_CODEC, ResponseEntitiesPacket::toClient);
    }
}
