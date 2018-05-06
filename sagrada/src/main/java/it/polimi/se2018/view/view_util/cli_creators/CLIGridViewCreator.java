package it.polimi.se2018.view.view_util.cli_creators;

import it.polimi.se2018.util.Pair;
import it.polimi.se2018.view.view_util.GridViewCreator;

/**
 * Class to create and to handle grid in CLI
 *
 * @author Mathyas Giudici
 */

public class CLIGridViewCreator implements GridViewCreator<String, String> {

    private CLIDieViewCreator cliDieViewCreator;

    private Pair grid;

    @Override
    public String display() {
        //TODO
        return null;
    }

    @Override
    public void addADie(String die, int height, int width) {
        //TODO
    }

    @Override
    public String pickDie(int height, int width) {
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
