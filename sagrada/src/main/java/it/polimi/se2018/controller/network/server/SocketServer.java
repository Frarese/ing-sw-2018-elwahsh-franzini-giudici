package it.polimi.se2018.controller.network.server;

import java.io.IOException;
import java.net.ServerSocket;
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
    private Logger logger;

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
    public Object login(String usn, String pw, boolean isRecover, boolean register) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String delete(String usn, String pw) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void close() {
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
        throw new UnsupportedOperationException();
    }

    /**
     * Initializes the service
     * @return true if no errors are raised
     */
    private boolean init(int objPort, int reqPort) {
        try {
            objSSoc=new ServerSocket(objPort);
            reqSSoc=new ServerSocket(reqPort);

            return true;
        } catch (IOException e) {
            logger.log(Level.SEVERE,"Error initializing socket server"+e.getMessage());
            this.close();
        }
        throw new UnsupportedOperationException();
    }


}

