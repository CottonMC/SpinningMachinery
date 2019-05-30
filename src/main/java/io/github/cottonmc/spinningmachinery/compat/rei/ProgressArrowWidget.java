package io.github.cottonmc.spinningmachinery.compat.rei;

import io.github.cottonmc.cotton.gui.widget.WBar;
import io.github.cottonmc.spinningmachinery.gui.Textures;
import me.shedaniel.rei.gui.widget.Widget;
import net.minecraft.client.gui.Element;
import net.minecraft.client.render.GuiLighting;
import net.minecraft.container.PropertyDelegate;

import java.util.Collections;
import java.util.List;

final class ProgressArrowWidget extends Widget {
    private final int x;
    private final int y;
    private final WBar renderBar;

    ProgressArrowWidget(int x, int y, int width, int height, int maxProgress) {
        this.x = x;
        this.y = y;
        PropertyDelegate renderProperties = new PropertyDelegate() {
            @Override
            public int get(int i) {
                if (i == 0)
                    return ((int) System.currentTimeMillis() / 2) % maxProgress;
                else
                    return maxProgress;
            }

            @Override
            public void set(int i, int value) {
            }

            @Override
            public int size() {
                return 2;
            }
        };
        renderBar = new RenderWBar(Textures.PROGRESS_BAR_BG, Textures.PROGRESS_BAR, 0, 1, WBar.Direction.RIGHT, renderProperties);
        renderBar.setSize(width, height);
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        GuiLighting.disable();
        renderBar.paintBackground(x, y);
    }

    @Override
    public List<? extends Element> children() {
        return Collections.emptyList();
    }
}
