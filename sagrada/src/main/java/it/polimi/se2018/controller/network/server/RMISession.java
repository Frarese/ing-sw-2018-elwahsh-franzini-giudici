package it.polimi.se2018.controller.network.server;

import java.io.Serializable;
import it.polimi.se2018.controller.network.AbsReq;

/**
 * The interface used by session objects
 * @author Francesco Franzini
 */
public interface RMISession {
    /**
     * Checks if there are unread requests
     * @return true if there are unread requests
     */
    public boolean hasReq();

    /**
     * Pops an ingoing(directed to the Client) request. If none are available it blocks until one is pushed
     * @return a request or {@code null} if errors occurred
     */
    public AbsReq getReq();

    /**
     * Pushes a request to the server
     * @param obj the request to push
     * @return true if no errors occurred
     */
    public boolean sendReq(AbsReq obj);

    /**
     * Checks if there are unread objects
     * @return true if there are unread objects
     */
    public boolean hasObj();

    /**
     * Pops an ingoing(directed to the Client) object. If none are available it blocks until one is pushed
     * @return a serializable object or {@code null} if errors occurred
     */
    public Serializable getObj();

    /**
     * Pushes a serializable object to the server
     * @param obj the object to push
     * @return true if no errors occurred
     */
    public boolean sendObj(Serializable obj);

    /**
     * Checks if the server has terminated this session object
     * @return true if this session object is no longer valid
     */
    public boolean isTerminated();

    /**
     * Returns a textual representation of the login result
     * @return a textual representation of the login result
     */
    public String getLoginOutput();


}