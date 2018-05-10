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
     * Pushes a request received from another client
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
     */
    void notifyMatchEnd( );

    /**
     * Notifies that the accepted match is starting
     * @param isHost true if this client is to be the host
     */
    void notifyMatchStart( boolean isHost );

    /**
     * Notifies that a user has been kicked
     * @param usn the username of the kicked user
     */
    void notifyKicked( String usn );

    /**
     * Notifies that a user has left
     * @param usn username
     * @param isNewHost true if this client is to be the new host
     */
    void notifyUserLeft( String usn, boolean isNewHost );

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
