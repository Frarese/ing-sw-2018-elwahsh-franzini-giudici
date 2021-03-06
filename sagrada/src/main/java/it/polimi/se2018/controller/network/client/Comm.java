package it.polimi.se2018.controller.network.client;

import it.polimi.se2018.controller.network.AbsReq;
import it.polimi.se2018.controller.network.ChangeCLayerRequest;
import it.polimi.se2018.controller.network.LogoutRequest;
import it.polimi.se2018.util.MatchIdentifier;
import it.polimi.se2018.util.UtilMethods;
import java.io.Serializable;
import java.time.Instant;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The object representing the network interface
 * @author Francesco Franzini
 */
public class Comm {
    private MatchIdentifier matchId;
    private String username;
    private String password;
    private final Object tsLock;
    private Instant lastSeen;
    private String host;
    private int reqPort;
    private int objPort;

    private final Queue<AbsReq> outReqQueue;
    private final Queue<Serializable> outObjQueue;

    private final Queue<Serializable> inObjQueue;
    private final Queue<AbsReq> inReqQueue;


    private final ClientDiscTimer discTimer;
    private final ReconnectWaker reconnectW;
    private final OutQueueEmptier qEmpReq;
    private final OutQueueEmptier qEmpObj;
    private CommLayer commLayer;
    private final InListener inListenerReq;
    private final InListener inListenerObj;

    private CommUtilizer utilizer;

    private final Logger logger;

    /**
     * Initializes a new comm object
     */
    public Comm(){
        outObjQueue= new LinkedList<>();
        outReqQueue=new LinkedList<>();
        inObjQueue=new LinkedList<>();
        inReqQueue=new LinkedList<>();

        discTimer=new ClientDiscTimer(this);
        reconnectW=new ReconnectWaker(this);
        qEmpReq=new OutQueueEmptier(this,true);
        qEmpObj=new OutQueueEmptier(this,false);

        inListenerObj=new InListener(this,utilizer,false);
        inListenerReq=new InListener(this,utilizer,true);

        tsLock=new Object();

        commLayer=null;

        logger=Logger.getGlobal();
    }

    /**
     * Starts the operations needed to change layer
     * @param toRMI flag to choose socket/RMI
     * @param reqPort request port to use or RMI port
     * @param objPort object port to use, not used if RMI
     */
    public void changeLayer(boolean toRMI, int reqPort,int objPort) {
        if(commLayer==null)return;

        //Only a disparity between the two requires actions taken
        if(toRMI == commLayer.getClass().equals(RMICommLayer.class))return;

        logger.log(Level.INFO,"Attempting to change layer to {0}",(toRMI)?"RMI":"Socket");
        if(!commLayer.sendOutReq(new ChangeCLayerRequest(toRMI,reqPort,objPort))){
            logger.log(Level.SEVERE,"Failed to send change layer request");
        }
        this.discTimer.stop();
        this.beginDisconnectedRoutines();
    }

    /**
     * Pushes an object in the outbound queue
     * @param obj the object to send
     */
    public void pushOutObj(Serializable obj) {
        UtilMethods.pushAndNotifyTS(outObjQueue,obj);
    }

    /**
     * Pushes a request in the outbound queue
     * @param req the request to send
     */
    public void pushOutReq(AbsReq req) {
        UtilMethods.pushAndNotifyTS(outReqQueue,req);
    }

    /**
     * Pops and sends an object to the server from the outbound queue, blocks until confirmation has been received
     * @throws InterruptedException if this method is interrupted while waiting
     */
    public void sendOutObj() throws InterruptedException {
        if(commLayer==null)return;
        Serializable obj=UtilMethods.waitAndPopTS(outObjQueue);
        if(!commLayer.sendOutObj(obj)){
            this.beginDisconnectedRoutines();
        }
    }

    /**
     * Pops and sends a request to the server from the outbound queue, blocks until confirmation has been received
     *  @throws InterruptedException if this method is interrupted while waiting
     */
    public void sendOutReq() throws InterruptedException {
        if(commLayer==null)return;
        AbsReq req=UtilMethods.waitAndPopTS(outReqQueue);
        if(!commLayer.sendOutReq(req)){
            logger.log(Level.SEVERE,"Failed to send request {0}",req);
            this.beginDisconnectedRoutines();
        }
    }

    /**
     * Pops an inbound object from the queue
     * @return the object that has been received
     * @throws InterruptedException if this method is interrupted while waiting
     */
    public Serializable popInPendingObj() throws InterruptedException {
        return UtilMethods.waitAndPopTS(inObjQueue);
    }

    /**
     * Pops an inbound request from the queue
     * @return the request that has been received
     * @throws InterruptedException if this method is interrupted while waiting
     */
    public AbsReq popInPendingReq() throws InterruptedException {
        return UtilMethods.waitAndPopTS(inReqQueue);
    }

    /**
     * Pushes an object in the inbound queue
     * @param obj the object that has been received
     */
    void pushInObj(Serializable obj) {
        updateTs();
        UtilMethods.pushAndNotifyTS(inObjQueue,obj);
    }

