package it.polimi.se2018.controller.network.client;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Object that tries to reconnect if the network fails
 * @author Francesco Franzini
 */
class ReconnectWaker extends TimerTask {
    public static final long DEFAULT_RECON_PERIOD=1000;
    public static final int DEFAULT_ATTEMPTS=5;
    private Timer t;
    private final Comm cComm;
    private int attempts;
    private int maxAttempts;

    /**
     * Initializes this waker with the given parameters
     * @param cComm the main client object
     */
    ReconnectWaker(Comm cComm) {
        this.cComm=cComm;
        t=new Timer();
        this.maxAttempts=-1;
    }

    @Override
    public void run() {
        attempts++;
        boolean result=cComm.tryRecover(attempts==maxAttempts);
        if(maxAttempts==attempts || result)this.cancel();
    }

    /**
     * Launches a new run of reconnection attempts
     * @param period time(in milliseconds) between attempts
     * @param noOfAttempts number of attempts
     */
    void reschedule(long period, int noOfAttempts) {

        t.cancel();
        t.purge();
        this.attempts=0;
        this.maxAttempts=noOfAttempts;
        t=new Timer();
        t.schedule(this,period,period);

    }


}
