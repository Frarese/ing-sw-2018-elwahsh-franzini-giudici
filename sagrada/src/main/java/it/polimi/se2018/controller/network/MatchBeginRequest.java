package it.polimi.se2018.controller.network;

import it.polimi.se2018.controller.network.client.Comm;
import it.polimi.se2018.controller.network.client.CommUtilizer;
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
    }

    @Override
    public void clientHandle(Comm clientComm, CommUtilizer commUtilizer) {
        throw new UnsupportedOperationException();
    }


}
