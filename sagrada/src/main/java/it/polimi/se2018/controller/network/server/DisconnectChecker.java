package it.polimi.se2018.controller.network.server;

import it.polimi.se2018.controller.network.KeepAliveRequest;

import java.time.Duration;
import java.time.Instant;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Object that checks if a username is timing out
 * @author Francesco Franzini
 */
class DisconnectChecker extends TimerTask {
    private boolean continua;
    private final long warningTimeout;
    private final long deathTimeout;
    private final long purgeTimeout;
    private final Client client;
    private volatile boolean warned;
    private Timer t;

    /**
     * Builds and runs a death timer on the given client
     * @param warningTimeout time(in milliseconds) before a warning is issued
     * @param deathTimeout time(in milliseconds) after a warning before a client becomes zombie
     * @param purgeTimeout time(in milliseconds) after becoming zombie before it is removed from the logged users
     * @param client client to check
     */
    public DisconnectChecker(long warningTimeout, long deathTimeout, long purgeTimeout, Client client) {
        this.warningTimeout = warningTimeout;
        this.deathTimeout = deathTimeout;
        this.purgeTimeout = purgeTimeout;
        this.client = client;
        warned=false;
        continua=true;
        t=new Timer();
    }

    @Override
    public void run() {
        if(continua){
            Instant i=client.lastSeen();
            Duration d=Duration.between(i,Instant.now());
            long value=d.getSeconds()*1000+(d.getNano()/1000000);
            if(!warned && value>warningTimeout){
                warned=true;
                boolean out=client.sendReq(new KeepAliveRequest());
                if(!out){
                    client.zombiefy(true,null);
                }
            }else if(warned && value>deathTimeout && !client.isZombie()){
                client.zombiefy(true,null);
            }else if(client.isZombie() && value>purgeTimeout){
                client.purge();
                this.stop();
            }
        }
    }

    /**
     * Launches this death timer
     */
    void reschedule(){
        t.cancel();
        t.purge();
        t=new Timer();
        continua=true;
        warned=false;
        long minT=(warningTimeout<deathTimeout)?warningTimeout:deathTimeout;
        minT=(purgeTimeout<minT)?purgeTimeout:minT;
        t.schedule(this,0,minT);
    }

    /**
     * Stops this death timer
     */
    void stop(){
        continua=false;
    }
}