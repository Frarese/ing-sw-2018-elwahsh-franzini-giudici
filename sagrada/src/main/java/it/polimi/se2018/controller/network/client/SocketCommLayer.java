package it.polimi.se2018.controller.network.client;

import it.polimi.se2018.controller.network.AbsReq;
import it.polimi.se2018.controller.network.server.LoginResponsesEnum;
import it.polimi.se2018.controller.network.server.SocketLoginRequest;
import it.polimi.se2018.util.SafeSocket;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * A socket implementation of the client network layer
 * @author Francesco Franzini
 */
class SocketCommLayer extends CommLayer {
    private SafeSocket reqSoc;
    private SafeSocket objSoc;

    private SocketInQueueFiller sockListenerReq;
    private SocketInQueueFiller sockListenerObj;

    private Logger logger;
    /**
     * Initializes this socket comm layer
     * @param comm the main comm object
     */
    SocketCommLayer(Comm comm) {
        super(comm);
        logger=Logger.getGlobal();
    }

    @Override
    synchronized String establishCon(String host, int reqPort, int objPort, boolean isRecovery, String usn, String pw, boolean newUser) {
        if(reqSoc!=null || objSoc!=null){
            logger.log(Level.WARNING,"Attempted to create socket connection on already logged comm");
            return "Already logged";
        }
        try {
            reqSoc=new SafeSocket(SafeSocket.DEFAULT_TIMEOUT);
            reqSoc.connect(host,reqPort);
            reqSoc.send(new SocketLoginRequest(usn,pw,isRecovery,newUser));
            LoginResponsesEnum answer= (LoginResponsesEnum) reqSoc.receive();
            if(answer.equals(LoginResponsesEnum.LOGIN_OK)){
                objSoc=new SafeSocket(SafeSocket.DEFAULT_TIMEOUT);
                objSoc.connect(host,objPort);
                objSoc.send(new SocketLoginRequest(usn,pw,isRecovery,newUser));
                answer= (LoginResponsesEnum) objSoc.receive();
                if(answer.equals(LoginResponsesEnum.LOGIN_OK)){
                    sockListenerObj=new SocketInQueueFiller(this,objSoc,false);
                    sockListenerReq=new SocketInQueueFiller(this,reqSoc,true);
                    sockListenerObj.start();
                    sockListenerReq.start();
                    return null;
                }else{
                    cleanUp();
                    return answer.msg;
                }
            }else{
                cleanUp();
                return answer.msg;
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE,"Error connecting through Socket "+e.getMessage());
            cleanUp();
            return "Failed initializing connection";
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE,"Error retrieving login result through Socket "+e.getMessage());
            cleanUp();
            Thread.currentThread().interrupt();
            return "Failed retrieving results";
        }
    }

    @Override
    synchronized boolean endCon() {
        close();
        cleanUp();
        return true;
    }

    @Override
    boolean sendOutObj(Serializable obj) {
        if(objSoc!=null){
            return objSoc.send(obj);
        }
        return false;
    }

    @Override
    boolean sendOutReq(AbsReq req) {
        if(reqSoc!=null){
            return reqSoc.send(req);
        }
        return false;
    }

    @Override
    synchronized boolean close() {
        reqSoc.close(false);
        objSoc.close(false);
        sockListenerReq.stop();
        sockListenerObj.stop();
        return true;
    }

    private void cleanUp(){
        sockListenerObj=null;
        sockListenerReq=null;
        reqSoc=null;
        objSoc=null;
    }
}