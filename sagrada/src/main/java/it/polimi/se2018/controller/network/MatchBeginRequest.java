package it.polimi.se2018.controller.network;

import it.polimi.se2018.controller.network.client.Comm;
import it.polimi.se2018.controller.network.client.CommUtilizer;
import it.polimi.se2018.controller.network.server.ServerVisitor;
import it.polimi.se2018.util.MatchIdentifier;

/**
 * Request used to notify clients that a match is starting
 * @author Francesco Franzini
 */
public class MatchBeginRequest extends AbsMatchReq {

    /**
     * Initializes this request with the given parameter
     *
     * @param match the {@link it.polimi.se2018.util.MatchIdentifier} of the match
     */
    public MatchBeginRequest(MatchIdentifier match) {
        super(match);
        if(!checkValid())throw new IllegalArgumentException("Parameters cannot be null");
    }

    @Override
    public void serverVisit(ServerVisitor sV) {
        sV.handle(this);
    }

    @Override
    public void clientHandle(Comm clientComm, CommUtilizer commUtilizer) {
        checkValid();
        clientComm.setMatchInfo(this.matchId);
        commUtilizer.notifyMatchStart();
    }

    @Override
    public boolean checkValid() {
        return matchId!=null;
    }


}
