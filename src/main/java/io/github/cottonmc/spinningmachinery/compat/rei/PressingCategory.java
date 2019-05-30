package io.github.cottonmc.spinningmachinery.compat.rei;

import com.google.common.collect.ImmutableList;
import io.github.cottonmc.spinningmachinery.block.SpinningBlocks;
import io.github.cottonmc.spinningmachinery.block.entity.GrinderBlockEntity;
import io.github.cottonmc.spinningmachinery.recipe.GrindingRecipe;
import me.shedaniel.rei.api.RecipeCategory;
import me.shedaniel.rei.api.Renderable;
import me.shedaniel.rei.api.Renderer;
import me.shedaniel.rei.gui.widget.LabelWidget;
import me.shedaniel.rei.gui.widget.RecipeBaseWidget;
import me.shedaniel.rei.gui.widget.Widget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.awt.Rectangle;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

final class PressingCategory implements RecipeCategory<PressingDisplay> {
    @Override
    public Identifier getIdentifier() {
        return SpinningREIPlugin.PRESSING;
    }

    @Override
    public String getCategoryName() {
        return I18n.translate("gui.spinning-machinery.pressing.category");
    }

    @Override
    public Renderer getIcon() {
        return Renderable.fromItemStack(new ItemStack(SpinningBlocks.PLATE_PRESS));
    }

    @Override
    public List<Widget> setupDisplay(Supplier<PressingDisplay> recipeDisplaySupplier, Rectangle bounds) {
        PressingDisplay display = recipeDisplaySupplier.get();
        int x = (int) bounds.getX();
        int y = (int) bounds.getCenterY() - 9;

        return ImmutableList.of(
                new RecipeBaseWidget(bounds),
                new ProgressArrowWidget(x + 3 * 18 + 6, y, 3 * 18 - 15, 18, GrinderBlockEntity.MAX_PROGRESS),
                new SMSlotWidget(x + 2 * 18, y, display.getInput().get(0), true, true),
                SMSlotWidget.createBig(x + 6 * 18, y, Collections.singletonList(display.getOutput().get(0)), true, true)
        );
    }
}
