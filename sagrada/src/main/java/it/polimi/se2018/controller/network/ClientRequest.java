package it.polimi.se2018.controller.network;

import it.polimi.se2018.controller.network.client.Comm;
import it.polimi.se2018.controller.network.client.CommUtilizer;
import it.polimi.se2018.controller.network.server.Client;
import it.polimi.se2018.controller.network.server.ServerMain;

import java.io.Serializable;

/**
 * A request that is used to wrap a serializable object sent from the controller over the request channel
 * @author Francesco Franzini
 */
public class ClientRequest extends AbsReqServerLogic {
    public final String destUsn;
    public final Serializable serializedReq;

    /**
     * Initializes this wrapper with the given parameters
     * @param destUsn username of the destination
     * @param request the serialized request to send
     */
    public ClientRequest(String destUsn, Serializable request) {
        this.destUsn=destUsn;
        this.serializedReq=request;
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
