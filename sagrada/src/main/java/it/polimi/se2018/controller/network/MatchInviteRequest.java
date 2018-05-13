package it.polimi.se2018.controller.network;

import it.polimi.se2018.controller.network.client.Comm;
import it.polimi.se2018.controller.network.client.CommUtilizer;
import it.polimi.se2018.controller.network.server.Client;
import it.polimi.se2018.controller.network.server.ServerMain;
import it.polimi.se2018.util.MatchIdentifier;

/**
 * Request used to invite players to a match
 * @author Francesco Franzini
 */
public class MatchInviteRequest extends AbsMatchReq {

    /**
     * Initializes this request with the given parameter
     *
     * @param match the {@link it.polimi.se2018.util.MatchIdentifier} of the match
     */
    public MatchInviteRequest(MatchIdentifier match) {
        super(match);
        if(!checkValid())throw new IllegalArgumentException("Match id cannot be null");
    }

    @Override
    public void serverHandle(Client client, ServerMain server) {
        if(!checkValid())return;
        server.addPendingMatch(matchId,client);
    }


    @Override
    public void clientHandle(Comm clientComm, CommUtilizer commUtilizer) {
        commUtilizer.notifyInvite(this.matchId);
    }

    @Override
    public boolean checkValid() {
        return matchId!=null;
    }

}
