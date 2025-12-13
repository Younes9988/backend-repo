package com.example.mslivre.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Category {
    // 1. Define the constants with a value in parentheses
    LITTERATURE_ET_FICTION("Littérature et fiction"),
    SCIENCES_ET_TECHNIQUES("Sciences et techniques"),
    HISTOIRE_ET_GEOGRAPHIE("Histoire et géographie"),
    ARTS_ET_CULTURE("Arts et culture"),
    LOISIRS_ET_PRATIQUES("Loisirs et pratiques"),
    REFERENCES_ET_DICTIONNAIRES("Références et dictionnaires"),
    RELIGION_ET_PHILOSOPHIE("Religion et philosophie"),
    BANDE_DESSINEE_ET_MANGAS("Bande dessinée et mangas");

    // 2. Define a field to hold the pretty string
    private final String displayName;

    // 3. Create a constructor to assign the value
    Category(String displayName) {
        this.displayName = displayName;
    }

    // 4. Create a getter
    // Adding @JsonValue tells Spring Boot to send "Littérature et fiction"
    // in the JSON response instead of "LITTERATURE_ET_FICTION"
    @JsonValue
    public String getDisplayName() {
        return displayName;
    }
}
