package it.polimi.se2018.controller.network;

import it.polimi.se2018.controller.network.client.Comm;
import it.polimi.se2018.controller.network.client.CommUtilizer;
import it.polimi.se2018.controller.network.server.ServerVisitor;
import it.polimi.se2018.util.MatchIdentifier;

/**
 * Request used to answer to an invite
 * @author Francesco Franzini
 */
public class MatchResponseRequest extends AbsMatchReq {
    public final boolean accept;

    /**
     * Initializes this request with the given parameter
     * @param accept outcome of the response
     * @param match the {@link it.polimi.se2018.util.MatchIdentifier} of the match
     */
    public MatchResponseRequest(boolean accept, MatchIdentifier match) {
        super(match);
        this.accept=accept;
        if(!checkValid())throw new IllegalArgumentException("Match id cannot be null");
    }


    @Override
    public boolean checkValid() {
        return matchId!=null;
    }

    @Override
    public void serverVisit(ServerVisitor sV) {
        sV.handle(this);
    }

    @Override
    public void clientHandle(Comm clientComm, CommUtilizer commUtilizer) {
        //This request means nothing if received by server
    }


}
