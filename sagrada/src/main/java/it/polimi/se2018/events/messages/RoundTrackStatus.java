package it.polimi.se2018.events.messages;

import it.polimi.se2018.events.ServerMessageHandler;
import it.polimi.se2018.model.IntColorPair;
import it.polimi.se2018.model.dice.RoundTracker;


/**
 * Update on the status of the round track
 * @author Alì El wahsh
 */
public class RoundTrackStatus extends ServerMessage {

    private final IntColorPair[][] dice = new IntColorPair[9][10];
    private final int round;

    /**
     * Constructor
     * @param t round track inside the model
     */
    public RoundTrackStatus(RoundTracker t)
    {

        round = t.lastFilledRound()+1;
        for(int i = 0; i<t.lastFilledRound();i++)
        {
            boolean done = false;
            int j = 0;

            while(!done)
            {
                if(t.getDie(i,j) != null)
                {
                    dice[j][i] =new IntColorPair(t.getDie(i,j).getValue(),t.getDie(i,j).getColor());
                    j++;
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
    public IntColorPair getDie(int round, int diePosition)
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
    public IntColorPair[][] getDice() {
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
     * @return a list of dice or null in case of invalid position
     */
    public IntColorPair[] getRound(int round) {
        try {
            return dice[round];
        } catch (IndexOutOfBoundsException e)
        {
            return new IntColorPair[9];
        }
    }

    @Override
    public void visit(ServerMessageHandler handler) {
        handler.handle(this);
    }
}
