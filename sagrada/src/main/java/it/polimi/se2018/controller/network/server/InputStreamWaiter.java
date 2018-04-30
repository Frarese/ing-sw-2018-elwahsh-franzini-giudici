package it.polimi.se2018.controller.network.server;


import it.polimi.se2018.controller.network.client.ThreadHandler;
import it.polimi.se2018.util.SafeSocket;

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
    public InputStreamWaiter(SafeSocket sSocket, boolean isReq, SocClientComm cComm) {
        this.cComm=cComm;
        this.sSocket=sSocket;
        throw new UnsupportedOperationException();
    }

    @Override
    public void methodToCall() {
        throw new UnsupportedOperationException();
    }


}
