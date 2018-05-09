package it.polimi.se2018.model;

import it.polimi.se2018.model.cards.ActiveObjectives;
import it.polimi.se2018.model.cards.ActiveTools;
import it.polimi.se2018.model.cards.ObjectiveCard;
import it.polimi.se2018.model.cards.ToolCard;
import it.polimi.se2018.model.dice.Bag;
import it.polimi.se2018.model.dice.Die;
import it.polimi.se2018.model.dice.Reserve;
import it.polimi.se2018.model.dice.RoundTracker;

public class Board {
    private ActiveTools tools;
    private ActiveObjectives objectives;
    private Bag bag;
    private Reserve reserve;
    private RoundTracker roundTrack;

    public Board()
    {
        tools = new ActiveTools();
        objectives = new ActiveObjectives();
        bag = Bag.getInstance();
        reserve = Reserve.getInstance();
        roundTrack = RoundTracker.getInstance();
    }

    public ToolCard getTool(int toolPosition) {
        return tools.getTool(toolPosition);
    }

    public ObjectiveCard getObjective(int objectivePosition) {
        return objectives.getObjective(objectivePosition);
    }

    public Reserve getReserve() {
        return reserve;
    }

    public RoundTracker getRoundTrack() {
        return roundTrack;
    }

    public void rollDiceOnReserve(int numberOfPlayers)
    {
        reserve.addAll(bag.popDice(numberOfPlayers*2+1));
        reserve.rollReserve();
    }

    public void reRollReserve()
    {
        reserve.rollReserve();
    }

    public void addDieToBag(Die d)
    {
        bag.add(d);
    }

    public void putReserveOnRoundTrack()
    {
        roundTrack.addAll(reserve.popAllDice());
    }
}
