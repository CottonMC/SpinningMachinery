package io.github.cottonmc.spinningmachinery.compat.rei;

import com.mojang.blaze3d.platform.GlStateManager;
import io.github.cottonmc.spinningmachinery.gui.Textures;
import me.shedaniel.rei.gui.widget.Widget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.render.GuiLighting;

import java.util.Collections;
import java.util.List;

final class StaticArrowWidget extends Widget {
    private final int x;
    private final int y;
    private final int width;
    private final int height;

    StaticArrowWidget(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        GuiLighting.disable();
        MinecraftClient.getInstance().getTextureManager().bindTexture(Textures.PROGRESS_BAR_BG);
        blit(x, y, 0, 0, width, height, 39, 18);
    }

    @Override
    public List<? extends Element> children() {
        return Collections.emptyList();
    }
}
