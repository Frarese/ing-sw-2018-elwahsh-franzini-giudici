package it.polimi.se2018.controller.network.server;


import it.polimi.se2018.controller.network.MatchAbortedRequest;
import it.polimi.se2018.util.MatchIdentifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a match that has not yet been approved by all players
 * @author Francesco Franzini
 */
public class PendingApprovalMatch {
    static final int DEFAULT_TIMEOUT = 60;
    private final HashMap<Integer,Client> clients;
    private final Timer t;
    private final ServerMain serverMain;
    public final MatchIdentifier matchId;
    private final Logger logger;

    /**
     * Creates a new PendingApprovalMatch with the given parameters
     * @param timeout time(in seconds) to wait until match is aborted
     * @param matchId {@link it.polimi.se2018.util.MatchIdentifier} of this match
     * @param serverMain mainServer to use
     * @param source the client that originated this match
     */
    PendingApprovalMatch(int timeout, MatchIdentifier matchId, ServerMain serverMain, Client source) {
        this.matchId=matchId;
        this.logger=Logger.getGlobal();
        this.serverMain=serverMain;
        clients=new HashMap<>(matchId.playerCount);

        int pos=matchId.findPos(source.usn);
        if(pos==-1)throw new IllegalArgumentException("Client is not in the matchID");
        clients.put(pos,source);
        t=new Timer();
        TimerTask tS=new TimerTask() {
            @Override
            public void run() {
                abort();
            }
        };
        t.schedule(tS,(long)timeout*1000);
    }

    /**
     * Notifies that a client has accepted
     * @param client the client that has accepted
     * @return true if no errors were raised
     */
    public synchronized boolean clientAccepted(Client client) {
        logger.log(Level.FINEST,"User {0} accepted match",client.usn);
        int pos;
        if((pos=matchId.findPos(client.usn))!=-1 && !clients.containsKey(pos)){
            if(client.acceptInvite()){
                clients.put(pos,client);
                client.acceptPAMatch(this);

                isComplete();
                return true;
            }
            abort();
        }
        return false;
    }

    /**
     * Checks if this match has been approved by all the players and if so registers the match
     */
    private synchronized void isComplete() {
        if(clients.keySet().size()!=matchId.playerCount){
            return;
        }
        t.cancel();
        Match m= buildMatch();
        serverMain.addMatch(m);
        serverMain.removePendingMatch(this);
    }

    /**
     * Builds a {@link it.polimi.se2018.controller.network.server.Match} from this object
     * @return the Match that was built
     */
    private synchronized Match buildMatch() {
        return new Match(matchId,new ArrayList<>(clients.values()),serverMain);
    }


    /**
     * Aborts this pending match
     */
    public synchronized void abort(){
        t.cancel();

        clients.forEach((pos,c)-> c.pushOutReq(new MatchAbortedRequest(matchId)));
        clients.forEach((pos,c)-> c.removePAMInstance());
        clients.forEach((pos,c)-> c.resetAccepted());

        serverMain.removePendingMatch(this);
    }
}
