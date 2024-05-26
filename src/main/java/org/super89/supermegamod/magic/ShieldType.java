package org.super89.supermegamod.magic;

public enum ShieldType {
    NETHERITE(1020, 12),
    IRON(1021, 7);
    // Здесь могла быть ваша реклама

    private final int modelData;
    private final int damageReduction;

    ShieldType(int modelData, int damageReduction) {
        this.modelData = modelData;
        this.damageReduction = damageReduction;
    }

    public int getModelData() {
        return modelData;
    }

    public int getDamageReduction() {
        return damageReduction;
    }


    public static ShieldType getShieldType(int modelData) {
        for (ShieldType type : values()) {
            if (type.getModelData() == modelData) {
                return type;
            }
        }
        return null;
    }
}