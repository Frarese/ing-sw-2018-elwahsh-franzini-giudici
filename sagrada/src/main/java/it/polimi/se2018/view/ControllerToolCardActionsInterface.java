package it.polimi.se2018.view;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.util.Pair;

/**
 * Interface for Controller -> View communication
 * (this interface is for the Tool Card use)
 *
 * @author Mathyas Giudici
 */

public interface ControllerToolCardActionsInterface {

    /**
     * To ask at currentPlayer to select a die from the Reserve
     */
    void selectDieFromReserve();

    /**
     * To ask at currentPlayer the new die's value
     *
     * @param up   old(die's value) -1
     * @param down old(die's value) +1
     */
    void selectNewValueForDie(int up, int down);

    /**
     * To update the Reserve
     */
    void updateReserve();

    /**
     * To ask at currentPlayer to select a die from the Grid
     */
    void selectDieFromGrid();

    /**
     * To ask at currentPlayer to set a die on the Grid
     *
     * @param die contains the die that player have to place
     */
    void setDieOnGrid(Pair die);

    /**
     * To ask at currentPlayer to select a die from the Round Tracker
     */
    void selectDieFromRoundTracker();

    /**
     * To ask at currentPlayer to select a die's face (value)
     *
     * @param die contains the die
     */
    void selectFace(Pair die);

    /**
     * To ask at currentPlayer to select a die from his grid with a color limit
     *
     * @param color contains the color restriction
     */
    void selectDieFromGridByColor(ColorModel color);
}
