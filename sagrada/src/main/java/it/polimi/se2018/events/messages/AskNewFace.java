package it.polimi.se2018.events.messages;

import it.polimi.se2018.events.ServerMessageHandler;
import it.polimi.se2018.model.ColorModel;

/**
 * Request for a new face for a die
 * @author Alì El wahsh
 */
public class AskNewFace extends ServerMessage {

    private final ColorModel color;
    private final int value;

    /**
     * Constructor
     * @param color die's color
     * @param value die's value
     */
    public AskNewFace(ColorModel color, int value)
    {
        this.color = color;
        this.value = value;
    }

    @Override
    public void visit(ServerMessageHandler handler) {
        handler.handle(this);
    }

    /**
     * Getter for die value
     * @return die value
     */
    public int getValue() {
        return value;
    }

    /**
     * Getter for die color
     * @return die color
     */
    public ColorModel getColor() {
        return color;
    }
}
