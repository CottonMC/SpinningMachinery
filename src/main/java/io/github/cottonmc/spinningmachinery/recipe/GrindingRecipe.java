package io.github.cottonmc.spinningmachinery.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.*;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.Optional;

public final class GrindingRecipe implements Recipe<GrindingInventory> {
    private final Identifier id;
    private final String group;
    private final Ingredient input;
    private final ItemStack primaryOutput;
    @Nullable
    private final ItemStack bonus;
    private final float bonusChance;
    @Nullable
    private final String sourceMod;

    public GrindingRecipe(Identifier id, String group, Ingredient input, ItemStack primaryOutput,
                          @Nullable ItemStack bonus, float bonusChance, @Nullable String sourceMod) {
        if (bonusChance < 0f || bonusChance > 1f) {
            throw new IllegalArgumentException("bonusChance must be between 0.0 and 1.0");
        }

        this.input = input;
        this.primaryOutput = primaryOutput;
        this.bonus = bonus == null || bonus.isEmpty() ? null : bonus;
        this.bonusChance = bonusChance;
        this.group = group;
        this.id = id;
        this.sourceMod = sourceMod;
    }

    @Override
    public boolean matches(GrindingInventory inventory, World world) {
        return input.test(inventory.getInvStack(0));
    }

    @Override
    public ItemStack craft(GrindingInventory inventory) {
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

    @Nullable
    public String getSourceMod() {
        return sourceMod;
    }

    public static final class Serializer implements RecipeSerializer<GrindingRecipe> {
        private static final Logger LOGGER = LogManager.getLogger();

        @Override
        public GrindingRecipe read(Identifier id, JsonObject obj) {
            try {
                JsonElement ingredientJson =
                        JsonHelper.hasArray(obj, "input")
                                ? JsonHelper.getArray(obj, "input")
                                : JsonHelper.getObject(obj, "input");

                return new GrindingRecipe(
                        id,
                        JsonHelper.getString(obj, "group", ""),
                        Ingredient.fromJson(ingredientJson),
                        ShapedRecipe.getItemStack(obj.getAsJsonObject("primary_output")),
                        obj.has("bonus") ? ShapedRecipe.getItemStack(obj.getAsJsonObject("bonus")) : null,
                        JsonHelper.getFloat(obj, "bonus_chance", 0f),
                        JsonHelper.getString(obj, "source_mod", null)
                );
            } catch (Exception e) {
                LOGGER.error("Failed to load grinding recipe " + id, e);
                throw new RuntimeException(e);
            }
        }

        @Override
        public GrindingRecipe read(Identifier id, PacketByteBuf buf) {
            return new GrindingRecipe(
                    id,
                    buf.readString(32767),
                    Ingredient.fromPacket(buf),
                    buf.readItemStack(),
                    buf.readItemStack(),
                    buf.readFloat(),
                    buf.readString(32767));
        }

        @Override
        public void write(PacketByteBuf buf, GrindingRecipe recipe) {
            buf.writeString(recipe.group);
            recipe.input.write(buf);
            buf.writeItemStack(recipe.primaryOutput);
            buf.writeItemStack(recipe.getBonusOrEmpty());
            buf.writeFloat(recipe.bonusChance);
            buf.writeString(recipe.sourceMod != null ? recipe.sourceMod : "");
        }
    }
}
