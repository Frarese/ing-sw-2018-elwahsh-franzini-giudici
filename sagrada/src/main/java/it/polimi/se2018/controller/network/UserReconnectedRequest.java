package it.polimi.se2018.controller.network;

import it.polimi.se2018.controller.network.client.Comm;
import it.polimi.se2018.controller.network.client.CommUtilizer;
import it.polimi.se2018.controller.network.server.ServerVisitor;

/**
 * Notifies that a user has reconnected
 * @author Francesco Franzini
 */
public class UserReconnectedRequest implements AbsReqServerLogic {
    private final String usn;

    /**
     * Initializes this request with the given username
     * @param usn username
     */
    public UserReconnectedRequest(String usn) {
        this.usn = usn;
        if(!checkValid())throw new IllegalArgumentException("Argument cannot be null");
    }

    @Override
    public void serverVisit(ServerVisitor sV) {
        sV.handle(this);
    }

    @Override
    public void clientHandle(Comm clientComm, CommUtilizer commUtilizer) {
        if(!checkValid())return;
        commUtilizer.notifyUserReconnected(usn);
    }

    @Override
    public boolean checkValid() {
        return usn!=null;
    }
}

