package it.polimi.se2018.controller.network.server;

/**
 * An implementation of the RMI login interface
 * @author Francesco Franzini
 */
class RMIServerIntImpl implements RMIServerInt {
    private final RMIServer server;

    public RMIServerIntImpl(RMIServer server) {
        this.server = server;
    }

    @Override
    public RMISession login(String usn, String pw, boolean isRecover, boolean register) {
        throw new UnsupportedOperationException();
    }


    @Override
    public String delete(String usn, String pw) {
        throw new UnsupportedOperationException();
    }


}