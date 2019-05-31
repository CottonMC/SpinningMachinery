package io.github.cottonmc.spinningmachinery.internal;

import net.minecraft.resource.ResourcePackContainer;
import net.minecraft.resource.ResourcePackCreator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public enum VirtualResourcePackCreator implements ResourcePackCreator {
    INSTANCE;

    private final List<VirtualResourcePack> packs = new ArrayList<>();

    @Override
    public <T extends ResourcePackContainer> void registerContainer(Map<String, T> map, ResourcePackContainer.Factory<T> factory) {
        int i = 0;
        for (VirtualResourcePack pack : packs) {
            String id = pack.getId(i++);
            T container = ResourcePackContainer.of(id, false, () -> pack, factory, ResourcePackContainer.InsertionPosition.TOP);
            if (container != null) {
                map.put(id, container);
            }
        }
    }

    public void addPack(VirtualResourcePack pack) {
        packs.add(pack);
    }
}
