package it.polimi.se2018.controller.network.server;

import it.polimi.se2018.controller.network.AbsReq;

import java.io.Serializable;

/**
 * Abstract Representation of the comm layer of a client
 * @author Francesco Franzini
 */
abstract class ClientComm {
    final Client client;

    /**
     * Initializes this ClientComm
     * @param client client object to call
     */
    ClientComm(Client client){
        this.client=client;
    }

    /**
     * Sends an object to the client and, if supported, returns if the operation was a success
     * @param obj the object to send
     * @return true if no errors were raised
     */
    abstract boolean sendObj(Serializable obj);

    /**
     * Sends a request to the client and, if supported, returns if the operation was a success
     * @param req the request to send
     * @return true if no errors were raised
     */
    abstract boolean sendReq(AbsReq req);

    /**
     * Pushes an inbound object for the server to handle
     * @param obj the object to push
     */
    void pushInObj(Serializable obj) {
        client.pushInObj(obj);
    }

    /**
     * Pushes an inbound request for the server to handle
     * @param req the request to push
     */
    void pushInReq(AbsReq req) {
        client.pushInReq(req);
    }

    /**
     * Terminates this comm layer
     */
    abstract void terminate();


}