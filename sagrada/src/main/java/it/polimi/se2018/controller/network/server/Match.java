package it.polimi.se2018.controller.network.server;

import it.polimi.se2018.controller.network.*;
import it.polimi.se2018.util.MatchIdentifier;

import java.io.Serializable;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a match that is being played
 * @author Francesco Franzini
 */
public class Match implements MatchNetworkInterface{
    private final HashMap<Integer,Client> clientMap;
    public final MatchIdentifier matchId;
    private final Set<String> dc;
    private final ServerMain serverMain;
    private final MatchController control;
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
            if(pos!=-1 && !clientMap.containsKey(pos) && c.enrollInMatch(this)){
                clientMap.put(pos,c);
            }else{
                this.abort();
                throw new IllegalArgumentException("Supplied Clients are not valid");
            }
        }

        clientMap.forEach((pos,c)->
                c.pushOutReq(new MatchBeginRequest(matchId))
        );
        clientMap.forEach((pos,c)->serverMain.removeFromMatchMaking(c));
        this.control=serverMain.factory.buildMatch(matchId,this);
        Logger.getGlobal().log(Level.INFO,"Match starting {0}",matchId);

    }

    /**
     * Handles an object from a client
     * @param obj the serializable object to handle
     * @param source the client that has sent the object
     */
    public synchronized void handleObj(Serializable obj, Client source) {
        clientMap.forEach((i,c)->{
            if(!dc.contains(c.usn) && (source!=c)){
                c.pushOutObj(obj);
            }
        });
    }

    /**
     * Builds a list of the players
     * @return a List of {@link it.polimi.se2018.controller.network.server.Client}
     */
    public List<Client> getClients() {
        return new ArrayList<>(clientMap.values());
    }

    /**
     * Notifies that a player has left or disconnected
     * @param username the player's username
     * @param hasDc true if this was a disconnection and a return may be possible
     */
    synchronized void playerLeft(String username,boolean hasDc) {
        if(matchId.findPos(username)==-1 || dc.contains(username))return;
        dc.add(username);
        if(matchId.playerCount-dc.size()<2){
            this.abort();
            return;
        }
        notifyDisconnection(username);
        if(!hasDc){
            Client c=serverMain.getClient(username);
            c.removeMatchInstance();
            c.resetAccepted();
        }
        control.playerLeft(username,!hasDc);
    }

    /**
     * Notifies that a player has reconnected
     * @param username the player's username
     */
    synchronized void playerReconnected(String username) {
        if(!dc.contains(username))return;
        clientMap.values().stream().filter(c->!c.usn.equals(username))
                .forEach(c->c.pushOutReq(new UserReconnectedRequest(username)));
        dc.remove(username);
        control.userReconnected(username);
    }


    /**
     * Notifies players that a user has disconnected
     * @param usn username of the disconnected user
     */
    private synchronized void notifyDisconnection(String usn){
        for(Map.Entry<Integer,Client> e:clientMap.entrySet()) {
            Client c = e.getValue();
            if(!c.usn.equals(usn)){
                c.pushOutReq(new LeaveMatchRequest(usn));
            }
        }
    }

    /**
     * Pushes a request from a client to the match controller
     * @param usn sender username
     * @param serializedReq the serialized request
     */
    public void handleReq(String usn, Serializable serializedReq) {
        this.control.handleRequest(usn,serializedReq);
    }

    @Override
    public synchronized void end(int playerScore0, int playerScore1, int playerScore2, int playerScore3) {
        int max=(playerScore0>playerScore1)?playerScore0:playerScore1;
        max=(max>playerScore2)?max:playerScore2;
        max=(max>playerScore3)?max:playerScore3;
        int[]scores={playerScore0,playerScore1,playerScore2,playerScore3};
        for(int i=0;i<matchId.playerCount;i++){
            Client c=clientMap.get(i);
            if(c==null)continue;
            serverMain.updateScore(c.usn,scores[i],(scores[i]==max)?1:0);
        }
        MatchEndedRequest mER=new MatchEndedRequest(matchId,playerScore0,playerScore1,playerScore2,playerScore3);
        clientMap.forEach((pos,c)->{c.resetAccepted();c.removeMatchInstance();c.pushOutReq(mER);});
        serverMain.removeMatch(this);
    }

    @Override
    public void sendReq(Serializable req,String dst) {
        Client c=clientMap.get(matchId.findPos(dst));
        if(c==null){
            Logger.getGlobal().log(Level.WARNING,"Unknown match player {0}",dst);
            return;
        }
        if(!dc.contains(c.usn)){
            c.pushOutReq(new ClientRequest(req));
        }
    }

    @Override
    public void sendObj(Serializable obj) {
        for (Client c:clientMap.values()) {
            c.pushOutObj(obj);
        }
    }

    @Override
    public synchronized void abort(){
        clientMap.forEach((pos,c)->c.pushOutReq(new MatchAbortedRequest(matchId)));
        clientMap.forEach((pos,c)->{c.resetAccepted();c.removeMatchInstance();});
        serverMain.removeMatch(this);
        if(control!=null)control.kill();
    }
}