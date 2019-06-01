package io.github.cottonmc.spinningmachinery.compat.rei.widget;

import io.github.cottonmc.cotton.gui.EmptyInventory;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import me.shedaniel.rei.gui.widget.SlotWidget;
import net.minecraft.item.ItemStack;

import java.util.List;

public final class SMSlotWidget extends SlotWidget {
    private final WItemSlot renderSlot;
    private final int x;
    private final int y;

    private SMSlotWidget(int x, int y, List<ItemStack> itemList, boolean showToolTips, boolean clickToMoreRecipes, boolean big) {
        super(x, y, itemList, false, showToolTips, clickToMoreRecipes);
        renderSlot = new WItemSlot(EmptyInventory.INSTANCE, 0, 1, 1, big, false);
        this.x = x;
        this.y = y;
    }

    public SMSlotWidget(int x, int y, List<ItemStack> itemList, boolean showToolTips, boolean clickToMoreRecipes) {
        this(x, y, itemList, showToolTips, clickToMoreRecipes, false);
    }

    public static SMSlotWidget createBig(int x, int y, List<ItemStack> itemList, boolean showToolTips, boolean clickToMoreRecipes) {
        return new SMSlotWidget(x, y, itemList, showToolTips, clickToMoreRecipes, true);
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        renderSlot.paintBackground(x, y);
        super.render(mouseX, mouseY, delta);
    }
}
