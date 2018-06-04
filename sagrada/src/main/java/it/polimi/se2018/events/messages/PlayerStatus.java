package it.polimi.se2018.events.messages;

import it.polimi.se2018.events.Event;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.dice.Die;
import it.polimi.se2018.model.dice.Grid;
import it.polimi.se2018.util.Pair;

/**
 * Update on player status
 * @author Alì El wahsh
 */
public class PlayerStatus extends Event {

    private String name;
    private int id;
    private int favourPoints;
    private int privateObjectiveId;

    private Pair[][] grid = new Pair[Grid.WIDTH][Grid.HEIGHT];

    /**
     * Constructor
     * @param player player information
     */
    public PlayerStatus(Player player)
    {
        this.name = player.getName();
        this.id = player.getId();
        this.favourPoints = player.getFavourPoints();
        this.privateObjectiveId = player.getPrivateObjective().getId();

        for(int i = 0; i<Grid.HEIGHT; i++)
            for(int j =0; j<Grid.WIDTH; j++) {
                Die d;
                if((d = player.getGrid().getDie(i,j)) != null)
                grid[i][j] = new Pair<>(d.getColor(),d.getValue());
            }

        this.description = "Player";
    }

    /**
     * Getter for grid status
     * @return grid status
     */
    public Pair[][] getGrid() {
        return grid;
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
     * Getter for private Objective ID
     * @return Private Objective ID
     */
    public int getPrivateObjectiveId() {
        return privateObjectiveId;
    }


}
