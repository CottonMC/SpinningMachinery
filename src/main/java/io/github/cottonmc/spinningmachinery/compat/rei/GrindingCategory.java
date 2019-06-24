package io.github.cottonmc.spinningmachinery.compat.rei;

import com.google.common.collect.ImmutableList;
import io.github.cottonmc.spinningmachinery.block.SpinningBlocks;
import io.github.cottonmc.spinningmachinery.block.entity.GrinderBlockEntity;
import io.github.cottonmc.spinningmachinery.compat.rei.widget.InfoWidget;
import io.github.cottonmc.spinningmachinery.compat.rei.widget.ProgressArrowWidget;
import io.github.cottonmc.spinningmachinery.compat.rei.widget.SMSlotWidget;
import io.github.cottonmc.spinningmachinery.recipe.GrindingRecipe;
import me.shedaniel.rei.api.RecipeCategory;
import me.shedaniel.rei.api.Renderable;
import me.shedaniel.rei.api.Renderer;
import me.shedaniel.rei.gui.widget.LabelWidget;
import me.shedaniel.rei.gui.widget.RecipeBaseWidget;
import me.shedaniel.rei.gui.widget.Widget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Identifier;

import java.awt.Rectangle;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

final class GrindingCategory implements RecipeCategory<GrindingDisplay> {
    @Override
    public Identifier getIdentifier() {
        return SpinningREIPlugin.GRINDING;
    }

    @Override
    public String getCategoryName() {
        return I18n.translate("gui.spinning-machinery.grinding.category");
    }

    @Override
    public Renderer getIcon() {
        return Renderable.fromItemStack(new ItemStack(SpinningBlocks.GRINDER));
    }

    @Override
    public List<Widget> setupDisplay(Supplier<GrindingDisplay> recipeDisplaySupplier, Rectangle bounds) {
        GrindingDisplay display = recipeDisplaySupplier.get();
        int x = (int) bounds.getX();
        int y = (int) bounds.getCenterY() - 9;

        List<ItemStack> bonusStacks = display.getBonusStacks();

        int bonusChance = display.getRecipe()
                .map(recipe -> (int) (display.getBonusChance() * 100.0))
                .orElse(0);

        ImmutableList.Builder<Widget> builder = ImmutableList.<Widget>builder()
                .add(new RecipeBaseWidget(bounds))
                .add(new ProgressArrowWidget(x + 2 * 18 + 6, y, 3 * 18 - 15, 18, GrinderBlockEntity.MAX_PROGRESS))
                .add(new SMSlotWidget(x + 18, y, display.getInput().get(0), true, true))
                .add(SMSlotWidget.createBig(x + 5 * 18, y, Collections.singletonList(display.getOutput().get(0)), true, true))
                .add(new SMSlotWidget(x + 6 * 18 + 10, y, bonusStacks, true, true))
                .add(new LabelWidget(
                        x + 7 * 18, y + 18,
                        bonusChance != 0
                                ? I18n.translate("gui.spinning-machinery.grinding.bonus_chance_format", bonusChance)
                                : ""
                ));

        if (display.getSourceMod() != null) {
            builder.add(new InfoWidget(
                    x + 4, bounds.y + 4, 12, 12,
                    new TranslatableComponent("gui.spinning-machinery.grinding.from_source", display.getSourceMod())
            ));
        }

        return builder.build();
    }
}
