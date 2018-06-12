package it.polimi.se2018.events.messages;

import it.polimi.se2018.events.ServerMessageHandler;

/**
 * Request for a new value for a die, choosing val1 or val2
 * @author Alì El wahsh
 */
public class AskNewValue extends ServerMessage {

    private final int val1;
    private final int val2;

    /**
     * Constructor
     * @param val1 lower value
     * @param val2 higher value
     */
    public AskNewValue(int val1,int val2) {
        this.val1 = val1;
        this.val2 = val2;
    }

    @Override
    public void visit(ServerMessageHandler handler) {
        handler.handle(this);
    }

    /**
     * Getter for val1
     * @return val1
     */
    public int getVal1() {
        return val1;
    }

    /**
     * Getter for val2
     * @return val2
     */
    public int getVal2() {
        return val2;
    }
}
