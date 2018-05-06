package it.polimi.se2018.view.view_util.fx_creators;

import it.polimi.se2018.util.Pair;
import it.polimi.se2018.view.view_util.GridViewCreator;
import javafx.scene.Group;
import javafx.scene.layout.GridPane;

/**
 * Class to create and to handle grid in GUI
 *
 * @author Mathyas Giudici
 */

public class FXGridViewCreator implements GridViewCreator<GridPane,Group> {

    private FXDieViewCreator fxDieViewCreator;

    private Pair grid;

    @Override
    public GridPane display() {
        //TODO
        return null;
    }

    @Override
    public void addADie(Group die, int height, int width) {
        //TODO
    }

    @Override
    public Group pickDie(int height, int width) {
        //TODO
        return null;
    }

    public Pair getGrid() {
        return grid;
    }

    public void setGrid(Pair grid) {
        this.grid = grid;
    }
}
