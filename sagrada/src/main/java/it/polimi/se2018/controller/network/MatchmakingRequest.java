package it.polimi.se2018.controller.network;

import it.polimi.se2018.controller.network.client.Comm;
import it.polimi.se2018.controller.network.client.CommUtilizer;
import it.polimi.se2018.controller.network.server.ServerVisitor;

/**
 * Request used to join or leave matchmaking
 * @author Francesco Franzini
 */
public class MatchmakingRequest implements AbsReqServerLogic{
    public final boolean join;

    /**
     * Initializes this request with the given parameter
     * @param join true if this is a join request, false to leave
     */
    public MatchmakingRequest(boolean join){
        this.join=join;
    }

    @Override
    public void serverVisit(ServerVisitor sV) {
        sV.handle(this);
    }

    @Override
    public void clientHandle(Comm clientComm, CommUtilizer commUtilizer) {
        //This request means nothing if received by client
    }

    @Override
    public boolean checkValid() {
        return true;
    }
}
