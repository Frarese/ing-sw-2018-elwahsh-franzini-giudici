package it.polimi.se2018.controller.network;

import it.polimi.se2018.controller.network.client.Comm;
import it.polimi.se2018.controller.network.client.CommUtilizer;
import it.polimi.se2018.util.MatchIdentifier;

/**
 * Request used to notify clients that a match is starting
 * @author Francesco Franzini
 */
public class MatchBeginRequest extends AbsMatchReq {
    private final boolean host;

    /**
     * Initializes this request with the given parameter
     *
     * @param match the {@link it.polimi.se2018.util.MatchIdentifier} of the match
     * @param host flag to designate the host
     */
    public MatchBeginRequest(MatchIdentifier match,boolean host) {
        super(match);
        this.host=host;
        if(!checkValid())throw new IllegalArgumentException("Parameters cannot be null");
    }

    @Override
    public void clientHandle(Comm clientComm, CommUtilizer commUtilizer) {
        checkValid();
        clientComm.setMatchInfo(this.matchId);
        commUtilizer.notifyMatchStart(host);
    }

    @Override
    public boolean checkValid() {
        return matchId!=null;
    }


}
