package it.polimi.se2018.controller.network.client;

import java.time.Duration;
import java.time.Instant;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Fallback class that ensures a graceful disconnect should the network fail abruptly
 * @author Francesco Franzini
 */
class ClientDiscTimer extends TimerTask {
    static final int DEFAULT_CLIENT_TIMEOUT = 90;
    private final Comm cComm;
    private int timeout;
    private boolean continua;
    private Timer t;

    /**
     * Creates a new disconnection timer with the given parameters
     * @param cComm the client comm
     */
    ClientDiscTimer(Comm cComm) {
        this.cComm=cComm;
        t=new Timer();
    }

    @Override
    public void run() {
        if(continua){
            Instant i=cComm.getLastSeen();
            Duration d=Duration.between(i,Instant.now());
            if(d.getSeconds()>timeout){
                this.stop();
                cComm.beginDisconnectedRoutines();
            }
        }

    }

    /**
     * Launches this disconnect timer
     * @param timeout timeout to use in seconds
     */
    void reschedule(int timeout){
        t.cancel();
        t.purge();
        t=new Timer();
        this.timeout=timeout;
        continua=true;
        t.schedule(this,0,Integer.toUnsignedLong(timeout)*1000);
    }

    /**
     * Stops this disconnect timer
     */
    void stop(){
        continua=false;
    }
}