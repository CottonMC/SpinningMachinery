package io.github.cottonmc.spinningmachinery.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.JsonOps;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.command.arguments.BlockStateArgument;
import net.minecraft.command.arguments.BlockStateArgumentType;
import net.minecraft.datafixers.NbtOps;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.state.property.Property;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.stream.Collectors;

public final class HammeringRecipe implements Recipe<HammeringInventory> {
    private final Identifier id;
    private final String group;
    private final BlockStateArgument input;
    private final ItemStack output;

    public HammeringRecipe(Identifier id, String group, BlockStateArgument input, ItemStack output) {
        this.input = input;
        this.output = output;
        this.group = group;
        this.id = id;
    }

    @Override
    public boolean matches(HammeringInventory inventory, World world) {
        return inventory.matches(input);
    }

    @Override
    public ItemStack craft(HammeringInventory inventory) {
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
        return SpinningRecipes.HAMMERING;
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SpinningRecipes.HAMMERING_SERIALIZER;
    }

    @Override
    public String getGroup() {
        return group;
    }

    @Override
    public DefaultedList<Ingredient> getPreviewInputs() {
        DefaultedList<Ingredient> list = DefaultedList.create();
        list.add(Ingredient.ofItems(input.getBlockState().getBlock()));
        return list;
    }

    public static final class Serializer implements RecipeSerializer<HammeringRecipe> {
        private static final BlockStateArgumentType BLOCK_STATE_ARGUMENT_TYPE = BlockStateArgumentType.create();

        @Override
        public HammeringRecipe read(Identifier id, JsonObject obj) {
            try {
                return new HammeringRecipe(
                        id,
                        JsonHelper.getString(obj, "group", ""),
                        BLOCK_STATE_ARGUMENT_TYPE.parse(new StringReader(JsonHelper.getString(obj, "input"))),
                        readItemStack(obj.get("output"))
                );
            } catch (CommandSyntaxException e) {
                throw new JsonParseException("Error while parsing hammering recipe", e);
            }
        }

        @Override
        public HammeringRecipe read(Identifier id, PacketByteBuf buf) {
            try {
                return new HammeringRecipe(
                        id,
                        buf.readString(32767),
                        BLOCK_STATE_ARGUMENT_TYPE.parse(new StringReader(buf.readString(32767))),
                        buf.readItemStack()
                );
            } catch (CommandSyntaxException e) {
                throw new RuntimeException("Error while loading hammering recipe from packet", e);
            }
        }

        @Override
        public void write(PacketByteBuf buf, HammeringRecipe recipe) {
            buf.writeString(recipe.group);
            buf.writeString(blockStateToString(recipe.input));
            buf.writeItemStack(recipe.output);
        }

        @SuppressWarnings("unchecked")
        private static String blockStateToString(BlockStateArgument state) {
            StringBuilder builder = new StringBuilder();

            builder.append(Registry.BLOCK.getId(state.getBlockState().getBlock()));
            builder.append('[');

            builder.append(
                    state.getBlockState().getEntries().entrySet().stream()
                            .map(entry -> String.format(
                                    "%s=%s",
                                    entry.getKey().getName(),
                                    ((Property) entry.getKey()).getValueAsString(entry.getValue())
                            ))
                            .collect(Collectors.joining(","))
            );

            builder.append(']');

            return builder.toString();
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
