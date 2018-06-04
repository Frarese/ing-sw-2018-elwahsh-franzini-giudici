package it.polimi.se2018.events.messages;

import it.polimi.se2018.events.Event;
import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.model.dice.Reserve;
import it.polimi.se2018.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Update of the reserve status
 * @author Alì El wahsh
 */
public class ReserveStatus  extends Event{

    private ArrayList<Pair<ColorModel,Integer>> dice = new ArrayList<>();

    /**
     * Constructor
     * @param r reserve inside the model
     */
    public ReserveStatus(Reserve r)
    {
        for(int i = 0; i< r.size();i++)
        {
            dice.add(new Pair<>(r.get(i).getColor(),r.get(i).getValue()));
        }

        this.description = "Reserve";
    }


    /**
     * Getter for dice inside the reserve
     * @return dice inside the reserve
     */
    public List<Pair<ColorModel, Integer>> getDice() {
        return dice;
    }
}
