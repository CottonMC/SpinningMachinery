package io.github.cottonmc.spinningmachinery.item;

import io.github.cottonmc.spinningmachinery.recipe.HammeringInventory;
import io.github.cottonmc.spinningmachinery.recipe.SpinningRecipes;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.recipe.Recipe;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class HammerItem extends Item {
    public HammerItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        HammeringInventory inv = new HammeringInventory(new CachedBlockPosition(world, pos, false));

        Recipe<HammeringInventory> recipe = world.getRecipeManager()
                .getFirstMatch(SpinningRecipes.HAMMERING, inv, world)
                .orElse(null);

        if (recipe != null) {
            if (!world.isClient) {
                world.breakBlock(pos, false);
                ItemScatterer.spawn(world, pos, DefaultedList.create(ItemStack.EMPTY, recipe.craft(inv)));

                PlayerEntity player = context.getPlayer();

                if (player != null && !player.isCreative() && player instanceof ServerPlayerEntity) {
                    context.getItemStack().applyDamage(1, world.random, (ServerPlayerEntity) player);
                }
            }

            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }
}
