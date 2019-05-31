package io.github.cottonmc.spinningmachinery.config;

import me.sargunvohra.mcmods.autoconfig1.annotation.ConfigEntry;

public final class MachineGuiConfigContainer {
    @ConfigEntry.Gui.CollapsibleObject
    public MachineGuiConfig grinder = new MachineGuiConfig();
    @ConfigEntry.Gui.CollapsibleObject
    public MachineGuiConfig platePress = new MachineGuiConfig();

    MachineGuiConfigContainer() {}
}
