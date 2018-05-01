package it.polimi.se2018.controller.network.server;

import it.polimi.se2018.controller.network.AbsReq;
import it.polimi.se2018.util.SafeSocket;

import java.io.Serializable;

/**
 * Socket implementation of the comm layer of a client
 * @author Francesco Franzini
 */
class SocClientComm extends ClientComm {
    private final SafeSocket objSoc;
    private final SafeSocket reqSoc;
    private InputStreamWaiter waiterReq;
    private InputStreamWaiter waiterObj;

    /**
     * Initializes this Socket comm layer with the given parameters
     * @param reqSocket  the request {@link it.polimi.se2018.util.SafeSocket}
     * @param objSocket  the object {@link it.polimi.se2018.util.SafeSocket}
     * @param client the {@link it.polimi.se2018.controller.network.server.Client} to call
     */
    public SocClientComm(SafeSocket reqSocket, SafeSocket objSocket,Client client) {
        super(client);
        this.reqSoc=reqSocket;
        this.objSoc=objSocket;
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
