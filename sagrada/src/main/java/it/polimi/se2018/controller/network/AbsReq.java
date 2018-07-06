package it.polimi.se2018.controller.network;

import it.polimi.se2018.controller.network.client.Comm;
import it.polimi.se2018.controller.network.client.CommUtilizer;
import it.polimi.se2018.controller.network.server.ServerVisitor;

import java.io.Serializable;

/**
 * Represents a generic request/response that is being sent between client and server
 * and follow the Visitor pattern
 * @author Francesco Franzini
 */
public interface AbsReq extends Serializable {

    /**
     * The method to call if this request is received by the server.
     * @param sV server visitor
     */
    void serverVisit(ServerVisitor sV);

    /**
     * The method to call if this request is received by the client.
     * Default does nothing
     * @param clientComm the {@link it.polimi.se2018.controller.network.client.Comm} object
     * @param commUtilizer the {@link it.polimi.se2018.controller.network.client.CommUtilizer} object
     */
    void clientHandle(Comm clientComm, CommUtilizer commUtilizer);

    boolean checkValid();
}
