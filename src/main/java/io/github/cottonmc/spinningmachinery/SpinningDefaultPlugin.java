package io.github.cottonmc.spinningmachinery;

import com.google.common.collect.ImmutableSet;
import io.github.cottonmc.cotton.datapack.recipe.CottonRecipes;
import io.github.cottonmc.spinningmachinery.recipe.GrindingInventory;
import io.github.cottonmc.spinningmachinery.recipe.SpinningRecipes;
import net.minecraft.inventory.Inventory;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Collections;

public final class SpinningDefaultPlugin implements SpinningMachineryPlugin {
    @Override
    @Nonnull
    public Collection<RecipeType<? extends Recipe<? super GrindingInventory>>> getGrindingRecipeTypes() {
        return ImmutableSet.of(SpinningRecipes.GRINDING);
    }

    @Override
    @Nonnull
    public Collection<RecipeType<? extends Recipe<? super Inventory>>> getPressingRecipeTypes() {
        return Collections.singleton(SpinningRecipes.PRESSING);
    }
}
