package io.github.cottonmc.spinningmachinery.compat.rei;

import com.google.common.collect.ImmutableList;
import io.github.cottonmc.spinningmachinery.block.SpinningBlocks;
import io.github.cottonmc.spinningmachinery.block.entity.PlatePressBlockEntity;
import io.github.cottonmc.spinningmachinery.item.SpinningItems;
import me.shedaniel.rei.api.RecipeCategory;
import me.shedaniel.rei.api.Renderable;
import me.shedaniel.rei.api.Renderer;
import me.shedaniel.rei.gui.widget.RecipeBaseWidget;
import me.shedaniel.rei.gui.widget.SlotWidget;
import me.shedaniel.rei.gui.widget.Widget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.awt.Rectangle;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

final class HammeringCategory implements RecipeCategory<HammeringDisplay> {
    @Override
    public Identifier getIdentifier() {
        return SpinningREIPlugin.HAMMERING;
    }

    @Override
    public String getCategoryName() {
        return I18n.translate("gui.spinning-machinery.hammering.category");
    }

    @Override
    public Renderer getIcon() {
        return Renderable.fromItemStack(new ItemStack(SpinningItems.HAMMER));
    }

    @Override
    public List<Widget> setupDisplay(Supplier<HammeringDisplay> recipeDisplaySupplier, Rectangle bounds) {
        HammeringDisplay display = recipeDisplaySupplier.get();
        int x = (int) bounds.getX();
        int y = (int) bounds.getCenterY() - 9;

        return ImmutableList.of(
                new RecipeBaseWidget(bounds),
                new StaticArrowWidget(x + 3 * 18 - 3, y, 3 * 18 - 15, 18),
                new SlotWidget(x + 2 * 18 - 9, y - 9, Collections.singletonList(new ItemStack(SpinningItems.HAMMER)), false, true, true),
                new SlotWidget(x + 2 * 18 - 9, y + 9, display.getInput().get(0), false, true, true),
                SMSlotWidget.createBig(x + 6 * 18 - 9, y, Collections.singletonList(display.getOutput().get(0)), true, true)
        );
    }
}
