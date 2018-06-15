package it.polimi.se2018.controller.network.client;

import it.polimi.se2018.util.MatchIdentifier;
import it.polimi.se2018.util.MessageTypes;
import it.polimi.se2018.util.ScoreEntry;

import java.io.Serializable;
import java.util.List;

/**
 * The interface used to push events coming from the network layer
 * @author Francesco Franzini
 */
public interface CommUtilizer
{
    /**
     * Pushes an object received from the server
     * @param obj the object that was received
     */
    void receiveObject( Serializable obj );

    /**
     * Pushes a request received from the server
     * @param req the req that was received
     */
    void receiveRequest( Serializable req );

    /**
     * Notifies that the current match has been aborted
     */
    void abortMatch( );

    /**
     * Notifies that an invite has been received
     * @param match the match descriptor
     */
    void notifyInvite( MatchIdentifier match );

    /**
     * Notifies that the match has ended
     * @param playerScore0 player0's score
     * @param playerScore1 player1's score
     * @param playerScore2 player2's score
     * @param playerScore3 player3's score
     */
    void notifyMatchEnd(int playerScore0, int playerScore1, int playerScore2, int playerScore3);

    /**
     * Notifies that the accepted match is starting
     * @param mId match identifier of the match
     */
    void notifyMatchStart(MatchIdentifier mId );


    /**
     * Notifies that a user has left
     * @param usn username
     */
    void notifyUserLeft( String usn);

    /**
     * Pushes the updated leaderboard
     * @param list a List of {@link it.polimi.se2018.util.ScoreEntry} with the leaderboard(ordered desc)
     */
    void pushLeaderboard( List<ScoreEntry> list );

    /**
     * Pushes the updated logged users list
     * @param list a List of {@link it.polimi.se2018.util.ScoreEntry} of the logged users(not ordered)
     */
    void pushUserList( List<ScoreEntry> list );

    /**
     * Notifies that the network is irreparably down
     */
    void notifyCommDropped( );

    /**
     * Pushes a received chat message
     * @param msg the message
     * @param type the {@link it.polimi.se2018.util.MessageTypes} of this message
     * @param source the username of the sender
     */
    void pushChatMessage(String msg, MessageTypes type, String source );

    /**
     * Notifies that the network is back up
     */
    void notifyReconnect( );

    /**
     * Notifies that a player has reconnected
     * @param usn the username of the reconnected client
     */
    void notifyUserReconnected( String usn );


}
