package io.github.cottonmc.spinningmachinery.recipe;

import io.github.cottonmc.spinningmachinery.SpinningMachinery;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.registry.Registry;

public final class SpinningRecipes {
    public static final RecipeType<GrindingRecipe> GRINDING = registerRecipeType("grinding");
    public static final RecipeType<PressingRecipe> PRESSING = registerRecipeType("pressing");
    public static final RecipeType<HammeringRecipe> HAMMERING = registerRecipeType("hammering");

    public static final RecipeSerializer<GrindingRecipe> GRINDING_SERIALIZER =
            Registry.register(Registry.RECIPE_SERIALIZER, SpinningMachinery.id("grinding"), new GrindingRecipe.Serializer());

    public static final RecipeSerializer<PressingRecipe> PRESSING_SERIALIZER =
            Registry.register(Registry.RECIPE_SERIALIZER, SpinningMachinery.id("pressing"), new PressingRecipe.Serializer());

    public static final RecipeSerializer<HammeringRecipe> HAMMERING_SERIALIZER =
            Registry.register(Registry.RECIPE_SERIALIZER, SpinningMachinery.id("hammering"), new HammeringRecipe.Serializer());

    public static void init() {}

    private static <R extends Recipe<?>> RecipeType<R> registerRecipeType(String id) {
        return Registry.register(Registry.RECIPE_TYPE, SpinningMachinery.id(id), new RecipeType<R>() {
            @Override
            public String toString() {
                return id;
            }
        });
    }
}
