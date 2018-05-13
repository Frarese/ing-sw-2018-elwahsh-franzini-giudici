package it.polimi.se2018.model.cards;

import it.polimi.se2018.model.Pattern;

/**
 * Model representation of a window pattern card
 * @author Alì El Wahsh
 */
public class PatternCard extends CardModel {

    Pattern frontSide; /*first pattern*/
    Pattern backSide; /*second pattern*/

    /**
     * PatternCard's constructor
     * @param path1 first side's filepath
     * @param path2 second side's filepath
     */
    public PatternCard(String path1, String path2)
    {
        frontSide = new Pattern(path1);
        backSide = new Pattern(path2);
    }

    /**
     * Getter for the backside
     * @return the backside pattern
     */
    public Pattern getBackSide() {
        return backSide;
    }

    /**
     * Getter for the front side
     * @return the front side pattern
     */
    public Pattern getFrontSide() {
        return frontSide;
    }

    /**
     * The player choose one of the two sides as his/hers
     * own pattern for the game
     * @param front true if the front side is the chosen one, false otherwise
     * @return the chosen pattern
     */
    public Pattern chooseSide(boolean front)
    {
        if(front)
            return frontSide;
        else
            return backSide;

    }

    /**
     * Simple toString method for CLI and log
     * @return the two sides of the card in their string format
     */
    @Override
    public String toString() {
       return  frontSide.toString() + "\n" + backSide.toString();
    }
}
