package it.polimi.se2018.controller.network;

import it.polimi.se2018.controller.network.client.Comm;
import it.polimi.se2018.controller.network.client.CommUtilizer;
import it.polimi.se2018.controller.network.server.Client;
import it.polimi.se2018.controller.network.server.ServerMain;

/**
 * Request to leave a match/notify that someone has left
 * @author Francesco Franzini
 */
public class LeaveMatchRequest extends AbsReqServerLogic {
    public final String usn;
    public final Boolean host;
    /**
     * Initializes this request with the given parameter
     * @param usn username
     * @param host flag to signal that this is the new host
     */
    public LeaveMatchRequest(String usn,boolean host) {
        this.usn=usn;
        this.host=host;
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
