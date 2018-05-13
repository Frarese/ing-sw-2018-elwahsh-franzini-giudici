package it.polimi.se2018.view;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.util.MatchIdentifier;
import it.polimi.se2018.util.Pair;

/**
 * Class for View-Controller communication
 *
 * @author Mathyas Giudici
 */

public class ViewActions {

    /**
     * To try a login to the server
     * @param name        contains user'name
     * @param password    contains user's password
     * @param newUser     to communicate if user has to sign up
     * @param host        IP address
     * @param isRMI       boolean value contains the type of connection
     * @param objectPort  contains the object port number
     * @param requestPort contains the request port number
     */
    public void login(String name, String password, boolean newUser, String host, Boolean isRMI, int objectPort, int requestPort) {
        throw new UnsupportedOperationException();
    }

    /**
     * To change layer from Socket to RMI or vice versa
     * @param toRMI boolean value
     * @param objectPort  contains the object port number
     * @param requestPort contains the request port number
     */
    public void changeLayer(boolean toRMI, int objectPort, int requestPort) {
        throw new UnsupportedOperationException();

    }

    /**
     * To leave the current match
     */
    public void leaveMatch() {
        throw new UnsupportedOperationException();

    }

    /**
     * To do a logout
     *
     */
    public void logout() {
        throw new UnsupportedOperationException();

    }

    /**
     * To ask the lobby
     */
    public void askLobby() {
        throw new UnsupportedOperationException();

    }

    /**
     * To send invites to other player to match
     *
     * @param invite contains a list of players (max 4)
     */
    public void pushInvite(MatchIdentifier invite) {
        throw new UnsupportedOperationException();

    }

    /**
     * To participate to match
     */
    public void autoCompleteGame() {
        throw new UnsupportedOperationException();

    }

    public void acceptInvite(MatchIdentifier matchIdentifier){
        throw new UnsupportedOperationException();

    }

    /**
     * To communicate to the Controller the pattern that user has select
     *
     * @param selected the selected pattern
     */
    public void selectedPattern(Pair<Integer,ColorModel>[][] selected) {
        throw new UnsupportedOperationException();

    }

    /**
     * To communicate to the Controller that View ended init operations
     */
    public void endInitGame() {
        throw new UnsupportedOperationException();

    }

    /**
     * To try a die placement
     *
     * @param die    contains die's ID
     * @param height contains height on the grid
     * @param width  contains width on the grid
     */
    public void setDie(Pair<Integer,ColorModel> die, int height, int width) {
        throw new UnsupportedOperationException();

    }

    /**
     * To try to use a Tool Card
     *
     * @param card contains the Tool Card's ID
     */
    public void useToolCard(int card) {
        throw new UnsupportedOperationException();

    }

    /**
     * To pass Turn
     */
    public void passTurn() {
        throw new UnsupportedOperationException();

    }
}
