package it.polimi.se2018.view;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.util.Pair;

/**
 * Interface for Controller-View communication
 * (this interface is for the Tool Card use)
 *
 * @author Mathyas Giudici
 */

public interface ControllerToolCardActionsInterface {

    /**
     * Asks at currentPlayer to select a die from the Reserve
     */
    void selectDieFromReserve();

    /**
     * Asks at currentPlayer the new die's value
     *
     * @param low  old(die's value) +1
     * @param high old(die's value) -1
     */
    void selectNewValueForDie(int low, int high);

    /**
     * Updates the Reserve
     */
    void updateReserve();

    /**
     * Asks at currentPlayer to select a die from the Grid
     */
    void selectDieFromGrid();

    /**
     * Asks at currentPlayer to set a die on the Grid
     *
     * @param die contains the die that player have to place
     */
    void setDieOnGrid(Pair<Integer, ColorModel> die);

    /**
     * Asks at currentPlayer to select a die from the Round Tracker
     */
    void selectDieFromRoundTracker();

    /**
     * Asks at currentPlayer to select a die's face (value)
     *
     * @param die contains the die
     */
    void selectFace(Pair<Integer, ColorModel> die);

    /**
     * Asks at currentPlayer to select a die from his grid with a color limit
     *
     * @param color contains the color restriction
     */
    void selectDieFromGridByColor(ColorModel color);
}
