package it.polimi.se2018.controller.network.server;

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
     * @param serverMain
     */
    public MatchMakingQueue(ServerMain serverMain) {
        this.serverMain=serverMain;
        this.q=new ConcurrentLinkedQueue<>();
    }

    /**
     * Adds the given client to matchmaking
     * @param client client to add
     */
    public void add(Client client) {
        throw new UnsupportedOperationException();
    }

    /**
     * Removes the given client from matchmaking
     * @param client client to remove
     */
    public void remove(Client client) {
        synchronized (q){
            if(q.contains(client)){
                q.remove(client);
            }
        }

    }


}