package it.polimi.se2018.controller.network.server;

import java.io.Serializable;

public interface MatchController {

    /**
     * Pushes a request from a client to the match controller
     * @param sender sender username
     * @param req the serialized request
     */
    void handleRequest(String sender, Serializable req);

    /**
     * Kills this controller
     */
    void kill();

    /**
     * Notifies the controller that a user has reconnected
     * @param username username
     */
    void userReconnected(String username);

    /**
     * Notifies the controller that a user has disconnected
     * @param username username
     * @param permanent true if the user has permanently left the match
     */
    void playerLeft(String username, boolean permanent);
}
