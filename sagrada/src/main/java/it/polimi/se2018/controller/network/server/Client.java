package it.polimi.se2018.controller.network.server;

import it.polimi.se2018.controller.network.client.AbsReq;
import it.polimi.se2018.util.SafeSocket;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Queue;

/**
 * Class representing a client connected to the server
 * @author Francesco Franzini
 */
class Client {
    private static int defaultDeathTimeout = 60;
    private static int defaultWarningTimeout = 60;

    private final ServerMain serverMain;
    public final String usn;

    private boolean isZombie;
    private boolean acceptedInvite;
    private Timestamp lastSeenTs;

    private ClientComm cComm;
    private Match match;

    private ServerInQueueEmptier inQueueEmp;
    private ServerOutQueueEmptier outQueueEmp;
    private DisconnectChecker disconnectChecker;

    private Queue inObjQueue;
    private Queue inReqQueue;
    private Queue outReqQueue;
    private Queue outObjQueue;

    /**
     * Builds a client with the given username
     * @param usn username
     * @param server server
     */
    public Client(String usn, ServerMain server) {
        this.usn=usn;
        this.serverMain=server;
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the current match, if present
     * @return the current {@link it.polimi.se2018.controller.network.server.Match} or {@code null} if not present
     */
    public Match getMatch() {
        return this.match;
    }

    /**
     * Builds a Socket comm layer for this client
     * @param reqSoc the request {@link it.polimi.se2018.util.SafeSocket}
     * @param objSoc the object {@link it.polimi.se2018.util.SafeSocket}
     */
    public void createSocketComm(SafeSocket reqSoc, SafeSocket objSoc) {
        throw new UnsupportedOperationException();
    }

    /**
     * Builds a RMI comm layer for this client
     * @param sessionObj the {@link it.polimi.se2018.controller.network.server.RMISessionImpl} that is used as session object
     */
    public void createRMIComm(RMISessionImpl sessionObj) {
        throw new UnsupportedOperationException();
    }

    /**
     * Updates the last seen timestamp
     */
    private void updateTs() {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the last seen timestamp
     * @return last seen timestamp
     */
    public Timestamp lastSeen() {
        return lastSeenTs;
    }

    /**
     * Checks if this client is zombie
     * @return true if this client is zombie
     */
    public boolean isZombie() {
        throw new UnsupportedOperationException();
    }

    /**
     * Checks if this client has accepted an invite
     * @return true if this client has accepted an invite
     */
    public boolean hasAcceptedInvite() {
        return this.acceptedInvite;
    }

    /**
     * Notifies this object that its user has accepted an invite
     * @return true if success(no other invites where accepted)
     */
    public boolean acceptInvite() {
        throw new UnsupportedOperationException();
    }

    /**
     * Notifies this object that the user has entered a match
     * @param match match
     * @return true if no other match was already active
     */
    public boolean enrollInMatch(Match match) {
        throw new UnsupportedOperationException();
    }

    /**
     * Removes the match instance from this client
     * @return true if no errors occur
     */
    public boolean removeMatchInstance() {
        throw new UnsupportedOperationException();
    }

    /**
     * Makes this client a zombie and if notifyMatchPlayers is true notifies the other players
     * @param notifyMatchPlayers flag to notify other players
     */
    public void zombiefy(boolean notifyMatchPlayers) {
        throw new UnsupportedOperationException();
    }

    /**
     * Purges this client from the server
     */
    public void purge() {
        throw new UnsupportedOperationException();
    }

    /**
     * Pushes an inbound object into the queue
     * @param obj the received object
     */
    public void pushInObj(Serializable obj) {
        throw new UnsupportedOperationException();
    }

    /**
     * Pops and processes an object that was received
     */
    public void handleObj() {
        throw new UnsupportedOperationException();
    }

    /**
     * Pushes an inbound request into the queue
     * @param req the received request
     */
    public void pushInReq(AbsReq req) {
        throw new UnsupportedOperationException();
    }

    /**
     * Pops and processes a request that was received
     */
    public void handleReq() {
        throw new UnsupportedOperationException();
    }

    /**
     * Pushes an outbound object into the queue
     * @param obj the outbound object
     */
    public void pushOutObj(Serializable obj) {
        throw new UnsupportedOperationException();
    }

    /**
     * Pops and processes an object that was queued up to be sent
     */
    public void processOutObj() {
        throw new UnsupportedOperationException();
    }

    /**
     * Sends an object
     * @param obj object to be sent
     * @return true if success(if supported)
     */
    public boolean sendObj(Serializable obj) {
        throw new UnsupportedOperationException();
    }

    /**
     * Pushes an outbound request into the queue
     * @param req the outbound request
     */
    public void pushOutReq(AbsReq req) {
        throw new UnsupportedOperationException();
    }

    /**
     * Pops and processes a request that was queued up to be sent
     */
    public void processOutReq() {
        throw new UnsupportedOperationException();
    }

    /**
     * Sends a request
     * @param req request to be sent
     * @return true if success(if supported)
     */
    public boolean sendReq(AbsReq req) {
        throw new UnsupportedOperationException();
    }


}