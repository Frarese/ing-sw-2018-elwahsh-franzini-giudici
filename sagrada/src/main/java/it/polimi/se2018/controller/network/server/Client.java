package it.polimi.se2018.controller.network.server;

import it.polimi.se2018.controller.network.AbsReq;
import it.polimi.se2018.controller.network.ChangeCLayerRequest;
import it.polimi.se2018.util.SafeSocket;
import it.polimi.se2018.util.UtilMethods;

import java.io.Serializable;
import java.time.Instant;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class representing a client connected to the server
 * @author Francesco Franzini
 */
public class Client {
    private static final long DEFAULT_DEATH_TIMEOUT = 1000;
    private static final long DEFAULT_WARNING_TIMEOUT = 1000;
    private static final long DEFAULT_PURGE_TIMEOUT = 1000;

    private final ServerMain serverMain;
    public final String usn;

    private boolean isZombie;
    private boolean acceptedInvite;
    private Instant lastSeenTs;

    private ClientComm cComm;
    private Match match;
    private PendingApprovalMatch pAM;

    private ServerInQueueEmptier inQueueEmpReq;
    private ServerInQueueEmptier inQueueEmpObj;
    private ServerOutQueueEmptier outQueueEmpReq;
    private ServerOutQueueEmptier outQueueEmpObj;
    private DisconnectChecker disconnectChecker;

    private final Queue<Serializable> inObjQueue;
    private final Queue<AbsReq> inReqQueue;
    private final Queue<Serializable> outObjQueue;
    private final Queue<AbsReq> outReqQueue;

    private final Logger logger;

    /**
     * Builds a client with the given username
     * @param usn username
     * @param server server
     */
    public Client(String usn, ServerMain server) {
        this.usn=usn;
        this.serverMain=server;
        this.logger=Logger.getGlobal();
        outReqQueue=new LinkedList<>();
        outObjQueue=new LinkedList<>();

        inReqQueue=new LinkedList<>();
        inObjQueue=new LinkedList<>();
    }

    /**
     * Inits the listeners for this client
     */
    private void init(){
        if(inQueueEmpObj==null)inQueueEmpObj=new ServerInQueueEmptier(this,false);
        if(inQueueEmpReq==null)inQueueEmpReq=new ServerInQueueEmptier(this,true);
        if(outQueueEmpObj==null)outQueueEmpObj=new ServerOutQueueEmptier(this,false);
        if(outQueueEmpReq==null)outQueueEmpReq=new ServerOutQueueEmptier(this,false);
        if(disconnectChecker==null)disconnectChecker=new DisconnectChecker(DEFAULT_WARNING_TIMEOUT, DEFAULT_DEATH_TIMEOUT, DEFAULT_PURGE_TIMEOUT,this);

        inQueueEmpObj.start();
        inQueueEmpReq.start();
        outQueueEmpObj.start();
        outQueueEmpReq.start();
        disconnectChecker.reschedule();
    }
    /**
     * Gets the current match, if present
     * @return the current {@link it.polimi.se2018.controller.network.server.Match} or {@code null} if not present
     */
    public Match getMatch() {
        return this.match;
    }

    /**
     * Gets the current pending approval match, if present
     * @return the current {@link it.polimi.se2018.controller.network.server.PendingApprovalMatch} or {@code null} if not present
     */
    public PendingApprovalMatch getPAM() {
        return this.pAM;
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
        sessionObj.setCComm(null);
        throw new UnsupportedOperationException();
    }

    /**
     * Updates the last seen timestamp
     */
    private void updateTs() {
        this.lastSeenTs=Instant.now();
    }

    /**
     * Gets the last seen timestamp
     * @return last seen timestamp
     */
    public Instant lastSeen() {
        return this.lastSeenTs;
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
        if(this.acceptedInvite)return false;
        this.acceptedInvite=true;
        return true;
    }

    /**
     * Notifies this object that the user has entered a match
     * @param match match
     * @return true if no other match was already active
     */
    public boolean enrollInMatch(Match match) {
        if(this.match!=null)return false;
        this.match=match;
        return true;
    }

