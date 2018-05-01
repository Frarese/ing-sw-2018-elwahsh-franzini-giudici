package it.polimi.se2018.controller.network.client;

import it.polimi.se2018.controller.network.ThreadHandler;

/**
 * Processes the outgoing objects of a stream
 * @author Francesco Franzini
 */
class OutQueueEmptier extends ThreadHandler implements Runnable {
    private final Comm cComm;

    /**
     * Initializes a new emptier with the given parameters
     * @param comm the client main object
     * @param isReq flag to choose the stream to call
     */
    public OutQueueEmptier(Comm comm, boolean isReq) {
        this.cComm=comm;
        this.isReq=isReq;
        throw new UnsupportedOperationException();
    }

    @Override
    protected void methodToCall() {
        throw new UnsupportedOperationException();
    }


}

