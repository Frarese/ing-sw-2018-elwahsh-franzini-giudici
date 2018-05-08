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
    static final long DEFAULT_CLIENT_TIMEOUT = 10000;
    private final Comm cComm;
    private long timeout;
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
            long value=d.getSeconds()*1000+(d.getNano()/1000000);
            if(value>timeout){
                this.stop();
                cComm.beginDisconnectedRoutines();
            }
        }

    }

    /**
     * Launches this disconnect timer
     * @param timeout timeout to use in milliseconds
     */
    void reschedule(long timeout){
        t.cancel();
        t.purge();
        t=new Timer();
        this.timeout=timeout;
        continua=true;
        //A check every 1 potential timeouts
        t.schedule(this,0,timeout);
    }

    /**
     * Stops this disconnect timer
     */
    void stop(){
        continua=false;
    }
}