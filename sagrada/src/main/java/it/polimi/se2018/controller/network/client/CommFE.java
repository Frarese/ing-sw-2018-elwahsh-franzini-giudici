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
    private final Comm comm;

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
        boolean out=comm.logout();
        comm.purgeComm();
        comm.stop();
        return out;
    }

    /**
     * Attempts to send an object to the server
     * @param obj the object to send
     */
    public void sendObj(Serializable obj) {
        if(obj!=null) {
            comm.pushOutObj(obj);
        }
    }

    /**
     * Sends a request to the match controller
     * @param req the object to send
     */
    public void sendReq(Serializable req) {
        if(req!=null) {
            comm.pushOutReq(new ClientRequest(req));
        }
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
