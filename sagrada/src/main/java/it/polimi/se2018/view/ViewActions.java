package it.polimi.se2018.view;

import it.polimi.se2018.util.Pair;

import java.util.List;

/**
 * Class for View -> Controller communication
 *
 * @author Mathyas Giudici
 */

public class ViewActions {

    /**
     * To try a login to the server
     *
     * @param isRMI       boolean value contains the type of connection
     * @param name        contains user'name
     * @param password    contains user's password
     * @param requestPort contains the request port number
     * @param objectPort  contains the onject port number
     * @param newUser     to communicate if user has to sign up
     */
    public void login(Boolean isRMI, String name, String password, int requestPort, int objectPort, boolean newUser) {

    }

    /**
     * To change layer from Socket to RMI or vice versa
     *
     * @param toRMI boolean value
     * @param requestPort contains the request port number
     * @param objectPort  contains the onject port number
     */
    public void changeLayer(boolean toRMI, int requestPort, int objectPort) {

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

    /**
     * To ask the lobby
     */
    public void askLobby() {

    }

    /**
     * To send invites to other player to match
     *
     * @param inviteList contains a list of players (max 4)
     */
    public void pushInviteList(List inviteList) {

    }

    /**
     * To partecipate to match
     */
    public void autoCompleteGame() {

    }

    /**
     * To select a type of game (Solo or Battle Royale)
     *
     * @param isSoloGame boolean value
     */
    public void selectedGame(boolean isSoloGame) {

    }

    /**
     * To communicate to the Controller the pattern that user has select
     *
     * @param selected
     */
    public void selectedPattern(List selected) {

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
