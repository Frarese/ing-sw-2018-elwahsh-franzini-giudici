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
    SocClientComm(SafeSocket reqSocket, SafeSocket objSocket,Client client) {
        super(client);
        this.reqSoc=reqSocket;
        this.objSoc=objSocket;
        waiterObj=new InputStreamWaiter(objSocket,false,this);
        waiterReq=new InputStreamWaiter(reqSocket,true,this);

        waiterObj.start();
        waiterReq.start();
    }


    @Override
    boolean sendObj(Serializable obj) {
        return objSoc.send(obj);
    }

    @Override
    boolean sendReq(AbsReq req) {
        return reqSoc.send(req);
    }

    @Override
    void terminate() {
        waiterReq.stop();
        waiterObj.stop();
        throw new UnsupportedOperationException();
    }
}
