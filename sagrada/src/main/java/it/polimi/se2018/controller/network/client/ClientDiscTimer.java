package it.polimi.se2018.controller.network.client;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Fallback class that ensures a graceful disconnect should the network fail abruptly
 * @author Francesco Franzini
 */
class ClientDiscTimer extends TimerTask {
    public static final int defaultClientTimeout = 90;
    private final Comm cComm;

    private Timer t;

    /**
     * Creates a new disconnection timer with the given parameters
     * @param cComm the client comm
     */
    public ClientDiscTimer(Comm cComm) {
        this.cComm=cComm;
        throw new UnsupportedOperationException();
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException();
    }


}