package it.polimi.se2018.events.messages;

import it.polimi.se2018.events.Event;
import it.polimi.se2018.events.actions.PlayerMove;

/**
 * Invalid move message, to be handled by the client controllers
 */
public class InvalidMove extends Event {


    private PlayerMove move;

    /**
     * InvalidMove's constructor
     * @param move invalid move
     * @param error error message
     */
    public InvalidMove(PlayerMove move, String error)
    {
        this.move = move;
        this.description = error;
    }

    /**
     * Getter for the invalid move
     * @return the invalid move
     */
    public PlayerMove getMove() {
        return move;
    }
}
