package io.github.cottonmc.spinningmachinery.config;

import io.github.cottonmc.repackage.blue.endless.jankson.Comment;

public final class MachineGuiConfig {
    @Comment("If true, overrides the default screen settings for this machine")
    public boolean enabled = false;

    @Comment("The screen background color as a RBG hexadecimal number (ignoring transparency/alpha)")
    public int backgroundColor = 0xC6C6C6;

    MachineGuiConfig() {}
}
