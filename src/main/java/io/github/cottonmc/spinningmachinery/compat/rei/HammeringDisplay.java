package io.github.cottonmc.spinningmachinery.compat.rei;

import com.google.common.collect.ImmutableList;
import io.github.cottonmc.spinningmachinery.recipe.HammeringRecipe;
import me.shedaniel.rei.api.RecipeDisplay;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

final class HammeringDisplay implements RecipeDisplay<HammeringRecipe> {
    private final HammeringRecipe recipe;
    private final List<List<ItemStack>> input;
    private final List<ItemStack> output;

    HammeringDisplay(HammeringRecipe recipe) {
        this.recipe = recipe;
        input = recipe.getPreviewInputs().stream()
                .map(ingredient -> Arrays.asList(ingredient.getStackArray()))
                .collect(Collectors.toList());

        output = ImmutableList.of(recipe.getOutput());
    }

    @Override
    public Optional<HammeringRecipe> getRecipe() {
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
        return SpinningREIPlugin.HAMMERING;
    }
}
