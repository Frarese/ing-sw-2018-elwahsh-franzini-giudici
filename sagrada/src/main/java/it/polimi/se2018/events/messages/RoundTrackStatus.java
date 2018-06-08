package it.polimi.se2018.events.messages;

import it.polimi.se2018.events.Event;
import it.polimi.se2018.model.dice.RoundTracker;
import it.polimi.se2018.util.Pair;



/**
 * Update on the status of the round track
 * @author Alì El wahsh
 */
public class RoundTrackStatus extends Event {

    private final Pair[][] dice = new Pair[9][10];
    private final int round;

    /**
     * Constructor
     * @param t round track inside the model
     */
    public RoundTrackStatus(RoundTracker t)
    {

        round = t.lastFilledRound();
        for(int i = 0; i<t.lastFilledRound();i++)
        {
            boolean done = false;
            int j = 0;

            while(!done)
            {
                if(t.getDie(i,j) != null)
                {
                    dice[i][j] =new Pair<>(t.getDie(i,j).getColor(), t.getDie(i,j).getValue());
                }
                else
                    done = true;
            }
        }

        this.description = "RoundTrack";
    }

    /**
     * Getter for a single die inside the round tracker
     * @param round round position
     * @param diePosition position in the single round
     * @return a die or null in case of invalid position
     */
    public Pair getDie(int round, int diePosition)
    {
        try {
            return dice[round][diePosition];
        } catch (IndexOutOfBoundsException e)
        {
            return null;
        }
    }

    /**
     * Getter for all the round track's dice
     * @return all round track content
     */
    public Pair[][] getDice() {
        return dice;
    }

    /**
     * Gets last filled round
     * @return last filled round
     */
    public int round()
    {
        return round;
    }

    /**
     *
     * @param round round position
     * @return a list of dide or null in case of invalid position
     */
    public Pair[] getRound(int round) {
        try {
            return dice[round];
        } catch (IndexOutOfBoundsException e)
        {
            return new Pair[9];
        }
    }
}
