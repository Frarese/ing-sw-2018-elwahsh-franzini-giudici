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
        if(server==null)throw new IllegalArgumentException("Server cannot be null");
        this.server = server;
    }

    @Override
    public RMISession login(String usn, String pw, boolean isRecover, boolean register) {
        return server.login(usn, pw, isRecover, register);
    }

    @Override
    public boolean equals(Object o) {
        return o != null && o.getClass().equals(this.getClass()) && super.equals(o) && ((RMIServerIntImpl) o).server.equals(this.server);
    }

    @Override
    public int hashCode(){
        return super.hashCode()^server.hashCode();
    }
}