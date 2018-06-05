package it.polimi.se2018.controller.network;

import it.polimi.se2018.controller.network.client.Comm;
import it.polimi.se2018.controller.network.client.CommUtilizer;
import it.polimi.se2018.controller.network.server.ServerVisitor;

/**
 * A request used to logout/notify a logout
 * @author Francesco Franzini
 */
public class LogoutRequest implements AbsReqServerLogic {


    @Override
    public void clientHandle(Comm clientComm, CommUtilizer commUtilizer) {
        clientComm.logoutRequestReceived();
        commUtilizer.notifyCommDropped();
    }

    @Override
    public boolean checkValid() {
        return true;
    }

    @Override
    public void serverVisit(ServerVisitor sV) {
        sV.handle(this);
    }


}
