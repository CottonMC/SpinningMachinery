package io.github.cottonmc.spinningmachinery.compat.rei;

import io.github.cottonmc.cotton.datapack.recipe.CrushingRecipe;
import io.github.cottonmc.spinningmachinery.SpinningMachinery;
import io.github.cottonmc.spinningmachinery.recipe.GrindingRecipe;
import io.github.cottonmc.spinningmachinery.recipe.HammeringRecipe;
import io.github.cottonmc.spinningmachinery.recipe.PressingRecipe;
import me.shedaniel.rei.api.REIPluginEntry;
import me.shedaniel.rei.api.RecipeHelper;
import net.minecraft.recipe.Recipe;
import net.minecraft.util.Identifier;

public final class SpinningREIPlugin implements REIPluginEntry {
    public static final Identifier ID = SpinningMachinery.id("rei_plugin");
    public static final Identifier GRINDING = SpinningMachinery.id("grinding");
    public static final Identifier PRESSING = SpinningMachinery.id("pressing");
    public static final Identifier HAMMERING = SpinningMachinery.id("hammering");

    @Override
    public Identifier getPluginIdentifier() {
        return ID;
    }

    @Override
    public void registerPluginCategories(RecipeHelper recipeHelper) {
        recipeHelper.registerCategory(new GrindingCategory());
        recipeHelper.registerCategory(new PressingCategory());
        recipeHelper.registerCategory(new HammeringCategory());
    }

    @Override
    public void registerRecipeDisplays(RecipeHelper recipeHelper) {
        for (Recipe<?> recipe : recipeHelper.getAllSortedRecipes()) {
            if (recipe instanceof GrindingRecipe) {
                recipeHelper.registerDisplay(GRINDING, new GrindingDisplay((GrindingRecipe) recipe));
            } else if (recipe instanceof PressingRecipe) {
                recipeHelper.registerDisplay(PRESSING, new PressingDisplay((PressingRecipe) recipe));
            } else if (recipe instanceof HammeringRecipe) {
                recipeHelper.registerDisplay(HAMMERING, new HammeringDisplay((HammeringRecipe) recipe));
            } else if (recipe instanceof CrushingRecipe) {
                recipeHelper.registerDisplay(GRINDING, new CrushingDisplay((CrushingRecipe) recipe));
            }
        }
    }
}
