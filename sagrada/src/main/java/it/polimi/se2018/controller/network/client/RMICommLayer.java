package it.polimi.se2018.controller.network.client;

import it.polimi.se2018.controller.network.AbsReq;
import it.polimi.se2018.controller.network.server.RMIServerInt;
import it.polimi.se2018.controller.network.server.RMISession;

import java.io.Serializable;

/**
 * A RMI implementation of the client network layer
 * @author Francesco Franzini
 */
class RMICommLayer extends CommLayer {
    private RMISession sessionObj;
    private RMIClientListener listenerReq;
    private RMIClientListener listenerObj;

    private RMIServerInt serverLoginObj;

    /**
     * Initializes this comm layer with the given parameters
     * @param comm client main comm
     */
    public RMICommLayer(Comm comm) {
        super(comm);
        throw new UnsupportedOperationException();
    }

    /**
     * Retrieves a request from the server
     * @return the request that has been retrieved, {@code null} if errors occurred
     */
    public AbsReq getReq() {
        throw new UnsupportedOperationException();
    }

    /**
     * Checks if the server has an outbound object ready to be read
     * @return true if the server has an outbound object ready to be read
     */
    public boolean hasObj() {
        throw new UnsupportedOperationException();
    }

    /**
     * Checks if the server has an outbound request ready to be read
     * @return true if the server has an outbound request ready to be read
     */
    public boolean hasReq() {
        throw new UnsupportedOperationException();
    }

    /**
     * Retrieves an object from the server
     * @return the object that has been retrieved, {@code null} if errors occurred
     */
    public Serializable getObj() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String establishCon(String host, int reqPort, int objPort, boolean isRecovery, String usn, String pw, boolean newUser) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean endCon(boolean quitGame, boolean logout) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean sendOutObj(Serializable obj) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean sendOutReq(AbsReq req) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean close() {
        throw new UnsupportedOperationException();
    }



}
