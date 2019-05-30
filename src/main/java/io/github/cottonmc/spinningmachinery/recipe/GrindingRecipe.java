package io.github.cottonmc.spinningmachinery.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.JsonOps;
import net.minecraft.datafixers.NbtOps;
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

import javax.annotation.Nullable;
import java.util.Optional;

public final class GrindingRecipe implements Recipe<ProcessingInventory> {
    private final Identifier id;
    private final String group;
    private final Ingredient input;
    private final ItemStack primaryOutput;
    @Nullable
    private final ItemStack bonus;
    private final float bonusChance;

    public GrindingRecipe(Identifier id, String group, Ingredient input, ItemStack primaryOutput,
                          @Nullable ItemStack bonus, float bonusChance) {
        if (bonusChance < 0f || bonusChance > 1f) {
            throw new IllegalArgumentException("bonusChance must be between 0.0 and 1.0");
        }

        this.input = input;
        this.primaryOutput = primaryOutput;
        this.bonus = bonus == null || bonus.isEmpty() ? null : bonus;
        this.bonusChance = bonusChance;
        this.group = group;
        this.id = id;
    }

    @Override
    public boolean matches(ProcessingInventory inventory, World world) {
        return input.test(inventory.getInvStack(0));
    }

    @Override
    public ItemStack craft(ProcessingInventory inventory) {
        if (Math.random() < bonusChance) {
            inventory.insertProcessingBonus(getBonusOrEmpty().copy());
        }

        return primaryOutput.copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getOutput() {
        return primaryOutput;
    }

    @Override
    public RecipeType<?> getType() {
        return SpinningRecipes.GRINDING;
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SpinningRecipes.GRINDING_SERIALIZER;
    }

    @Override
    public String getGroup() {
        return group;
    }

    public Optional<ItemStack> getBonus() {
        return Optional.ofNullable(bonus);
    }

    private ItemStack getBonusOrEmpty() {
        return bonus != null ? bonus : ItemStack.EMPTY;
    }

    public double getBonusChance() {
        return bonusChance;
    }

    @Override
    public DefaultedList<Ingredient> getPreviewInputs() {
        DefaultedList<Ingredient> list = DefaultedList.create();
        list.add(input);
        return list;
    }

    public static final class Serializer implements RecipeSerializer<GrindingRecipe> {
        @Override
        public GrindingRecipe read(Identifier id, JsonObject obj) {
            JsonElement ingredientJson =
                    JsonHelper.hasArray(obj, "input")
                            ? JsonHelper.getArray(obj, "input")
                            : JsonHelper.getObject(obj, "input");

            return new GrindingRecipe(
                    id,
                    JsonHelper.getString(obj, "group", ""),
                    Ingredient.fromJson(ingredientJson),
                    readItemStack(obj.get("primary_output")),
                    obj.has("bonus") ? readItemStack(obj.get("bonus")) : null,
                    JsonHelper.getFloat(obj, "bonus_chance", 0f)
            );
        }

        @Override
        public GrindingRecipe read(Identifier id, PacketByteBuf buf) {
            return new GrindingRecipe(
                    id,
                    buf.readString(32767),
                    Ingredient.fromPacket(buf),
                    buf.readItemStack(),
                    buf.readItemStack(),
                    buf.readFloat()
            );
        }

        @Override
        public void write(PacketByteBuf buf, GrindingRecipe recipe) {
            buf.writeString(recipe.group);
            recipe.input.write(buf);
            buf.writeItemStack(recipe.primaryOutput);
            buf.writeItemStack(recipe.getBonusOrEmpty());
            buf.writeFloat(recipe.bonusChance);
        }

        private static ItemStack readItemStack(JsonElement json) {
            if (json.isJsonPrimitive())
                return new ItemStack(
                        Registry.ITEM.getOrEmpty(
                                new Identifier(json.getAsString())
                        ).orElseThrow(() -> new IllegalStateException("Item " + json.getAsString() + " does not exist"))
                );
            else if (!json.isJsonObject())
                throw new IllegalArgumentException("Invalid json input type for an item stack; must be a string or an object");
            else
                return ItemStack.fromTag((CompoundTag) Dynamic.convert(JsonOps.INSTANCE, NbtOps.INSTANCE, json));
        }
    }
}
