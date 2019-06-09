package io.github.cottonmc.spinningmachinery.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.*;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class PressingRecipe implements Recipe<Inventory> {
    private final Identifier id;
    private final String group;
    private final Ingredient input;
    private final ItemStack output;

    public PressingRecipe(Identifier id, String group, Ingredient input, ItemStack output) {
        this.input = input;
        this.output = output;
        this.group = group;
        this.id = id;
    }

    @Override
    public boolean matches(Inventory inventory, World world) {
        return input.test(inventory.getInvStack(0));
    }

    @Override
    public ItemStack craft(Inventory inventory) {
        return output.copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getOutput() {
        return output;
    }

    @Override
    public RecipeType<?> getType() {
        return SpinningRecipes.PRESSING;
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SpinningRecipes.PRESSING_SERIALIZER;
    }

    @Override
    public String getGroup() {
        return group;
    }

    @Override
    public DefaultedList<Ingredient> getPreviewInputs() {
        DefaultedList<Ingredient> list = DefaultedList.create();
        list.add(input);
        return list;
    }

    public static final class Serializer implements RecipeSerializer<PressingRecipe> {
        private static final Logger LOGGER = LogManager.getLogger();
        @Override
        public PressingRecipe read(Identifier id, JsonObject obj) {
            try {
                JsonElement ingredientJson =
                        JsonHelper.hasArray(obj, "input")
                                ? JsonHelper.getArray(obj, "input")
                                : JsonHelper.getObject(obj, "input");

                return new PressingRecipe(
                        id,
                        JsonHelper.getString(obj, "group", ""),
                        Ingredient.fromJson(ingredientJson),
                        ShapedRecipe.getItemStack(obj.getAsJsonObject("output"))
                );
            } catch (Exception e) {
                LOGGER.error("Failed to load pressing recipe " + id, e);
                throw new RuntimeException(e);
            }
        }

        @Override
        public PressingRecipe read(Identifier id, PacketByteBuf buf) {
            return new PressingRecipe(
                    id,
                    buf.readString(32767),
                    Ingredient.fromPacket(buf),
                    buf.readItemStack()
            );
        }

        @Override
        public void write(PacketByteBuf buf, PressingRecipe recipe) {
            buf.writeString(recipe.group);
            recipe.input.write(buf);
            buf.writeItemStack(recipe.output);
        }
    }
}
