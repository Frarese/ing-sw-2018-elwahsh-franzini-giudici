package it.polimi.se2018.controller.network.server;

import it.polimi.se2018.util.MatchIdentifier;
import it.polimi.se2018.util.ScoreEntry;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * This is the server's main class
 * @author Francesco Franzini
 */
public class ServerMain {
    private final ServerComm serverRMI;
    private final ServerComm serverSoc;
    private final ConcurrentHashMap<String,Client> clientMap;
    private final ConcurrentHashMap<MatchIdentifier,Match> matches;
    private final UserBase userBase;
    private final ConcurrentHashMap<MatchIdentifier,PendingApprovalMatch> pendingMatchesMap;
    private final MatchMakingQueue matchMakingQueue;
    private final Logger logger;

    /**
     * Creates a new server with the given parameters
     * @param objPort socket object port
     * @param reqPort socket request port
     * @param useDB true if a database is to be used for the user credentials validation
     * @param rmiPort rmi port
     * @param rmiName rmi name
     */
    public ServerMain(int objPort,int reqPort, boolean useDB, int rmiPort, String rmiName) throws IOException{
        logger=Logger.getGlobal();
        clientMap=new ConcurrentHashMap<>();
        matches=new ConcurrentHashMap<>();
        pendingMatchesMap=new ConcurrentHashMap<>();
        matchMakingQueue=new MatchMakingQueue(this);
        try {
            if (useDB) {
                userBase = new DBUserBase();
            }else{
                userBase= new FileUserBase(FileUserBase.DEFAULT_FILENAME);
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE,"Could not open UserBase"+e.getMessage());
            throw e;
        }
        serverRMI=new RMIServer(this,rmiPort,rmiName);
        serverSoc=new SocketServer(this,objPort,reqPort);

    }

    /**
     * Tries to register a new client
     * @param client the client object to register
     * @param isNew flag indicating a new user
     * @return true if the process is completed with success
     */
    public boolean addClient(Client client, boolean isNew) {
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
    public boolean isUserLogged(String usn) {
        return clientMap.containsKey(usn);
    }

    /**
     * Checks if the given username, password combination is correct
     * @param usn username
     * @param pw password
     * @return true if the username exists and the password is correct
     */
    boolean isPwCorrect(String usn, String pw) {
        if(userBase.containsUser(usn)){
            return userBase.getPw(usn).equals(pw);
        }
        return false;
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
    public void closeDown() {
        serverSoc.close();
        serverRMI.close();
        matches.forEach((mId,m)->m.abort());
        throw new UnsupportedOperationException();
    }

    /**
     * Removes a Client from the logged users map
     * @param usn username
     */
    public void removeClient(String usn) {
        Client c=clientMap.get(usn);
        if(c==null)return;
        c.purge();
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
     * @param timeout timeout for the invites
     * @param mId the id
     * @param c the source client
     */
    public void addPendingMatch(int timeout, MatchIdentifier mId, Client c) {
        PendingApprovalMatch pA=new PendingApprovalMatch(timeout,mId,this,c);
        pendingMatchesMap.put(mId,pA);
    }

    /**
     * Removes a {@link it.polimi.se2018.controller.network.server.PendingApprovalMatch} from the server
     * @param paMatch the PendingApprovalMatch to remove
     */
    public void removePendingMatch(PendingApprovalMatch paMatch) {
        pendingMatchesMap.remove(paMatch.matchId);
    }

    /**
     * Gets the corresponding {@link it.polimi.se2018.controller.network.server.PendingApprovalMatch} to a {@link it.polimi.se2018.util.MatchIdentifier}
     * @param matchId the matchId to fetch
     * @return the corresponding PendingApprovalMatch or {@code null} if not present
     */
    public PendingApprovalMatch getPendingMatch(MatchIdentifier matchId) {
        return pendingMatchesMap.get(matchId);
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
        this.matches.putIfAbsent(m.matchId,m);
    }

    /**
     * Removes a match to the match list
     * @param m the match to remove
     */
    void removeMatch(Match m){
        this.matches.remove(m.matchId,m);
    }

    /**
     * Returns a List of {@link it.polimi.se2018.util.ScoreEntry} with all the registered users, unordered
     */
    public List<ScoreEntry> getRegisteredUsers() {
        return userBase.getLeaderBoard();
    }
}