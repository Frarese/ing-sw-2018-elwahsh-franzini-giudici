package it.polimi.se2018.view;

import it.polimi.se2018.controller.game.client.ActionSender;

import java.util.Observable;

/**
 * Class for View-Controller communication
 * (this class is for the Tool Card use)
 *
 * @author Mathyas Giudici
 */

public class ViewToolCardActions extends Observable {

    private final ActionSender actionSender;

    /**
     * Class constructor
     * Sets player's name used in events thrown to the Controller
     *
     * @param actionSender contains the controller class to manage events
     */
    public ViewToolCardActions(ActionSender actionSender) {
        this.actionSender = actionSender;
    }

    /**
     * Communicates to the Controller the die that player selected from the Reserve
     *
     * @param index contains the die's ID
     */
    public void selectedDieFromReserve(int index) {
        actionSender.selectedDieFromReserve(index);
    }

    /**
     * Communicates to the Controller the die's value that player selected
     *
     * @param value contains the die's value
     */
    public void selectedValueForDie(int value) {
        actionSender.selectedNewValueForDie(value);
    }

    /**
     * Communicates to the Controller the die's that player selected from the grid
     *
     * @param width  contains the x coordinate
     * @param height contains the y coordinate
     */
    public void selectedDieFromGrid(int width, int height) {
        actionSender.selectedDieFromGrid(height, width);
    }

    /**
     * Communicates to the Controller the die's that player selected to add in the grid
     *
     * @param width  contains the x coordinate
     * @param height contains the y coordinate
     */
    public void selectedDieToGrid(int width, int height) {
        actionSender.selectedDieToGrid(height, width);
    }

    /**
     * Communicates to the Controller the die's that player selected from the Round Tracker
     *
     * @param roundIndex contains the roundIndex in roundTracker
     * @param dieIndex   contains the dieIndex in roundTracker[roundIndex]
     */
    public void selectedDieFromRoundTracker(int roundIndex, int dieIndex) {
        actionSender.selectedDieFromRoundTrack(roundIndex, dieIndex);

    }

    /**
     * Communicates to the Controller the die's that player selected
     *
     * @param value contains the die's value
     */
    public void selectedFace(int value) {
        actionSender.selectedNewValueForDie(value);
    }

    /**
     * Communicates to the Controller the die's that player selected
     *
     * @param width  contains the x coordinate
     * @param height contains the y coordinate
     */
    public void selectedDieFromGridByColor(int width, int height) {
        actionSender.selectedDieFromGrid(height, width);
    }

    /**
     * Communicates to the Controller the number of placement selected
     *
     * @param number contains the numbers of placement
     */
    public void selectedNumbersOfPlacement(int number) {
        actionSender.sendNumberOfPlacements(number);
    }
}
