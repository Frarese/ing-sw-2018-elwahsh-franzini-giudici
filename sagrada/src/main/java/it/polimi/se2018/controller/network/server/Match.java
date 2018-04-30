package it.polimi.se2018.controller.network.server;

import it.polimi.se2018.util.MatchIdentifier;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a match that is being played
 * @author Francesco Franzini
 */
class Match {
    private Client host;
    private final Client player0;
    private final Client player1;
    private final Client player2;
    private final Client player3;
    private final MatchIdentifier matchId;

    /**
     * Builds a Match object with the given parameters
     * @param matchId {@link it.polimi.se2018.util.MatchIdentifier} of this match
     * @param player0 {@link it.polimi.se2018.controller.network.server.Client} of the first player
     * @param player1 {@link it.polimi.se2018.controller.network.server.Client} of the second player
     * @param player2 {@link it.polimi.se2018.controller.network.server.Client} of the third player
     * @param player3 {@link it.polimi.se2018.controller.network.server.Client} of the fourth player
     */
    public Match(MatchIdentifier matchId, Client player0, Client player1, Client player2, Client player3) {
        this.matchId=matchId;
        this.player0=player0;
        this.player1=player1;
        this.player2=player2;
        this.player3=player3;
    }

    /**
     * Handles an object from a client
     * @param obj the serializable object to handle
     * @param source the client that has sent the object
     */
    public void handleObj(Serializable obj, Client source) {
        throw new UnsupportedOperationException();
    }

    /**
     * Builds a list of the players
     * @return a List of {@link it.polimi.se2018.controller.network.server.Client}
     */
    public List<Client> getClients() {
        ArrayList out=new ArrayList<Client>();
        out.add(player0);
        out.add(player1);
        if(player2!=null){
            out.add(player2);
            if(player3!=null){
                out.add(player3);
            }
        }
        return out;
    }

    /**
     * Notifies that a player has left
     * @param username the player's username
     */
    public void playerLeft(String username) {
        throw new UnsupportedOperationException();
    }

    /**
     * Notifies that a player has reconnected
     * @param username the player's username
     */
    public void playerReconnected(String username) {
        throw new UnsupportedOperationException();
    }


}