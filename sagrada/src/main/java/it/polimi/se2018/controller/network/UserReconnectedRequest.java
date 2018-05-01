package it.polimi.se2018.controller.network;

import it.polimi.se2018.controller.network.client.Comm;
import it.polimi.se2018.controller.network.client.CommUtilizer;
import it.polimi.se2018.controller.network.server.Client;
import it.polimi.se2018.controller.network.server.ServerMain;

/**
 * Notifies that a user has reconnected
 * @author Francesco Franzini
 */
public class UserReconnectedRequest extends AbsReqServerLogic {
    public final String usr;

    /**
     * Initializes this request with the given username
     * @param usr username
     */
    public UserReconnectedRequest(String usr) {
        this.usr = usr;
    }

    @Override
    public void serverHandle(Client client, ServerMain server) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clientHandle(Comm clientComm, CommUtilizer commUtilizer) {
        throw new UnsupportedOperationException();
    }
}

