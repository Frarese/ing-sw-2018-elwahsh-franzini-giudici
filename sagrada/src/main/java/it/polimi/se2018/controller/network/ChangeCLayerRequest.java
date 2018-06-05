package it.polimi.se2018.controller.network;

import it.polimi.se2018.controller.network.client.Comm;
import it.polimi.se2018.controller.network.client.CommUtilizer;
import it.polimi.se2018.controller.network.server.ServerVisitor;

/**
 * A request to change network technology
 * @author Francesco Franzini
 */
public class ChangeCLayerRequest implements AbsReqServerLogic {
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
                ,clientComm.getUsername(),clientComm.getPassword()
                ,false,toRMI,commUtilizer);
    }

    @Override
    public boolean checkValid() {
        return reqPort!=objPort && reqPort>0 && reqPort<65025 && objPort>0 && objPort<65025;
    }


    @Override
    public void serverVisit(ServerVisitor sV) {
        sV.handle(this);
    }


}