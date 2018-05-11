package it.polimi.se2018.controller.network.server;

import it.polimi.se2018.controller.network.AbsReq;

import java.io.Serializable;

class RMISessionImpl implements RMISession {
    private RMIClientComm cComm;
    private boolean terminated;
    private final LoginResponsesEnum loginOutput;

    /**
     * Initializes this session object with the given login output string
     * @param loginOutput the textual representation of the login output
     */
    RMISessionImpl(LoginResponsesEnum loginOutput) {
        this.loginOutput=loginOutput;
        this.terminated=false;
    }

    /**
     * Terminates this session object
     */
    void terminate() {
        this.terminated=true;
        cComm=null;
    }

    /**
     * Sets the client RMI comm layer to call
     * @param cComm the comm layer
     */
    void setCComm(RMIClientComm cComm) {
        this.cComm=cComm;
    }

    @Override
    public boolean hasReq() {
        if(cComm==null)return false;
        return cComm.hasReq();
    }

    @Override
    public AbsReq getReq() {
        if(cComm==null)return null;
        return cComm.getOutReq();
    }

    @Override
    public boolean sendReq(AbsReq req) {
        if(cComm==null)return false;
        cComm.pushInReq(req);
        return true;
    }

    @Override
    public boolean hasObj() {
        if(cComm==null)return false;
        return cComm.hasObj();
    }

    @Override
    public Serializable getObj() {
        if(cComm==null)return null;
        return cComm.getOutObj();
    }

    @Override
    public boolean sendObj(Serializable obj) {
        if(cComm==null)return false;
        cComm.pushInObj(obj);
        return true;
    }

    @Override
    public boolean isTerminated() {
        return terminated;
    }

    @Override
    public LoginResponsesEnum getLoginOutput() {
        return this.loginOutput;
    }


}