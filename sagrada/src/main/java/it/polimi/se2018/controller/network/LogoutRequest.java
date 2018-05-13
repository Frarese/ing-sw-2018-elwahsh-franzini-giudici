package it.polimi.se2018.controller.network;

import it.polimi.se2018.controller.network.client.Comm;
import it.polimi.se2018.controller.network.client.CommUtilizer;
import it.polimi.se2018.controller.network.server.Client;
import it.polimi.se2018.controller.network.server.Match;
import it.polimi.se2018.controller.network.server.ServerMain;

/**
 * A request used to logout/notify a logout
 * @author Francesco Franzini
 */
public class LogoutRequest extends AbsReqServerLogic {


    @Override
    public void clientHandle(Comm clientComm, CommUtilizer commUtilizer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean checkValid() {
        return true;
    }

    @Override
    public void serverHandle(Client client, ServerMain server) {
        Match m=client.getMatch();
        if(m!=null)m.playerLeft(client.usn,false);
        client.purge();
    }


}
