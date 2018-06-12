package it.polimi.se2018.events.actions;

/**
 * Die selected from round track
 * @author Alì El wahsh
 */
public class DieFromRoundTrack extends PlayerMove {

    private final int round;
    private final int diePosition;

    /**
     * Constructor
     * @param player player's name
     * @param round round position
     * @param diePosition die position inside round
     */
    public DieFromRoundTrack(String player, int round, int diePosition)
    {
        this.playerName = player;
        this.round = round;
        this.diePosition = diePosition;
        this.description = "DieFromRoundTrack";
    }

    /**
     * Getter for round position
     * @return round
     */
    public int getRound() {
        return round;
    }

    /**
     * Getter for die position
     * @return die position
     */
    public int getDiePosition() {
        return diePosition;
    }
}
