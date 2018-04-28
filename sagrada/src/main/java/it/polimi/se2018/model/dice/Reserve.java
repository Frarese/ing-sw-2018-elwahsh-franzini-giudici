package it.polimi.se2018.model.dice;

import java.util.ArrayList;
import java.util.List;

/**
 * Model representation of the dice reserve, in which players can pick a die
 * to be put on their grid
 * Since it will exists only one reserve it's a Singleton
 */
public class Reserve {
    private static Reserve instance;
    private static ArrayList<Die> dice = new ArrayList<>();

    /**
     * Reserve constructor
     */
    private Reserve()
    {
    }

    /**
     * Getter of an instance of the reserve
     * @return the existing instance of reserve (or a newly built one if it's the first call)
     */
    public static synchronized Reserve getInstance()
    {
        if (instance == null)
        {
            instance = new Reserve();
        }

        return instance;
    }

    /**
     * Getter of a die in a specified position (without popping it)
     * @param diePosition position of the die inside the reserve
     * @return the die desired or null if the position is invalid;
     */
    public Die get(int diePosition) {
        try {
            return dice.get(diePosition);
        } catch(IndexOutOfBoundsException e)
        {
            return null;
        }
    }

    /**
     * Getter of the size
     * @return size of dice array
     */
    public int size() {
        return dice.size();
    }

    /**
     * Add a single die to the reserve
     * @param d the die to be added
     */
    public void add(Die d)
    {
        dice.add(d);
    }

    /**
     * Add List of dice to the reserve
     * @param ds list of dice
     */
    public void addAll(List<Die> ds)
    {
        dice.addAll(ds);
    }

    /**
     * Popper for a single die in the reserve
     * @param diePosition position of the sie inseide the reserve
     * @return the desired die or null in case of invalid position
     */
    public Die popDie(int diePosition)
    {
        try {
            return dice.remove(diePosition);
        } catch(IndexOutOfBoundsException e)
        {
            return null;
        }
    }

    /**
     * toString of the dice array
     * @return String of all the dice inside the reserve
     */

    @Override
    public String toString()
    {
        return dice.toString();
    }

}
