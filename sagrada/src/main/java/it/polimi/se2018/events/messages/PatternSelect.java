package it.polimi.se2018.events.messages;

import it.polimi.se2018.events.Event;
import it.polimi.se2018.model.IntColorPair;
import it.polimi.se2018.model.Pattern;

/**
 * One of the options from which the player has to choose from
 * @author Alì El wahsh
 */
public class PatternSelect extends Event {

    private String name;
    private int favourPoints;
    private IntColorPair [][] pattern = new IntColorPair[Pattern.HEIGHT][Pattern.WIDTH];

    /**
     * PatterSelect's constructor
     * @param p Pattern from model
     */
    public PatternSelect(Pattern p)
    {
        name = p.getName();
        favourPoints = p.getFavourPoints();
        for(int i = 0; i< Pattern.HEIGHT;i++)
        {
            for(int j = 0; j<Pattern.WIDTH;j++)
                pattern[i][j] = new IntColorPair(p.getValue(i,j),p.getColor(i,j));
        }
        description = "Pattern";
    }

    /**
     * Getter for pattern's name
     * @return pattern's name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for pattern's difficulty
     * @return pattern's difficulty
     */
    public int getFavourPoints() {
        return favourPoints;
    }

    /**
     * Getter for pattern's layout
     * @return pattern's layout
     */
    public IntColorPair[][] getPattern() {
        return pattern;
    }
}
