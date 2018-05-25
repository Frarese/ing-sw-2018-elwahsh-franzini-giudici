package it.polimi.se2018.controller.network;

import it.polimi.se2018.controller.network.client.Comm;
import it.polimi.se2018.controller.network.client.CommUtilizer;
import it.polimi.se2018.controller.network.server.Client;
import it.polimi.se2018.controller.network.server.Match;
import it.polimi.se2018.controller.network.server.ServerMain;

/**
 * Request to leave a match/notify that someone has left
 * @author Francesco Franzini
 */
public class LeaveMatchRequest extends AbsReqServerLogic {
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
    public void serverHandle(Client client, ServerMain server) {
        if(!checkValid())return;
        Match m=client.getMatch();
        if(m==null)return;
        m.playerLeft(usn,false);
    }


}
