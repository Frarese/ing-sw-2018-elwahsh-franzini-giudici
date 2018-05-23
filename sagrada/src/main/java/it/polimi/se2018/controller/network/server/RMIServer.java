package it.polimi.se2018.controller.network.server;

import java.net.InetAddress;
import java.net.UnknownHostException;
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
    private final Logger logger;

    /**
     * Creates a RMI login service
     *
     * @param handler the handler for the requests
     * @param port the port to use
     * @param name the name to use for the resource
     * @param ip the name of the network interface to use
     * @throws RemoteException if errors occurred
     */
    RMIServer(ServerMain handler, int port, String name,InetAddress ip) throws RemoteException{
        super(handler);
        logger=Logger.getGlobal();
        try {
            System.setProperty("java.rmi.server.hostname",ip.getHostAddress());
            rmiObj=new RMIServerIntImpl(this);
            LocateRegistry.createRegistry(port);
            this.tryUnexport();
            RMIServerInt stub = (RMIServerInt) UnicastRemoteObject.exportObject(rmiObj, 0);
            LocateRegistry.getRegistry(port).rebind(name,stub);
            logger.log(Level.INFO,"RMI server listening on {0}",InetAddress.getLocalHost().getHostAddress()+":"+port+" "+name);
        } catch (RemoteException e) {
            logger.log(Level.SEVERE, "Couldn't create RMI server ",e);
            this.close();
            throw e;
        } catch (UnknownHostException e) {
            logger.log(Level.SEVERE,"Error reading hostname");
        }
    }


    /**
     * Attempts a login with the given parameters
     * @param usn username
     * @param pw password
     * @param isRecover flag to indicate that this is a connection recovery
     * @param register flag to indicate that this is a new user
     * @return an Object describing the result
     */
    RMISession login(String usn, String pw, boolean isRecover, boolean register) {
        RMISessionImpl rmiS=null;
        try {
            String toLog="Attempted login from "+usn+" rec:"+isRecover+" reg:"+register;
            logger.log(Level.FINER,toLog);
            LoginResponsesEnum result=super.tryLogin(usn,pw,isRecover,register);

            if (result.equals(LoginResponsesEnum.LOGIN_OK)) {
                Client c;
                boolean createResult=true;
                if(isRecover){
                    c=handler.getClient(usn);
                }else{
                    c=new Client(usn,this.handler);
                    createResult=this.handler.addClient(c);
                }
                if(createResult){
                    rmiS = new RMISessionImpl(result);
                    createResult=c.createRMIComm(rmiS);
                    if(createResult)return rmiS;
                }
                rmiS = new RMISessionImpl(LoginResponsesEnum.USER_ALREADY_LOGGED);

            } else {
                rmiS = new RMISessionImpl(result);
                rmiS.terminate();
            }
        }catch (RemoteException e){
            logger.log(Level.SEVERE,"Error creating session object "+e);
            return null;
        }catch (Exception e){
            logger.log(Level.SEVERE,"Exception trying to login "+usn+" on RMI "+e.getMessage());
        }
        return rmiS;
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