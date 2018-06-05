package it.polimi.se2018.controller.network;

import it.polimi.se2018.controller.network.client.Comm;
import it.polimi.se2018.controller.network.client.CommUtilizer;
import it.polimi.se2018.controller.network.server.ServerVisitor;

/**
 * Request to leave a match/notify that someone has left
 * @author Francesco Franzini
 */
public class LeaveMatchRequest implements AbsReqServerLogic {
    public final String usn;
    /**
     * Initializes this request with the given parameter
     * @param usn username
     */
    public LeaveMatchRequest(String usn) {
        this.usn=usn;
        if(!checkValid())throw new IllegalArgumentException("Parameters cannot be null");
    }

    @Override
    public void clientHandle(Comm clientComm, CommUtilizer commUtilizer) {
        if(!checkValid())return;
        commUtilizer.notifyUserLeft(usn);
    }

    @Override
    public boolean checkValid() {
        return usn!=null;
    }

    @Override
    public void serverVisit(ServerVisitor sV) {
        sV.handle(this);
    }


}
