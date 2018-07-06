package it.polimi.se2018.controller.network;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A wrapper for a generic Thread Handler that calls a method until stopped
 * @author Francesco Franzini
 */
public abstract class ThreadHandler implements Runnable {
    private Thread t;
    private boolean continua;
    protected boolean isReq;
    private Logger logger=Logger.getGlobal();
    private boolean ended=true;

    @Override
    public void run() {
        while(continua){
            try {
                this.methodToCall();
            } catch (InterruptedException e) {
                continua=false;
                Thread.currentThread().interrupt();
            }
            if(Thread.currentThread().isInterrupted()){
                continua=false;
            }
        }
        logger.log(Level.INFO,"Thread handler finished execution {0}",this.getClass().getName());
        ended=true;
    }

    /**
     * Starts this handler
     * @return true if no errors are raised
     */
    public synchronized boolean start() {

        logger=Logger.getGlobal();
        continua=true;
        if(!this.isRunning()){
            ended=false;
            cleanUp();
            t=new Thread(this);
            t.start();
            logger.log(Level.FINE,"Starting a "+this.getClass().getName()+" thread handler");
            return true;
        }else{
            ended=false;
            logger.log(Level.WARNING,"Attempting to start an already running ThreadHandler");
            return false;
        }
    }

    /**
     * Signals this handler to stop, it will finish the current iteration however
     */
    public synchronized void stop() {
        logger.log(Level.INFO,"Stopping handler {0}",this.getClass().getName());
        continua=false;
    }


    /**
     * Checks if this handler is currently running
     * @return true if the handler is running
     */
    public synchronized boolean isRunning() {
        return t != null && !ended;
    }

    /**
     * Forces this handler to stop, interrupting the execution
     */
    public synchronized void forceStop() {
        this.stop();
        if(t==null)return;
        t.interrupt();
        try {
            t.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * The method that is called by this handler
     * @throws InterruptedException if this method is interrupted
     */
    protected abstract void methodToCall() throws InterruptedException;

    /**
     * Cleans up the objects used in this ThreadHandler
     */
    private synchronized void cleanUp(){
        if(t==null)return;
        t=null;
    }
}