package io.github.cottonmc.spinningmachinery;

import io.github.cottonmc.spinningmachinery.recipe.GrindingInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Collections;

public interface SpinningMachineryPlugin {
    @Nonnull
    default Collection<RecipeType<? extends Recipe<? super GrindingInventory>>> getGrindingRecipeTypes() {
        return Collections.emptySet();
    }

    @Nonnull
    default Collection<RecipeType<? extends Recipe<? super Inventory>>> getPressingRecipeTypes() {
        return Collections.emptySet();
    }
}
