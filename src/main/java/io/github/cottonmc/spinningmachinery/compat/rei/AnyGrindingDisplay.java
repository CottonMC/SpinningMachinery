package io.github.cottonmc.spinningmachinery.compat.rei;

import me.shedaniel.rei.api.RecipeDisplay;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;

import javax.annotation.Nullable;
import java.util.List;

interface AnyGrindingDisplay<T extends Recipe> extends RecipeDisplay<T> {
    @Nullable
    String getSourceMod();

    List<ItemStack> getBonusStacks();

    double getBonusChance();
}
