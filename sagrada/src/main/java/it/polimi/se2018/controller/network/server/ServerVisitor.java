package it.polimi.se2018.controller.network.server;

import it.polimi.se2018.controller.network.*;

/**
 * Visitor interface used in server-side request handling
 * @author Francesco Franzini
 */
@SuppressWarnings({"unused", "EmptyMethod"})
public interface ServerVisitor {

    /**
     * Handles a change layer request from the client
     * @param c request to handle
     */
    void handle(ChangeCLayerRequest c);

    /**
     * Handles a chat message from the client
     * @param c message request to handle
     */
    void handle(ChatMessageRequest c);

    /**
     * Handles a request for the match controller from the client
     * @param c request wrapper to handle
     */
    void handle(ClientRequest c);

    /**
     * Handles a request for a leaderboard update from the client
     * @param c request to handle
     */
    void handle(GetLeaderBoardRequest c);

    /**
     * Handles a keep-alive request from the client
     * @param c request to handle
     */
    void handle(KeepAliveRequest c);

    /**
     * Handles a request to leave the current match from the client
     * @param c request to handle
     */
    void handle(LeaveMatchRequest c);

    /**
     * Handles a request for a lobby update from the client
     * @param c request to handle
     */
    void handle(ListUsersRequest c);

    /**
     * Handles a logout request from the client
     * @param c request to handle
     */
    void handle(LogoutRequest c);

    /**
     * Handles an abort request from the client(not used)
     * This method is created to catch eventual requests and should do nothing
     * @param c request to handle
     */
    void handle(MatchAbortedRequest c);

    /**
     * Handles a match begin request from the client(not used)
     * This method is created to catch eventual requests and should do nothing
     * @param c request to handle
     */
    void handle(MatchBeginRequest c);

    /**
     * Handles a match end request from the client(not used)
     * This method is created to catch eventual requests and should do nothing
     * @param c request to handle
     */
    void handle(MatchEndedRequest c);

    /**
     * Handles an invite request from the client
     * @param c invite to handle
     */
    void handle(MatchInviteRequest c);

    /**
     * Handles a matchmaking request from the client
     * @param c request to handle
     */
    void handle(MatchmakingRequest c);

    /**
     * Handles an invite response request from the client
     * @param c request to handle
     */
    void handle(MatchResponseRequest c);

    /**
     * Handles a user reconnected request from the client(not used)
     * This method is created to catch eventual requests and should do nothing
     * @param c request to handle
     */
    void handle(UserReconnectedRequest c);
}
