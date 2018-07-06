package it.polimi.se2018.controller.game.client;

import it.polimi.se2018.controller.network.client.CommFE;
import it.polimi.se2018.events.actions.*;
import it.polimi.se2018.events.messages.ReadyView;
import it.polimi.se2018.util.MatchIdentifier;

/**
 * Sends actions generated from the view to the match controller
 *
 * @author Alì El wahsh
 */
public class ActionSender {

    private String username;
    private final CommFE network = new CommFE();
    private ClientController controller;


    /**
     * Tries to login
     *
     * @param host        host IP
     * @param requestPort request port
     * @param objectPort  object port
     * @param username    player's username
     * @param password    player's password
     * @param newUser     true if it's a new user
     * @param useRMI      true if the user want's to use RMI, false for Socket
     * @return null if it's successful, an error otherwise
     */
    public String login(String host, int requestPort, int objectPort, String username, String password, boolean newUser, boolean useRMI) {
        String result = network.login(host, requestPort, objectPort, false, username, password, newUser, useRMI, controller);
        if (result == null) {
            this.username = username;
            controller.setLocalPlayer(username);
        }

        return result;
    }

    /**
     * Tries a logout
     */
    public void logout() {
        network.logout();
    }

    /**
     * Changes layer from Socket to RMI or vice versa
     *
     * @param toRMI       boolean value
     * @param objectPort  contains the object port number
     * @param requestPort contains the request port number
     */
    public void changeLayer(boolean toRMI, int requestPort, int objectPort) {
        network.changeLayer(toRMI, requestPort, objectPort);
    }

    /**
     * Leaves the current match
     */
    public void leaveMatch() {
        network.leaveMatch();
    }

    /**
     * Asks the lobby
     */
    public void askLobby() {
        network.requestUserList();
        network.requestLeaderboard();
    }

    /**
     * Sends invites to other player to match
     *
     * @param invite contains a list of players (max 4)
     */
    public void pushInvite(MatchIdentifier invite) {
        network.inviteToMatch(invite);
        controller.setMId(invite);
    }

    /**
     * Accepts an invite from another user
     *
     * @param invite contains the matchIdentifier object of the match
     */
    public void acceptInvite(MatchIdentifier invite) {
        network.answerInvite(invite, true);
        controller.setMId(invite);
    }

    /**
     * Communicates to the Controller the pattern that user has select
     *
     * @param pattern the selected pattern
     */
    public void selectedPattern(String pattern) {

        network.sendReq(new PatternChoice(username, pattern));

    }

    /**
     * Communicates to the Controller that View ended init operations
     */
    public void endInitGame() {
        network.sendObj(new ReadyView());
    }


    /**
     * Participates to a match making
     */
    public void joinMatchMaking() {
        network.joinMatchMakingQueue();
    }

    /**
     * Leaves matchmaking
     */
    public void leaveMatchMaking() {
        network.leaveMatchMakingQueue();
    }

    /**
     * Tries a die placement
     *
     * @param reserveIndex contains the reserveIndex of the die
     * @param height       contains height on the grid
     * @param width        contains width on the grid
     */
    public void setDie(int reserveIndex, int height, int width) {
        DiePlacementMove move = new DiePlacementMove(height, width, reserveIndex, username, true, true, true);
        network.sendReq(move);
        controller.setLastAction(move);
    }

    /**
     * Tries to use a Tool Card
     *
     * @param id contains the Tool Card's ID
     */
    public void userToolCard(int id) {
        UseToolCardMove move= new UseToolCardMove(username, id);
        network.sendReq(move);
        controller.setLastAction(move);
    }

    /**
     * Pass Turn operation
     */
    public void passTurn() {
        PassTurn move = new PassTurn(username);
        network.sendReq(move);
        controller.setLastAction(move);
    }

    /**
     * Sets the controller
     * @param controller Client controller
     */
    public void setController(ClientController controller) {
        this.controller = controller;
    }

    /**
     * Sends a selected die from the reserve position
     * @param index position of the die
     */
    public void selectedDieFromReserve(int index)
    {
        network.sendReq(new DieFromReserve(username,index));
    }

    /**
     * Sends the new value asked for the selected die
     * @param newVal die's new value
     */
    public void selectedNewValueForDie(int newVal)
    {
        network.sendReq(new NewValue(username,newVal));
    }

    /**
     * Sends a die selected from the grid
     * @param h height on the grid
     * @param w width on the grid
     */
    public void selectedDieFromGrid(int h, int w)
    {
        network.sendReq(new DieFromGrid(username,h,w));
    }

    /**
     * Tries to set a die from the gird to the grid
     * @param h height of die
     * @param w width of die
     */
    public void selectedDieToGrid(int h, int w)
    {
        network.sendReq(new DieSet(username,h,w));
    }

    /**
     * Sends a die selected from the round track
     * @param round round position
     * @param diePosition die position
     */
    public void selectedDieFromRoundTrack(int round,int diePosition)
    {
        network.sendReq(new DieFromRoundTrack(username,round,diePosition));
    }

    /**
     * Sends the number of placement required by the player
     * @param number number of placement
     */
    public void sendNumberOfPlacements(int number)
    {
        network.sendReq(new NewValue(username,number));
    }
}
