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

    @Override
    public void run() {
        while(continua){
            try {
                this.methodToCall();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Starts this handler
     */
    public synchronized boolean start() {
        Logger logger=Logger.getGlobal();
        if(!this.isRunning()){
            continua=true;
            cleanUp();
            t=new Thread(this);
            t.start();
            logger.log(Level.FINE,"Starting a "+this.getClass().getName()+" thread handler");
            return true;
        }else{
            logger.log(Level.WARNING,"Attempting to start an already running ThreadHandler");
            return false;
        }
    }

    /**
     * Signals this handler to stop, it will finish the current iteration however
     */
    public synchronized void stop() {
        continua=false;
    }


    /**
     * Checks if this handler is currently running
     * @return true if the handler is running
     */
    public synchronized boolean isRunning() {
        if(t==null)return false;
        return t.isAlive();
    }

    /**
     * Forces this handler to stop, interrupting the execution
     */
    public synchronized void forceStop() {
        continua=false;
        t.interrupt();
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