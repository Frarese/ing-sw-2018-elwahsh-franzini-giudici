package it.polimi.se2018.model.cards;

/**
 * Model representation of a generic card
 * @author Alì El Wahsh
 */
public abstract class CardModel {

    protected int id; /*Unique identifier for every card*/
    protected String text; /*Card text*/


    /**
     * Getter for card identifier
     * @return the card's identifier
     */
    public int getId()
    {
        return id;
    }

    /**
     * Card text for CLI Players
     * @return The card's text
     */
    @Override
    public String toString() {
        return text;
    }
}
