package it.polimi.se2018.controller.network.server;


import it.polimi.se2018.util.MatchIdentifier;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Represents a match that has not yet been approved by all players
 */
class PendingApprovalMatch extends TimerTask {
    private Client player0;
    private Client player1;
    private Client player2;
    private Client player3;
    private Timer timer;
    private final ServerMain serverMain;
    public final MatchIdentifier matchId;
    private int hostIndex;

    /**
     * Creates a new PendingApprovalMatch with the given parameters
     * @param timeout time(in seconds) to wait until match is aborted
     * @param matchId {@link it.polimi.se2018.util.MatchIdentifier} of this match
     * @param serverMain mainServer to use
     * @param source the client that originated this match
     */
    public PendingApprovalMatch(int timeout, MatchIdentifier matchId, ServerMain serverMain, Client source) {
        this.matchId=matchId;
        this.serverMain=serverMain;
        throw new UnsupportedOperationException();
    }

    /**
     * Notifies that a client has accepted
     * @param client the client that has accepted
     * @return true if no errors were raised
     */
    public boolean clientAccepted(Client client) {
        throw new UnsupportedOperationException();
    }

    /**
     * Checks if this match has been approved by all the players
     * @return true if this match has been approved by all the players
     */
    public boolean isComplete() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException();
    }

    /**
     * Builds a {@link it.polimi.se2018.controller.network.server.Match} from this object
     * @return the Match that was built
     */
    public Match buildMatch() {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets which user is to be the host
     * @param newHostIndex the index of the new host
     * @return true if the index was valid (0<=newHostIndex<4)
     */
    public boolean setHostIndex(int newHostIndex) {
        if(newHostIndex>3 || newHostIndex<0) return false;
        this.hostIndex=newHostIndex;
        return true;
    }

}
