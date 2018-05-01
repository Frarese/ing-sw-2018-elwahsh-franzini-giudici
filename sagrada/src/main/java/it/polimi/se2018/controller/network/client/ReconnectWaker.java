package it.polimi.se2018.controller.network.client;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Object that tries to reconnect if the network fails
 * @author Francesco Franzini
 */
class ReconnectWaker extends TimerTask {
    public static final long defaultReconPeriod=1000;
    public static final int defaultAttemps=5;
    private Timer t;
    private final Comm cComm;
    private int attemps;
    private int maxAttemps;

    /**
     * Initializes this waker with the given parameters
     * @param cComm the main client object
     */
    public ReconnectWaker(Comm cComm) {
        this.cComm=cComm;
        throw new UnsupportedOperationException();
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException();
    }

    /**
     * Launches a new run of reconnection attempts
     * @param period time(in milliseconds) between attempts
     * @param attempts number of attempts
     */
    public void reschedule(long period, int attempts) {
        throw new UnsupportedOperationException();
    }


}
