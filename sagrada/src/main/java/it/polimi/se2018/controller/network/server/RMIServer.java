package it.polimi.se2018.controller.network.server;

/**
 * A RMI implementation of the server's login layer
 * @author Francesco Franzini
 */
class RMIServer extends ServerComm {
    private RMIServerIntImpl rmiObj;

    /**
     * Creates a RMI login service
     *
     * @param handler the handler for the requests
     */
    public RMIServer(ServerMain handler) {
        super(handler);
    }


    @Override
    public Object login(String usn, String pw, boolean isRecover, boolean register) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String delete(String usn, String pw) {
        throw new UnsupportedOperationException();
    }

}