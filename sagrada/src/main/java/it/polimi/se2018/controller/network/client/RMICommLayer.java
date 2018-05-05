package it.polimi.se2018.controller.network.client;

import it.polimi.se2018.controller.network.AbsReq;
import it.polimi.se2018.controller.network.server.LoginResponsesEnum;
import it.polimi.se2018.controller.network.server.RMIServerInt;
import it.polimi.se2018.controller.network.server.RMISession;
import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A RMI implementation of the client network layer
 * @author Francesco Franzini
 */
class RMICommLayer extends CommLayer {
    private RMISession sessionObj;
    private RMIClientListener listenerReq;
    private RMIClientListener listenerObj;
    private Logger logger;
    private RMIServerInt serverLoginObj;


    /**
     * Initializes this comm layer with the given parameters
     * @param comm client main comm
     */
    RMICommLayer(Comm comm) {
        super(comm);
        sessionObj=null;
        logger=Logger.getGlobal();

        listenerReq=new RMIClientListener(this,true);
        listenerObj=new RMIClientListener(this,false);
    }

    /**
     * Retrieves a request from the server
     * @return the request that has been retrieved, {@code null} if errors occurred
     */
    AbsReq getReq() {
        if(sessionObj!=null){
            try {
                return sessionObj.getReq();
            } catch (RemoteException e) {
                logger.log(Level.SEVERE, "Error retrieving request through RMI");
            }
        }
        return null;
    }

    /**
     * Checks if the server has an outbound object ready to be read
     * @return true if the server has an outbound object ready to be read
     */
    boolean hasObj() {
        if(sessionObj!=null){
            try {
                return sessionObj.hasObj();
            } catch (RemoteException e) {
                logger.log(Level.SEVERE, "Error retrieving object availability through RMI");
            }
        }
        return false;
    }

    /**
     * Checks if the server has an outbound request ready to be read
     * @return true if the server has an outbound request ready to be read
     */
    boolean hasReq() {
        if(sessionObj!=null){
            try {
                return sessionObj.hasReq();
            } catch (RemoteException e) {
                logger.log(Level.SEVERE, "Error retrieving request availability through RMI");
            }
        }
        return false;
    }

    /**
     * Retrieves an object from the server
     * @return the object that has been retrieved, {@code null} if errors occurred
     */
    Serializable getObj() {
        if(sessionObj!=null){
            try {
                return sessionObj.getObj();
            } catch (RemoteException e) {
                logger.log(Level.SEVERE, "Error retrieving object through RMI");
            }
        }
        return null;
    }

    @Override
    synchronized String establishCon(String host, int reqPort, int objPort, boolean isRecovery, String usn, String pw, boolean newUser) {
        if(sessionObj!=null || serverLoginObj!=null){
            logger.log(Level.WARNING,"Attempted to create rmi connection on already logged comm");
            return "Already logged";
        }
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", reqPort);
            RMIServerInt loginObj = (RMIServerInt) registry.lookup(LoginResponsesEnum.RESOURCE_NAME.msg);
            RMISession session=loginObj.login(usn,pw,isRecovery,newUser);
            if(session.getLoginOutput().equals(LoginResponsesEnum.LOGIN_OK)){
                this.sessionObj=session;
                this.serverLoginObj=loginObj;
                listenerReq.start();
                listenerObj.start();
                return null;
            }else{
                return session.getLoginOutput().msg;
            }
        } catch(RemoteException e){
            logger.log(Level.SEVERE,"Error connecting through RMI "+e.getMessage());
            return "Failed initializing connection";
        } catch (NotBoundException e) {
            logger.log(Level.SEVERE,"RMI Resource not found"+e.getMessage());
            return "Failed to find RMI resource";
        }

    }

    @Override
    synchronized boolean endCon() {
        close();
        listenerReq=null;
        listenerObj=null;
        sessionObj=null;
        serverLoginObj=null;
        return true;
    }

    @Override
    boolean sendOutObj(Serializable obj) {
        if(sessionObj!=null){
            try {
                return sessionObj.sendObj(obj);
            } catch (RemoteException e) {
                logger.log(Level.SEVERE, "Error sending object through RMI");
            }
        }
        return false;
    }

    @Override
    boolean sendOutReq(AbsReq req) {
        if(sessionObj!=null){
            try {
                return sessionObj.sendReq(req);
            } catch (RemoteException e) {
                logger.log(Level.SEVERE, "Error sending request through RMI");
            }
        }
        return false;
    }

    @Override
    synchronized boolean close() {
        listenerObj.stop();
        listenerReq.stop();
        return true;
    }



}
