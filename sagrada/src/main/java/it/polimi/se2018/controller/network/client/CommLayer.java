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
    public CommLayer(Comm comm) {
        this.cComm=comm;
    }

    /**
     * Attempts to establish a connection to the given parameters
     * @param host hostname to connect to
     * @param reqPort port to use for the request socket
     * @param objPort port to use for the object socket
     * @param isRecovery flag to indicate that this is a connection recovery attempt
     * @param usn username
     * @param pw password
     * @param newUser flag to indicate that this is a registration attempt
     * @return {@code null} if no errors were raised, a textual representation of the error otherwise
     */
    public abstract String establishCon(String host, int reqPort, int objPort, boolean isRecovery, String usn, String pw, boolean newUser);

    /**
     * Attempts to end the current connection
     * @param quitGame flag to choose if the game is to be abandoned
     * @param logout flag to choose if the logout is to be done
     * @return true if no errors were raised
     */
    public abstract boolean endCon(boolean quitGame, boolean logout);

    /**
     * Sends an object to the server
     * @param obj the object to send
     * @return true if no errors were raised
     */
    public abstract boolean sendOutObj(Serializable obj);

    /**
     * Sends a request to the server
     * @param req the request to send
     * @return true if no errors were raised
     */
    public abstract boolean sendOutReq(AbsReq req);

    /**
     * Pushes a received object to the main client object
     * @param obj the object that has been received
     * @return true if no errors are raised
     */
    public boolean receiveInObj(Serializable obj) {
        throw new UnsupportedOperationException();
    }

    /**
     * Pushes a received request to the main client object
     * @param req the request that has been received
     * @return true if no errors are raised
     */
    public boolean receiveInReq(AbsReq req) {
        throw new UnsupportedOperationException();
    }

    /**
     * Terminates this comm layer
     * @return true if no errors were raised
     */
    public abstract boolean close();


}