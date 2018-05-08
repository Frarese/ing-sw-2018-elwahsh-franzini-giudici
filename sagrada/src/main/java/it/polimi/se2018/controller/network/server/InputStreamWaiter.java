package it.polimi.se2018.controller.network.server;


import it.polimi.se2018.controller.network.AbsReq;
import it.polimi.se2018.controller.network.ThreadHandler;
import it.polimi.se2018.util.SafeSocket;

import java.io.Serializable;

/**
 * Waiter that reads inbound data from a SafeSocket
 * @author Francesco Franzini
 */
class InputStreamWaiter extends ThreadHandler {

    private final SocClientComm cComm;
    private final SafeSocket sSocket;

    /**
     * Initializes this waiter with given parameters
     * @param sSocket the {@link it.polimi.se2018.util.SafeSocket} to listen
     * @param isReq flag to choose request/object
     * @param cComm socket client comm to call
     */
    InputStreamWaiter(SafeSocket sSocket, boolean isReq, SocClientComm cComm) {
        this.cComm=cComm;
        this.sSocket=sSocket;
        this.isReq=isReq;
    }

    @Override
    protected void methodToCall() throws InterruptedException {
        Serializable obj=sSocket.receive();
        if(isReq){
            cComm.pushInReq((AbsReq)obj);
        }else{
            cComm.pushInObj(obj);
        }
    }


}
