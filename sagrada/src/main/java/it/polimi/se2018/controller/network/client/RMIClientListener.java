package it.polimi.se2018.controller.network.client;

import it.polimi.se2018.controller.network.ThreadHandler;

/**
 * An active listener that extracts incoming objects from the RMI session object
 * @author Francesco Franzini
 */
abstract class RMIClientListener extends ThreadHandler implements Runnable {
    private RMICommLayer commLayer;

    /**
     * Initializes this listener with the given parameters
     * @param commLayer the main RMI comm layer
     * @param isReq flag to choose which method to call
     */
    public RMIClientListener(RMICommLayer commLayer, boolean isReq) {
        this.isReq=isReq;
        this.commLayer=commLayer;
        throw new UnsupportedOperationException();
    }

    @Override
    protected void methodToCall() {
        throw new UnsupportedOperationException();
    }


}