package it.polimi.se2018.controller.network.server;

import it.polimi.se2018.controller.network.KeepAliveRequest;

import java.time.Duration;
import java.time.Instant;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Object that checks if a username is timing out
 * @author Francesco Franzini
 */
class DisconnectChecker{
    private boolean continua;
    private final long warningTimeout;
    private final long deathTimeout;
    private final long purgeTimeout;
    private final Client client;
    private final AtomicBoolean warned;
    private Timer t;
    private final Logger logger;

    /**
     * Builds and runs a death timer on the given client
     * @param warningTimeout time(in milliseconds) before a warning is issued
     * @param deathTimeout time(in milliseconds) after a warning before a client becomes zombie
     * @param purgeTimeout time(in milliseconds) after becoming zombie before it is removed from the logged users
     * @param client client to check
     */
    DisconnectChecker(long warningTimeout, long deathTimeout, long purgeTimeout, Client client) {
        this.warningTimeout = warningTimeout;
        this.deathTimeout = deathTimeout;
        this.purgeTimeout = purgeTimeout;
        this.client = client;
        this.logger=Logger.getGlobal();
        warned=new AtomicBoolean(false);
        continua=false;
        t=new Timer();
    }

    private void runTask() {
        if(continua){
            Instant i=client.lastSeen();
            Duration d=Duration.between(i,Instant.now());
            long value=d.getSeconds()*1000+(d.getNano()/1000000);
            if(!warned.get() && value>warningTimeout){
                logger.log(Level.FINE,"Warning {0} of disconnect", client.usn);
                warned.set(true);
                boolean out=client.sendReq(new KeepAliveRequest());
                if(!out){
                    logger.log(Level.FINE,"Failed to notify warning to {0}",client.usn);
                    client.zombiefy(true,null);
                }
            }else if(warned.get() && value>deathTimeout && !client.isZombie()){
                client.zombiefy(true,null);
            }else if(client.isZombie() && value>purgeTimeout){
                client.purge(false);
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
        warned.set(false);
        long minT=(warningTimeout<deathTimeout)?warningTimeout:deathTimeout;
        minT=(purgeTimeout<minT)?purgeTimeout:minT;

        TimerTask tS=new TimerTask() {
            @Override
            public void run() {
                runTask();
            }
        };
        t.schedule(tS,0,minT/2);
    }

    /**
     * Stops this death timer
     */
    void stop(){
        continua=false;
        t.cancel();
    }

    void resetWarned(){
        this.warned.set(false);
    }
}