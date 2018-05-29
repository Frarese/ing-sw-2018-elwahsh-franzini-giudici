package it.polimi.se2018.controller.network.server;

import java.io.Serializable;

/**
 * The interface of the Network layer for a Match Controller
 * @author Francesco Franzini
 */
public interface MatchNetworkInterface {
    /**
     * Ends this match
     * @param playerScore0 player0's score
     * @param playerScore1 player1's score
     * @param playerScore2 player2's score
     * @param playerScore3 player3's score
     */
    void end(int playerScore0, int playerScore1, int playerScore2, int playerScore3);

    /**
     * Sends a request to the specified client
     * @param req request to send
     * @param dst username of the destination
     */
    void sendReq(Serializable req,String dst);

    /**
     * Sends an object to all the players
     * @param obj the object to send
     */
    void sendObj(Serializable obj);

    /**
     * Aborts this match
     */
    void abort();
}
