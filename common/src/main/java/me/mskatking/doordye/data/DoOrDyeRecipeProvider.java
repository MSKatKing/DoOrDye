package me.mskatking.doordye.data;

import me.mskatking.doordye.color.DoOrDyeColor;
import me.mskatking.doordye.color.DoOrDyeColors;
import me.mskatking.doordye.item.DoOrDyeItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.recipes.packs.VanillaRecipeProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class DoOrDyeRecipeProvider extends VanillaRecipeProvider {
    public DoOrDyeRecipeProvider(PackOutput p_250820_, CompletableFuture<HolderLookup.Provider> p_323814_) {
        super(p_250820_, p_323814_);
    }

    @Override
    public void buildRecipes(@NotNull RecipeOutput recipeOutput) {
        for (DoOrDyeColor color : DoOrDyeColor.colors()) {
            stainedGlassFromGlassAndDye(recipeOutput, color.getStainedGlassBlock(), color.getDyeItem());
            concretePowder(recipeOutput, color.getConcretePowderBlock(), color.getDyeItem());
            coloredTerracottaFromTerracottaAndDye(recipeOutput, color.getTerracottaBlock(), color.getDyeItem());
            carpet(recipeOutput, color.getCarpetBlock(), color.getWoolBlock());
            // TODO: glass pane
            // TODO: shulker
            // TODO: candle
            // TODO: banner
            // TODO: bed
            // TODO: glazed terracotta
        }

        oneToOneConversionRecipe(recipeOutput, DoOrDyeColors.CHERRY, DoOrDyeItems.CHERRIES, "cherry_dye", 1);
        oneToOneConversionRecipe(recipeOutput, Items.BLUE_DYE, DoOrDyeItems.BLUEBERRIES, "blue_dye", 1);

    }

    protected void glazedTerracottaFromTerracotta(@NotNull RecipeOutput recipeOutput, String color, ItemLike pGlazedTerracotta, ItemLike pTerracotta) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(pTerracotta), RecipeCategory.DECORATIONS, pGlazedTerracotta, 0.1F, 200).unlockedBy("has_" + color + "_terracotta", has(pTerracotta)).save(recipeOutput);
    }
}
