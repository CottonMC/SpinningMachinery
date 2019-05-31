package io.github.cottonmc.spinningmachinery.config;

import io.github.cottonmc.cotton.config.ConfigManager;
import io.github.cottonmc.cotton.config.annotations.ConfigFile;
import io.github.cottonmc.repackage.blue.endless.jankson.Comment;
import io.github.cottonmc.spinningmachinery.compat.autoconfig.SpinningAutoConfig;
import me.sargunvohra.mcmods.autoconfig1.annotation.ConfigEntry;
import net.fabricmc.loader.api.FabricLoader;

@ConfigFile(name = SpinningConfig.NAME)
public class SpinningConfig {
    public static final String NAME = "SpinningMachinery";

    @ConfigEntry.Gui.CollapsibleObject
    @Comment("The settings for the machine screens.")
    public MachineGuiConfigContainer machineScreens;

    public static SpinningConfig getInstance() {
        if (FabricLoader.getInstance().isModLoaded("autoconfig1"))
            return SpinningAutoConfig.getInstance();
        else
            return ConfigManager.loadConfig(SpinningConfig.class);
    }
}
