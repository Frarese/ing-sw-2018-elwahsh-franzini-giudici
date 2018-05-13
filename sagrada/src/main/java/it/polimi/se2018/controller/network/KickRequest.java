package it.polimi.se2018.controller.network;

import it.polimi.se2018.controller.network.client.Comm;
import it.polimi.se2018.controller.network.client.CommUtilizer;
import it.polimi.se2018.controller.network.server.Client;
import it.polimi.se2018.controller.network.server.Match;
import it.polimi.se2018.controller.network.server.ServerMain;

/**
 * A request used to kick someone/notify that someone has been kicked
 *
 * @author Francesco Franzini
 */
public class KickRequest extends AbsReqServerLogic {
    public final String usnToKick;

    /**
     * Initializes this request with the given parameter
     *
     * @param usnToKick username
     */
    public KickRequest(String usnToKick) {
        this.usnToKick = usnToKick;
        if(!checkValid())throw new IllegalArgumentException("Parameters cannot be null");
    }

    @Override
    public void clientHandle(Comm clientComm, CommUtilizer commUtilizer) {
        if(!checkValid())return;
        commUtilizer.notifyKicked(usnToKick);
        if(usnToKick.equals(clientComm.getUsername())){
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public boolean checkValid() {
        return usnToKick!=null;
    }

    @Override
    public void serverHandle(Client client, ServerMain server) {
        if(!checkValid() ||client.usn.equals(usnToKick))return;
        Match m=client.getMatch();
        if(m==null || !m.isHost(client))return;
        m.playerLeft(usnToKick,false);
        m.getClients().forEach(c->client.pushOutReq(this));
    }


}

