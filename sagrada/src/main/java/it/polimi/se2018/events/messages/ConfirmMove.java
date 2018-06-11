package it.polimi.se2018.events.messages;

import it.polimi.se2018.events.ServerMessageHandler;
import it.polimi.se2018.events.actions.PlayerMove;

public class ConfirmMove extends ServerMessage {
    private final PlayerMove move;
    private final boolean placement;

    /**
     * ConfirmMove's constructor
     * @param move invalid move
     */
    public ConfirmMove(PlayerMove move,boolean placement)
    {
        this.move = move;
        this.description = "You did it";
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
