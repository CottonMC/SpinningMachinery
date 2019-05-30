package io.github.cottonmc.spinningmachinery.recipe;

import io.github.cottonmc.spinningmachinery.SpinningMachinery;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.registry.Registry;

public final class SpinningRecipes {
    public static final RecipeType<GrindingRecipe> GRINDING =
            Registry.register(Registry.RECIPE_TYPE, SpinningMachinery.id("grinding"), new RecipeType<GrindingRecipe>() {
                @Override
                public String toString() {
                    return "grinding";
                }
            });

    public static final RecipeType<PressingRecipe> PRESSING =
            Registry.register(Registry.RECIPE_TYPE, SpinningMachinery.id("pressing"), new RecipeType<PressingRecipe>() {
                @Override
                public String toString() {
                    return "pressing";
                }
            });

    public static final RecipeSerializer<GrindingRecipe> GRINDING_SERIALIZER =
            Registry.register(Registry.RECIPE_SERIALIZER, SpinningMachinery.id("grinding"), new GrindingRecipe.Serializer());

    public static final RecipeSerializer<PressingRecipe> PRESSING_SERIALIZER =
            Registry.register(Registry.RECIPE_SERIALIZER, SpinningMachinery.id("pressing"), new PressingRecipe.Serializer());

    public static void init() {}
}
