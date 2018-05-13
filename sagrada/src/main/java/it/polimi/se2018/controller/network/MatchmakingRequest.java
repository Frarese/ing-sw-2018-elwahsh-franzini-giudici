package it.polimi.se2018.controller.network;

import it.polimi.se2018.controller.network.server.Client;
import it.polimi.se2018.controller.network.server.ServerMain;

/**
 * Request used to join or leave matchmaking
 * @author Francesco Franzini
 */
public class MatchmakingRequest extends AbsReqServerLogic{
    public final boolean join;

    /**
     * Initializes this request with the given parameter
     * @param join true if this is a join request, false to leave
     */
    public MatchmakingRequest(boolean join){
        this.join=join;
    }

    @Override
    public void serverHandle(Client client, ServerMain server) {
        if(join)server.addToMatchMaking(client);
        else server.removeFromMatchMaking(client);
    }

    @Override
    public boolean checkValid() {
        return true;
    }
}
