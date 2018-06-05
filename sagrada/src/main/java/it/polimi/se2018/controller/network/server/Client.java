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
    private static final long DEFAULT_DEATH_TIMEOUT = 3000;
    private static final long DEFAULT_WARNING_TIMEOUT = 2000;
    private static final long DEFAULT_PURGE_TIMEOUT = 5000;

    private final ServerMain serverMain;
    public final String usn;

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

    private final Object tSLock;
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

        this.tSLock=new Object();
    }

    /**
     * Inits the listeners for this client
     */
    private void init(){
        updateTs();
        if(inQueueEmpObj==null)inQueueEmpObj=new ServerInQueueEmptier(this,false);
        if(inQueueEmpReq==null)inQueueEmpReq=new ServerInQueueEmptier(this,true);
        if(outQueueEmpObj==null)outQueueEmpObj=new ServerOutQueueEmptier(this,false);
        if(outQueueEmpReq==null)outQueueEmpReq=new ServerOutQueueEmptier(this,true);
        if(disconnectChecker==null)disconnectChecker=new DisconnectChecker(DEFAULT_WARNING_TIMEOUT, DEFAULT_DEATH_TIMEOUT, DEFAULT_PURGE_TIMEOUT,this);

        inQueueEmpObj.start();
        inQueueEmpReq.start();
        outQueueEmpObj.start();
        outQueueEmpReq.start();
        disconnectChecker.reschedule();

        if(this.match!=null){
            this.match.playerReconnected(usn);
        }
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
    PendingApprovalMatch getPAM() {
        return this.pAM;
    }

    /**
     * Builds a Socket comm layer for this client
     * @param reqSoc the request {@link it.polimi.se2018.util.SafeSocket}
     * @param objSoc the object {@link it.polimi.se2018.util.SafeSocket}
     * @return true if the comm layer was created, false if it wasn't null or errors occurred
     */
    boolean createSocketComm(SafeSocket reqSoc, SafeSocket objSoc) {
        if(this.cComm!=null)return false;
        this.cComm=new SocClientComm(reqSoc,objSoc,this);
        this.init();
        return true;
    }

    /**
     * Builds a RMI comm layer for this client
     * @param sessionObj the {@link it.polimi.se2018.controller.network.server.RMISessionImpl} that is used as session object
     * @return true if the comm layer was created, false if it wasn't null or errors occurred
     */
    boolean createRMIComm(RMISessionImpl sessionObj) {
        if(this.cComm!=null)return false;
        this.cComm=new RMIClientComm(sessionObj,this);
        this.init();
        return true;
    }

    /**
     * Updates the last seen timestamp
     */
    private void updateTs() {
        synchronized (tSLock){
            this.lastSeenTs=Instant.now();
        }
        if(disconnectChecker!=null)disconnectChecker.resetWarned();
    }

    /**
     * Gets the last seen timestamp
     * @return last seen timestamp
     */
    public Instant lastSeen() {
        synchronized (tSLock){
            return lastSeenTs;
        }
    }

    /**
     * Checks if this client is zombie
     * @return true if this client is zombie
     */
    public boolean isZombie() {
        return this.cComm==null;
    }

    /**
     * Checks if this client has accepted an invite
     * @return true if this client has accepted an invite
     */
    boolean hasAcceptedInvite() {
        return this.acceptedInvite;
    }

    /**
     * Notifies this object that its user has accepted an invite
     * @return true if success(no other invites where accepted)
     */
    boolean acceptInvite() {
        if(this.acceptedInvite)return false;
        this.acceptedInvite=true;
        return true;
    }

    /**
     * Notifies this object that the user has entered a match
     * @param match match
     * @return true if no other match was already active
     */
    boolean enrollInMatch(Match match) {
        if(this.match!=null)return false;
        this.match=match;
        return true;
    }

    /**
     * Removes the match instance from this client
     */
    void removeMatchInstance() {
        if(this.match==null)return;
        this.match=null;
    }

    /**
     * Notifies this object that the user has accepted a PendingApprovalMatch
     * @param match match
     * @return true if no other match was already active
     */
    boolean acceptPAMatch(PendingApprovalMatch match) {
        if(this.pAM!=null || this.match!=null)return false;
        this.pAM=match;
        return true;
    }

    /**
     * Removes the Pending approval instance from this client
     * @return true if no errors occur
     */
    boolean removePAMInstance() {
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
        logger.log(Level.FINE,"Zombiefying {0}", usn);
        if(cReq!=null)this.sendReq(cReq);
        if(this.cComm==null)return;
        this.cComm.terminate();
        this.cComm=null;
        if(notifyMatchPlayers && match!=null)match.playerLeft(usn,true);

    }

    /**
     * Purges this client from the server
     * @param leaveMatch true if an eventual match is to be abandoned
     */
    public void purge(boolean leaveMatch) {
        logger.log(Level.FINE,"Purging {0}", usn);
        serverMain.removeClient(usn);

        if(match!=null)match.playerLeft(usn,!leaveMatch);

        if(!this.isZombie())this.zombiefy(true,null);
        if(inQueueEmpObj!=null) {
            inQueueEmpObj.forceStop();
            inQueueEmpObj=null;
        }

        if(inQueueEmpReq!=null) {
            inQueueEmpReq.forceStop();
            inQueueEmpReq=null;
        }

        if(outQueueEmpObj!=null) {
            outQueueEmpObj.forceStop();
            outQueueEmpObj=null;
        }
        if(outQueueEmpReq!=null) {
            outQueueEmpReq.forceStop();
            outQueueEmpReq=null;
        }

        if(disconnectChecker!=null) {
            disconnectChecker.stop();
            disconnectChecker=null;
        }
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
         logger.log(Level.FINEST,"Received in req {0}",req);
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
            Serializable obj=UtilMethods.waitAndPopTS(outObjQueue);
            boolean result=this.sendObj(obj);
            if(!result){
                logger.log(Level.WARNING,"Failed to send object {0}",obj);
                this.pushObjBack(obj);
            }
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
    private boolean sendObj(Serializable obj) {
        return cComm != null && cComm.sendObj(obj);
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
            AbsReq req=UtilMethods.waitAndPopTS(outReqQueue);
            boolean result=this.sendReq(req);
            if(!result){
                logger.log(Level.WARNING,"Failed to send request {0}",req);
                this.pushRequestBack(req);
            }
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
        return cComm != null && cComm.sendReq(req);
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
        synchronized (outReqQueue){
            LinkedList<AbsReq> asList=(LinkedList<AbsReq>)outReqQueue;
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