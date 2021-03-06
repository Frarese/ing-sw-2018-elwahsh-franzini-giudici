package it.polimi.se2018.model.dice;

import java.util.ArrayList;
import java.util.List;

/**
 * Model representation of the round track, since there is only one it's a Singleton
 * @author Alì El Wahsh
 */

public class RoundTracker {
    public static final int ROUNDS = 10; //Number of rounds
    private final ArrayList<ArrayList<Die>> dice;


    /**
     * Constructor of RoundTracker
     */
    public RoundTracker()
    {
        dice = new ArrayList<>();
    }



    /**
     * Getter for a Die in a specified round and position
     * @param round round in which the die was placed
     * @param diePosition position of the die in the round
     * @return the desired die or null if the position is invalid
     */
    public Die getDie(int round, int diePosition) {
        try{
            return dice.get(round).get(diePosition);
        } catch (IndexOutOfBoundsException e)
        {
            return null;
        }
    }

    /**
     * Getter of the size of dice (it tells also how many rounds have passed)
     * @return size of the dice ArrayList
     */
    public int lastFilledRound() {
        return dice.size();
    }

    /**
     * Adder of remaining dice to the actual round
     * @param d list of die remaining in the Reserve
     */
    public void addAll(List<Die> d)  {

        if( lastFilledRound()< ROUNDS)
            dice.add(new ArrayList<>(d));
    }

    /**
     * Adds die to a specific round
     * @param d die to be added
     * @param round round to be filled
     */
    public void addDie(Die d, int round)
    {
        dice.get(round).add(d);
    }
    /**
     * Popper of a die in the RoundTracker (Used by a couple of cards)
     * @param round round in which the die was placed
     * @param diePosition position of the die in the round
     * @return the desired die or null if the position is invalid
     */
    public Die popDie(int round, int diePosition)
    {
        try {
            return dice.get(round).remove(diePosition);
        } catch(IndexOutOfBoundsException e)
        {
            return null;
        }
    }

    /**
     * toString method for CLI and log purposes
     * @return A String of dice for each round
     */
    @Override
    public String toString()
    {
        return dice.toString();
    }

}
