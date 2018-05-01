package it.polimi.se2018.controller.network.server;

import it.polimi.se2018.controller.network.AbsReq;

import java.io.Serializable;

class RMISessionImpl implements RMISession {
    private RMIClientComm cComm;
    private boolean terminated;
    private final String loginOutput;

    /**
     * Initializes this session object with the given login output string
     * @param loginOutput the textual representation of the login output
     */
    public RMISessionImpl(String loginOutput) {
        this.loginOutput=loginOutput;
        throw new UnsupportedOperationException();
    }

    /**
     * Terminates this session object
     */
    public void terminate() {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the client RMI comm layer to call
     * @param cComm the comm layer
     */
    public void setCComm(RMIClientComm cComm) {
        this.cComm=cComm;
    }

    @Override
    public boolean hasReq() {
        throw new UnsupportedOperationException();
    }

    @Override
    public AbsReq getReq() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean sendReq(AbsReq obj) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasObj() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Serializable getObj() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean sendObj(Serializable obj) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isTerminated() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getLoginOutput() {
        throw new UnsupportedOperationException();
    }


}