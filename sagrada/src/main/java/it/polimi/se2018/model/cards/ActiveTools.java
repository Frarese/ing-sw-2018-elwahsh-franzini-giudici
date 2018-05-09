package it.polimi.se2018.model.cards;

import java.util.ArrayList;

public class ActiveTools {

    private ArrayList<ToolCard> tools = new ArrayList<>();

    public ActiveTools()
    {
        throw new UnsupportedOperationException();
    }

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
