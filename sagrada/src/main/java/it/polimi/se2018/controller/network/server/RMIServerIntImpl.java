package it.polimi.se2018.controller.network.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * An implementation of the RMI login interface
 * @author Francesco Franzini
 */
class RMIServerIntImpl extends UnicastRemoteObject implements RMIServerInt {
    private final transient RMIServer server;

    RMIServerIntImpl(RMIServer server) throws RemoteException {
        super();
        this.server = server;
    }

    @Override
    public RMISession login(String usn, String pw, boolean isRecover, boolean register) {
        return server.login(usn, pw, isRecover, register);
    }


    @Override
    public String delete(String usn, String pw) {
        throw new UnsupportedOperationException();
    }


}