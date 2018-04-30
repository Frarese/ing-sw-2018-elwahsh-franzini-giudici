package it.polimi.se2018.controller.network.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;

/**
 * A Socket implementation of the server's login layer
 * @author Francesco Franzini
 */
class SocketServer extends ServerComm {
    private ServerSocket objSSoc;
    private ServerSocket reqSSoc;
    private final HashMap<String,WaitingObjSocketClient> waitingObjConnClients;

    /**
     * Creates a Socket login service
     *
     * @param handler the handler for the requests
     */
    public SocketServer(ServerMain handler,int objPort,int reqPort) {
        super(handler);
        waitingObjConnClients=new HashMap<String,WaitingObjSocketClient>();
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

    /**
     * Initializes the service
     * @return true if no errors are raised
     */
    private boolean init(int objPort, int reqPort) {
        try {
            objSSoc=new ServerSocket(objPort);
            reqSSoc=new ServerSocket(reqPort);
        } catch (IOException e) {
            e.printStackTrace();
        }

        throw new UnsupportedOperationException();
    }
}

