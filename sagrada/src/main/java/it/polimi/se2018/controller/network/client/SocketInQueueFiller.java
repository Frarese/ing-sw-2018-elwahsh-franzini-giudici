package it.polimi.se2018.controller.network.client;

import it.polimi.se2018.controller.network.AbsReq;
import it.polimi.se2018.controller.network.ThreadHandler;
import it.polimi.se2018.util.SafeSocket;

import java.io.Serializable;

/**
 * A listener that extracts incoming objects from a SafeSocket
 * @author Francesco Franzini
 */
class SocketInQueueFiller extends ThreadHandler{
    private final SafeSocket sSocket;
    private final SocketCommLayer commLayer;

    /**
     * Initializes this listeners with the given parameters
     * @param commLayer main comm layer
     * @param sSocket the {@link it.polimi.se2018.util.SafeSocket}
     * @param isReq flag to choose which method to call
     */
    SocketInQueueFiller(SocketCommLayer commLayer, SafeSocket sSocket, boolean isReq) {
        this.isReq=isReq;
        this.sSocket=sSocket;
        this.commLayer=commLayer;
    }

    @Override
    protected void methodToCall() throws InterruptedException {
        Serializable obj=sSocket.receive();
        if(isReq){
            commLayer.receiveInReq((AbsReq)obj);
        }else{
            commLayer.receiveInObj(obj);
        }
    }


}
