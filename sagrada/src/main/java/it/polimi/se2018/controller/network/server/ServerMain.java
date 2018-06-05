package it.polimi.se2018.controller.network.server;

import it.polimi.se2018.controller.network.MatchInviteRequest;
import it.polimi.se2018.util.MatchIdentifier;
import it.polimi.se2018.util.ScoreEntry;

import java.io.IOException;
import java.net.InetAddress;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * This is the server's main class
 * @author Francesco Franzini
 */
public class ServerMain {
    private final int rmiPort;
    private final int objPort;
    private final int reqPort;
    private final String rmiName;
    private final InetAddress rmiIP;
    private ServerComm serverRMI;
    private ServerComm serverSoc;
    private final ConcurrentHashMap<String,Client> clientMap;
    private final ConcurrentHashMap<String,Match> matches;
    private final UserBase userBase;
    private final ConcurrentHashMap<String,PendingApprovalMatch> pendingMatchesMap;
    private final MatchMakingQueue matchMakingQueue;
    final MatchControllerFactory factory;
    private final Logger logger;

    /**
     * Creates a new server with the given parameters
     * @param objPort socket object port
     * @param reqPort socket request port
     * @param rmiPort rmi port
     * @param rmiName rmi name
     * @param rmiIp rmi ip to use
     * @throws IOException if an I/O error occurs
     */
    public ServerMain(int objPort, int reqPort, int rmiPort, String rmiName, InetAddress rmiIp,MatchControllerFactory factory) throws IOException{
        this.factory=factory;
        logger=Logger.getGlobal();
        clientMap=new ConcurrentHashMap<>();
        matches=new ConcurrentHashMap<>();
        pendingMatchesMap=new ConcurrentHashMap<>();
        matchMakingQueue=new MatchMakingQueue(this);
        try {
            userBase= new FileUserBase(FileUserBase.DEFAULT_FILENAME);
        } catch (IOException e) {
            logger.log(Level.SEVERE,"Could not open UserBase"+e.getMessage());
            throw e;
        }
        this.rmiPort=rmiPort;
        this.rmiName=rmiName;
        this.rmiIP=rmiIp;
        this.objPort=objPort;
        this.reqPort=reqPort;
    }

    /**
     * Builds the server objects that are going to be used
     */
    void buildServers() throws IOException {
        if(serverRMI==null){
            serverRMI=new RMIServer(this,rmiPort,rmiName,rmiIP);
            serverRMI.connect();
        }
        if(serverSoc==null){
            serverSoc=new SocketServer(this,objPort,reqPort);
            serverSoc.connect();
        }
    }

    /**
     * Tries to register a new client
     * @param client the client object to register
     * @return true if the process is completed with success
     */
    boolean addClient(Client client) {
        Client outcome=clientMap.putIfAbsent(client.usn,client);
        if(outcome==null){
            logger.log(Level.FINEST,"{0} logged in",client.usn);
        }
        return outcome==null;
    }

    /**
     * Checks if a user is logged
     * @param usn the username to check
     * @return true if the user is logged
     */
    boolean isUserLogged(String usn) {
        return clientMap.containsKey(usn);
    }

    /**
     * Checks if the given username, password combination is correct
     * @param usn username
     * @param pw password
     * @return true if the username exists and the password is correct
     */
    boolean isPwCorrect(String usn, String pw) {
        return userBase.containsUser(usn) && userBase.getPw(usn).equals(pw);
    }

    /**
     * Checks if the given username is in the user base
     * @param usn username
     * @return true if the username exists
     */
    boolean existsUsn(String usn) {
        return userBase.containsUser(usn);
    }

    /**
     * Returns the Client corresponding to a username
     * @param usn username
     * @return the corresponding {@link it.polimi.se2018.controller.network.server.Client} or {@code null} if it is not logged
     */
    public Client getClient(String usn) {
        return clientMap.get(usn);
    }

    /**
     * Builds and returns a list of all the logged users with their leaderboard info, ordered desc
     * @return a List of {@link it.polimi.se2018.util.ScoreEntry}
     */
    public List<ScoreEntry> getUserListCopy() {
        List<ScoreEntry> list=userBase.getLeaderBoard();
        list.removeIf(a->!clientMap.containsKey(a.usn));
        return list;
    }

    /**
     * Closes this server down and blocks until closure has been completed
     */
    void closeDown() {
        if(serverSoc!=null){
            serverSoc.close();
        }
        serverSoc=null;
        if(serverRMI!=null){
            serverRMI.close();
        }
        serverRMI=null;
        pendingMatchesMap.values().forEach(PendingApprovalMatch::abort);
        matches.forEach((mId,m)->m.abort());
        clientMap.values().forEach(c->c.purge(false));

    }

