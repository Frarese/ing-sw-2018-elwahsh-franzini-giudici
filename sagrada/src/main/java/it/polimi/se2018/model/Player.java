package it.polimi.se2018.model;


import it.polimi.se2018.model.cards.PrivateObjectiveCard;
import it.polimi.se2018.model.dice.Grid;

import java.util.Observable;

/**
 * Model representation of a player in game
 */
public class Player extends Observable {

    /*Info about the player*/
    protected String name;
    protected int id;
    protected int favourPoints;
    protected PrivateObjectiveCard privateObjective;

    /*Placement and card usage rights*/
    protected boolean firstTurnPlacement;
    protected boolean firstTurnCard;
    protected boolean secondTurnPlacement;
    protected boolean secondTurnCard;

    /*His/Hers grid and the pattern beneath it*/
    protected Grid grid;
    protected Pattern pattern;


    /**
     * Player constructor
     * @param name Player's username
     * @param id Player's identifier (ex player 1, player 2, etc.)
     */
    public Player(String name, int id)
    {
        this.name = name;
        this.id = id;
    }

    /**
     * Getter for the grid (mutable)
     * @return the player's grid
     */
    public Grid getGrid() {
        return grid;
    }

    /**
     * Getter for favour points
     * @return the player's favour points
     */
    public int getFavourPoints() {
        return favourPoints;
    }

    /**
     * Getter for player's identifier
     * @return the player's id
     */
    public int getId() {
        return id;
    }

    /**
     * Getter for the name of the player
     * @return player's name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for player's private objective card
     * @return the player's own objective card
     */
    public PrivateObjectiveCard getPrivateObjective() {
        return privateObjective;
    }

    /**
     * Getter for the player's pattern (immutable)
     * @return the pattern chosen by the player
     */
    public Pattern getPattern() {
        return pattern;
    }

    /**
     * Check the placement rights of the player on his/hers turn
     * @param firstTurn true if it's the first turn or false if it's the second one
     * @return true if the player can place the die or false otherwise
     */
    public boolean canPlaceOnThisTurn(boolean firstTurn)
    {
        if(firstTurn)
            return firstTurnPlacement;
        else
            return secondTurnPlacement;
    }

    /**
     * Check the card usage rights of the player on his/hers turn
     * @param firstTurn true if it's the first turn or false if it's the second one
     * @return true if the player can use the card or false otherwise
     */
    public boolean canUseCardOnThisTurn(boolean firstTurn)
    {
        if(firstTurn)
            return firstTurnCard;
        else
            return secondTurnPlacement;
    }

    /**
     * Set a new value for the player's favour points
     * @param newValue new value of the favour points (usually \old(favourPoints) - constant)
     */
    public void setFavourPoints(int newValue)
    {
        favourPoints = newValue;
    }

    /**
     * Setter for the player's placement rights
     * @param firstTurn true if it's the first turn or false if it's the second one
     * @param newValue the new value for *****TurnPlacement
     */
    public void setPlacementRights(boolean firstTurn, boolean newValue)
    {
        if(firstTurn)
            firstTurnPlacement = newValue;
        else
            secondTurnPlacement = newValue;
    }

    /**
     * Setter for the player's card usage rights
     * @param firstTurn true if it's the first turn or false if it's the second one
     * @param newValue the new value for *****TurnCard
     */
    public void setCardRights(boolean firstTurn, boolean newValue)
    {
        if(firstTurn)
            firstTurnCard = newValue;
        else
            secondTurnCard = newValue;
    }

    /**
     * Setter for the private objective card
     * @param privateObjective the private objective card assigned to the player
     */
    public void setPrivateObjective(PrivateObjectiveCard privateObjective) {
        this.privateObjective = privateObjective;
    }

    /**
     * Setter for player's window pattern
     * @param pattern the pattern chose by the player
     */
    public void setPattern(Pattern pattern)
    {
        this.pattern = pattern;
    }

    /**
     * Initialize player's favour point from the pattern's difficulty
     */
    public void initFavourPoints()
    {
        favourPoints = pattern.getFavourPoints();
    }
}