    /**
     * Removes the match instance from this client
     * @return true if no errors occur
     */
    public boolean removeMatchInstance() {
        if(this.match==null)return false;
        this.match=null;
        return true;
    }

    /**
     * Notifies this object that the user has accepted a PendingApprovalMatch
     * @param match match
     * @return true if no other match was already active
     */
    public boolean acceptPAMatch(PendingApprovalMatch match) {
        if(this.pAM!=null)return false;
        this.pAM=match;
        return true;
    }

    /**
     * Removes the Pending approval instance from this client
     * @return true if no errors occur
     */
    public boolean removePAMInstance() {
        if(this.pAM==null)return false;
        this.pAM=null;
        return true;
    }

    /**
     * Makes this client a zombie and if notifyMatchPlayers is true notifies the other players
     * @param notifyMatchPlayers flag to notify other players
     * @param cReq eventual request to forward before terminating
     */
    public void zombiefy(boolean notifyMatchPlayers, ChangeCLayerRequest cReq) {
        throw new UnsupportedOperationException();
    }

    /**
     * Purges this client from the server
     */
    public void purge() {
        if(!this.isZombie)this.zombiefy(true,null);
        throw new UnsupportedOperationException();
    }

    /**
     * Pushes an inbound object into the queue
     * @param obj the received object
     */
    void pushInObj(Serializable obj) {
        updateTs();
        UtilMethods.pushAndNotifyTS(inObjQueue,obj);
    }

    /**
     * Pops and processes an object that was received
     */
    public void handleObj() {
        try {
            Serializable obj=UtilMethods.waitAndPopTS(inObjQueue);

            this.match.handleObj(obj,this);
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE,"Interrupted processing in request "+e.getMessage());
            Thread.currentThread().interrupt();
        } catch (NullPointerException e){
            logger.log(Level.FINE, "Client sending objects while not in a match {0}",this.usn);
        }
    }

    /**
     * Pushes an inbound request into the queue
     * @param req the received request
     */
     void pushInReq(AbsReq req) {
         updateTs();
        UtilMethods.pushAndNotifyTS(inReqQueue,req);
    }

    /**
     * Pops and processes a request that was received
     */
    public void handleReq() {
        try {
            UtilMethods.waitAndPopTS(inReqQueue).serverHandle(this,serverMain);
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE,"Interrupted processing in request "+e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Pushes an outbound object into the queue
     * @param obj the outbound object
     */
    public void pushOutObj(Serializable obj) {
        UtilMethods.pushAndNotifyTS(outObjQueue,obj);
    }

    /**
     * Pops and processes an object that was queued up to be sent
     */
    public void processOutObj() {
        try {
            cComm.pushInObj(UtilMethods.waitAndPopTS(outObjQueue));
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE,"Interrupted processing out obj "+e.getMessage());
            Thread.currentThread().interrupt();
        }
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
        UtilMethods.pushAndNotifyTS(outReqQueue,req);
    }

    /**
     * Pops and processes a request that was queued up to be sent
     */
    public void processOutReq() {
        try {
            cComm.pushInReq(UtilMethods.waitAndPopTS(outReqQueue));
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE,"Interrupted processing out request "+e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Sends a request
     * @param req request to be sent
     * @return true if success(if supported)
     */
    public boolean sendReq(AbsReq req) {
        throw new UnsupportedOperationException();
    }

    /**
     * Pushes an object back on top of the queue, used to reinsert objects that are unable to be sent at the moment
     * @param obj the object to put back on top
     */
    void pushObjBack(Serializable obj){
        synchronized (outObjQueue){
            LinkedList<Serializable> asList=(LinkedList<Serializable>)outObjQueue;
            asList.addFirst(obj);
        }
    }

    /**
     * Pushes a request back on top of the queue, used to reinsert requests that are unable to be sent at the moment
     * @param req the request to put back on top
     */
    void pushRequestBack(AbsReq req){
        synchronized (outObjQueue){
            LinkedList<Serializable> asList=(LinkedList<Serializable>)outObjQueue;
            asList.addFirst(req);
        }
    }

    /**
     * Resets the accepted status of this client
     */
    void resetAccepted() {
        this.acceptedInvite=false;
    }
}