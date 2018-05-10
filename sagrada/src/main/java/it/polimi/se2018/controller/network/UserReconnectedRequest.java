package it.polimi.se2018.controller.network;

import it.polimi.se2018.controller.network.client.Comm;
import it.polimi.se2018.controller.network.client.CommUtilizer;

/**
 * Notifies that a user has reconnected
 * @author Francesco Franzini
 */
public class UserReconnectedRequest extends AbsReqServerLogic {
    public final String usn;

    /**
     * Initializes this request with the given username
     * @param usn username
     */
    public UserReconnectedRequest(String usn) {
        this.usn = usn;
    }

    @Override
    public void clientHandle(Comm clientComm, CommUtilizer commUtilizer) {
        commUtilizer.notifyUserReconnected(usn);
    }
}

