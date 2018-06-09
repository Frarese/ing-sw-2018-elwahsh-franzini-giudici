package it.polimi.se2018.view.tools;

import it.polimi.se2018.model.IntColorPair;

/**
 * Class to define GridViewCreator's Object
 *
 * @author Mathyas Giudici
 */

public abstract class GridViewCreator<E, V> {

    protected DieViewCreator dieViewCreator;

    protected IntColorPair[][] grid;

    protected IntColorPair[][] gridPattern;

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
    protected GridViewCreator(IntColorPair[][] grid, IntColorPair[][] gridPattern) {
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
    public synchronized IntColorPair[][] getGrid() {
        return grid;
    }

    /**
     * Setter for Grid
     *
     * @param grid contains the grid to set
     */
    public synchronized void setGrid(IntColorPair[][] grid) {
        this.grid = grid;
    }

    /**
     * Getter for Grid's pattern
     *
     * @return the grid's pattern
     */
    public synchronized IntColorPair[][] getGridPattern() {
        return gridPattern;
    }

    /**
     * Setter for Grid's pattern
     *
     * @param gridPattern contains the grid's pattern to set
     */
    public synchronized void setGridPattern(IntColorPair[][] gridPattern) {
        this.gridPattern = gridPattern;
    }
}
