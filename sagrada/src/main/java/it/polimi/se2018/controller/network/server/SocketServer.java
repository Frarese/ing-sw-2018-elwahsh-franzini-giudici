package it.polimi.se2018.controller.network.server;

import it.polimi.se2018.util.SafeSocket;

import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A Socket implementation of the server's login layer
 * @author Francesco Franzini
 */
class SocketServer extends ServerComm {
    private ServerSocket objSSoc;
    private ServerSocket reqSSoc;
    private final HashMap<String,WaitingObjSocketClient> waitingObjConnClients;
    private final Logger logger;

    private Thread reqT;
    private Thread objT;

    private final int objPort;
    private final int reqPort;

    /**
     * Creates a Socket login service
     *
     * @param handler the handler for the requests
     * @param objPort object port to use
     * @param reqPort request port to use
     */
    SocketServer(ServerMain handler,int objPort,int reqPort){
        super(handler);
        this.reqPort=reqPort;
        this.objPort=objPort;
        logger=Logger.getGlobal();
        logger.log(Level.INFO,"Starting Socket Server");
        waitingObjConnClients=new HashMap<>();
    }

    @Override
    void connect() throws IOException{
        if(!this.init(objPort,reqPort))throw new IOException("Failed to initialize socket server");
        logger.log(Level.INFO,"Socket Server running on request:obj {0}",reqPort+":"+objPort);
    }

    @Override
    void close() {
        if(objSSoc!=null){
            try {
                objSSoc.close();
            } catch (IOException e) {
                logger.log(Level.SEVERE,"Error closing object socket server"+e.getMessage());
            }
            objSSoc=null;
        }
        if(reqSSoc!=null){
            try {
                reqSSoc.close();
            } catch (IOException e) {
                logger.log(Level.SEVERE,"Error closing request socket server"+e.getMessage());
            }
            reqSSoc=null;
        }
        if(objT!=null){
            objT.interrupt();
            objT=null;
        }
        if(reqT!=null){
            reqT.interrupt();
            reqT=null;
        }
    }

    /**
     * Initializes the service
     * @return true if no errors are raised
     */
    private boolean init(int objPort, int reqPort) {
        try {
            objSSoc=new ServerSocket(objPort);
            reqSSoc=new ServerSocket(reqPort);
        } catch (IOException e) {
            logger.log(Level.SEVERE,"Error initializing socket server"+e.getMessage());
            this.close();
            return false;
        }
        reqT=new Thread(new LoginSocketListener(true));
        objT=new Thread(new LoginSocketListener(false));

        reqT.start();
        objT.start();
        return true;
    }

    /**
     * Helper method to extract the first request and make the first checks
     * @param ss the SafeSocket to use
     * @return the SocketLoginRequest that has been received, or {@code null} if it was not valid
     * @throws InterruptedException if interrupted while reading
     */
    private SocketLoginRequest checkAndWrap(SafeSocket ss) throws InterruptedException {
        Serializable reqSer=ss.receive();
        if(reqSer==null)return null;
        if(!reqSer.getClass().equals(SocketLoginRequest.class)){
            logger.log(Level.FINEST,"A Client is sending malformed login object "+reqSer.getClass());
            ss.close(true);
            return null;
        }

        SocketLoginRequest req=(SocketLoginRequest)reqSer;
        if(!req.isValid()){
            logger.log(Level.FINEST,"A Client is sending an invalid login object");
            ss.send(LoginResponsesEnum.MALFORMED_REQUEST);
            ss.close(true);
            return null;
        }
        return req;
    }

    /**
     * Class that is used to listen to incoming requests
     */
    private class LoginSocketListener implements Runnable{
        private final boolean isLogin;

        /**
         * Initializes this listener
         * @param isLogin the parameter to pass to {@link it.polimi.se2018.controller.network.server.SocketServer.LoginSocketListener#loopCall}
         */
        LoginSocketListener(boolean isLogin) {
            this.isLogin=isLogin;
        }

