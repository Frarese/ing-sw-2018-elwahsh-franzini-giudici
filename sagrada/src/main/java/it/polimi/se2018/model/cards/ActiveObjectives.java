package it.polimi.se2018.model.cards;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.cards.objectivecards.*;

import java.util.ArrayList;

/**
 * Model representation of the public objectives in game
 * @author Alì El Wahsh
 */
public class ActiveObjectives {

    private ArrayList<ObjectiveCard> objectives = new ArrayList<>();

    /**
     * ActiveObjectives's constructor
     * May change it with a better implementation
     */
    public ActiveObjectives()
    {
        ArrayList<ObjectiveCard> temp = new ArrayList<>();
        temp.add(new ColorDiagonal());
        temp.add(new ColorVariety());
        temp.add(new ColumnColorVariety());
        temp.add(new ColumnShadeVariety());
        temp.add(new DeepShades());
        temp.add(new LightShades());
        temp.add(new MediumShades());
        temp.add(new RowColorVariety());
        temp.add(new RowShadeVariety());
        temp.add(new ShadeVariety());

        Deck<ObjectiveCard> objectiveCardDeck =new Deck<>(temp);
        objectiveCardDeck.shuffle();

        objectives.addAll(objectiveCardDeck.draw(3));
    }

    /**
     * Getter of in game objectives
     * @param objectivePosition position of the objective
     * @return an objective card or null if the value is invalid
     */
    public ObjectiveCard getObjective(int objectivePosition)
    {
        try
        {
            return objectives.get(objectivePosition);
        } catch (IndexOutOfBoundsException e)
        {
            return null;
        }
    }

    /**
     * Calculates the total public score of a player
     * @param player the player to be scored
     * @return the total score
     */
    public int totalScore(Player player)
    {
        int temp = 0;
        for(ObjectiveCard obj : objectives)
        {
            temp = temp + obj.score(player);
        }
        return temp;
    }
}
