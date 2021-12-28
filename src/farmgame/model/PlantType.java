package farmgame.model;

import javafx.scene.paint.Color;

public enum PlantType {
    TOMATO(Color.rgb(255, 0, 0), 100),
    CORN(Color.rgb(255, 255, 0), 90),
    WHEAT(Color.WHEAT, 75);

    private Color color;
    private int value;
    PlantType(Color color, int value) {
        this.color = color;
        this.value = value;
    }

    public Color getColor() {
        return color;
    }

    public int getValue() {
        return value;
    }

    public static PlantType fromString(String s) {
        switch (s) {
        case "Tomato":
            return TOMATO;
        case "Corn":
            return CORN;
        case "Wheat":
            return WHEAT;
        default:
            throw new IllegalArgumentException(
                    "No plant type matching string: \"" + s + "\""
            );
        }
    }

    public String toString() {
        String name = name();
        if (name.length() <= 1) {
            return name.toUpperCase();
        } else {
            return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
        }
    }
}
