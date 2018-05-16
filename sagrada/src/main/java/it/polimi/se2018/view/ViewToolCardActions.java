package it.polimi.se2018.view;

/**
 * Class for View-Controller communication
 * (this class is for the Tool Card use)
 *
 * @author Mathyas Giudici
 */

public class ViewToolCardActions {

    /**
     * Communicates to the Controller the die that player selected from the Reserve
     *
     * @param index contains the die's ID
     */
    public void selectedDieFromReserve(int index) {
        throw new UnsupportedOperationException();

    }

    /**
     * Communicates to the Controller the die's value that player selected
     *
     * @param value contains the die's value
     */
    public void selectedValueForDie(int value) {
        throw new UnsupportedOperationException();

    }

    /**
     * Communicates to the Controller the die's that player selected from the grid
     *
     * @param width  contains the x coordinate
     * @param height contains the y coordinate
     */
    public void selectedDieFromGrid(int width, int height) {
        throw new UnsupportedOperationException();

    }

    /**
     * Communicates to the Controller the die's that player selected to add in the grid
     *  @param width  contains the x coordinate
     * @param height contains the y coordinate
     */
    public void selectedDieToGrid(int width, int height) {
        throw new UnsupportedOperationException();

    }

    /**
     * Communicates to the Controller the die's that player selected from the Round Tracker
     *
     * @param roundIndex contains the roundIndex in roundTracker
     * @param dieIndex   contains the dieIndex in roundTracker[roundIndex]
     */
    public void selectedDieFromRoundTracker(int roundIndex, int dieIndex) {
        throw new UnsupportedOperationException();

    }

    /**
     * Communicates to the Controller the die's that player selected
     *
     * @param value contains the die's value
     */
    public void selectedFace(int value) {
        throw new UnsupportedOperationException();

    }

    /**
     * Communicates to the Controller the die's that player selected
     *
     * @param width  contains the x coordinate
     * @param height contains the y coordinate
     */
    public void selectedDieFromGridByColor(int width, int height) {
        throw new UnsupportedOperationException();

    }
}
