package io.github.cottonmc.spinningmachinery.compat.vivatech;

import io.github.cottonmc.spinningmachinery.SpinningMachineryPlugin;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.inventory.Inventory;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import vivatech.init.VivatechRecipes;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Collections;

public final class SpinningVivatechPlugin implements SpinningMachineryPlugin {
    private static final boolean ENABLED = FabricLoader.getInstance().isModLoaded("vivatech");

    @Override
    @Nonnull
    public Collection<RecipeType<? extends Recipe<? super Inventory>>> getPressingRecipeTypes() {
        return ENABLED ? Collections.singleton(VivatechRecipes.PRESSING) : Collections.emptySet();
    }
}
