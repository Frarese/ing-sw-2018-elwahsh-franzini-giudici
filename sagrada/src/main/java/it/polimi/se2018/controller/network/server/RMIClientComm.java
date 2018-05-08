package it.polimi.se2018.controller.network.server;

import it.polimi.se2018.controller.network.AbsReq;
import it.polimi.se2018.util.UtilMethods;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A RMI implementation of the comm layer of a client
 * @author Francesco Franzini
 */
class RMIClientComm extends ClientComm {
    private final RMISessionImpl sessionObj;
    private Logger logger;
    private Queue<Serializable> rmiOutObjQueue;
    private Queue<AbsReq> rmiOutReqQueue;

    /**
     * Initializes a RMI comm layer with the given parameters
     * @param sessionObj the session object to use
     * @param client the client to call
     */
    RMIClientComm(RMISessionImpl sessionObj,Client client) {
        super(client);
        this.sessionObj=sessionObj;
        logger=Logger.getGlobal();
        rmiOutObjQueue=new LinkedList<>();
        rmiOutReqQueue=new LinkedList<>();
    }

    /**
     * Checks if there are unread objects
     * @return true if there are unread objects
     */
    boolean hasObj() {
        return UtilMethods.checkEmpty(rmiOutObjQueue);
    }

    /**
     * Checks if there are unread requests
     * @return true if there are unread requests
     */
    boolean hasReq() {
        return UtilMethods.checkEmpty(rmiOutReqQueue);
    }

    /**
     * Pops an outgoing object. If none are available it blocks until one is pushed
     * @return an object or {@code null} if errors occurred
     */
    Serializable getOutObj() {
        try {
            return UtilMethods.waitAndPopTS(rmiOutObjQueue);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.log(Level.SEVERE,"Failed popping object from rmi outgoing queue "+e.getMessage());
        }
        return null;
    }

    /**
     * Pops an outgoing request. If none are available it blocks until one is pushed
     * @return a request or {@code null} if errors occurred
     */
    AbsReq getOutReq() {
        try {
            return UtilMethods.waitAndPopTS(rmiOutReqQueue);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.log(Level.SEVERE,"Failed popping request from rmi outgoing queue "+e.getMessage());
        }
        return null;
    }


    @Override
    boolean sendObj(Serializable obj) {
        UtilMethods.pushAndNotifyTS(rmiOutObjQueue,obj);
        return true;
    }

    @Override
    boolean sendReq(AbsReq req) {
        UtilMethods.pushAndNotifyTS(rmiOutReqQueue,req);
        return true;
    }

    @Override
    void terminate() {
        throw new UnsupportedOperationException();
    }
}

