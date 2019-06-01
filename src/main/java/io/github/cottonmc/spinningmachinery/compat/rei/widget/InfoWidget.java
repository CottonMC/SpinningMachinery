package io.github.cottonmc.spinningmachinery.compat.rei.widget;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.platform.GlStateManager;
import me.shedaniel.rei.client.ScreenHelper;
import me.shedaniel.rei.gui.widget.HighlightableWidget;
import me.shedaniel.rei.gui.widget.QueuedTooltip;
import net.minecraft.ChatFormat;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.network.chat.Component;

import java.awt.Rectangle;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class InfoWidget extends HighlightableWidget {
    private final Rectangle bounds;
    private final int x;
    private final int y;
    private final int width;
    private final List<Component> tooltip;

    public InfoWidget(int x, int y, int width, int height, List<Component> tooltip) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.tooltip = tooltip;
        bounds = new Rectangle(x, y, width, height);
    }

    public InfoWidget(int x, int y, int width, int height, Component... tooltip) {
        this(x, y, width, height, ImmutableList.copyOf(tooltip));
    }

    private static List<String> textComponentsToStrings(Collection<Component> tooltip) {
        return tooltip.stream()
                .map(Component::getFormattedText)
                .collect(Collectors.toList());
    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }

    @Override
    public void render(int mouseX, int mouseY, float v) {
        GlStateManager.color4f(1f, 1f, 1f, 1f);
        drawCenteredString(
                MinecraftClient.getInstance().textRenderer,
                ChatFormat.BOLD + I18n.translate("gui.spinning-machinery.info_symbol"),
                x + width / 2, y + 2, 0xFFFFFFFF
        );
        if (isHighlighted(mouseX, mouseY)) {
            ScreenHelper.getLastOverlay().addTooltip(QueuedTooltip.create(textComponentsToStrings(tooltip)));
        }
    }

    @Override
    public List<? extends Element> children() {
        return Collections.emptyList();
    }
}
