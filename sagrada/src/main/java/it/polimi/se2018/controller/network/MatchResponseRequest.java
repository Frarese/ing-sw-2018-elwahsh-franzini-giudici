package it.polimi.se2018.controller.network;

import it.polimi.se2018.controller.network.client.Comm;
import it.polimi.se2018.controller.network.client.CommUtilizer;
import it.polimi.se2018.controller.network.server.Client;
import it.polimi.se2018.controller.network.server.ServerMain;
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
    }

    @Override
    public void clientHandle(Comm clientComm, CommUtilizer commUtilizer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void serverHandle(Client client, ServerMain server) {
        throw new UnsupportedOperationException();
    }


}
