package io.github.cottonmc.spinningmachinery.block.entity;

import com.jamieswhiteshirt.clotheslinefabric.api.NetworkManagerProvider;
import com.jamieswhiteshirt.clotheslinefabric.api.NetworkNode;
import io.github.cottonmc.spinningmachinery.block.GrinderBlock;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Collection;

// TODO: Combine with AMBE?
public abstract class AbstractProcessorBlockEntity<I extends Inventory> extends AbstractMachineBlockEntity implements Tickable {
    public static final String NBT_PROGRESS = "Progress";
    public static final String NBT_ACTIVE = "Active";
    private final Collection<RecipeType<? extends Recipe<? super I>>> recipeTypes;

    protected int progress = 0;
    private boolean active = false;

    /**
     * The constructor.
     *
     * {@code items} should have at least the following slots:
     * <ul>
     *     <li>0: the input slot</li>
     *     <li>1: the primary output slot</li>
     * </ul>
     *
     * @param type the block entity type
     * @param items the inventory
     * @param recipeTypes the recipe types
     */
    protected AbstractProcessorBlockEntity(BlockEntityType<?> type, DefaultedList<ItemStack> items, Collection<RecipeType<? extends Recipe<? super I>>> recipeTypes) {
        super(type, items);
        this.recipeTypes = recipeTypes;
    }

    protected abstract int getMaxProgress();

    @Override
    public void fromTag(CompoundTag tag) {
        super.fromTag(tag);
        progress = tag.getInt(NBT_PROGRESS);
        active = tag.getBoolean(NBT_ACTIVE);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        tag.putInt(NBT_PROGRESS, progress);
        tag.putBoolean(NBT_ACTIVE, active);
        return super.toTag(tag);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void tick() {
        if (!world.isClient) {
            boolean oldActive = active;
            int momentum = getNetworkMomentum(world, pos.up());

            if (momentum != 0) {
                active = true;
                boolean anyFound = false;
                for (RecipeType<? extends Recipe<? super I>> recipeType : recipeTypes) {
                    Recipe<I> recipe = world.getRecipeManager()
                            .getFirstMatch((RecipeType<? extends Recipe<I>>) recipeType, (I) this, world)
                            .orElse(null);

                    if (recipe != null && !items.get(0).isEmpty()) {
                        // TODO: Tweak this
                        progress += Math.abs(momentum) / 5;

                        if (progress >= getMaxProgress()) {
                            if (canAcceptRecipeOutput(recipe)) {
                                progress = 0;
                                insertIntoSlot(1, recipe.craft((I) this));
                                items.get(0).subtractAmount(1);
                            } else {
                                progress = getMaxProgress();
                            }
                        }

                        anyFound = true;
                        markDirty();
                        break;
                    }
                }

                if (!anyFound) {
                    progress = 0;
                    markDirty();
                }
            } else {
                active = false;
            }

            if (active != oldActive) {
                updateActive(active);
                world.setBlockState(pos, world.getBlockState(pos).with(GrinderBlock.ACTIVE, active));
                markDirty();
            }
        }
    }

    protected boolean canAcceptRecipeOutput(Recipe<?> recipe) {
        if (!items.get(0).isEmpty()) {
            return canInsertIntoSlot(1, recipe.getOutput());
        } else {
            return false;
        }
    }

    protected boolean canInsertIntoSlot(int slot, ItemStack stack) {
        ItemStack current = items.get(slot);

        if (stack.isEmpty() || current.isEmpty()) {
            return true;
        } else if (!current.isEqualIgnoreTags(stack)) {
            return false;
        } else {
            int combinedAmount = current.getAmount() + stack.getAmount();
            return combinedAmount < getInvMaxStackAmount() && combinedAmount < current.getMaxAmount();
        }
    }

    protected void insertIntoSlot(int slot, ItemStack stack) {
        ItemStack current = items.get(slot);
        if (current.isEmpty()) {
            items.set(slot, stack);
        } else {
            current.addAmount(stack.getAmount());
        }
        markDirty();
    }

    protected abstract void updateActive(boolean newActive);

    @Override
    protected void validateProgress(int slot, ItemStack stack) {
        if (slot == 0) {
            ItemStack current = getInvStack(slot);
            boolean canContinueProcessing = !stack.isEmpty() && stack.isEqualIgnoreDurability(current) && ItemStack.areTagsEqual(stack, current);
            if (!canContinueProcessing) {
                progress = 0;
                markDirty();
            }
        }
    }

    private static int getNetworkMomentum(World world, BlockPos pos) {
        NetworkNode node = ((NetworkManagerProvider) world).getNetworkManager().getNetworks().getNodes().get(pos);
        return node != null ? node.getNetwork().getState().getMomentum() : 0;
    }
}
