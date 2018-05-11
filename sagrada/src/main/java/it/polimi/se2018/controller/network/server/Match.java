package it.polimi.se2018.controller.network.server;

import it.polimi.se2018.controller.network.LeaveMatchRequest;
import it.polimi.se2018.controller.network.MatchAbortedRequest;
import it.polimi.se2018.controller.network.MatchBeginRequest;
import it.polimi.se2018.controller.network.UserReconnectedRequest;
import it.polimi.se2018.util.MatchIdentifier;

import java.io.Serializable;
import java.util.*;

/**
 * Represents a match that is being played
 * @author Francesco Franzini
 */
public class Match {
    private int hostI;
    private final HashMap<Integer,Client> clientMap;
    public final MatchIdentifier matchId;
    private final Set<String> dc;
    private final ServerMain serverMain;
    /**
     * Builds a Match object with the given parameters
     * @param matchId {@link it.polimi.se2018.util.MatchIdentifier} of this match
     * @param clients List of {@link it.polimi.se2018.controller.network.server.Client}, order is irrelevant
     */
    Match(MatchIdentifier matchId, List<Client> clients,ServerMain serverMain) {
        this.serverMain=serverMain;
        this.matchId=matchId;
        this.dc=new HashSet<>();
        this.clientMap=new HashMap<>(matchId.playerCount);
        if(matchId.playerCount!=clients.size()){
            throw new IllegalArgumentException("Not the right amount of clients");
        }
        for(Client c:clients){
            int pos=matchId.findPos(c.usn);
            if(pos!=-1 && !clientMap.containsKey(pos)){
                clientMap.put(pos,c);
            }else{
                throw new IllegalArgumentException("Supplied Clients are not valid");
            }
        }
        hostI=0;

        clientMap.forEach((pos,c)->
                c.pushOutReq(new MatchBeginRequest(matchId,pos==hostI))
        );

    }

    /**
     * Handles an object from a client
     * @param obj the serializable object to handle
     * @param source the client that has sent the object
     */
    public synchronized void handleObj(Serializable obj, Client source) {
        throw new UnsupportedOperationException();
    }

    /**
     * Builds a list of the players
     * @return a List of {@link it.polimi.se2018.controller.network.server.Client}
     */
    public List<Client> getClients() {
        return new ArrayList<>(clientMap.values());
    }

    /**
     * Notifies that a player has left
     * @param username the player's username
     */
    public synchronized void playerLeft(String username) {
        if(matchId.findPos(username)==-1)return;
        dc.add(username);
        if(matchId.playerCount-dc.size()<2){
            this.abort();
            return;
        }
        if(username.equals(clientMap.get(hostI).usn)){
            for(Map.Entry<Integer,Client> e:clientMap.entrySet()){
                Client c=e.getValue();
                Integer pos=e.getKey();

                if(!dc.contains(c.usn)){
                    hostI=pos;
                }
            }
        }
        notifyDisconnection(username);
    }

    /**
     * Notifies that a player has reconnected
     * @param username the player's username
     */
    public synchronized void playerReconnected(String username) {
        if(!dc.contains(username))return;
        clientMap.values().stream().filter(c->!c.usn.equals(username))
                .forEach(c->c.pushOutReq(new UserReconnectedRequest(username)));
        dc.remove(username);
    }

    /**
     * Aborts this match
     */
    public synchronized void abort(){
        clientMap.forEach((pos,c)->c.pushOutReq(new MatchAbortedRequest(matchId)));
        serverMain.removeMatch(this);
        clientMap.forEach((pos,c)->{c.resetAccepted();c.removeMatchInstance();});
    }

    /**
     * Notifies players that a user has disconnected
     * @param usn username of the disconnected user
     */
    private synchronized void notifyDisconnection(String usn){
        for(Map.Entry<Integer,Client> e:clientMap.entrySet()) {
            Client c = e.getValue();
            Integer pos = e.getKey();
            if(!c.usn.equals(usn)){
                c.pushOutReq(new LeaveMatchRequest(usn,pos==hostI));
            }
        }
    }

}