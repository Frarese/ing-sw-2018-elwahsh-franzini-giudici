package it.polimi.se2018.controller.network.client;

import it.polimi.se2018.controller.network.ThreadHandler;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Processes all the inbound objects on one stream
 * @author Francesco Franzini
 */
class InListener extends ThreadHandler implements Runnable {
    private final Comm cComm;
    private CommUtilizer utilizer;
    private final Logger logger;

    /**
     * Initializes this listener with the given parameters
     * @param comm the main comm object
     * @param utilizer the utilizer that handles the received objects/requests
     * @param isReq flag to choose which stream to use
     */
    InListener(Comm comm, CommUtilizer utilizer, boolean isReq) {
        this.isReq=isReq;
        this.utilizer=utilizer;
        this.cComm=comm;
        this.logger=Logger.getGlobal();
    }

    @Override
    protected void methodToCall() throws InterruptedException {
        if(isReq){
            cComm.popInPendingReq().clientHandle(cComm,utilizer);
        }else{
            try {
                Serializable ser=cComm.popInPendingObj();
                utilizer.receiveObject(ser);
            }catch (Exception e){
                logger.log(Level.WARNING,"Exception was thrown pushing to utilizer"+e.getMessage());
            }
        }
    }

    /**
     * Changes the utilizer
     * @param utilizer new utilizer
     */
    void setUtilizer(CommUtilizer utilizer) {
        this.utilizer=utilizer;
    }

    /**
     * Notifies the utilizer that the network is irreparably down
     */
    void notifyDisconnect() {
        if(utilizer!=null){
            utilizer.notifyCommDropped();
        }
    }


}

