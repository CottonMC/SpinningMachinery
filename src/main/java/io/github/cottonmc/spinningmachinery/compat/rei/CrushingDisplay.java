package io.github.cottonmc.spinningmachinery.compat.rei;

import io.github.cottonmc.cotton.datapack.recipe.CrushingRecipe;
import io.github.cottonmc.spinningmachinery.compat.rei.loot.LootUtils;
import me.shedaniel.rei.api.RecipeDisplay;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

final class CrushingDisplay implements RecipeDisplay<CrushingRecipe>, AnyGrindingDisplay<CrushingRecipe> {
    private final CrushingRecipe recipe;
    private final List<List<ItemStack>> input;
    private final List<ItemStack> output;
    private final List<ItemStack> bonusStacks;

    CrushingDisplay(CrushingRecipe recipe) {
        this.recipe = recipe;
        input = recipe.getPreviewInputs().stream()
                .map(ingredient -> Arrays.asList(ingredient.getStackArray()))
                .collect(Collectors.toList());
        output = Collections.singletonList(recipe.getOutput());
        bonusStacks = LootUtils.getAllStacks(recipe.getBonusLootTable());
    }

    @Override
    public Optional<CrushingRecipe> getRecipe() {
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
    public Identifier getRecipeCategory() {
        return SpinningREIPlugin.GRINDING;
    }

    @Override
    public String getSourceMod() {
        return "Cotton";
    }

    @Override
    public List<ItemStack> getBonusStacks() {
        return bonusStacks;
    }

    @Override
    public double getBonusChance() {
        return 0;
    }
}
