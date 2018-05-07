package it.polimi.se2018.controller.network.client;

import it.polimi.se2018.controller.network.*;
import it.polimi.se2018.util.MatchIdentifier;
import it.polimi.se2018.util.MessageTypes;

import java.io.Serializable;

/**
 * Represents the Client Front End of the network architecture
 * @author Francesco Franzini
 */
public class CommFE {
    private Comm comm;

    /**
     * Initializes the client front end
     */
    public CommFE(){
        comm=new Comm();
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
        return comm.login(host,requestPort,objectPort,isRecovery,usn,pw,newUser,useRMI,utilizer);
    }

    /**
     * Attempts a logout from the server
     * @return true if no errors were raised
     */
    public boolean logout() {
        return comm.logout();
    }

    /**
     * Attempts to send an object to the server
     * @param obj the object to send
     * @return true if no errors were raised
     */
    public boolean sendObj(Serializable obj) {
        if(obj!=null) {
            comm.pushOutObj(obj);
        }
        return true;
    }

    /**
     * Attempts to send an object to another client through the request comm line
     * @param req the object to send
     * @param dst the user to forward the request to
     * @return true if no errors were raised
     */
    public boolean sendReq(Serializable req, String dst) {
        if(req!=null && dst!=null) {
            comm.pushOutReq(new ClientRequest(dst,req));
        }
        return true;
    }

    /**
     * Attempts to change the network layer
     * @param toRMI flag to choose socket/RMI
     * @param reqPort request port to use or RMI port
     * @param objPort object port to use, ignored if RMI
     */
    public void changeLayer(boolean toRMI, int reqPort,int objPort) {
        comm.changeLayer(toRMI,reqPort,objPort);
    }

    /**
     * Notifies to the server the answer to an invite
     * @param match the match that has been answered
     * @param accepted accepted/declined the invite
     */
    public void answerInvite(MatchIdentifier match, boolean accepted) {
        if(match!=null){
            MatchResponseRequest req=new MatchResponseRequest(accepted,match);
            comm.pushOutReq(req);
        }
    }

    /**
     * Invites the players indicated in the arguments to a match
     * @param match the match descriptor
     */
    public void inviteToMatch(MatchIdentifier match) {
        if(match!=null){
            comm.pushOutReq(new MatchInviteRequest(match));
        }
    }

    /**
     * Attempts to kick a player from a match(host only)
     * @param usn the username of the user to kick
     */
    public void kickPlayer(String usn) {
        if(usn!=null){
            comm.pushOutReq(new KickRequest(usn));
        }
    }

    /**
     * Attempts to abort this match(host only)
     */
    public void abortMatch() {
        comm.pushOutReq(new MatchAbortedRequest(comm.getMatchInfo()));
    }

    /**
     * Uploads to the server the result of a match and signals that it has ended
     * @param playerScore0 player0's score
     * @param playerScore1 player1's score
     * @param playerScore2 player2's score
     * @param playerScore3 player3's score
     */
    public void uploadMatchResults(int playerScore0, int playerScore1, int playerScore2, int playerScore3) {
        comm.pushOutReq(new MatchEndedRequest(comm.getMatchInfo(),playerScore0,playerScore1,playerScore2,playerScore3));
    }

    /**
     * Sends a request for a leaderboard update to the server
     */
    public void requestLeaderboard() {
        comm.pushOutReq(new GetLeaderBoardRequest());
    }

    /**
     * Sends a request for an update of the logged users list to the server
     */
    public void requestUserList() {
        comm.pushOutReq(new ListUsersRequest());
    }

    /**
     * Sends a chat message
     * @param msg the message
     * @param type the {@link it.polimi.se2018.util.MessageTypes} of this message
     * @param destination the eventual destination username
     */
    public void sendChatMessage(String msg, MessageTypes type, String destination) {
        if(msg!=null && type!=null && destination!=null){
            comm.pushOutReq(new ChatMessageRequest(comm.getUsername(),destination,msg,type));
        }
    }

    /**
     * Leaves the current match
     */
    public void leaveMatch() {
        comm.pushOutReq(new LeaveMatchRequest(comm.getUsername()));
    }

    /**
     * Joins the matchmaking queue
     */
    public void joinMatchMakingQueue() {
        comm.pushOutReq(new MatchmakingRequest(true));
    }

    /**
     * Leaves the matchmaking queue
     */
    public void leaveMatchMakingQueue() {
        comm.pushOutReq(new MatchmakingRequest(false));
    }


}
