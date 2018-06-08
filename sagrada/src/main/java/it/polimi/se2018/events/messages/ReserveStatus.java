package it.polimi.se2018.events.messages;

import it.polimi.se2018.events.Event;
import it.polimi.se2018.model.dice.Reserve;
import it.polimi.se2018.util.Pair;

/**
 * Update of the reserve status
 * @author Alì El wahsh
 */
public class ReserveStatus  extends Event{

    private final Pair[] dice = new Pair[9];

    /**
     * Constructor
     * @param r reserve inside the model
     */
    public ReserveStatus(Reserve r)
    {
        for(int i = 0; i< r.size();i++)
        {
            dice[i] = new Pair<>(r.get(i).getColor(),r.get(i).getValue());
        }

        this.description = "Reserve";
    }


    /**
     * Getter for dice inside the reserve
     * @return dice inside the reserve
     */
    public Pair[] getDice() {
        return dice;
    }
}
