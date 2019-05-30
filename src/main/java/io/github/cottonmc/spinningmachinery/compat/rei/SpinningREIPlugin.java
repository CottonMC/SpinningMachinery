package io.github.cottonmc.spinningmachinery.compat.rei;

import io.github.cottonmc.spinningmachinery.SpinningMachinery;
import io.github.cottonmc.spinningmachinery.recipe.GrindingRecipe;
import me.shedaniel.rei.api.REIPluginEntry;
import me.shedaniel.rei.api.RecipeHelper;
import net.minecraft.recipe.Recipe;
import net.minecraft.util.Identifier;

public final class SpinningREIPlugin implements REIPluginEntry {
    public static final Identifier ID = SpinningMachinery.id("rei_plugin");
    public static final Identifier GRINDING = SpinningMachinery.id("grinding");

    @Override
    public Identifier getPluginIdentifier() {
        return ID;
    }

    @Override
    public void registerPluginCategories(RecipeHelper recipeHelper) {
        recipeHelper.registerCategory(new GrindingCategory());
    }

    @Override
    public void registerRecipeDisplays(RecipeHelper recipeHelper) {
        for (Recipe<?> recipe : recipeHelper.getAllSortedRecipes()) {
            if (recipe instanceof GrindingRecipe) {
                recipeHelper.registerDisplay(GRINDING, new GrindingDisplay((GrindingRecipe) recipe));
            }
        }
    }
}
