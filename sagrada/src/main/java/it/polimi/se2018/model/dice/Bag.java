package it.polimi.se2018.model.dice;


import it.polimi.se2018.model.ColorModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static it.polimi.se2018.model.ColorModel.WHITE;

/**
 * Model representation of the dice bag
 * Since it will exist only one bag it's a Singleton
 * @author Alì El Wahsh
 */
public class Bag {
    public static final int MAX_SIZE = 90;
    public static final int DICE_FOR_COLOR = 18;
    private ArrayList<Die> content;
    private static Bag instance;

    /**
     * Constructor of Bag
     * It loops a lambda expression that builds a die for each color
     * and adds it to the bag
     */
    private Bag()
    {
        content = new ArrayList<>();
        for(int i=0; i<DICE_FOR_COLOR;i++){
            Stream.of(ColorModel.values()).filter(c-> c != WHITE).forEach(c->content.add(new Die(c)));
        }
        Collections.shuffle(content); /*Random order of the dice guaranteed*/
    }

    /**
     * Getter for the instance of Bag
     * @return the existing instance of bag (or a newly built one if it's the first call)
     */
    public static synchronized Bag getInstance()
    {
        if (instance == null)
        {
            instance = new Bag();
        }

        return instance;
    }

    /**
     * Getter for a die inside the bag
     * @param i position of the die inside the bag
     * @return  an existing die inside the bag or null if the spot is empty
     */

    public Die getDie(int i) {
        try {
            return content.get(i);
        } catch(IndexOutOfBoundsException e)
        {
           return null;
        }
    }

    /**
     * Max value = MAX_SIZE
     * @return the number of dice inside the bag
     */
    public int size() {
        return content.size();
    }


    /**
     * Reinsert a die inside the bag
     * @param d the die to be put inside the bag
     */
    public void add(Die d) {
    if(size() < MAX_SIZE)
    {
        content.add(d);
        Collections.shuffle(content); /*Random order guaranteed*/
    }
    }


    /**
     * Extract dice from the bag popping them out of content
     * @param numberOfDice number of dice to be popped
     * @return an ArrayList of Dice w/ size &lt;= numberOfDice
     */
    public List<Die> popDice(int numberOfDice)
    {
        ArrayList<Die> temp = new ArrayList<>();
       for(int i=0; i<numberOfDice; i++)
       {
           if(size()>0)
               temp.add(content.remove(0));
           else return temp;
       }
        return temp;
    }

}
