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
    private final int reqPort;
    private final int objPort;

    public ChangeCLayerRequest(boolean toRMI, int reqPort, int objPort) {
        this.toRMI = toRMI;
        this.reqPort = reqPort;
        this.objPort = objPort;
        if(!checkValid())throw new IllegalArgumentException("Port numbers are not valid");
    }

    @Override
    public void clientHandle(Comm clientComm, CommUtilizer commUtilizer) {
        if(!checkValid())return;
        clientComm.purgeComm();
        clientComm.login(clientComm.getHost(),reqPort,objPort,true
                ,clientComm.getUsername(),clientComm.getsPassword()
                ,false,toRMI,commUtilizer);
    }

    @Override
    public boolean checkValid() {
        return reqPort>0 && reqPort<65025 && objPort>0 && objPort<65025;
    }


    @Override
    public void serverHandle(Client client, ServerMain server) {
        if(!checkValid())return;
        client.zombiefy(false,this);
    }


}