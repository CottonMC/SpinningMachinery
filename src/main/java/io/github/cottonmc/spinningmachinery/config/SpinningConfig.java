package io.github.cottonmc.spinningmachinery.config;

import io.github.cottonmc.cotton.config.ConfigManager;
import io.github.cottonmc.cotton.config.annotations.ConfigFile;
import io.github.cottonmc.repackage.blue.endless.jankson.Comment;

@ConfigFile(name = SpinningConfig.NAME)
public final class SpinningConfig {
    public static final String NAME = "SpinningMachinery";

    @Comment("The settings for the machine screens.")
    public MachineGuiConfigContainer machineScreens = new MachineGuiConfigContainer();

    public static SpinningConfig getInstance() {
        return ConfigManager.loadConfig(SpinningConfig.class);
    }
}
