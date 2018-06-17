package it.polimi.se2018.events.messages;

import it.polimi.se2018.events.ServerMessageHandler;
import it.polimi.se2018.events.actions.PlayerMove;

/**
 * Invalid move message, to be handled by the client controllers
 * @author Alì El Wahsh
 */
public class InvalidMove extends ServerMessage {


    private final PlayerMove move;
    private final boolean placement;

    /**
     * InvalidMove's constructor
     * @param move invalid move
     * @param error error message
     * @param placement true if this was a placement issue
     */
    public InvalidMove(PlayerMove move, String error,boolean placement)
    {
        this.move = move;
        this.description = error;
        this.placement = placement;
    }

    /**
     * Getter for the invalid move
     * @return the invalid move
     */
    public PlayerMove getMove() {
        return move;
    }

    @Override
    public void visit(ServerMessageHandler handler) {
        handler.handle(this);
    }

    /**
     * Checks if it's a placement move or use tool card
     * @return true if it's a placement, false otherwise
     */
    public boolean isPlacement() {
        return placement;
    }
}
