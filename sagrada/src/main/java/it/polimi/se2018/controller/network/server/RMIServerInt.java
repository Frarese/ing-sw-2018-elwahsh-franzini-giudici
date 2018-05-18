package it.polimi.se2018.controller.network.server;


import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The RMI login interface
 * @author Francesco Franzini
 */
public interface RMIServerInt extends Remote {

    /**
     * Attempts a login with the given parameters
     * @param usn username
     * @param pw password
     * @param isRecover flag to indicate that this is a connection recovery
     * @param register flag to indicate that this is a new user
     * @return an Object describing the result
     * @throws RemoteException if an I/O error occurs
     */
    RMISession login(String usn, String pw, boolean isRecover, boolean register) throws RemoteException;

}

