package it.polimi.se2018.view;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.util.MatchIdentifier;
import it.polimi.se2018.util.Pair;

/**
 * Class for View -> Controller communication
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
     * @param objectPort  contains the onject port number
     * @param requestPort contains the request port number
     */
    public void login(String name, String password, boolean newUser, String host, Boolean isRMI, int objectPort, int requestPort) {

    }

    /**
     * To change layer from Socket to RMI or vice versa
     *  @param toRMI boolean value
     * @param objectPort  contains the onject port number
     * @param requestPort contains the request port number
     */
    public void changeLayer(boolean toRMI, int objectPort, int requestPort) {

    }

    /**
     * To leave the current match
     */
    public void leaveMatch() {

    }

    /**
     * To do a logout
     *
     * @param quitGame communicate if player is playing in a match
     */
    public void logout(boolean quitGame) {

    }

//    /**
//     * To select a type of game (Solo or Battle Royale)
//     *
//     * @param isSoloGame boolean value
//     */
//    public void selectedGame(boolean isSoloGame) {
//
//    }

    /**
     * To ask the lobby
     */
    public void askLobby() {

    }

    /**
     * To send invites to other player to match
     *
     * @param invite contains a list of players (max 4)
     */
    public void pushInvite(MatchIdentifier invite) {

    }

    /**
     * To partecipate to match
     */
    public void autoCompleteGame() {

    }

    public void acceptInvite(MatchIdentifier matchIdentifier){

    }

    /**
     * To communicate to the Controller the pattern that user has select
     *
     * @param selected
     */
    public void selectedPattern(Pair<Integer,ColorModel>[][] selected) {

    }

    /**
     * To communicate to the Controller that View ended init operations
     */
    public void endInitGame() {

    }

    /**
     * To try a die placement
     *
     * @param die    contains die's ID
     * @param height contains height on the grid
     * @param width  contains width on the grid
     */
    public void setDie(Pair die, int height, int width) {

    }

    /**
     * To try to use a Tool Card
     *
     * @param card contains the Tool Card's ID
     */
    public void useToolCard(int card) {

    }

    /**
     * To pass Turn
     */
    public void passTurn() {

    }
}
