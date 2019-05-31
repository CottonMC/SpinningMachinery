package io.github.cottonmc.spinningmachinery.recipe;

import com.google.common.collect.ImmutableList;
import io.github.cottonmc.spinningmachinery.SpinningMachinery;
import io.github.cottonmc.spinningmachinery.SpinningMachineryPlugin;
import net.minecraft.inventory.Inventory;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

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

    private static final List<RecipeType<? extends Recipe<? super GrindingInventory>>> grindingRecipeTypes = new ArrayList<>();
    private static final List<RecipeType<? extends Recipe<? super Inventory>>> pressingRecipeTypes = new ArrayList<>();

    public static void init() {}

    private static <R extends Recipe<?>> RecipeType<R> registerRecipeType(String id) {
        return Registry.register(Registry.RECIPE_TYPE, SpinningMachinery.id(id), new RecipeType<R>() {
            @Override
            public String toString() {
                return id;
            }
        });
    }

    public static ImmutableList<RecipeType<? extends Recipe<? super GrindingInventory>>> getAllGrindingRecipeTypes() {
        return ImmutableList.copyOf(grindingRecipeTypes);
    }

    public static ImmutableList<RecipeType<? extends Recipe<? super Inventory>>> getAllPressingRecipeTypes() {
        return ImmutableList.copyOf(pressingRecipeTypes);
    }

    public static void initPlugins(Collection<? extends SpinningMachineryPlugin> plugins) {
        plugins.stream()
                .flatMap(plugin -> Stream.of(plugin.getGrindingRecipeTypes()))
                .forEach(grindingRecipeTypes::addAll);

        plugins.stream()
                .flatMap(plugin -> Stream.of(plugin.getPressingRecipeTypes()))
                .forEach(pressingRecipeTypes::addAll);
    }
}
