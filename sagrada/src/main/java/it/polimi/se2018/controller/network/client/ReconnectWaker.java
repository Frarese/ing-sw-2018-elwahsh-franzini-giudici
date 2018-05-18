package it.polimi.se2018.controller.network.client;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Object that tries to reconnect if the network fails
 * @author Francesco Franzini
 */
class ReconnectWaker{
    static final long DEFAULT_RECON_PERIOD=1000;
    static final int DEFAULT_ATTEMPTS=5;
    private Timer t;
    private final Comm cComm;
    private int attempts;
    private int maxAttempts;
    private final Logger logger;

    /**
     * Initializes this waker with the given parameters
     * @param cComm the main client object
     */
    ReconnectWaker(Comm cComm) {
        this.logger=Logger.getGlobal();
        this.cComm=cComm;
        t=new Timer();
        this.maxAttempts=-1;
    }

    private void runTask() {
        attempts++;
        boolean result=cComm.tryRecover(attempts==maxAttempts);

        if(maxAttempts==attempts || result)t.cancel();
    }

    /**
     * Launches a new run of reconnection attempts
     * @param period time(in milliseconds) between attempts
     * @param noOfAttempts number of attempts
     */
    void reschedule(long period, int noOfAttempts) {
        logger.log(Level.SEVERE,"Starting reconnection timer");
        t.cancel();
        t.purge();
        this.attempts=0;
        this.maxAttempts=noOfAttempts;
        t=new Timer();
        TimerTask tS=new TimerTask() {
            @Override
            public void run() {
                runTask();
            }
        };
        t.schedule(tS,period,period);

    }


}
