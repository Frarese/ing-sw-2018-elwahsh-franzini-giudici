package it.polimi.se2018.controller.network.client;

import it.polimi.se2018.controller.network.ThreadHandler;

/**
 * An active listener that extracts incoming objects from the RMI session object
 * @author Francesco Franzini
 */
class RMIClientListener extends ThreadHandler implements Runnable {
    private final RMICommLayer commLayer;

    /**
     * Initializes this listener with the given parameters
     * @param commLayer the main RMI comm layer
     * @param isReq flag to choose which method to call
     */
    RMIClientListener(RMICommLayer commLayer, boolean isReq) {
        this.isReq=isReq;
        this.commLayer=commLayer;
    }

    @Override
    protected void methodToCall() {
        if(isReq){
            commLayer.receiveInReq(commLayer.getReq());
        }else{
            commLayer.receiveInObj(commLayer.getObj());
        }
    }


}