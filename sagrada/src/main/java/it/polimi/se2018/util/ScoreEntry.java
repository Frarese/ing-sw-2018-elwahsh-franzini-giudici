package it.polimi.se2018.util;

import java.io.Serializable;

/**
 * A helper class to wrap info about a user's current leaderboard data
 * @author Francesco Franzini
 */
public class ScoreEntry implements Serializable {

    public final String usn;
    public final int tot;
    public final int wins;

    /**
     * Initializes this ScoreEntry with the given values
     * @param usn username
     * @param tot total points of the user
     * @param wins total wins of the user
     */
    public ScoreEntry(String usn, int tot, int wins) {
        this.usn = usn;
        this.tot = tot;
        this.wins = wins;
    }
}