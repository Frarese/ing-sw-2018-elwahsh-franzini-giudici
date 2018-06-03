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

    private final Logger logger;

    private String host;
    private int reqPort;
    private int objPort;

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
        logger.log(Level.INFO,"Attempting socket login to {0}",reqPort);
        if(reqSoc!=null || objSoc!=null){
            logger.log(Level.WARNING,"Attempted to create socket connection on already logged comm");
            return "Already logged";
        }
        try {
            this.host=host;
            this.reqPort=reqPort;
            this.objPort=objPort;
            return connect(new SafeSocket(SafeSocket.DEFAULT_TIMEOUT), new SafeSocket(SafeSocket.DEFAULT_TIMEOUT),isRecovery,usn,pw,newUser);
        } catch (IOException e) {
            logger.log(Level.SEVERE,"Error building safe socket"+e.getMessage());
            close();
            return "Failed initializing connection";
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
        return objSoc != null && objSoc.send(obj);
    }

    @Override
    boolean sendOutReq(AbsReq req) {
        return reqSoc != null && reqSoc.send(req);
    }

    @Override
    synchronized boolean close() {
        if(reqSoc!=null){
            reqSoc.close(false);
        }
        if(objSoc!=null){
            objSoc.close(false);
        }
        if(sockListenerObj!=null){
            sockListenerObj.stop();
        }
        if(sockListenerReq!=null){
            sockListenerReq.stop();
        }
        cleanUp();
        return true;
    }

    private void cleanUp(){
        sockListenerObj=null;
        sockListenerReq=null;
        reqSoc=null;
        objSoc=null;
        host="";
        reqPort=0;
        objPort=0;
    }

    private String connect(SafeSocket reqSoc,SafeSocket objSoc, boolean isRecovery, String usn, String pw, boolean newUser){
        try {
            this.reqSoc=reqSoc;
            if(!reqSoc.connect(host,reqPort)){
                return "Failed to connect to "+host+" "+reqPort;
            }

            reqSoc.send(new SocketLoginRequest(usn,pw,isRecovery,newUser));
            LoginResponsesEnum answer= (LoginResponsesEnum) reqSoc.receive();

            if(answer == LoginResponsesEnum.LOGIN_OK){
                logger.log(Level.INFO,"First login phase successful");
                this.objSoc=objSoc;
                objSoc.connect(host,objPort);

                objSoc.send(new SocketLoginRequest(usn,pw,isRecovery,newUser));
                answer= (LoginResponsesEnum) objSoc.receive();

                if(answer == LoginResponsesEnum.LOGIN_OK){
                    sockListenerObj=new SocketInQueueFiller(this,objSoc,false);
                    sockListenerReq=new SocketInQueueFiller(this,reqSoc,true);
                    sockListenerObj.start();
                    sockListenerReq.start();

                    logger.log(Level.INFO,"Login was successful");
                    return null;
                }else{
                    close();
                    return answer.msg;
                }
            }else{
                close();
                return answer.msg;
            }
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE,"Error retrieving login result through Socket "+e.getMessage());
            close();
            Thread.currentThread().interrupt();
            return "Failed retrieving results";
        }
    }
}