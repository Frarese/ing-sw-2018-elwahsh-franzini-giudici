package it.polimi.se2018.events.messages;

import it.polimi.se2018.events.Event;
import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.model.dice.RoundTracker;
import it.polimi.se2018.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Update on the status of the round track
 * @author Alì El wahsh
 */
public class RoundTrackStatus extends Event {

    private ArrayList<ArrayList<Pair<ColorModel,Integer>>> dice = new ArrayList<>();

    /**
     * Constructor
     * @param t round track inside the model
     */
    public RoundTrackStatus(RoundTracker t)
    {

        for(int i = 0; i<t.lastFilledRound();i++)
        {
            boolean done = false;
            int j = 0;
            ArrayList<Pair<ColorModel,Integer>> temp = new ArrayList<>();
            while(!done)
            {
                if(t.getDie(i,j) != null)
                {
                    temp.add(new Pair<>(t.getDie(i,j).getColor(), t.getDie(i,j).getValue()));
                }
                else
                    done = true;

            }
            dice.add(temp);
        }

        this.description = "RoundTrack";
    }

    /**
     * Getter for a single die inside the round tracker
     * @param round round position
     * @param diePosition position in the single round
     * @return a die or null in case of invalid position
     */
    public Pair<ColorModel, Integer> getDie(int round, int diePosition)
    {
        try {
            return dice.get(round).get(diePosition);
        } catch (IndexOutOfBoundsException e)
        {
            return null;
        }
    }

    /**
     *
     * @param round round position
     * @return a list of dide or null in case of invalid position
     */
    public List<Pair<ColorModel, Integer>> getRound(int round) {
        try {
            return dice.get(round);
        } catch (IndexOutOfBoundsException e)
        {
            return null;
        }
    }
}
