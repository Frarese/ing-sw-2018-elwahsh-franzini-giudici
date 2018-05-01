package it.polimi.se2018.controller.network;

import it.polimi.se2018.controller.network.client.Comm;
import it.polimi.se2018.controller.network.client.CommUtilizer;
import it.polimi.se2018.controller.network.server.Client;
import it.polimi.se2018.controller.network.server.ServerMain;

/**
 * A request to change network technology
 * @author Francesco Franzini
 */
public class ChangeCLayerRequest extends AbsReqServerLogic {
    private final boolean toRMI;

    public ChangeCLayerRequest(boolean toRMI) {
        this.toRMI = toRMI;
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