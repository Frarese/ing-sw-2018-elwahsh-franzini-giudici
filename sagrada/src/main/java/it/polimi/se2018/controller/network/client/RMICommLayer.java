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
    private final RMIClientListener listenerReq;
    private final RMIClientListener listenerObj;
    private final Logger logger;
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
                listenerReq.stop();
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
                listenerReq.stop();
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
                listenerObj.stop();
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
            logger.log(Level.INFO,"Attempting login through rmi at {0}",host+":"+reqPort);
            Registry registry = LocateRegistry.getRegistry(host, reqPort);
            RMIServerInt loginObj = (RMIServerInt) registry.lookup(LoginResponsesEnum.RESOURCE_NAME.msg);
            RMISession session=loginObj.login(usn,pw,isRecovery,newUser);
            if(session==null)return "Login failed";
            if(session.getLoginOutput() == LoginResponsesEnum.LOGIN_OK){
                logger.log(Level.INFO,"Login was successful");
                this.sessionObj=session;
                this.serverLoginObj=loginObj;
                listenerReq.start();
                listenerObj.start();
                return null;
            }else{
                logger.log(Level.INFO,"Login failed");
                cleanUp();
                return session.getLoginOutput().msg;
            }
        } catch(RemoteException e){
            logger.log(Level.SEVERE,"Error connecting through RMI {0}",e.getMessage());
            cleanUp();
            return "Failed initializing RMI connection";
        } catch (NotBoundException e) {
            logger.log(Level.SEVERE,"RMI Resource not found"+e.getMessage());
            cleanUp();
            return "Failed to find RMI resource";
        } catch (Exception e){
            logger.log(Level.SEVERE,"Failed initializing RMI connection "+e.getMessage());
        }
        return "Error";

    }

    @Override
    synchronized boolean endCon() {
        close();
        cleanUp();
        return true;
    }

    @Override
    boolean sendOutObj(Serializable obj) {
        if(sessionObj!=null){
            try {
                return sessionObj.sendObj(obj);
            } catch (RemoteException e) {
                logger.log(Level.SEVERE, "Error sending object through RMI");
                listenerObj.stop();
            }
        }
        return false;
    }

    @Override
    boolean sendOutReq(AbsReq req) {
        logger.log(Level.FINEST,"Sending out req {0}",req);
        if(sessionObj!=null){
            try {
                return sessionObj.sendReq(req);
            } catch (RemoteException e) {
                logger.log(Level.SEVERE, "Error sending request through RMI");
                listenerReq.stop();
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

    private void cleanUp(){
        sessionObj=null;
        serverLoginObj=null;
    }

}
