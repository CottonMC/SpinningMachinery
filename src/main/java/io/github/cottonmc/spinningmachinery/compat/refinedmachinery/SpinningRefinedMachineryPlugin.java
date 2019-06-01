package io.github.cottonmc.spinningmachinery.compat.refinedmachinery;

import abused_master.refinedmachinery.registry.PulverizerRecipes;
import abused_master.refinedmachinery.registry.PulverizerRecipes.PulverizerRecipe;
import com.google.common.base.Charsets;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mojang.datafixers.DataFixUtils;
import io.github.cottonmc.spinningmachinery.internal.VirtualResourcePack;
import io.github.cottonmc.spinningmachinery.internal.VirtualResourcePackCreator;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.Tag;
import net.minecraft.util.Pair;
import net.minecraft.util.registry.Registry;
import org.apache.commons.io.input.ReaderInputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Stream;

public final class SpinningRefinedMachineryPlugin {
    private static final Logger LOGGER = LogManager.getLogger();

    @SuppressWarnings("unchecked")
    public static void initCotton() {
        LOGGER.info("[Spinning Machinery] Converting Refined Machinery recipes");
        PulverizerRecipes recipes = PulverizerRecipes.INSTANCE;
        try {
            Field recipeMapField = PulverizerRecipes.class.getDeclaredField("recipesMap");
            recipeMapField.setAccessible(true);
            Map<Object, PulverizerRecipe> recipeMap = (Map<Object, PulverizerRecipe>) recipeMapField.get(recipes);
            Gson gson = new Gson();
            AtomicInteger i = new AtomicInteger();
            Stream<Pair<String, Supplier<InputStream>>> virtualContents = recipeMap.entrySet().stream()
                    .map(entry -> {
                        PulverizerRecipe recipe = entry.getValue();
                        JsonObject result = new JsonObject();

                        result.addProperty("type", "spinning-machinery:grinding");
                        result.add("input", getIngredient(entry.getKey()));
                        result.add("primary_output", DataFixUtils.make(new JsonObject(), output -> {
                            output.addProperty("id", Registry.ITEM.getId(recipe.getOutput().getItem()).toString());
                            output.addProperty("Count", recipe.getOutputAmount());
                        }));
                        result.add("bonus", DataFixUtils.make(new JsonObject(), output -> {
                            output.addProperty("id", Registry.ITEM.getId(recipe.getRandomDrop().getItem()).toString());
                            output.addProperty("Count", recipe.getRandomDropAmoumt());
                        }));
                        result.addProperty("bonus_chance", recipe.getPercentageDrop() / 100.0);
                        result.addProperty("source_mod", "Refined Machinery");

                        String jsonString = gson.toJson(result);
                        StringReader reader = new StringReader(jsonString);

                        return new Pair<>("data/spinning-machinery/recipes/grinding/virtual/rm_" + i.getAndAdd(1) + ".json", () -> new ReaderInputStream(reader, Charsets.UTF_8));
                    });
            Map<String, Supplier<InputStream>> contents = new HashMap<>();
            virtualContents.forEach(pair -> contents.put(pair.getLeft(), pair.getRight()));
            VirtualResourcePackCreator.INSTANCE.addPack(
                    new VirtualResourcePack(
                            "refined_machinery_converted_recipes",
                            Collections.singleton("spinning-machinery"),
                            contents
                    )
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
            result.addProperty("tag", ((Tag) o).getId().toString());
            return result;
        } else {
            throw new IllegalArgumentException("Unknown recipe input: " + o);
        }
    }
}
