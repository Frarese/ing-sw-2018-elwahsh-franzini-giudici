package it.polimi.se2018.controller.network;

import it.polimi.se2018.controller.network.client.Comm;
import it.polimi.se2018.controller.network.client.CommUtilizer;
import it.polimi.se2018.controller.network.server.ServerVisitor;

import java.io.Serializable;

/**
 * A request that is used to wrap a serializable object sent from the controller over the request channel
 * @author Francesco Franzini
 */
public class ClientRequest implements AbsReqServerLogic {
    public final Serializable serializedReq;

    /**
     * Initializes this wrapper with the given parameters
     * @param request the serialized request to send
     */
    public ClientRequest( Serializable request) {
        this.serializedReq=request;
        if(!checkValid())throw new IllegalArgumentException("Parameters cannot be null");
    }

    @Override
    public void clientHandle(Comm clientComm, CommUtilizer commUtilizer) {
        if(!checkValid())return;
        commUtilizer.receiveRequest(serializedReq);
    }

    @Override
    public boolean checkValid() {
        return serializedReq !=null;
    }

    @Override
    public void serverVisit(ServerVisitor sV) {
        sV.handle(this);

    }


}
