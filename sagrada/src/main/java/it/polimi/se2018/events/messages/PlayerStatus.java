package it.polimi.se2018.events.messages;

import it.polimi.se2018.events.Event;
import it.polimi.se2018.model.IntColorPair;
import it.polimi.se2018.model.Pattern;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.dice.Die;
import it.polimi.se2018.model.dice.Grid;

/**
 * Update on player status
 * @author Alì El wahsh
 */
public class PlayerStatus extends Event {

    private final String name;
    private final int id;
    private final int favourPoints;

    private final IntColorPair[][] grid = new IntColorPair[Grid.HEIGHT][Grid.WIDTH];
    private final IntColorPair[][] pattern = new IntColorPair[Pattern.HEIGHT][Pattern.WIDTH];
    private final boolean firstTurnPlacement;
    private final boolean secondTurnPlacement;
    private final boolean firstTurnCard;
    private final boolean secondTurnCard;

    /**
     * Constructor
     * @param player player information
     */
    public PlayerStatus(Player player)
    {
        this.name = player.getName();
        this.id = player.getId();
        this.favourPoints = player.getFavourPoints();

        for(int i = 0; i<Grid.HEIGHT; i++)
            for(int j =0; j<Grid.WIDTH; j++) {
                Die d;
                if((d = player.getGrid().getDie(i,j)) != null)
                    grid[i][j] = new IntColorPair(d.getValue(),d.getColor());
                if(player.getPattern()!= null)
                    pattern[i][j] = new IntColorPair(player.getPattern().getValue(i,j),player.getPattern().getColor(i,j));
        }

        this.firstTurnPlacement = player.canPlaceOnThisTurn(true);
        this.secondTurnPlacement = player.canPlaceOnThisTurn(false);
        this.firstTurnCard = player.canUseCardOnThisTurn(true);
        this.secondTurnCard = player.canUseCardOnThisTurn(false);

        this.description = "Player";
    }

    /**
     * Getter for grid status
     * @return grid status
     */
    public IntColorPair[][] getGrid() {
        return grid;
    }

    /**
     * Getter for pattern selected
     * @return pattern selected
     */
    public IntColorPair[][] getPattern() {
        return pattern;
    }

    /**
     * Getter for favour points status
     * @return favour points status
     */
    public int getFavourPoints() {
        return favourPoints;
    }

    /**
     * Getter for Player ID
     * @return Player ID
     */
    public int getId() {
        return id;
    }

    /**
     * Getter for username
     * @return player's username
     */
    public String getName() {
        return name;
    }

    /**
     * Returns player's first turn card rights
     * @return card rights
     */
    public boolean isFirstTurnCard() {
        return firstTurnCard;
    }

    /**
     * Returns player's first turn placement rights
     * @return placement rights
     */
    public boolean isFirstTurnPlacement() {
        return firstTurnPlacement;
    }

    /**
     * Returns player's second turn card rights
     * @return card rights
     */
    public boolean isSecondTurnCard() {
        return secondTurnCard;
    }

    /**
     * Returns player's second turn placement rights
     * @return placement rights
     */
    public boolean isSecondTurnPlacement() {
        return secondTurnPlacement;
    }
}
