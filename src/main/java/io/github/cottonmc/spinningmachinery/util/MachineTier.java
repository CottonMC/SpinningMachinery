package io.github.cottonmc.spinningmachinery.util;

public enum MachineTier {
    PRIMITIVE("primitive", 0.5f),
    INDUSTRIAL("industrial", 1f);

    private final String id;
    private final float speedMultiplier;

    MachineTier(String id, float speedMultiplier) {
        this.id = id;
        this.speedMultiplier = speedMultiplier;
    }

    public String getId() {
        return id;
    }

    public float getSpeedMultiplier() {
        return speedMultiplier;
    }

    @Override
    public String toString() {
        return "MachineTier: " + id;
    }
}
