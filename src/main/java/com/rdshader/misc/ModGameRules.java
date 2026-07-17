package com.rdshader.misc;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.serialization.Codec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.gamerules.*;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

@SuppressWarnings("ALL")
public class ModGameRules {
    public static final DeferredRegister<GameRule<?>> GAME_RULES =
            DeferredRegister.create(BuiltInRegistries.GAME_RULE, RDSMisc.MODID);

    public static Supplier<GameRule<Boolean>> DROP_INVENTORY =
            GAME_RULES.register("drop_inventory", registryName ->
                    new GameRule(GameRuleCategory.PLAYER, GameRuleType.BOOL, BoolArgumentType.bool(),
                            GameRuleTypeVisitor::visitBoolean, Codec.BOOL, i -> (boolean) i ? 1 : 0,
                            false, FeatureFlagSet.of()));
    public static Supplier<GameRule<Integer>> DROP_FREQUENCY =
            GAME_RULES.register("drop_frequency", registryName ->
                    new GameRule(GameRuleCategory.PLAYER, GameRuleType.INT, IntegerArgumentType.integer(0, Integer.MAX_VALUE),
                            GameRuleTypeVisitor::visitInteger, Codec.intRange(0, Integer.MAX_VALUE), i -> (int) i,
                            100, FeatureFlagSet.of()));
}
