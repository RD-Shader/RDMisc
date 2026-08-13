package com.rdshader.misc.network;

import com.rdshader.misc.RDSMisc;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

public class RequestEntitiesPacket implements CustomPacketPayload {
    public static final RequestEntitiesPacket INSTANCE = new RequestEntitiesPacket();

    public static final CustomPacketPayload.Type<RequestEntitiesPacket> TYPE = new CustomPacketPayload.Type<>(
            Identifier.fromNamespaceAndPath(RDSMisc.MODID, "request_entities"));

    public static final StreamCodec<ByteBuf, RequestEntitiesPacket> STREAM_CODEC = StreamCodec.unit(INSTANCE);

    private RequestEntitiesPacket() {}

    @SuppressWarnings("unused")
    public static void toServer(RequestEntitiesPacket packet, IPayloadContext iPayloadContext) {
        ServerPlayer player = (ServerPlayer) iPayloadContext.player();
        ServerLevel level = player.level();
        List<EntityData> dataList = new ArrayList<>();
        EntityData data;

        for (Entity entity: level.getEntities().getAll()) {
            if (!entity.isRemoved()) {
                ItemStack stack = ItemStack.EMPTY;
                switch (entity) {
                    case ItemEntity itemEntity -> stack = itemEntity.getItem();
                    case FallingBlockEntity fallingBlockEntity -> stack = fallingBlockEntity.getBlockState().getBlock().asItem().getDefaultInstance();
                    case Player player1 -> stack = Items.PLAYER_HEAD.getDefaultInstance();
                    default -> {}
                }

                ItemStack pickStack = entity.getPickResult();
                if (pickStack != null) {
                    stack = pickStack;
                }
                data = new EntityData(entity.getStringUUID(), entity.getType(), entity.getX(), entity.getY(), entity.getZ(), stack);
                dataList.add(data);
            }
        }

        PacketDistributor.sendToPlayer(player, new ResponseEntitiesPacket(dataList));
    }

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
