package it.polimi.se2018.controller.network.client;

import it.polimi.se2018.controller.network.AbsReq;

import java.io.Serializable;

/**
 * Represents a generic client network layer
 * @author Francesco Franzini
 */
abstract class CommLayer {
    private final Comm cComm;

    /**
     * Initializes this comm layer with the given client object
     * @param comm main client object
     */
    CommLayer(Comm comm) {
        this.cComm=comm;
    }

    /**
     * Attempts to establish a connection to the given parameters
     * @param host hostname to connect to
     * @param reqPort port to use for the request socket or RMI port
     * @param objPort port to use for the object socket, unused if RMI
     * @param isRecovery flag to indicate that this is a connection recovery attempt
     * @param usn username
     * @param pw password
     * @param newUser flag to indicate that this is a registration attempt
     * @return {@code null} if no errors were raised, a textual representation of the error otherwise
     */
    abstract String establishCon(String host, int reqPort, int objPort, boolean isRecovery, String usn, String pw, boolean newUser);

    /**
     * Attempts to end the current connection
     * @return true if no errors were raised
     */
    abstract boolean endCon();

    /**
     * Sends an object to the server
     * @param obj the object to send
     * @return true if no errors were raised
     */
    abstract boolean sendOutObj(Serializable obj);

    /**
     * Sends a request to the server
     * @param req the request to send
     * @return true if no errors were raised
     */
    abstract boolean sendOutReq(AbsReq req);

    /**
     * Pushes a received object to the main client object
     * @param obj the object that has been received
     */
    void receiveInObj(Serializable obj) {
        cComm.pushInObj(obj);
    }

    /**
     * Pushes a received request to the main client object
     * @param req the request that has been received
     */
    void receiveInReq(AbsReq req) {
        cComm.pushInReq(req);
    }

    /**
     * Terminates this comm layer
     * @return true if no errors were raised
     */
    abstract boolean close();


}