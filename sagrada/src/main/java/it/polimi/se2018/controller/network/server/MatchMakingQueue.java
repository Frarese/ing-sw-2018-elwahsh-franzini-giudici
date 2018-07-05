package it.polimi.se2018.controller.network.server;

import it.polimi.se2018.util.MatchIdentifier;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Represents the matchmaking queue
 * @author Francesco Franzini
 */
class MatchMakingQueue {
    private final ServerMain serverMain;
    private final Queue<Client> q;

    /**
     * Creates a new MatchMaking Queue
     * @param serverMain main server
     */
    MatchMakingQueue(ServerMain serverMain) {
        this.serverMain=serverMain;
        this.q=new ConcurrentLinkedQueue<>();
    }

    /**
     * Adds the given client to matchmaking
     * @param client client to add
     */
    synchronized void add(Client client) {
        if(q.contains(client))return;
        q.add(client);
        this.detachPendingMatch();
    }

    /**
     * Removes the given client from matchmaking
     * @param client client to remove
     */
    synchronized void remove(Client client) {
        q.remove(client);
    }

    /**
     * Detaches a pending match if possible, does nothing otherwise
     */
    private synchronized void detachPendingMatch(){
        if(q.size()>=4){
            Client c1=q.remove();
            Client c2=q.remove();
            Client c3=q.remove();
            Client c4=q.remove();
            MatchIdentifier mId=new MatchIdentifier(c1.usn,c2.usn,c3.usn,c4.usn);
            serverMain.addPendingMatch(mId,null);
        }
    }

    boolean contains(Client c){
        return q.contains(c);
    }
}