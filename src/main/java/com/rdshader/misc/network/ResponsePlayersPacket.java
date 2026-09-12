package com.rdshader.misc.network;

import com.rdshader.misc.RDSMisc;
import com.rdshader.misc.RDSMiscUtil;
import com.rdshader.misc.gui.ExplorerScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemContainerContents;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record ResponsePlayersPacket(Map<String, ItemContainerContents> playerData) implements CustomPacketPayload {
    public static final Type<ResponsePlayersPacket> TYPE = new Type<>(
        Identifier.fromNamespaceAndPath(RDSMisc.MODID, "response_players"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ResponsePlayersPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.map(HashMap::new, ByteBufCodecs.STRING_UTF8, ItemContainerContents.STREAM_CODEC),
            ResponsePlayersPacket::playerData, ResponsePlayersPacket::new);

    public static void toClient(ResponsePlayersPacket packet, IPayloadContext iPayloadContext) {
        iPayloadContext.enqueueWork(() -> {
            Minecraft minecraft = Minecraft.getInstance();
            if (minecraft.screen instanceof ExplorerScreen screen) {
                List<ItemStack> stacks = new ArrayList<>();
                for (Map.Entry<String, ItemContainerContents> entry : packet.playerData.entrySet()) {
                    ItemStack stack = RDSMiscUtil.namedStack(Items.PLAYER_HEAD.getDefaultInstance(), entry.getKey());
                    stack.set(DataComponents.CONTAINER, entry.getValue());
                    stacks.add(stack);
                }
                screen.ROOT_STACKS.getFirst().set(DataComponents.CONTAINER, ItemContainerContents.fromItems(stacks));
            }
        });
    }

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
