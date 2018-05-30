package it.polimi.se2018.view.tools;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.util.Pair;

/**
 * Class to define GridViewCreator's Object
 *
 * @author Mathyas Giudici
 */

public abstract class GridViewCreator<E, V> {

    protected DieViewCreator dieViewCreator;

    protected Pair<Integer, ColorModel>[][] grid;

    protected Pair<Integer, ColorModel>[][] gridPattern;

    /**
     * Basic Class constructor that initializes elements at default value
     */
    protected GridViewCreator() {
        this.grid = null;
        this.gridPattern = null;
    }

    /**
     * Class constructor
     *
     * @param grid        contains user grid
     * @param gridPattern contains user pattern
     */
    protected GridViewCreator(Pair<Integer, ColorModel>[][] grid, Pair<Integer, ColorModel>[][] gridPattern) {
        this.grid = grid;
        this.gridPattern = gridPattern;
    }

    /**
     * Use to show the current grid
     *
     * @return grid
     */
    public abstract E display();

    /**
     * Use to add a die in the grid
     *
     * @param die    contains the die to add
     * @param height contains the height position on the grid
     * @param width  contains the width position on the grid
     */
    public abstract void addADie(V die, int height, int width);

    /**
     * Use to pick a die from the grid
     *
     * @param height contains the height position in the grid
     * @param width  contains the width position in the grid
     * @return die
     */
    public abstract V pickDie(int height, int width);

    /**
     * Getter for Grid
     *
     * @return the grid
     */
    public synchronized Pair<Integer, ColorModel>[][] getGrid() {
        return grid;
    }

    /**
     * Setter for Grid
     *
     * @param grid contains the grid to set
     */
    public synchronized void setGrid(Pair<Integer, ColorModel>[][] grid) {
        this.grid = grid;
    }

    /**
     * Getter for Grid's pattern
     *
     * @return the grid's pattern
     */
    public synchronized Pair<Integer, ColorModel>[][] getGridPattern() {
        return gridPattern;
    }

    /**
     * Setter for Grid's pattern
     *
     * @param gridPattern contains the grid's pattern to set
     */
    public synchronized void setGridPattern(Pair<Integer, ColorModel>[][] gridPattern) {
        this.gridPattern = gridPattern;
    }
}
