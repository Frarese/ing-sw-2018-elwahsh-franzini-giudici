package it.polimi.se2018.events.messages;

import it.polimi.se2018.events.ServerMessageHandler;

/**
 * Request for a new face for a die
 * @author Alì El wahsh
 */
public class AskNewFace extends ServerMessage {

    private final int index;

    /**
     * Constructor
     * @param index die position inside the reserve
     */
    public AskNewFace(int index)
    {
        this.index = index;
    }

    @Override
    public void visit(ServerMessageHandler handler) {
        handler.handle(this);
    }

    /**
     * Getter for die position
     * @return die position
     */
    public int getIndex() {
        return index;
    }
}
