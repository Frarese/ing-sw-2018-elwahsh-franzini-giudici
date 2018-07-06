package it.polimi.se2018.view;

import it.polimi.se2018.controller.game.client.ActionSender;
import it.polimi.se2018.util.MatchIdentifier;


/**
 * Class for View-Controller communication
 *
 * @author Mathyas Giudici
 */

public class ViewActions {

    private final ActionSender actionSender;

    /**
     * Class constructor
     * Sets player's name used in events thrown to the Controller
     *
     * @param actionSender contains the controller class to manage events
     */
    public ViewActions(ActionSender actionSender) {
        this.actionSender = actionSender;
    }

    /**
     * Tries login operation to the server
     *
     * @param name        contains user'name
     * @param password    contains user's password
     * @param newUser     to communicate if user has to sign up
     * @param host        IP address
     * @param isRMI       boolean value contains the type of connection
     * @param objectPort  contains the object port number
     * @param requestPort contains the request port number
     * @return null if ok, an error message otherwise
     */
    public String login(String name, String password, boolean newUser, String host, boolean isRMI, int objectPort, int requestPort) {
        return actionSender.login(host, requestPort, objectPort, name, password, newUser, isRMI);
    }

    /**
     * Changes layer from Socket to RMI or vice versa
     *
     * @param toRMI       boolean value
     * @param objectPort  contains the object port number
     * @param requestPort contains the request port number
     */
    public void changeLayer(boolean toRMI, int objectPort, int requestPort) {
        actionSender.changeLayer(toRMI, requestPort, objectPort);
    }

    /**
     * Leaves the current match
     */
    public void leaveMatch() {
        actionSender.leaveMatch();
    }

    /**
     * Tries logout operation
     */
    public void logout() {
        actionSender.logout();
    }

    /**
     * Asks the lobby
     */
    public void askLobby() {
        actionSender.askLobby();
    }

    /**
     * Sends invites to other player to match
     *
     * @param invite contains a list of players (max 4)
     */
    public void pushInvite(MatchIdentifier invite) {
        actionSender.pushInvite(invite);
    }

    /**
     * Participates to a match
     */
    public void joinMatchMaking() {
        actionSender.joinMatchMaking();
    }

    /**
     * Stops matchmaking
     */
    public void leaveMatchMaking() {
        actionSender.leaveMatchMaking();
    }

    /**
     * Accepts an invite from another user
     *
     * @param matchIdentifier contains the matchIdentifier object of the match
     */
    public void acceptInvite(MatchIdentifier matchIdentifier) {
        actionSender.acceptInvite(matchIdentifier);
    }

    /**
     * Communicates to the Controller the pattern that user has select
     *
     * @param selected the selected pattern
     */
    public void selectedPattern(String selected) {
        actionSender.selectedPattern(selected);
    }

    /**
     * Communicates to the Controller that View ended init operations
     */
    public void endInitGame() {
        actionSender.endInitGame();
    }

    /**
     * Tries a die placement
     *
     * @param reserveIndex contains the reserveIndex of the die
     * @param height       contains height on the grid
     * @param width        contains width on the grid
     */
    public void setDie(int reserveIndex, int height, int width) {
        actionSender.setDie(reserveIndex, height, width);
    }

    /**
     * Tries to use a Tool Card
     *
     * @param card contains the Tool Card's ID
     */
    public void useToolCard(int card) {
        actionSender.userToolCard(card);
    }

    /**
     * Pass Turn operation
     */
    public void passTurn() {
        actionSender.passTurn();
    }
}
