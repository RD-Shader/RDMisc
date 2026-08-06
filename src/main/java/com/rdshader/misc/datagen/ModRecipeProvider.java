package com.rdshader.misc.datagen;

import com.rdshader.misc.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    protected ModRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {
        ShapedRecipeBuilder.shaped(registries.lookupOrThrow(Registries.ITEM), RecipeCategory.MISC, ModItems.GRAVEL_BOULDER)
                .pattern("XXX").pattern("XXX").pattern("XXX")
                .define('X', Items.GRAVEL).unlockedBy("has_gravel", has(Items.GRAVEL))
                .save(output);
        ShapedRecipeBuilder.shaped(registries.lookupOrThrow(Registries.ITEM), RecipeCategory.MISC, ModItems.BOULDER)
                .pattern("XXX").pattern("XXX").pattern("XXX")
                .define('X', Items.STONE).unlockedBy("has_stone", has(Items.STONE))
                .save(output);
        ShapedRecipeBuilder.shaped(registries.lookupOrThrow(Registries.ITEM), RecipeCategory.MISC, ModItems.DEEPSLATE_BOULDER)
                .pattern("XXX").pattern("XXX").pattern("XXX")
                .define('X', Items.DEEPSLATE).unlockedBy("has_deepslate", has(Items.DEEPSLATE))
                .save(output);
        ShapedRecipeBuilder.shaped(registries.lookupOrThrow(Registries.ITEM), RecipeCategory.MISC, ModItems.SAND_BOULDER)
                .pattern("XXX").pattern("XXX").pattern("XXX")
                .define('X', Items.SAND).unlockedBy("has_sand", has(Items.SAND))
                .save(output);

        ShapelessRecipeBuilder.shapeless(registries.lookupOrThrow(Registries.ITEM), RecipeCategory.DECORATIONS, ModItems.DOUBLE_BED)
                .requires(ItemTags.BEDS).requires(ItemTags.BEDS).requires(Items.CRYING_OBSIDIAN)
                .unlockedBy("has_bed", has(ItemTags.BEDS))
                .unlockedBy("has_crying_obsidian", has(Items.CRYING_OBSIDIAN))
                .save(output);
        ShapelessRecipeBuilder.shapeless(registries.lookupOrThrow(Registries.ITEM), RecipeCategory.DECORATIONS, ModItems.QUADRUPLE_BED)
                .requires(ModItems.DOUBLE_BED, 2)
                .unlockedBy("has_double_bed", has(ModItems.DOUBLE_BED))
                .save(output);
        ShapelessRecipeBuilder.shapeless(registries.lookupOrThrow(Registries.ITEM), RecipeCategory.DECORATIONS, ModItems.TRIPLE_DEPRESSED_BED)
                .requires(ModItems.QUADRUPLE_BED, 2)
                .unlockedBy("has_quadruple_bed", has(ModItems.QUADRUPLE_BED))
                .save(output);
        ShapelessRecipeBuilder.shapeless(registries.lookupOrThrow(Registries.ITEM), RecipeCategory.DECORATIONS, ModItems.FOURFOLD_DEPRESSED_BED)
                .requires(ModItems.TRIPLE_DEPRESSED_BED, 2)
                .unlockedBy("has_triple_depressed_bed", has(ModItems.TRIPLE_DEPRESSED_BED))
                .save(output);
        ShapelessRecipeBuilder.shapeless(registries.lookupOrThrow(Registries.ITEM), RecipeCategory.DECORATIONS, ModItems.FIVEFOLD_DEPRESSED_BED)
                .requires(ModItems.FOURFOLD_DEPRESSED_BED, 2)
                .unlockedBy("has_fourfold_depressed_bed", has(ModItems.FOURFOLD_DEPRESSED_BED))
                .save(output);
        ShapelessRecipeBuilder.shapeless(registries.lookupOrThrow(Registries.ITEM), RecipeCategory.DECORATIONS, ModItems.SIX_FOLD_DEPRESSED_BED)
                .requires(ModItems.FIVEFOLD_DEPRESSED_BED, 2)
                .unlockedBy("has_fivefold_depressed_bed", has(ModItems.FIVEFOLD_DEPRESSED_BED))
                .save(output);
        ShapelessRecipeBuilder.shapeless(registries.lookupOrThrow(Registries.ITEM), RecipeCategory.DECORATIONS, ModItems.SEVEN_FOLD_DEPRESSED_BED)
                .requires(ModItems.SIX_FOLD_DEPRESSED_BED, 2)
                .unlockedBy("has_sixfold_depressed_bed", has(ModItems.SIX_FOLD_DEPRESSED_BED))
                .save(output);
        ShapelessRecipeBuilder.shapeless(registries.lookupOrThrow(Registries.ITEM), RecipeCategory.DECORATIONS, ModItems.EIGHT_FOLD_DEPRESSED_BED)
                .requires(ModItems.SEVEN_FOLD_DEPRESSED_BED, 2)
                .unlockedBy("has_sevenfold_depressed_bed", has(ModItems.SEVEN_FOLD_DEPRESSED_BED))
                .save(output);
    }

    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, lookupProvider);
        }

        @Override
        protected @NonNull RecipeProvider createRecipeProvider(HolderLookup.@NonNull Provider provider, @NonNull RecipeOutput output) {
            return new ModRecipeProvider(provider, output);
        }

        @Override
        public @NonNull String getName() {
            return "Recipes";
        }
    }
}
