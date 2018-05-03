package it.polimi.se2018.controller.network.client;

import it.polimi.se2018.controller.network.AbsReq;
import it.polimi.se2018.util.MatchIdentifier;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Queue;

/**
 * The object representing the network interface
 * @author Francesco Franzini
 */
public class Comm {
    private MatchIdentifier matchId;
    private String username;
    private String password;
    private Timestamp lastSeen;
    private String host;
    private int reqPort;
    private int objPort;

    private final Queue outReqQueue;
    private final Queue outObjQueue;

    private final Queue inObjQueue;
    private final Queue inReqQueue;


    private ClientDiscTimer discTimer;
    private ReconnectWaker reconnectW;
    private OutQueueEmptier qEmpReq;
    private OutQueueEmptier qEmpObj;
    private CommLayer commLayer;
    private InListener inListenerReq;
    private InListener inListenerObj;

    /**
     * Initializes a new comm object
     */
    public Comm(){
        throw new UnsupportedOperationException();
    }

    /**
     * Starts the operations needed to change layer
     * @param toRMI flag to choose socket/RMI
     */
    public void changeLayer(Boolean toRMI) {
        throw new UnsupportedOperationException();
    }

    /**
     * Pushes an object in the outbound queue
     * @param obj the object to send
     */
    public void pushOutObj(Serializable obj) {
        throw new UnsupportedOperationException();
    }

    /**
     * Pushes a request in the outbound queue
     * @param req the request to send
     */
    public void pushOutReq(AbsReq req) {
        throw new UnsupportedOperationException();
    }

    /**
     * Pops and sends an object to the server from the outbound queue, blocks until confirmation has been received
     */
    public void sendOutObj() {
        throw new UnsupportedOperationException();
    }

    /**
     * Pops and sends a request to the server from the outbound queue, blocks until confirmation has been received
     */
    public void sendOutReq() {
        throw new UnsupportedOperationException();
    }

    /**
     * Pops an inbound object from the queue
     * @return the object that has been received
     */
    public Serializable popInPendingObj() {
        throw new UnsupportedOperationException();
    }

    /**
     * Pops an inbound request from the queue
     * @return the request that has been received
     */
    public AbsReq popInPendingReq() {
        throw new UnsupportedOperationException();
    }

    /**
     * Pushes an object in the inbound queue
     * @param obj the object that has been received
     */
    public void pushInObj(Serializable obj) {
        throw new UnsupportedOperationException();
    }

    /**
     * Pushes a request in the inbound queue
     * @param req the request that has been received
     */
    public void pushInReq(AbsReq req) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the username that has been used in the login phase
     * @return the username that has been used in the login phase, {@code null} if none is in use
     */
    public String getUsername(){
        return this.username;
    }

    /**
     * Changes the current username
     * @param newUsername new username to use
     */
    public void setUsername(String newUsername) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the current match identifier
     * @return the current match identifier, {@code null} if none is in use
     */
    public MatchIdentifier getMatchInfo() {
        return this.matchId;
    }

    /**
     * Changes the match identifier
     * @param newInfo new match id to use
     */
    public void setMatchInfo(MatchIdentifier newInfo) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the password that has been used in the login phase
     * @return the password that has been used in the login phase, {@code null} if none is in use
     */
    public String getsPassword() {
        return this.password;
    }

    /**
     * Changes the current password
     * @param password new password to use
     */
    public void setPassword(String password) {
        throw new UnsupportedOperationException();
    }

    /**
     * Attempts a login with the given parameters
     * @param host hostname to connect to
     * @param requestPort port for the request socket or RMI connection
     * @param objectPort port for the object socket, unused if RMI
     * @param isRecovery flag to indicate that this is a login recovery attempt
     * @param usn username
     * @param pw password
     * @param newUser flag to indicate that this is a registration attempt
     * @param useRMI flag to use rmi instead of socket
     * @param utilizer object that will handle the events
     * @return {@code null} if no errors were raised, a textual representation of the error otherwise
     */
    public String login(String host, int requestPort, int objectPort, boolean isRecovery, String usn, String pw, boolean newUser, boolean useRMI, CommUtilizer utilizer) {
        throw new UnsupportedOperationException();
    }

    /**
     * Attempts to logout
     * @return true if no errors were raised
     */
    public boolean logout() {
        throw new UnsupportedOperationException();
    }

    /**
     * Handles a logout request from the server
     */
    public void logoutReqeustReceived() {
        throw new UnsupportedOperationException();
    }

    /**
     * Attempts to recover the connection to the server
     * @param purgeOnFail if true the comm layer will be purged on failure to reconnect
     * @return true if the connection has been recovered
     */
    public boolean tryRecover(boolean purgeOnFail) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the last seen timestamp
     * @return the last seen timestamp
     */
    public Timestamp getLastSeen() {
        return this.lastSeen;
    }

    /**
     * Disconnects the comm layer
     * @return true if no errors were raised
     */
    public boolean beginDisconnectedRoutines() {
        throw new UnsupportedOperationException();
    }

    /**
     * Updates this object's last seen timestamp
     */
    public void updateTs() {
        throw new UnsupportedOperationException();
    }

    /**
     * Purges the comm layer
     */
    private void purgeComm() {
        throw new UnsupportedOperationException();
    }


}
