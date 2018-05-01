package it.polimi.se2018.controller.network.client;

import it.polimi.se2018.controller.network.ThreadHandler;

/**
 * Processes all the inbound objects on one stream
 * @author Francesco Franzini
 */
class InListener extends ThreadHandler implements Runnable {
    private final Comm cComm;
    private CommUtilizer utilizer;

    /**
     * Initializes this listener with the given parameters
     * @param comm the main comm object
     * @param utilizer the utilizer that handles the received objects/requests
     * @param isReq flag to choose which stream to use
     */
    public InListener(Comm comm, CommUtilizer utilizer, boolean isReq) {
        this.isReq=isReq;
        this.utilizer=utilizer;
        this.cComm=comm;
    }

    @Override
    protected void methodToCall() {
        throw new UnsupportedOperationException();
    }

    /**
     * Changes the utilizer
     * @param utilizer new utilizer
     */
    public void setUtilizer(CommUtilizer utilizer) {
        throw new UnsupportedOperationException();
    }

    /**
     * Notifies the utilizer that the network is irreparably down
     */
    public void notifyDisconnect() {
        throw new UnsupportedOperationException();
    }


}

