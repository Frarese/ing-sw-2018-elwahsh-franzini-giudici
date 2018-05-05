package it.polimi.se2018.controller.network.server;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

import it.polimi.se2018.controller.network.AbsReq;

/**
 * The interface used by session objects
 * @author Francesco Franzini
 */
public interface RMISession extends Remote {
    /**
     * Checks if there are unread requests
     * @return true if there are unread requests
     */
    public boolean hasReq() throws RemoteException;

    /**
     * Pops an ingoing(directed to the Client) request. If none are available it blocks until one is pushed
     * @return a request or {@code null} if errors occurred
     */
    public AbsReq getReq() throws RemoteException;

    /**
     * Pushes a request to the server
     * @param obj the request to push
     * @return true if no errors occurred
     */
    public boolean sendReq(AbsReq obj) throws RemoteException;

    /**
     * Checks if there are unread objects
     * @return true if there are unread objects
     */
    public boolean hasObj() throws RemoteException;

    /**
     * Pops an ingoing(directed to the Client) object. If none are available it blocks until one is pushed
     * @return a serializable object or {@code null} if errors occurred
     */
    public Serializable getObj() throws RemoteException;

    /**
     * Pushes a serializable object to the server
     * @param obj the object to push
     * @return true if no errors occurred
     */
    public boolean sendObj(Serializable obj) throws RemoteException;

    /**
     * Checks if the server has terminated this session object
     * @return true if this session object is no longer valid
     */
    public boolean isTerminated() throws RemoteException;

    /**
     * Returns a textual representation of the login result
     * @return a textual representation of the login result
     */
    public LoginResponsesEnum getLoginOutput() throws RemoteException;


}