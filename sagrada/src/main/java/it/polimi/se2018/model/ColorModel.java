package it.polimi.se2018.model;

import javafx.scene.paint.Color;

import java.util.HashMap;

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
     * @autor Mathyas Giudici
     * This function convert a ColorModel in a value useful for visual representation.
     * For now it will support JavaFX, next releases may use different standards
     * @return JavaFX version of my color
     */
    public Color toJavaFXColor(){
        HashMap<String,String> colorMap = new HashMap<>();
        colorMap.put("red","#D11E22");
        colorMap.put("blue","#48B0B3");
        colorMap.put("green","#32AA63");
        colorMap.put("yellow","#E4D806");
        colorMap.put("violet","#A02894");
        colorMap.put("white","#F0F0F0");

        return Color.valueOf(colorMap.get(toString()));
    }
}
