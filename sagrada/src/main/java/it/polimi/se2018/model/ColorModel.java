package it.polimi.se2018.model;

import javafx.scene.paint.Color;

/**
 * This enumeration represents all the possible colors visible in game
 */
public enum ColorModel {

    /**
     *  All colors except white are for both dice and patterns, while white is just for patterns
     */
    RED, BLUE, GREEN, YELLOW, VIOLET, WHITE;

    /**
     * Simple toString function for CLI and log purposes
     * @return lowercase color name
     */
    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }

    /**
     * This function convert a ColorModel in a value useful for visual representation.
     * For now it will support JavaFX, next releases may use different standards
     * @return JavaFX version of my color
     */
    public Color toJavaFXColor()
    {
        return Color.valueOf(toString());
    }
}
