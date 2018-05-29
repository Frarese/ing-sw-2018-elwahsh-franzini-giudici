package it.polimi.se2018.view;

import it.polimi.se2018.util.MatchIdentifier;
import it.polimi.se2018.util.PatternView;

/**
 * Class for View-Controller communication
 *
 * @author Mathyas Giudici
 */

public class ViewActions {

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
     */
    public void login(String name, String password, boolean newUser, String host, boolean isRMI, int objectPort, int requestPort) {
        throw new UnsupportedOperationException();
    }

    /**
     * Changes layer from Socket to RMI or vice versa
     *
     * @param toRMI       boolean value
     * @param objectPort  contains the object port number
     * @param requestPort contains the request port number
     */
    public void changeLayer(boolean toRMI, int objectPort, int requestPort) {
        throw new UnsupportedOperationException();

    }

    /**
     * Leaves the current match
     */
    public void leaveMatch() {
        throw new UnsupportedOperationException();

    }

    /**
     * Tries logout operation
     */
    public void logout() {
        throw new UnsupportedOperationException();

    }

    /**
     * Asks the lobby
     */
    public void askLobby() {
        throw new UnsupportedOperationException();

    }

    /**
     * Sends invites to other player to match
     *
     * @param invite contains a list of players (max 4)
     */
    public void pushInvite(MatchIdentifier invite) {
        throw new UnsupportedOperationException();

    }

    /**
     * Participates to a match
     */
    public void autoCompleteGame() {
        throw new UnsupportedOperationException();

    }

    /**
     * Accepts an invite from another user
     *
     * @param matchIdentifier contains the matchIdentifier object of the match
     */
    public void acceptInvite(MatchIdentifier matchIdentifier) {
        throw new UnsupportedOperationException();

    }

    /**
     * Communicates to the Controller the pattern that user has select
     *
     * @param selected the selected pattern
     */
    public void selectedPattern(PatternView selected) {
        throw new UnsupportedOperationException();

    }

    /**
     * Communicates to the Controller that View ended init operations
     */
    public void endInitGame() {
        throw new UnsupportedOperationException();

    }

    /**
     * Tries a die placement
     *
     * @param reserveIndex contains the reserveIndex of the die
     * @param height       contains height on the grid
     * @param width        contains width on the grid
     */
    public void setDie(int reserveIndex, int height, int width) {
        throw new UnsupportedOperationException();

    }

    /**
     * Tries to use a Tool Card
     *
     * @param card contains the Tool Card's ID
     */
    public void useToolCard(int card) {
        throw new UnsupportedOperationException();

    }

    /**
     * Pass Turn operation
     */
    public void passTurn() {
        throw new UnsupportedOperationException();

    }
}
