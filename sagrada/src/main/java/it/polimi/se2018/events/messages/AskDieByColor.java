package it.polimi.se2018.events.messages;

import it.polimi.se2018.events.ServerMessageHandler;
import it.polimi.se2018.model.ColorModel;

/**
 * Request for a die with a specific color
 * @author Alì El wahsh
 */
public class AskDieByColor extends ServerMessage {

    private final ColorModel colorModel;

    /**
     * Constructor
     * @param colorModel color asked
     */
    public AskDieByColor(ColorModel colorModel) {
        this.colorModel = colorModel;
    }

    @Override
    public void visit(ServerMessageHandler handler) {
        handler.handle(this);
    }

    /**
     * Getter for color
     * @return color
     */
    public ColorModel getColorModel() {
        return colorModel;
    }
}
