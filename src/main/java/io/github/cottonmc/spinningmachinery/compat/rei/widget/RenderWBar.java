package io.github.cottonmc.spinningmachinery.compat.rei.widget;

import io.github.cottonmc.cotton.gui.widget.WBar;
import net.minecraft.container.PropertyDelegate;
import net.minecraft.util.Identifier;

final class RenderWBar extends WBar {
    RenderWBar(Identifier bg, Identifier bar, int field, int maxfield, Direction dir, PropertyDelegate properties) {
        super(bg, bar, field, maxfield, dir);
        this.properties = properties;
    }
}
