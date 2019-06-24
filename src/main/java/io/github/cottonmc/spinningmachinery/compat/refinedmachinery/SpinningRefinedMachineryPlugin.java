package io.github.cottonmc.spinningmachinery.compat.refinedmachinery;

import abused_master.refinedmachinery.registry.PulverizerRecipes;
import abused_master.refinedmachinery.registry.PulverizerRecipes.PulverizerRecipe;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mojang.datafixers.DataFixUtils;
import io.github.cottonmc.cotton.datapack.virtual.InputStreamProvider;
import io.github.cottonmc.cotton.datapack.virtual.VirtualResourcePack;
import io.github.cottonmc.cotton.datapack.virtual.VirtualResourcePackManager;
import io.github.cottonmc.spinningmachinery.SpinningMachinery;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.ResourceType;
import net.minecraft.tag.Tag;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class SpinningRefinedMachineryPlugin {
    private static final Logger LOGGER = LogManager.getLogger();

    @SuppressWarnings("unchecked")
    public static void initCotton() {
        LOGGER.info("[Spinning Machinery] Converting Refined Machinery recipes");
        PulverizerRecipes recipes = PulverizerRecipes.INSTANCE;
        try {
            // TODO: Can the reflection be removed?
            Field recipeMapField = PulverizerRecipes.class.getDeclaredField("recipesMap");
            recipeMapField.setAccessible(true);
            Map<Object, PulverizerRecipe> recipeMap = (Map<Object, PulverizerRecipe>) recipeMapField.get(recipes);
            Gson gson = new Gson();
            AtomicInteger i = new AtomicInteger();
            Stream<Map.Entry<String, InputStreamProvider>> virtualContents = recipeMap.entrySet().stream()
                    .map(entry -> {
                        PulverizerRecipe recipe = entry.getValue();
                        JsonObject result = new JsonObject();

                        result.addProperty("type", "spinning-machinery:grinding");
                        result.add("input", getIngredient(entry.getKey()));
                        result.add("primary_output", DataFixUtils.make(new JsonObject(), output -> {
                            output.addProperty("item", Registry.ITEM.getId(recipe.getOutput().getItem()).toString());
                            output.addProperty("count", recipe.getOutputAmount());
                        }));
                        result.add("bonus", DataFixUtils.make(new JsonObject(), output -> {
                            output.addProperty("item", Registry.ITEM.getId(recipe.getRandomDrop().getItem()).toString());
                            output.addProperty("count", recipe.getRandomDropAmoumt());
                        }));
                        result.addProperty("bonus_chance", recipe.getPercentageDrop() / 100.0);
                        result.addProperty("source_mod", "Refined Machinery");

                        String jsonString = gson.toJson(result);

                        return Maps.immutableEntry(
                                "data/spinning-machinery/recipes/grinding/virtual/rm_" + i.getAndAdd(1) + ".json",
                                InputStreamProvider.of(() -> jsonString)
                        );
                    });

            VirtualResourcePackManager.INSTANCE.addPack(
                    new VirtualResourcePack(
                            SpinningMachinery.id("refined_machinery_converted_recipes"),
                            ImmutableMap.copyOf(virtualContents.collect(Collectors.toList()))
                    ),
                    Collections.singleton(ResourceType.SERVER_DATA)
            );
        } catch (Exception e) {
            LOGGER.error("Exception while converting Refined Machinery recipes to Spinning Machinery recipes", e);
        }
    }

    private static JsonObject getIngredient(Object o) {
        JsonObject result = new JsonObject();
        if (o instanceof ItemStack) {
            result.addProperty("item", Registry.ITEM.getId(((ItemStack) o).getItem()).toString());
            return result;
        } else if (o instanceof Tag<?>) {
            result.addProperty("tag", ((Tag<?>) o).getId().toString());
            return result;
        } else {
            throw new IllegalArgumentException("Unknown recipe input: " + o);
        }
    }
}