    /**
     * Removes a Client from the logged users map
     * @param usn username
     */
    void removeClient(String usn) {
        clientMap.remove(usn);
    }

    /**
     * Adds a {@link it.polimi.se2018.controller.network.server.Client} to the matchmaking Queue
     * @param client client to add
     */
    public void addToMatchMaking(Client client) {
        matchMakingQueue.add(client);
    }

    /**
     * Removes a {@link it.polimi.se2018.controller.network.server.Client} from the matchmaking Queue
     * @param client client to remove
     */
    public void removeFromMatchMaking(Client client) {
        matchMakingQueue.remove(client);
    }

    /**
     * Registers a {@link it.polimi.se2018.controller.network.server.PendingApprovalMatch}
     * @param mId the id
     * @param c the source client
     */
    public void addPendingMatch(MatchIdentifier mId, Client c) {
        if(this.getPendingMatch(mId)!=null || this.getMatch(mId)!=null )return;
        List<Client> list=new ArrayList<>();
        PendingApprovalMatch pA=new PendingApprovalMatch(PendingApprovalMatch.DEFAULT_TIMEOUT,mId,this,c);
        if(!checkPALogged(list,pA)){
            pA.abort();
            return;
        }
        if(!c.acceptInvite()){
            return;
        }
        pendingMatchesMap.put(mId.toString(),pA);
        list.stream().filter(cl->cl!=c).forEach(cl->cl.pushOutReq(new MatchInviteRequest(mId)));
    }

    /**
     * Removes a {@link it.polimi.se2018.controller.network.server.PendingApprovalMatch} from the server
     * @param paMatch the PendingApprovalMatch to remove
     */
    void removePendingMatch(PendingApprovalMatch paMatch) {
        pendingMatchesMap.remove(paMatch.matchId.toString());
    }

    /**
     * Gets the corresponding {@link it.polimi.se2018.controller.network.server.PendingApprovalMatch} to a {@link it.polimi.se2018.util.MatchIdentifier}
     * @param matchId the matchId to fetch
     * @return the corresponding PendingApprovalMatch or {@code null} if not present
     */
    public PendingApprovalMatch getPendingMatch(MatchIdentifier matchId) {
        return pendingMatchesMap.get(matchId.toString());
    }

    /**
     * Creates a new user in the user base
     * @param usn username
     * @param pw password
     * @return true if success, false otherwise
     */
    boolean createUser(String usn,String pw){
        return userBase.addUser(usn,pw);
    }

    /**
     * Adds a match to the match list
     * @param m the match to add
     */
    void addMatch(Match m){
        this.matches.putIfAbsent(m.matchId.toString(),m);
    }

    /**
     * Gets a match from the match list
     * @param mId the match id to fetch
     * @return the match or {@code null} if not found
     */
    Match getMatch(MatchIdentifier mId){
        return this.matches.get(mId.toString());
    }
    /**
     * Removes a match to the match list
     * @param m the match to remove
     */
    void removeMatch(Match m){
        this.matches.remove(m.matchId.toString(),m);
    }

    /**
     * Returns a List of {@link it.polimi.se2018.util.ScoreEntry} with all the registered users, unordered
     * @return a List of {@link it.polimi.se2018.util.ScoreEntry} with all the registered users
     */
    public List<ScoreEntry> getRegisteredUsers() {
        return userBase.getLeaderBoard();
    }

    /**
     * Updates the score of a player
     * @param usn username
     * @param dTot amount to add to the total points
     * @param dW amount to add to the wins
     */
    void updateScore(String usn, int dTot,int dW){
        userBase.alterUserScore(usn,dTot,dW);
    }


    /**
     * Checks that the required clients are logged and not taken
     * @return true if this request can proceed
     */
    private boolean checkPALogged(List<Client> list,PendingApprovalMatch pA){
        MatchIdentifier mId=pA.matchId;
        Client c;
        if(( c=checkClientLoggedFree(mId.player0))!=null){
            list.add(c);
        }else{
            return false;
        }

        if(( c=checkClientLoggedFree(mId.player1))!=null){
            list.add(c);
        }else{
            return false;
        }
        if(mId.playerCount==2)return true;

        if(( c=checkClientLoggedFree(mId.player2))!=null){
            list.add(c);
        }else{
            return false;
        }
        if(mId.playerCount==3)return true;

        if(( c=checkClientLoggedFree(mId.player3))!=null){
            list.add(c);
        }else{
            return false;
        }
        return true;
    }

    /**
     * Checks if a username is logged and free
     * @param username username
     * @return the Client if logged and free, {@code null} otherwise
     */
    private Client checkClientLoggedFree(String username){
        Client c=this.getClient(username);
        if(c==null)return null;
        return (c.hasAcceptedInvite())?null:c;
    }
}