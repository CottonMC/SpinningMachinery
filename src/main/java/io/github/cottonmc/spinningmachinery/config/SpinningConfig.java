package io.github.cottonmc.spinningmachinery.config;

import blue.endless.jankson.Comment;
import io.github.cottonmc.cotton.config.ConfigManager;
import io.github.cottonmc.cotton.config.annotations.ConfigFile;

@ConfigFile(name = SpinningConfig.NAME)
public final class SpinningConfig {
    public static final String NAME = "SpinningMachinery";

    @Comment("The settings for the machine screens.")
    public MachineGuiConfigContainer machineScreens = new MachineGuiConfigContainer();

    public static SpinningConfig getInstance() {
        return ConfigManager.loadConfig(SpinningConfig.class);
    }
}
