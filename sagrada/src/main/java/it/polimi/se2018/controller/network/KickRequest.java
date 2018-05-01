package it.polimi.se2018.controller.network;

import it.polimi.se2018.controller.network.client.Comm;
import it.polimi.se2018.controller.network.client.CommUtilizer;
import it.polimi.se2018.controller.network.server.Client;
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
    }

    @Override
    public void clientHandle(Comm clientComm, CommUtilizer commUtilizer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void serverHandle(Client client, ServerMain server) {
        throw new UnsupportedOperationException();
    }


}

