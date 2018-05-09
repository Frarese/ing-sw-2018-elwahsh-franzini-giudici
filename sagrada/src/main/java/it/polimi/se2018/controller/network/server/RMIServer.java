package it.polimi.se2018.controller.network.server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A RMI implementation of the server's login layer
 * @author Francesco Franzini
 */
class RMIServer extends ServerComm {

    private RMIServerIntImpl rmiObj;
    private final int port;
    private final String name;
    private Logger logger;

    /**
     * Creates a RMI login service
     *
     * @param handler the handler for the requests
     * @throws MalformedURLException if the given name is not valid
     * @throws RemoteException if errors occurred
     */
    RMIServer(ServerMain handler, int port, String name) throws MalformedURLException,RemoteException{
        super(handler);
        this.port=port;
        this.name=name;
        logger=Logger.getGlobal();

        try {
            rmiObj=new RMIServerIntImpl(this);
            LocateRegistry.createRegistry(port);
            this.tryUnexport();
            RMIServerInt stub = (RMIServerInt) UnicastRemoteObject.exportObject(rmiObj, 0);
            Naming.rebind(name, stub);
        } catch (RemoteException e) {
            logger.log(Level.SEVERE, "Couldn't create remote server "+e.getMessage());
            this.close();
            throw e;
        } catch (MalformedURLException e) {
            logger.log(Level.SEVERE,"Given name is not valid "+e.getMessage());
            this.close();
            throw e;
        }
    }


    @Override
    RMISession login(String usn, String pw, boolean isRecover, boolean register) {
        String toLog="Attempted login from "+usn+" rec:"+isRecover+" reg:"+register;
        logger.log(Level.FINER,toLog);
        LoginResponsesEnum result=super.tryLogin(usn,pw,isRecover,register);
        RMISessionImpl rmiS;
        if(result.equals(LoginResponsesEnum.LOGIN_OK)){
            Client c=new Client(usn,handler);
            if(this.handler.addClient(c,register)){
                rmiS=new RMISessionImpl(result);
                c.createRMIComm(rmiS);
            }else{
                rmiS=new RMISessionImpl(LoginResponsesEnum.USER_ALREADY_LOGGED);
            }

        }else{
            rmiS=new RMISessionImpl(result);
            rmiS.terminate();
        }
        return rmiS;
    }

    @Override
    String delete(String usn, String pw) {
        throw new UnsupportedOperationException();
    }

    @Override
    void close() {
        throw new UnsupportedOperationException();
    }

    private void tryUnexport(){
        try {
            UnicastRemoteObject.unexportObject(rmiObj, false);
        } catch (NoSuchObjectException e) {
            logger.log(Level.FINE, "Object was not exported "+e.getMessage());
        }
    }
}