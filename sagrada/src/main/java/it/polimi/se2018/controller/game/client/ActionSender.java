package it.polimi.se2018.controller.game.client;

import it.polimi.se2018.controller.network.client.CommFE;
import it.polimi.se2018.controller.network.client.CommUtilizer;
import it.polimi.se2018.events.actions.DiePlacementMove;
import it.polimi.se2018.events.actions.PassTurn;
import it.polimi.se2018.events.actions.PatternChoice;
import it.polimi.se2018.events.actions.UseToolCardMove;
import it.polimi.se2018.util.MatchIdentifier;

/**
 * Sends actions generated from the view to the match controller
 * @author Alì El wahsh
 */
public class ActionSender {

    private String username;
    private CommFE network = new CommFE();
    private CommUtilizer controller;

    /**
     * Constructor
     * @param controller FrontEnd network controller
     */
    public ActionSender(CommUtilizer controller)
    {
        this.controller = controller;
    }

    /**
     * Tries to login
     * @param host host IP
     * @param requestPort request port
     * @param objectPort object port
     * @param username player's username
     * @param password player's password
     * @param newUser true if it's a nuew user
     * @param useRMI true if the user want's to use RMI, false for Socket
     * @return null if it's successful, an error otherwise
     */
    public String login(String host, int requestPort, int objectPort, String username, String password, boolean newUser, boolean useRMI)
    {
       String result = network.login(host,requestPort,objectPort,false,username,password,newUser,useRMI,controller);
       if(result == null)
           this.username = username;

       return result;
    }

    /**
     * Tries a logout
     */
    public void logout()
    {
        network.logout();
    }

    /**
     * Changes layer from Socket to RMI or vice versa
     *
     * @param toRMI       boolean value
     * @param objectPort  contains the object port number
     * @param requestPort contains the request port number
     */
    public void changeLayer(boolean toRMI,int requestPort, int objectPort)
    {
        network.changeLayer(toRMI,requestPort,objectPort);
    }

    /**
     * Leaves the current match
     */
    public void leaveMatch()
    {
        network.leaveMatch();
    }

    /**
     * Sends invites to other player to match
     *
     * @param invite contains a list of players (max 4)
     */
    public void pushInvite(MatchIdentifier invite)
    {
        network.inviteToMatch(invite);
    }

    /**
     * Accepts an invite from another user
     *
     * @param invite contains the matchIdentifier object of the match
     */
    public void acceptInvite(MatchIdentifier invite)
    {
        network.answerInvite(invite,true);
    }

    /**
     * Communicates to the Controller the pattern that user has select
     *
     * @param pattern the selected pattern
     */
    public void selectedPattern(String pattern) {

        network.sendReq(new PatternChoice(username,pattern));

    }


    /**
     * Participates to a match making
     */
    public void joinMatchMaking() {
       network.joinMatchMakingQueue();

    }

    /**
     *  Leaves matchmaking
     */
    public void leaveMatchMaking()
    {
        network.leaveMatchMakingQueue();
    }

    /**
     * Tries a die placement
     *
     * @param reserveIndex contains the reserveIndex of the die
     * @param height       contains height on the grid
     * @param width        contains width on the grid
     */
    public void setDie(int reserveIndex, int height, int width)
    {
        network.sendReq(new DiePlacementMove(height,width,reserveIndex,username,true,true,true));
    }

    /**
     * Tries to use a Tool Card
     *
     * @param id contains the Tool Card's ID
     */
    public void userToolCard(int id)
    {
        network.sendReq(new UseToolCardMove(username,id));
    }

    /**
     * Pass Turn operation
     */
    public void passTurn()
    {
        network.sendReq(new PassTurn(username));
    }


}
