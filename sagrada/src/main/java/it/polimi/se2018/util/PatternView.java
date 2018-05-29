package it.polimi.se2018.util;

import it.polimi.se2018.model.ColorModel;

/**
 * Simple class that contains window's pattern properties
 *
 * @author Mathyas Giudici
 */

public class PatternView {

    public final String patternName;
    public final int favours;
    public final Pair<Integer, ColorModel>[][] template;

    /**
     * Class constructor
     *
     * @param patternName contains the window's pattern name
     * @param favours     contains the window's pattern favour points
     * @param template    contains the window's pattern grid
     */
    public PatternView(String patternName, int favours, Pair<Integer, ColorModel>[][] template) {
        this.patternName = patternName;
        this.favours = favours;
        this.template = template;
    }
}
