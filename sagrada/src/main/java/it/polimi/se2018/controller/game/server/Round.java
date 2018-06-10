package it.polimi.se2018.controller.game.server;

import it.polimi.se2018.model.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Representation of the round system of Sagrada
 * @author Alì El Wahsh
 */
public class Round {

    private final ArrayList<Player> order = new ArrayList<>();
    private boolean firstTurn;
    private int index;
    private int roundNumber;

    /**
     * Round's constructor
     * @param order order of the player on their first round
     */
    public Round(List<Player> order)
    {
        firstTurn = true;
        this.order.addAll(order);
        index = 0;
        roundNumber = 1;
    }

    /**
     * Sets index on the new player's position.
     * Follows the Sagrada's turn order (1-2-3-3-2-1)
     */
    public void nextTurn()
    {

        if(firstTurn)
        {
            if(index == order.size() -1) {
                firstTurn = false;
            }
            else
                index++;
        }
        else index--;
    }

    /**
     * Prepare the order for a new round
     */
    public void prepareNextRound()
    {
        index = 0;
        firstTurn = true;
        order.add(order.remove(0));
        roundNumber++;
    }

    /**
     * Getter for the current player
     * @return current player
     */
    public Player getCurrentPlayer()
    {
        if(index>=0)
            return order.get(index);
        else return null;

    }

    /**
     * Checks if it's the first turn
     * @return true during the first turn, false otherwise
     */
    public boolean getFirstTurn()
    {
        return firstTurn;
    }

    /**
     * Getter for the actual round number
     * @return actual round number
     */
    public int getRoundNumber() {
        return roundNumber;
    }
}
