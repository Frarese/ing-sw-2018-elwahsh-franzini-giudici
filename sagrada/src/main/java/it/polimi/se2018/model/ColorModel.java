package it.polimi.se2018.model;

import javafx.scene.paint.Color;

import java.util.HashMap;

/**
 * This enumeration represents all the possible colors visible in game
 *
 * @author Alì El Wahsh &amp; Mathyas Giudici
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
        return super.toString().toUpperCase();
    }

    /**
     * This function converts a ColorModel into a value useful for visual representation.
     * For now it supports JavaFX, next releases may use different standards
     * @return JavaFX version of my color
     */
    public Color toJavaFXColor(){
        HashMap<String,String> colorMap = new HashMap<>();
        colorMap.put("RED","#D11E22");
        colorMap.put("BLUE","#48B0B3");
        colorMap.put("GREEN","#32AA63");
        colorMap.put("YELLOW","#E4D806");
        colorMap.put("VIOLET","#A02894");
        colorMap.put("WHITE","#F0F0F0");

        return Color.valueOf(colorMap.get(toString()));
    }
}
