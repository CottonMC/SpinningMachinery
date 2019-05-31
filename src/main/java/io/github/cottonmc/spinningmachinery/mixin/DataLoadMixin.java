package io.github.cottonmc.spinningmachinery.mixin;

import io.github.cottonmc.spinningmachinery.internal.VirtualResourcePackCreator;
import net.minecraft.resource.ResourcePackContainer;
import net.minecraft.resource.ResourcePackContainerManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.LevelProperties;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;

@Mixin(MinecraftServer.class)
public class DataLoadMixin {
    @Shadow @Final private ResourcePackContainerManager<ResourcePackContainer> dataPackContainerManager;

    @Inject(method = "loadWorldDataPacks", at = @At("HEAD"))
    private void onLoadWorldDataPacks(File file, LevelProperties properties, CallbackInfo info) {
        dataPackContainerManager.addCreator(VirtualResourcePackCreator.INSTANCE);
    }
}
