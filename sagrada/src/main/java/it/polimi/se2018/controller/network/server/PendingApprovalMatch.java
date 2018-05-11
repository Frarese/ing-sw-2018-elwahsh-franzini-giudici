package it.polimi.se2018.controller.network.server;


import it.polimi.se2018.controller.network.MatchAbortedRequest;
import it.polimi.se2018.util.MatchIdentifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Represents a match that has not yet been approved by all players
 */
public class PendingApprovalMatch extends TimerTask {
    static final int DEFAULT_TIMEOUT = 60;
    private final HashMap<Integer,Client> clients;
    private Timer t;
    private final ServerMain serverMain;
    public final MatchIdentifier matchId;

    /**
     * Creates a new PendingApprovalMatch with the given parameters
     * @param timeout time(in seconds) to wait until match is aborted
     * @param matchId {@link it.polimi.se2018.util.MatchIdentifier} of this match
     * @param serverMain mainServer to use
     * @param source the client that originated this match
     */
    PendingApprovalMatch(int timeout, MatchIdentifier matchId, ServerMain serverMain, Client source) {
        this.matchId=matchId;
        this.serverMain=serverMain;
        clients=new HashMap<>(matchId.playerCount);

        int pos=matchId.findPos(source.usn);
        if(pos==-1)throw new IllegalArgumentException("Client is not in the matchID");
        clients.put(pos,source);

        t=new Timer();
        t.schedule(this,timeout);
    }

    /**
     * Notifies that a client has accepted
     * @param client the client that has accepted
     * @return true if no errors were raised
     */
    public synchronized boolean clientAccepted(Client client) {
        int pos;
        if((pos=matchId.findPos(client.usn))!=-1 && !clients.containsKey(pos)){
            clients.put(pos,client);
            client.acceptPAMatch(this);
            return true;
        }
        return false;
    }

    /**
     * Checks if this match has been approved by all the players and if so registers the match
     * @return true if this match has been approved by all the players
     */
    public synchronized boolean isComplete() {
        if(clients.keySet().size()!=matchId.playerCount){
            return false;
        }
        t.cancel();
        Match m= buildMatch();
        serverMain.addMatch(m);
        serverMain.removePendingMatch(this);
        return true;
    }

    @Override
    public void run() {
        this.abort();
    }

    /**
     * Builds a {@link it.polimi.se2018.controller.network.server.Match} from this object
     * @return the Match that was built
     */
    synchronized Match buildMatch() {
        return new Match(matchId,new ArrayList<>(clients.values()),serverMain);
    }


    /**
     * Aborts this pending match
     */
    synchronized void abort(){
        t.cancel();

        clients.forEach((pos,c)-> c.pushOutReq(new MatchAbortedRequest(matchId)));
        clients.forEach((pos,c)-> c.removePAMInstance());
        clients.forEach((pos,c)-> c.resetAccepted());

        serverMain.removePendingMatch(this);
    }
}