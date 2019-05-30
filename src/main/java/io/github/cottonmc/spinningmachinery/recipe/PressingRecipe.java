package io.github.cottonmc.spinningmachinery.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.JsonOps;
import net.minecraft.datafixers.NbtOps;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

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
        @Override
        public PressingRecipe read(Identifier id, JsonObject obj) {
            JsonElement ingredientJson =
                    JsonHelper.hasArray(obj, "input")
                            ? JsonHelper.getArray(obj, "input")
                            : JsonHelper.getObject(obj, "input");

            return new PressingRecipe(
                    id,
                    JsonHelper.getString(obj, "group", ""),
                    Ingredient.fromJson(ingredientJson),
                    readItemStack(obj.get("output"))
            );
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

        private static ItemStack readItemStack(JsonElement json) {
            if (json.isJsonPrimitive())
                return new ItemStack(
                        Registry.ITEM.getOrEmpty(
                                new Identifier(json.getAsString())
                        ).orElseThrow(() -> new IllegalStateException("Item " + json.getAsString() + " does not exist"))
                );
            else if (!json.isJsonObject())
                throw new JsonParseException("Invalid json input type for an item stack; must be a string or an object");
            else
                return ItemStack.fromTag((CompoundTag) Dynamic.convert(JsonOps.INSTANCE, NbtOps.INSTANCE, json));
        }
    }
}
