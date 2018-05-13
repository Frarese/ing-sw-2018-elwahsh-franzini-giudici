package it.polimi.se2018.view.view_util;

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
     * Class constructor
     *
     * @param grid        contains user grid
     * @param gridPattern contains user pattern
     */
    public GridViewCreator(Pair<Integer, ColorModel>[][] grid, Pair<Integer, ColorModel>[][] gridPattern) {
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
    public Pair<Integer, ColorModel>[][] getGrid() {
        return grid;
    }

    /**
     * Setter for Grid
     *
     * @param grid contains the grid to set
     */
    public void setGrid(Pair<Integer, ColorModel>[][] grid) {
        this.grid = grid;
    }
}
