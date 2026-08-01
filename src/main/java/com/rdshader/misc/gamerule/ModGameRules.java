package com.rdshader.misc.gamerule;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.serialization.Codec;
import com.rdshader.misc.RDSMisc;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.gamerules.*;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

@SuppressWarnings("ALL")
public class ModGameRules {
    public static final DeferredRegister<GameRule<?>> GAME_RULES =
            DeferredRegister.create(BuiltInRegistries.GAME_RULE, RDSMisc.MODID);

    public static final Supplier<GameRule<Boolean>> DROP_INVENTORY = registerBooleanGamerule("drop_inventory");

    public static final Supplier<GameRule<Boolean>> RANDOM_EXPLODE = registerBooleanGamerule("random_explode");

    public static final Supplier<GameRule<Boolean>> FURNACE_EXPLODE = registerBooleanGamerule("furnace_explode");

    public static final Supplier<GameRule<Integer>> DROP_FREQUENCY =
            GAME_RULES.register("drop_frequency", registryName ->
                    new GameRule(GameRuleCategory.PLAYER, GameRuleType.INT, IntegerArgumentType.integer(0, Integer.MAX_VALUE),
                            GameRuleTypeVisitor::visitInteger, Codec.intRange(0, Integer.MAX_VALUE), i -> (int) i,
                            100, FeatureFlagSet.of()));

    private static Supplier<GameRule<Boolean>> registerBooleanGamerule(String name) {
        return GAME_RULES.register(name, registryName ->
                new GameRule(GameRuleCategory.MISC, GameRuleType.BOOL, BoolArgumentType.bool(),
                        GameRuleTypeVisitor::visitBoolean, Codec.BOOL, i -> (boolean) i ? 1 : 0,
                        false, FeatureFlagSet.of()));
    }
}
