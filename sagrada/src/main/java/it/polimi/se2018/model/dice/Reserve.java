package it.polimi.se2018.model.dice;

import java.util.ArrayList;
import java.util.List;

/**
 * Model representation of the dice reserve, in which players can pick a die
 * to be put on their grid
 * @author Alì El Wahsh
 */
public class Reserve {
    private  ArrayList<Die> dice;

    /**
     * Reserve constructor
     */
    public Reserve()
    {
        dice = new ArrayList<>();
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
     * @param diePosition position of the die inside the reserve
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
     * Popper for all remaining dice inside Reserve
     * @return a List of dice
     */
    public List<Die> popAllDice()
    {
        ArrayList<Die> temp = new ArrayList<>();
        if(dice.isEmpty())
            return new ArrayList<>();

        for(int i = dice.size(); i> 0; i--)
            temp.add(dice.remove(0));
        return temp;
    }

    /**
     * Rolls all dice inside the reserve
     */
    public void rollReserve()
    {
        for(Die d : dice) d.roll();
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