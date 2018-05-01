package it.polimi.se2018.controller.network.client;

import it.polimi.se2018.controller.network.ThreadHandler;
import it.polimi.se2018.util.SafeSocket;

/**
 * A listener that extracts incoming objects from a SafeSocket
 * @author Francesco Franzini
 */
abstract class SocketInQueueFiller extends ThreadHandler implements Runnable {
    private final SafeSocket sSocket;
    private final SocketCommLayer commLayer;

    /**
     * Initializes this listeners with the given parameters
     * @param commLayer main comm layer
     * @param sSocket the {@link it.polimi.se2018.util.SafeSocket}
     * @param isReq flag to choose which method to call
     */
    public SocketInQueueFiller(SocketCommLayer commLayer, SafeSocket sSocket, boolean isReq) {
        this.isReq=isReq;
        this.sSocket=sSocket;
        this.commLayer=commLayer;
        throw new UnsupportedOperationException();
    }

    @Override
    protected void methodToCall() {
        throw new UnsupportedOperationException();
    }


}