        @Override
        public void run(){
            while(!Thread.currentThread().isInterrupted()){
                try {
                    Socket s;
                    if(isLogin){
                        s=reqSSoc.accept();
                    }else{
                        s=objSSoc.accept();
                    }
                    Thread t=new Thread(()->{
                        try {
                            loopCall(s,isLogin);
                        } catch (IOException e) {
                            logger.log(Level.SEVERE,"IO Error accepting login "+e.getMessage());
                        } catch (InterruptedException e) {
                            logger.log(Level.WARNING,"Interrupted accepting login "+e.getMessage());
                            Thread.currentThread().interrupt();
                        }
                    });
                    t.start();
                } catch (IOException e) {
                    logger.log(Level.SEVERE,"IO Error accepting client "+e.getMessage());
                    Thread.currentThread().interrupt();
                }
            }
        }

    }

    /**
     * Helper method to call in the listener loop
     * @param s socket that has been accepted
     * @param isLogin boolean to choose behaviour(true for login, false for completion)
     * @throws IOException if an IOException occurs
     * @throws InterruptedException if this thread is interrupted while waiting
     */
    private void loopCall(Socket s,boolean isLogin) throws IOException, InterruptedException {
        SafeSocket ss=new SafeSocket(s,SafeSocket.DEFAULT_TIMEOUT);
        if(isLogin){
            listenLogin(ss);
        }else{
            listenCompletion(ss);
        }
    }

    /**
     * Processes the login for this socket
     * @param ss SafeSocket to process
     * @throws InterruptedException if this thread is interrupted
     */
    private void listenLogin(SafeSocket ss) throws InterruptedException {
        logger.log(Level.FINEST,"Attempted socket login from {0}",ss.getLocalSocketAddress());
        SocketLoginRequest logReq=checkAndWrap(ss);
        if(logReq==null)return;
        LoginResponsesEnum response=SocketServer.super.tryLogin(logReq.username,logReq.password,logReq.isRecovery,logReq.isNewUser);
        if(response == LoginResponsesEnum.LOGIN_OK){
            Client c;
            boolean createResult=true;
            if(!logReq.isRecovery){
                c=new Client(logReq.username,handler);
                createResult=SocketServer.this.handler.addClient(c);
            }
            if(createResult){
                waitingObjConnClients.put(logReq.username,
                        new WaitingObjSocketClient(ss,logReq.password,logReq.isRecovery));
                ss.send(LoginResponsesEnum.LOGIN_OK);
                logger.log(Level.FINEST,"Added {0} to waitingObj map",logReq.username);
            }else{
                logger.log(Level.FINEST,"Client {0} was already logged ",logReq.username);
                ss.send(LoginResponsesEnum.USER_ALREADY_LOGGED);
                ss.close(true);
            }

        }else{
            ss.send(response);
            ss.close(true);
        }

    }

    /**
     * Processes the completion for this socket
     * @param ss Socket to process
     * @throws InterruptedException if this thread is interrupted
     */
    private void listenCompletion(SafeSocket ss)throws InterruptedException{
        logger.log(Level.FINEST,"Attempted socket completion from {0}",ss.getLocalSocketAddress());
        SocketLoginRequest cReq=checkAndWrap(ss);
        if(cReq==null)return;

        String username=cReq.username;
        String password=cReq.password;
        boolean isRecovery=cReq.isRecovery;
        WaitingObjSocketClient wObj=waitingObjConnClients.get(username);
        if(wObj==null){
            logger.log(Level.FINEST,"A client is trying to complete before initiating login");
            ss.send(LoginResponsesEnum.MALFORMED_REQUEST);
            ss.close(true);
            return;
        }
        if(wObj.psw.equals(password) && wObj.isRecovery==isRecovery){
            waitingObjConnClients.remove(username);
            Client c=SocketServer.this.handler.getClient(username);
            if(!c.createSocketComm(wObj.reqS,ss)){
                ss.send(LoginResponsesEnum.USER_ALREADY_LOGGED);
                ss.close(true);
            }else{
                ss.send(LoginResponsesEnum.LOGIN_OK);
                logger.log(Level.FINEST,"Successful socket completion from {0}",username);
            }

        }else{
            logger.log(Level.FINEST,"A client is trying to complete with wrong credentials");
            ss.send(LoginResponsesEnum.WRONG_CREDENTIALS);
            ss.close(true);
        }
    }
}

