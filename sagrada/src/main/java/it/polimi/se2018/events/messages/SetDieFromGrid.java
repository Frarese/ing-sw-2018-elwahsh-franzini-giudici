package it.polimi.se2018.events.messages;

import it.polimi.se2018.events.ServerMessageHandler;
import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.util.Pair;

/**
 * Request fro a die to be set from the grid
 * @author Alì El wahsh
 */
public class SetDieFromGrid extends ServerMessage {

    private final Pair<Integer,ColorModel> die;

    /**
     * Constructor
     * @param die die's infos
     */
    public SetDieFromGrid(Pair<Integer,ColorModel> die) {
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
    public Pair<Integer, ColorModel> getDie() {
        return die;
    }
}
