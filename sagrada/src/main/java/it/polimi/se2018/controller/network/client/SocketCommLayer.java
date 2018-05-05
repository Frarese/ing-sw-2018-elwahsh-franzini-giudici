package it.polimi.se2018.controller.network.client;

import it.polimi.se2018.controller.network.AbsReq;
import it.polimi.se2018.util.SafeSocket;

import java.io.Serializable;

/**
 * A socket implementation of the client network layer
 * @author Francesco Franzini
 */
class SocketCommLayer extends CommLayer {
    private SafeSocket reqSoc;
    private SafeSocket objSoc;

    private SocketInQueueFiller sockListenerReq;
    private SocketInQueueFiller sockListenerObj;

    /**
     * Initializes this socket comm layer
     * @param comm the main comm object
     */
    public SocketCommLayer(Comm comm) {
        super(comm);
        //TODO
    }

    @Override
    String establishCon(String host, int reqPort, int objPort, boolean isRecovery, String usn, String pw, boolean newUser) {
        throw new UnsupportedOperationException();
    }

    @Override
    boolean endCon() {
        throw new UnsupportedOperationException();
    }

    @Override
    boolean sendOutObj(Serializable obj) {
        throw new UnsupportedOperationException();
    }

    @Override
    boolean sendOutReq(AbsReq req) {
        throw new UnsupportedOperationException();
    }

    @Override
    boolean close() {
        throw new UnsupportedOperationException();
    }

}