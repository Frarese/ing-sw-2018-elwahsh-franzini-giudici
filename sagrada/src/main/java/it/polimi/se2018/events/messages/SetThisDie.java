package it.polimi.se2018.events.messages;

import it.polimi.se2018.events.ServerMessageHandler;
import it.polimi.se2018.model.IntColorPair;

/**
 * Request fro a die to be set from the grid
 * @author Alì El wahsh
 */
public class SetThisDie extends ServerMessage {

    private final IntColorPair die;

    /**
     * Constructor
     * @param die die's infos
     */
    public SetThisDie(IntColorPair die) {
        this.die = die;
    }

    @Override
    public void visit(ServerMessageHandler handler) {
        handler.handle(this);
    }

    /**
     * Getter for die
     * @return die
     */
    public IntColorPair getDie() {
        return die;
    }
}
