package it.polimi.se2018.controller.network;

import it.polimi.se2018.controller.network.client.Comm;
import it.polimi.se2018.controller.network.client.CommUtilizer;
import it.polimi.se2018.controller.network.server.ServerVisitor;

/**
 * A request used as a timely keep-alive
 *
 * @author Francesco Franzini
 */
public class KeepAliveRequest implements AbsReqServerComm {
    private boolean bounced=false;
    @Override
    public void serverVisit(ServerVisitor sV) {
        sV.handle(this);
    }

    @Override
    public void clientHandle(Comm clientComm, CommUtilizer commUtilizer) {
        if(!bounced){
            setBounced();
            clientComm.pushOutReq(this);
        }
    }

    @Override
    public boolean checkValid() {
        return true;
    }

    /**
     * Checks if this request has bounced
     * @return true if this request has bounced
     */
    public boolean hasBounced() {
        return bounced;
    }

    public void setBounced(){
        bounced=true;
    }
}

