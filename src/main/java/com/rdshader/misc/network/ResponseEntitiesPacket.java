package com.rdshader.misc.network;

import com.rdshader.misc.RDSMisc;
import com.rdshader.misc.gui.TaskManagerScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public record ResponseEntitiesPacket(List<EntityData> data) implements CustomPacketPayload {
    public static final Type<ResponseEntitiesPacket> TYPE = new Type<>(
            Identifier.fromNamespaceAndPath(RDSMisc.MODID, "response_entities"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ResponseEntitiesPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.collection(ArrayList::new, EntityData.STREAM_CODEC, 2048),
            ResponseEntitiesPacket::data, ResponseEntitiesPacket::new);

    public static void toClient(ResponseEntitiesPacket packet, IPayloadContext iPayloadContext) {
        iPayloadContext.enqueueWork(() -> {
            Minecraft minecraft = Minecraft.getInstance();
            if (minecraft.screen instanceof TaskManagerScreen screen) {
                screen.dataList = packet.data().stream().sorted(Comparator.comparing(EntityData::getEntityTypeId)).toList();
                screen.shownList = new ArrayList<>(screen.dataList);
                screen.refresh();
            }
        });
    }

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
