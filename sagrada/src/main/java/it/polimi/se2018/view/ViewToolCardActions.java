package it.polimi.se2018.view;

import it.polimi.se2018.util.Pair;

/**
 * Class for View-Controller communication
 * (this class is for the Tool Card use)
 *
 * @author Mathyas Giudici
 */

public class ViewToolCardActions {

    /**
     * To communicate to the Controller the die that player selected from the Reserve
     *
     * @param die contains the die's ID
     */
    public void selectedDieFromReserve(Pair die) {
        throw new UnsupportedOperationException();

    }

    /**
     * To communicate to the Controller the die's value that player selected
     *
     * @param value contains the die's value
     */
    public void selectedValueForDie(int value) {
        throw new UnsupportedOperationException();

    }

    /**
     * To communicate to the Controller the die's that player selected from the grid
     *
     * @param die contains the die's ID
     */
    public void selectedDieFromGrid(Pair die) {
        throw new UnsupportedOperationException();

    }

    /**
     * To communicate to the Controller the die's that player selected from the Round Tracker
     *
     * @param die contains the die's ID
     */
    public void selectedDieFromRoundTracker(Pair die) {
        throw new UnsupportedOperationException();

    }

    /**
     * To communicate to the Controller the die's that player selected
     *
     * @param die   contains the die
     * @param value contains the die's value
     */
    public void selectedFace(Pair die, int value) {
        throw new UnsupportedOperationException();

    }

    /**
     * To communicate to the Controller the die's that player selected
     *
     * @param die contains the die
     */
    public void selectedDieFromGridByColor(Pair die) {
        throw new UnsupportedOperationException();

    }
}
