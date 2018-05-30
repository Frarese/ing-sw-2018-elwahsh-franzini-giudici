package it.polimi.se2018.view;

import it.polimi.se2018.util.MessageTypes;

import java.util.Observable;

/**
 * Class for View-Controller communication
 *
 * @author Mathyas Giudici
 */

public class ViewMessage extends Observable {

    private String ownerName;

    /**
     * Class constructor
     * Sets player's name used in events thrown to the Controller
     *
     * @param ownerName contains the player's name
     */
    public ViewMessage(String ownerName) {
        this.ownerName = ownerName;
    }

    /**
     * Sends a message
     *
     * @param destination contains the destination of the message (user)
     * @param type        contains message's type
     * @param message     contains message
     */
    public void sendMessage(String destination, MessageTypes type, String message) {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets player's name used in events thrown to the Controller
     *
     * @param ownerName contains the player's name
     */
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
}
