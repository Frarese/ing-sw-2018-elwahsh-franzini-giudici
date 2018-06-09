package it.polimi.se2018.events.messages;

import it.polimi.se2018.events.Event;
import it.polimi.se2018.model.IntColorPair;
import it.polimi.se2018.model.dice.Reserve;

/**
 * Update of the reserve status
 * @author Alì El wahsh
 */
public class ReserveStatus  extends Event{

    private final IntColorPair[] dice = new IntColorPair[9];

    /**
     * Constructor
     * @param r reserve inside the model
     */
    public ReserveStatus(Reserve r)
    {
        for(int i = 0; i< r.size();i++)
        {
            dice[i] = new IntColorPair(r.get(i).getValue(),r.get(i).getColor());
        }

        this.description = "Reserve";
    }


    /**
     * Getter for dice inside the reserve
     * @return dice inside the reserve
     */
    public IntColorPair[] getDice() {
        return dice;
    }
}
