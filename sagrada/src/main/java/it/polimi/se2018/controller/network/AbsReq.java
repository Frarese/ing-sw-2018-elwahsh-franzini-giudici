package it.polimi.se2018.controller.network;

import it.polimi.se2018.controller.network.client.Comm;
import it.polimi.se2018.controller.network.client.CommUtilizer;
import it.polimi.se2018.controller.network.server.Client;
import it.polimi.se2018.controller.network.server.ServerMain;

import java.io.Serializable;

/**
 * Represents a generic request/response that is being sent between client and server
 * @author Francesco Franzini
 */
public abstract class AbsReq implements Serializable {

    /**
     * The method to call if this request is received by the server.
     * Default does nothing
     * @param client the {@link it.polimi.se2018.controller.network.server.Client} who sent this request
     * @param server the {@link it.polimi.se2018.controller.network.server.ServerMain} object
     */
    public void serverHandle(Client client, ServerMain server) {
    }

    /**
     * The method to call if this request is received by the client.
     * Default does nothing
     * @param clientComm the {@link it.polimi.se2018.controller.network.client.Comm} object
     * @param commUtilizer the {@link it.polimi.se2018.controller.network.client.CommUtilizer} object
     */
    public void clientHandle(Comm clientComm, CommUtilizer commUtilizer) {
    }

    public boolean checkValid(){
        return false;
    }
}
