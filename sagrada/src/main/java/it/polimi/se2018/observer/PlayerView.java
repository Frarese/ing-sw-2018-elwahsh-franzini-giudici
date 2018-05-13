package it.polimi.se2018.observer;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.util.Pair;

/**
 * This class represents an observer of a player
 *
 * @author Mathyas Giudici
 */

public class PlayerView {

    private boolean hasChanged;

    public String getPlayerName() {
        throw new UnsupportedOperationException();
    }

    public int getPlayerFavours() {
        throw new UnsupportedOperationException();
    }

    public Pair<Integer,ColorModel>[][] getPlayerTemplate() {
        throw new UnsupportedOperationException();
    }

    public int getPlayerID() {
        throw new UnsupportedOperationException();
    }

    public Pair<Integer,ColorModel>[][] getPlayerGrid() {
        throw new UnsupportedOperationException();
    }

    public boolean hasPlacementRights() {
        throw new UnsupportedOperationException();
    }

    public boolean hasCardRights() {
        throw new UnsupportedOperationException();
    }
}
