package org.super89.supermegamod.magic;

public enum ShieldType {
    WOODEN(1, 2),
    IRON(2, 4),
    DIAMOND(3, 6);
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