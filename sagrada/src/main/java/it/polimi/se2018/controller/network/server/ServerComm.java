package it.polimi.se2018.controller.network.server;

/**
 * The server's login service abstract comm layer
 * @author Francesco Franzini
 */
abstract class ServerComm {
    private ServerMain handler;

    /**
     * Creates a login service
     * @param handler the handler for the requests
     */
    ServerComm(ServerMain handler) {
        this.handler=handler;
    }

    /**
     * Attempts a login with the given parameters
     * @param usn username
     * @param pw password
     * @param isRecover flag to indicate that this is a connection recovery
     * @param register flag to indicate that this is a new user
     * @return an Object describing the result
     */
    public abstract Object login(String usn, String pw, boolean isRecover, boolean register);

    /**
     * Attempts to delete a user
     * @param usn username
     * @param pw password
     * @return a textual representation of the outcome
     */
    public abstract String delete(String usn, String pw);


}