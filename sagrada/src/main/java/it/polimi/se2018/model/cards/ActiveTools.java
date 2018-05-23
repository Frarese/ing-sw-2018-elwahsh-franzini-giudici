package it.polimi.se2018.model.cards;

import it.polimi.se2018.model.cards.toolcards.*;

import java.util.ArrayList;

/**
 * Model representation of the tools in game
 * @author Alì El Wahsh
 */
public class ActiveTools {

    private ArrayList<ToolCard> tools = new ArrayList<>();

    /**
     * ActiveTools's constructor
     */
    public ActiveTools()
    {
        ArrayList<ToolCard> temp = new ArrayList<>();
        temp.add(new CopperFoilBurnisher());
        temp.add(new CorkBackedStraightedge());
        temp.add(new EglomiseBrush());
        temp.add(new FluxBrush());
        temp.add(new FluxRemover());
        temp.add(new GlazingHammer());
        temp.add(new GrindingStone());
        temp.add(new GrozingPliers());
        temp.add(new Lathekin());
        temp.add(new LensCutter());
        temp.add(new RunningPliers());
        temp.add(new TapWheel());

        Deck<ToolCard> objectiveCardDeck =new Deck<>(temp);
        objectiveCardDeck.shuffle();

        tools.addAll(objectiveCardDeck.draw(3));
    }

    /**
     * Getter for a tool card in game
     * @param toolNumber position of the tool card
     * @return the desired tool card or null in case of invalid position
     */
    public ToolCard getTool(int toolNumber)
    {
        try
        {
            return tools.get(toolNumber);
        } catch(IndexOutOfBoundsException e)
        {
            return null;
        }
    }
}
