package com.gerardo.thrive.exercise.enums;

import lombok.Getter;

@Getter
public enum MuscleGroup {
    CHEST("Chest"),
    BACK("Back"),
    SHOULDERS("Shoulders"),
    BICEPS("Biceps"),
    TRICEPS("Triceps"),
    ABS("Abs"),
    FOREARMS("Forearms");

    private final String displayName;

    MuscleGroup(String displayName) {
        this.displayName = displayName;
    }

}
