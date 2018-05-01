package it.polimi.se2018.controller.network;

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
        throw new UnsupportedOperationException();
    }

    /**
     * Starts this handler
     */
    public void start() {
        throw new UnsupportedOperationException();
    }

    /**
     * Signals this handler to stop, it will finish the current iteration however
     */
    public void stop() {
        throw new UnsupportedOperationException();
    }


    /**
     * Checks if this handler is currently running
     * @return true if the handler is running
     */
    public boolean isRunning() {
        throw new UnsupportedOperationException();
    }

    /**
     * Forces this handler to stop, interrupting the execution
     */
    public void forceStop() {
        throw new UnsupportedOperationException();
    }

    /**
     * The method that is called by this handler
     */
    protected abstract void methodToCall();
}