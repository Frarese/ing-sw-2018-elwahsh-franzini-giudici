package it.polimi.se2018.controller.network.server;

import java.io.Serializable;

public interface MatchNetworkInterface {
    void end(int playerScore0, int playerScore1, int playerScore2, int playerScore3);
    void sendReq(Serializable req,String dst);
    void sendObj(Serializable obj);

    /**
     * Aborts this match
     */
    void abort();
}
