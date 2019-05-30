package io.github.cottonmc.spinningmachinery.compat.rei;

import com.google.common.collect.ImmutableList;
import io.github.cottonmc.spinningmachinery.recipe.GrindingRecipe;
import me.shedaniel.rei.api.RecipeDisplay;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

final class GrindingDisplay implements RecipeDisplay<GrindingRecipe> {
    private final GrindingRecipe recipe;
    private final List<List<ItemStack>> input;
    private final List<ItemStack> output;

    GrindingDisplay(GrindingRecipe recipe) {
        this.recipe = recipe;
        input = recipe.getPreviewInputs().stream()
                .map(ingredient -> Arrays.asList(ingredient.getStackArray()))
                .collect(Collectors.toList());

        output = ImmutableList.of(recipe.getOutput(), recipe.getBonus().orElse(ItemStack.EMPTY));
    }

    @Override
    public Optional<GrindingRecipe> getRecipe() {
        return Optional.of(recipe);
    }

    @Override
    public List<List<ItemStack>> getInput() {
        return input;
    }

    @Override
    public List<ItemStack> getOutput() {
        return output;
    }

    @Override
    public List<List<ItemStack>> getRequiredItems() {
        return input;
    }

    @Override
    public Identifier getRecipeCategory() {
        return SpinningREIPlugin.GRINDING;
    }
}
