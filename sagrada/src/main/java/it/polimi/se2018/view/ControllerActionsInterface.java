package it.polimi.se2018.view;


import it.polimi.se2018.observable.CardView;
import it.polimi.se2018.observable.PlayerView;
import it.polimi.se2018.observable.ReserveView;
import it.polimi.se2018.observable.RoundTrackerView;
import it.polimi.se2018.util.MatchIdentifier;
import it.polimi.se2018.util.PatternView;
import it.polimi.se2018.util.ScoreEntry;

import java.util.List;

/**
 * Interface for Controller-View communication
 *
 * @author Mathyas Giudici
 */

public interface ControllerActionsInterface {

    /**
     * To enable o disable animation on the screen
     *
     * @param enable boolean value.
     */
    void animation(boolean enable);

    /**
     * Starts login operations
     *
     * @param displayWelcome true if a welcome is to be shown
     */
    void startLogin(boolean displayWelcome);

    /**
     * Communicates if login has success
     *
     * @param success represents the response
     * @param error   contains error message
     */
    void loginResult(boolean success, String error);

    /**
     * Communicates if layer has changed
     *
     * @param successRMI represents the response
     */
    void changeLayerResult(boolean successRMI);

    /**
     * Communicates if player successfully left match
     *
     * @param success represents the response
     */
    void leaveMatchResult(boolean success);

    /**
     * Communicates if player successfully logout
     *
     * @param success represents the response
     */
    void logoutResult(boolean success);

    /**
     * Lobby creation in View:
     * before method'call better if {@link #pullConnectedPlayers(List)} and {@link #pullLeaderBoard(List)}
     * have been called
     */
    void createLobby();

    /**
     * Updates View current connected players in lobby
     *
     * @param players contains the connected players
     */
    void pullConnectedPlayers(List<ScoreEntry> players);

    /**
     * Updates View current leader board in lobby
     *
     * @param leaderBoard contains the leaderBoard ordinated
     */
    void pullLeaderBoard(List<ScoreEntry> leaderBoard);

    /**
     * After a askLobby() request Controller returns to View the player's inviteList
     *
     * @param invite contains the invites
     */
    void pullInvitate(MatchIdentifier invite);

    /**
     * Before game starts player has to choose a pattern
     *
     * @param pattern1 first pattern
     * @param pattern2 second patter
     * @param pattern3 third patter
     * @param pattern4 fourth pattern
     * @param cardView contains card view observable object
     */
    void askPattern(PatternView pattern1, PatternView pattern2, PatternView pattern3, PatternView pattern4, CardView cardView);

    /**
     * View's method to initialize a game
     *
     * @param players          contains game's players
     * @param reserveView      contains reserve view observable object
     * @param roundTrackerView contains round tracker view observable object
     */
    void initGame(List<PlayerView> players, ReserveView reserveView, RoundTrackerView roundTrackerView);

    /**
     * Communicates to player when another player has left the match
     *
     * @param playerName contains the playerID of the player has just left
     */
    void otherPlayerLeave(String playerName);

    /**
     * Communicates to player when another player reconnect
     *
     * @param playerName contains the playerID of the player has just left
     */
    void otherPlayerReconnection(String playerName);

    /**
     * Communicates a player's turn start
     */
    void startTurn();

    /**
     * After a setDie() request Controller returns the result
     *
     * @param result      boolean value
     * @param errorString optional error message
     */
    void setDieResult(boolean result, String errorString);

    /**
     * Notifies a Die placement in a player's grid
     *  @param playerName   contains the player's name of the player has just done placement
     * @param height       contains the height position on the grid
     * @param width        contains the width position on the grid
     */
    void addUpdate(String playerName, int height, int width);

    /**
     * After a useToolCard() request Controller returns the result
     *
     * @param result      boolean value
     * @param errorString optional error message
     */
    void useToolCardResult(boolean result, String errorString);

    /**
     * Notifies a Tool Card use
     *
     * @param playerName contains the player's name of the player has just used a Tool Card
     * @param card       contains Tool Card's index
     */
    void useToolCardUpdate(String playerName, int card);

    /**
     * Communicates if player correctly has passed a turn
     *
     * @param result boolean value
     */
    void passTurnResult(boolean result);

    /**
     * Notifies when a player pass his turn
     *
     * @param playerName contains the player's name of the player just passed
     */
    void passTurnUpdate(String playerName);

    /**
     * Notifies when match ends
     *
     * @param matchIdentifier contains the MatchIdentifier of the match just ended
     * @param player0         contains the points of first player
     * @param player1         contains the points of second player
     * @param player2         contains the points of third player
     * @param player3         contains the points of fourth player
     */
    void gameEnd(MatchIdentifier matchIdentifier, int player0, int player1, int player2, int player3);

    /**
     * Notifies game abort
     */
    void abortMatch();

}
