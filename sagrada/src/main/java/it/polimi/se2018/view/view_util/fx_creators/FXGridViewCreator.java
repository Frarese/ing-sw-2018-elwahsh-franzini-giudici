package it.polimi.se2018.view.view_util.fx_creators;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.util.Pair;
import it.polimi.se2018.view.view_util.GridViewCreator;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

/**
 * Class to create and to handle grid in GUI
 *
 * @author Mathyas Giudici
 */

public class FXGridViewCreator extends GridViewCreator<GridPane,Image> {

    /**
     * Class constructor
     * @param grid the grid to use
     * @param gridPattern the pattern to use
     */
    public FXGridViewCreator(Pair<Integer, ColorModel>[][] grid, Pair<Integer, ColorModel>[][] gridPattern) {
        super(grid, gridPattern);
        this.dieViewCreator = new FXDieViewCreator();
    }

    @Override
    public GridPane display() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addADie(Image die, int height, int width) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Image pickDie(int height, int width) {
        throw new UnsupportedOperationException();
    }
}
