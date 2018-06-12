package it.polimi.se2018.events.messages;

import it.polimi.se2018.events.ServerMessageHandler;

/**
 * Request fro a die placement
 * @author Alì El wahsh
 */
public class SetDie extends ServerMessage {

    private final int index;

    /**
     * Constructor
     * @param index die position inside the reserve
     */
    public SetDie(int index)
    {
        this.index = index;
    }

    @Override
    public void visit(ServerMessageHandler handler) {
        handler.handle(this);
    }

    /**
     * Getter for index
     * @return index
     */
    public int getIndex() {
        return index;
    }
}
