package it.polimi.se2018.events.actions;

/**
 * Sent when a player ends his own turn
 */
public class PassTurn extends PlayerMove {

    /**
     * PassTurn's constructor
     * @param name player name
     */
    public PassTurn(String name)
    {
        this.playerName = name;
        this.description = "PassTurn";
    }
}