    /**
     * Pushes a request in the inbound queue
     * @param req the request that has been received
     */
    void pushInReq(AbsReq req) {
        updateTs();
        UtilMethods.pushAndNotifyTS(inReqQueue,req);
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
    private void setUsername(String newUsername) {
        this.username=newUsername;
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
        this.matchId=newInfo;
    }

    /**
     * Returns the password that has been used in the login phase
     * @return the password that has been used in the login phase, {@code null} if none is in use
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Changes the current password
     * @param password new password to use
     */
    private void setPassword(String password) {
        this.password=password;
    }

    /**
     * Attempts a login with the given parameters
     * @param host hostname to connect to
     * @param requestPort port for the request socket or RMI connection
     * @param objectPort port for the object socket, unused if RMI
     * @param usn username
     * @param pw password
     * @param newUser flag to indicate that this is a registration attempt
     * @param useRMI flag to use rmi instead of socket
     * @param utilizer object that will handle the events
     * @return {@code null} if no errors were raised, a textual representation of the error otherwise
     */
    public String login(String host, int requestPort, int objectPort, String usn, String pw, boolean newUser, boolean useRMI, CommUtilizer utilizer) {
        if(!this.createComm(useRMI))return "Already connected";
        String outcome=commLayer.establishCon(host,requestPort,objectPort,usn,pw,newUser);
        if(outcome!=null){
            logger.log(Level.FINE,"Login output {0}",outcome);
            commLayer=null;
            return outcome;
        }
        setUsername(usn);
        setPassword(pw);
        this.host=host;
        this.reqPort=requestPort;
        if(useRMI){
            this.objPort=0;
        }else{
            this.objPort=objectPort;
        }

        updateTs();

        this.utilizer=utilizer;

        inListenerObj.setUtilizer(utilizer);
        inListenerReq.setUtilizer(utilizer);

        inListenerReq.start();
        inListenerObj.start();

        qEmpObj.start();
        qEmpReq.start();


        discTimer.reschedule(ClientDiscTimer.DEFAULT_CLIENT_TIMEOUT);
        logger.log(Level.INFO,"Successful login");
        return null;
    }

    boolean createComm(boolean useRMI) {
        if(commLayer!=null)return false;
        if(useRMI){
            commLayer=new RMICommLayer(this);
        }else{
            commLayer=new SocketCommLayer(this);
        }
        return true;
    }

    /**
     * Attempts to logout
     * @return true if no errors were raised
     */
    boolean logout() {
        if(commLayer==null)return false;
        this.stop();
        commLayer.sendOutReq(new LogoutRequest());
        commLayer.endCon();

        this.purgeComm();

        outObjQueue.retainAll(new LinkedList<>());
        inObjQueue.retainAll(new LinkedList<>());
        return true;
    }

    /**
     * Handles a logout request from the server
     */
    public void logoutRequestReceived() {
        this.purgeComm();
        this.stop();
        if(utilizer!=null){
            utilizer.notifyCommDropped();
        }
    }

    /**
     * Attempts to recover the connection to the server
     * @param purgeOnFail if true the comm layer will be purged on failure to reconnect
     * @return true if the connection has been recovered
     */
    boolean tryRecover(boolean purgeOnFail) {
        logger.log(Level.WARNING,"Attempting to reconnect to {0}",host);
        String result=login(host,reqPort,objPort,username,password,false,(objPort==0),utilizer);
        if(result==null){
            logger.log(Level.INFO,"Successfully reconnected");
            return true;
        }
        logger.log(Level.SEVERE,"Failed to reconnect {0}",result);
        if(purgeOnFail){
            logger.log(Level.SEVERE,"Failed to restore connection, notifying drop");
            try {
                this.utilizer.notifyCommDropped();
            }catch (Exception e){
                logger.log(Level.SEVERE,"Exception caught from utilizer "+e.getMessage());
            }
            purgeComm();
        }
        return false;
    }

    /**
     * Returns the last seen timestamp
     * @return the last seen timestamp
     */
    public Instant getLastSeen() {
        synchronized (tsLock){
            return this.lastSeen;
        }
    }

    /**
     * Disconnects the comm layer
     */
    public void beginDisconnectedRoutines() {
        logger.log(Level.INFO,"Beginning disconnect routines");
        this.stop();
        this.purgeComm();
        reconnectW.reschedule(ReconnectWaker.DEFAULT_RECON_PERIOD,ReconnectWaker.DEFAULT_ATTEMPTS);
    }

    /**
     * Updates this object's last seen timestamp
     */
    private void updateTs() {
        synchronized (tsLock){
            lastSeen=Instant.now();
        }
    }

    /**
     * Purges the comm layer
     */
    public void purgeComm() {
        if(commLayer!=null)commLayer.close();
        commLayer=null;
    }

    /**
     * Returns the current hostname
     * @return the current hostname
     */
    public String getHost() {
        return host;
    }

    /**
     * Stops the current listeners/handlers
     */
    void stop(){
        discTimer.stop();
        inListenerObj.forceStop();
        inListenerReq.forceStop();
        qEmpObj.forceStop();
        qEmpReq.forceStop();
    }
}
