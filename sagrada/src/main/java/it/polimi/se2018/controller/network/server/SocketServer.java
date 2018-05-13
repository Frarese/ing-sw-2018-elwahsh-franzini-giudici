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

    /**
     * Creates a Socket login service
     *
     * @param handler the handler for the requests
     */
    SocketServer(ServerMain handler,int objPort,int reqPort) {
        super(handler);
        logger=Logger.getGlobal();
        waitingObjConnClients=new HashMap<>();
        this.init(objPort,reqPort);
    }


    @Override
    Object login(String usn, String pw, boolean isRecover, boolean register) {
        throw new UnsupportedOperationException();
    }

    @Override
    String delete(String usn, String pw) {
        throw new UnsupportedOperationException();
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
        reqT=new Thread(new LoginListener());
        objT=new Thread(new CompletionListener());

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
        if(!reqSer.getClass().equals(SocketLoginRequest.class)){
            logger.log(Level.FINEST,"A Client is sending malformed login object");
            ss.close(true);
            return null;
        }

        SocketLoginRequest req=(SocketLoginRequest)reqSer;
        if(!req.isValid()){
            logger.log(Level.FINEST,"A Client is sending malformed login object");
            ss.send(LoginResponsesEnum.MALFORMED_REQUEST);
            ss.close(true);
            return null;
        }
        return req;
    }

    /**
     * Class that is used to listen to incoming login requests
     */
    private class LoginListener implements Runnable{

        @Override
        public void run(){
            while(!Thread.currentThread().isInterrupted()){
                try {
                    Socket s=reqSSoc.accept();
                    Thread t=new Thread(()->{
                        try {
                            listenLogin(s);
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

        private void listenLogin(Socket s) throws IOException, InterruptedException {
            SafeSocket ss=new SafeSocket(s,SafeSocket.DEFAULT_TIMEOUT);
            SocketLoginRequest logReq=checkAndWrap(ss);
            if(logReq==null)return;
            LoginResponsesEnum response=SocketServer.super.tryLogin(logReq.username,logReq.password,logReq.isRecovery,logReq.isNewUser);
            if(response.equals(LoginResponsesEnum.LOGIN_OK)){
                Client c=new Client(logReq.username,handler);
                if(SocketServer.this.handler.addClient(c,logReq.isNewUser)){
                    ss.send(LoginResponsesEnum.LOGIN_OK);
                    waitingObjConnClients.put(logReq.username,
                            new WaitingObjSocketClient(ss,logReq.username,logReq.password,logReq.isRecovery));
                }else{
                    ss.send(LoginResponsesEnum.USER_ALREADY_LOGGED);
                    ss.close(true);
                }

            }else{
                ss.send(response);
                ss.close(true);
            }

        }


    }

    /**
     * Class that is used to listen for incoming completion requests
     */
    private class CompletionListener implements Runnable{
        @Override
        public void run(){
            while(!Thread.currentThread().isInterrupted()){
                try {
                    Socket s=reqSSoc.accept();
                    Thread t=new Thread(()->{
                        try {
                            listenCompletion(s);
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
        private void listenCompletion(Socket s)throws IOException,InterruptedException{
            SafeSocket ss=new SafeSocket(s,SafeSocket.DEFAULT_TIMEOUT);
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
                Client c=new Client(username,handler);
                if(SocketServer.this.handler.addClient(c,cReq.isRecovery)){
                    c.createSocketComm(wObj.reqS,ss);
                }else{
                    ss.send(LoginResponsesEnum.USER_ALREADY_LOGGED);
                    ss.close(true);
                }

            }else{
                logger.log(Level.FINEST,"A client is trying to complete with wrong credentials");
                ss.send(LoginResponsesEnum.WRONG_CREDENTIALS);
                ss.close(true);
            }
        }
    }
}

