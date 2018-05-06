package it.polimi.se2018.view;


import it.polimi.se2018.util.Pair;
import it.polimi.se2018.observer.PlayerView;
import it.polimi.se2018.observer.ReserveView;
import it.polimi.se2018.observer.RoundTrackerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Interface for Controller -> View communication
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
     * To communicate if login has success
     *
     * @param success represents the response
     */
    void loginResult(boolean success);

    /**
     * To communicate if layer has changed
     *
     * @param successRMI represents the response
     */
    void changeLayerResult(boolean successRMI);

    /**
     * To communicate if player successfully left match
     *
     * @param success represents the response
     */
    void leaveMatchResult(boolean success);

    /**
     * To communicate if player successfully logout
     *
     * @param success represents the response
     */
    void logoutResult(boolean success);

    /**
     * After a askLobby() request Controller returns to View the leaderBoard
     *
     * @param leaderBoard contains the leaderBoard
     */
    void pullLeaderBoard(List leaderBoard);

    /**
     * After a askLobby() request Controller returns to View the player's inviteList
     *
     * @param inviteList contains the invites
     */
    void pullInvitate(List inviteList);

    /**
     * After a askLobby() request Controller returns to View the connected users
     *
     * @param users contains the connected users
     */
    void pullConnectedUsers(List users);

    /**
     * Before game starts player has to choose a pattern
     *
     * @param pattern1 first pattern
     * @param pattern2 second patter
     * @param pattern3 third patter
     * @param pattern4 fourth pattern
     */
    void askPattern(Pair pattern1, Pair pattern2, Pair pattern3, Pair pattern4);

    /**
     * View's metod to initialize a game
     *
     * @param players                  contains game's players
     * @param yourPrivateObjectiveCard contains localPlayer's private objective card
     * @param publicObjectiveCards     contains public objective cards
     * @param toolCards                contains tool cards
     * @param roundTracker             contains game's round tracker
     */
    void initGame(List players, int yourPrivateObjectiveCard, ArrayList publicObjectiveCards, ArrayList toolCards, RoundTrackerView roundTracker);

    /**
     * To communicate to player when another player has left the match
     *
     * @param playerID contains the playerID of the player has just left
     */
    void otherPlayerLeave(int playerID);

    /**
     * To communicate to player when another player reconnect
     *
     * @param playerID contains the playerID of the player has just left
     */
    void otherPlayerReconnection(int playerID);

    /**
     * To communicate a player's turn start
     *
     * @param player       contains the ID of the player
     * @param reserve      contains a ReserveView object
     * @param roundTracker contains a RoundTrackerView object
     */
    void startTurn(PlayerView player, ReserveView reserve, RoundTrackerView roundTracker);

    /**
     * After a setDie() request Controller returns the result
     *
     * @param result      boolean value
     * @param errorString optional error message
     */
    void setDieResult(boolean result, String errorString);

    /**
     * To disable the Reserve usability
     */
    void cannotUseReserve();

    /**
     * To notify a Die placement in a player's grid
     *
     * @param playerID     contains the playerID of the player has just done placement
     * @param height       contains the height position on the grid
     * @param width        contains the width position on the grid
     * @param reserveIndex contains the reserveIndex of the die just placed
     */
    void addUpdate(int playerID, int height, int width, int reserveIndex);

    /**
     * After a useToolCard() request Controller returns the result
     *
     * @param result      boolean value
     * @param errorString optional error message
     */
    void useToolCardResult(boolean result, String errorString);

    /**
     * To notify a Tool Card use
     *
     * @param playerID contains the playerID of the player has just used a Tool Card
     * @param card     contains Tool Card's index
     */
    void useToolCardUpdate(int playerID, int card);

    /**
     * To communicate if player correctly has passed a turn
     *
     * @param result boolean value
     */
    void passTurnResult(boolean result);

    /**
     * To notify when a player pass his turn
     *
     * @param playerID contains the playerID of the player just passed
     */
    void passTurnUpdate(int playerID);

    /**
     * To notify when match ends
     *
     * @param scores contains a list of score,player (order)
     */
    void gameEnd(ArrayList scores);

}
