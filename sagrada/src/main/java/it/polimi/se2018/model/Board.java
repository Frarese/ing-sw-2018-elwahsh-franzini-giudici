package it.polimi.se2018.model;

import it.polimi.se2018.model.cards.ActiveObjectives;
import it.polimi.se2018.model.cards.ActiveTools;
import it.polimi.se2018.model.cards.ObjectiveCard;
import it.polimi.se2018.model.cards.ToolCard;
import it.polimi.se2018.model.dice.Bag;
import it.polimi.se2018.model.dice.Die;
import it.polimi.se2018.model.dice.Reserve;
import it.polimi.se2018.model.dice.RoundTracker;

/**
 * Model representation of the game board without the player's grid
 * @author Alì El Wahsh
 */
public class Board {
    private final ActiveTools tools;
    private final ActiveObjectives objectives;
    private final Bag bag;
    private final Reserve reserve;
    private final RoundTracker roundTrack;

    /**
     * Board's constructor
     */
    public Board()
    {
        tools = new ActiveTools();
        objectives = new ActiveObjectives();
        bag = new Bag();
        reserve = new Reserve();
        roundTrack = new RoundTracker();
    }

    /**
     * Getter for tool cards n game
     * @param toolPosition position of the card
     * @return a card or null in case of invalid position
     */
    public ToolCard getTool(int toolPosition) {
        return tools.getTool(toolPosition);
    }

    /**
     * Getter for public objectives
     * @return public objectives
     */
    public ActiveObjectives getObjectives() {
        return objectives;
    }

    /**
     * Getter for active tools
     * @return active tools
     */
    public ActiveTools getTools() {
        return tools;
    }

    /**
     * Getter for objective cards n game
     * @param objectivePosition position of the card
     * @return a card or null in case of invalid position
     */
    public ObjectiveCard getObjective(int objectivePosition) {
        return objectives.getObjective(objectivePosition);
    }

    /**
     * Getter for the reserve
     * @return instance of Reserve
     */
    public Reserve getReserve() {
        return reserve;
    }

    /**
     * Getter for the round track
     * @return instance of Round Track
     */
    public RoundTracker getRoundTrack() {
        return roundTrack;
    }

    /**
     * Rolls dice from bag to the reserve
     * @param numberOfPlayers number of players in game
     */
    public void rollDiceOnReserve(int numberOfPlayers)
    {
        reserve.addAll(bag.popDice(numberOfPlayers*2+1));
        reserve.rollReserve();
    }

    /**
     * Getter for a player total public score
     * @param player player to score
     * @return the score
     */
    public int totalScore(Player player) {
        return objectives.totalScore(player);
    }

    /**
     * Puts all the remaining dice inside the Round track
     */
    public void putReserveOnRoundTrack()
    {
        roundTrack.addAll(reserve.popAllDice());
    }

    /**
     * Rerolls all the dice in the reserve
     */
    public void reRollReserve()
    {
        reserve.rollReserve();
    }

    /**
     * Puts a die inside Bag
     * @param d the die to be put inside the bag
     */
    public void addDieToBag(Die d)
    {
        bag.add(d);
    }

    /**
     * Gets the bag
     * @return bag
     */
    public Bag getBag() {
        return bag;
    }
}