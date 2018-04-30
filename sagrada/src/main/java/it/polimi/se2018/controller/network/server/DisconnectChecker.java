package it.polimi.se2018.controller.network.server;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Object that checks if a username is timing out
 * @author Francesco Franzini
 */
class DisconnectChecker extends TimerTask {
    private int warningTimeout;
    private int deathTimeout;
    private int purgeTimeout;
    private Client client;

    private Timer t;

    /**
     * Builds and runs a death timer on the given client
     * @param warningTimeout time(in seconds) before a warning is issued
     * @param deathTimeout time(in seconds) after a warning before a client becomes zombie
     * @param purgeTimeout time(in seconds) after becoming zombie before it is removed from the logged users
     * @param client client to check
     */
    public DisconnectChecker(int warningTimeout, int deathTimeout, int purgeTimeout, Client client) {
        this.warningTimeout = warningTimeout;
        this.deathTimeout = deathTimeout;
        this.purgeTimeout = purgeTimeout;
        this.client = client;
        throw new UnsupportedOperationException();
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException();
    }


}