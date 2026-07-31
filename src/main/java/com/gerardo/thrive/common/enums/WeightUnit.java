package com.gerardo.thrive.common.enums;

import lombok.Getter;

@Getter
public enum WeightUnit {
    KG("Kilograms"),
    LBS("Pounds");

    private final String displayName;

    WeightUnit(String displayName) {
        this.displayName = displayName;
    }

}
