package it.polimi.se2018.controller.network;

import it.polimi.se2018.controller.network.client.Comm;
import it.polimi.se2018.controller.network.client.CommUtilizer;
import it.polimi.se2018.controller.network.server.Client;
import it.polimi.se2018.controller.network.server.ServerMain;

/**
 * A request used as a timely keepalive
 *
 * @author Francesco Franzini
 */
public class KeepAliveRequest extends AbsReqServerComm {
    private boolean bounced=false;
    @Override
    public void serverHandle(Client client, ServerMain server) {
        if(!bounced){
            bounced=true;
            client.sendReq(this);
        }
    }

    @Override
    public void clientHandle(Comm clientComm, CommUtilizer commUtilizer) {
        if(!bounced){
            bounced=true;
            clientComm.pushOutReq(this);
        }
    }


}

