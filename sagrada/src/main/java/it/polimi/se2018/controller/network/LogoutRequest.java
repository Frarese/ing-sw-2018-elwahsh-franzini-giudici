package it.polimi.se2018.controller.network;

import it.polimi.se2018.controller.network.client.Comm;
import it.polimi.se2018.controller.network.client.CommUtilizer;
import it.polimi.se2018.controller.network.server.Client;
import it.polimi.se2018.controller.network.server.ServerMain;

/**
 * A request used to logout/notify a logout
 * @author Francesco Franzini
 */
public class LogoutRequest extends AbsReqServerLogic {
    public final String usn;

    /**
     * Initializes this request with the given parameter
     * @param usn username
     */
    public LogoutRequest(String usn) {
        this.usn=usn;
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
