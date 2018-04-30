package it.polimi.se2018.controller.network.server;

import it.polimi.se2018.util.ScoreEntry;

import java.util.List;

/**
 * An abstract User Base
 * @author Francesco Franzini
 */
abstract class UserBase {

    /**
     * Checks if the given username is in the user base
     * @param usn username
     * @return true if the username exists
     */
    public abstract boolean containsUser(String usn);

    /**
     * Gets the given username's corresponding password
     * @param usn username
     * @return the password or {@code nulll} if the user is not existing
     */
    public abstract String getPw(String usn);

    /**
     * Adds a user to the User Base
     * @param usn username
     * @param pw password
     * @return true no errors were raised
     */
    public abstract boolean addUser(String usn, String pw);

    /**
     * Removes a user from the User Base
     * @param usn username
     * @return true if no errors were raised
     */
    public abstract boolean removeUser(String usn);

    /**
     * Gets the {@link it.polimi.se2018.util.ScoreEntry} corresponding to a given user
     * @param usn username
     * @return the ScoreEntry of that user or {@code null} if not present
     */
    public abstract ScoreEntry getUserScore(String usn);

    /**
     * Alters the given username's score values
     * @param usn username
     * @param dTot value to sum to the existing Point Total
     * @param dWins value to sum to the existing Win Total
     */
    public abstract void alterUserScore(String usn, Integer dTot, Integer dWins);

    /**
     * Returns the leaderboard for this User Base
     * @return a List of {@link it.polimi.se2018.util.ScoreEntry}
     */
    public abstract List<ScoreEntry> getLeaderBoard();


}