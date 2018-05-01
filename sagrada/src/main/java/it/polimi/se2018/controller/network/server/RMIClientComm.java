package it.polimi.se2018.controller.network.server;

import it.polimi.se2018.controller.network.AbsReq;

import java.io.Serializable;
import java.util.Queue;

/**
 * A RMI implementation of the comm layer of a client
 * @author Francesco Franzini
 */
class RMIClientComm extends ClientComm {
    private final RMISessionImpl sessionObj;

    private Queue rmiOutObjQueue;
    private Queue rmiOutReqQueue;

    /**
     * Initializes a RMI comm layer with the given parameters
     * @param sessionObj the session object to use
     * @param client the client to call
     */
    public RMIClientComm(RMISessionImpl sessionObj,Client client) {
        super(client);
        this.sessionObj=sessionObj;
        throw new UnsupportedOperationException();
    }

    /**
     * Checks if there are unread objects
     * @return true if there are unread objects
     */
    public boolean hasObj() {
        throw new UnsupportedOperationException();
    }

    /**
     * Checks if there are unread requests
     * @return true if there are unread requests
     */
    public boolean hasReq() {
        throw new UnsupportedOperationException();
    }

    /**
     * Pops an outgoing object. If none are available it blocks until one is pushed
     * @return an object or {@code null} if errors occurred
     */
    public Serializable getOutObj() {
        throw new UnsupportedOperationException();
    }

    /**
     * Pops an outgoing request. If none are available it blocks until one is pushed
     * @return a request or {@code null} if errors occurred
     */
    public AbsReq getOutReq() {
        throw new UnsupportedOperationException();
    }


    @Override
    public boolean sendObj(Serializable obj) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean sendReq(AbsReq req) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void terminate() {
        throw new UnsupportedOperationException();
    }
}

