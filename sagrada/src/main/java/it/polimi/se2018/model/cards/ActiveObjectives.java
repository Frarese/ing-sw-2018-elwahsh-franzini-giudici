package it.polimi.se2018.model.cards;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.cards.objectivecards.*;

import java.util.ArrayList;

/**
 * Model representation of the public objectives in game
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

    public ObjectiveCard getObjective(int numberOfObjective)
    {
        try
        {
            return objectives.get(numberOfObjective);
        } catch (IndexOutOfBoundsException e)
        {
            return null;
        }
    }

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
