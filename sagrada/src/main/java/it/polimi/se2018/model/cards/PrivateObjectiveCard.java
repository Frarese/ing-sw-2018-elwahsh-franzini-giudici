package it.polimi.se2018.model.cards;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.model.dice.Grid;
import it.polimi.se2018.model.Player;

public class PrivateObjectiveCard extends ObjectiveCard {

    private ColorModel color;


    public PrivateObjectiveCard( ColorModel color)
    {
        this.color = color;
    }

    public int score(Player player)
    {
        int temp = 0;
        for(int i = 0; i< Grid.HEIGHT; i++)
        {
            for(int j = 0; j<Grid.WIDTH; j++)
            {
                if(player.getGrid().getDie(i,j)!= null && player.getGrid().getDie(i,j).getColor() == color)
                    temp = temp + player.getGrid().getDie(i,j).getValue();
            }
        }
        return temp;
    }
}
