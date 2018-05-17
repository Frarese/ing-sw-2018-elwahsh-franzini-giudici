package it.polimi.se2018.controller.network.server;

import it.polimi.se2018.controller.network.AbsReq;
import it.polimi.se2018.util.UtilMethods;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A RMI implementation of the comm layer of a client
 * @author Francesco Franzini
 */
class RMIClientComm extends ClientComm {
    private RMISessionImpl sessionObj;
    private final Logger logger;
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
        sessionObj.setCComm(this);
        logger=Logger.getGlobal();
        rmiOutObjQueue=new LinkedList<>();
        rmiOutReqQueue=new LinkedList<>();
    }

    /**
     * Checks if there are unread objects
     * @return true if there are unread objects
     */
    boolean hasObj() {
        if(rmiOutObjQueue==null)return false;
        return UtilMethods.checkNotEmpty(rmiOutObjQueue);
    }

    /**
     * Checks if there are unread requests
     * @return true if there are unread requests
     */
    boolean hasReq() {
        if(rmiOutReqQueue==null)return false;
        return UtilMethods.checkNotEmpty(rmiOutReqQueue);
    }

    /**
     * Pops an outgoing object. If none are available it blocks until one is pushed
     * @return an object or {@code null} if errors occurred
     */
    Serializable getOutObj() {
        if(rmiOutObjQueue==null)return null;
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
        if(rmiOutReqQueue==null)return null;
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
        if(rmiOutObjQueue==null)return false;
        UtilMethods.pushAndNotifyTS(rmiOutObjQueue,obj);
        return true;
    }

    @Override
    boolean sendReq(AbsReq req) {
        if(rmiOutReqQueue==null)return false;
        UtilMethods.pushAndNotifyTS(rmiOutReqQueue,req);
        return true;
    }

    @Override
    void terminate() {
        sessionObj.terminate();
        pushBackObjects();
        rmiOutReqQueue=null;
        rmiOutObjQueue=null;
        sessionObj=null;
    }

    private void pushBackObjects(){
        LinkedList<Serializable> temp1=new LinkedList<>(rmiOutObjQueue);
        LinkedList<AbsReq> temp2=new LinkedList<>(rmiOutReqQueue);
        Collections.reverse(temp1);
        Collections.reverse(temp2);
        for (Serializable s:temp1) {
            this.client.pushObjBack(s);
        }
        for (AbsReq r:temp2) {
            this.client.pushRequestBack(r);
        }

    }

}

