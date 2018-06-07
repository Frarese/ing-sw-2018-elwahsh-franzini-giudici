package it.polimi.se2018.view;

import it.polimi.se2018.controller.game.client.ActionSender;
import it.polimi.se2018.util.MessageTypes;

import java.util.Observable;

/**
 * Class for View-Controller communication
 *
 * @author Mathyas Giudici
 */

public class ViewMessage extends Observable {

    private final ActionSender actionSender;

    /**
     * Class constructor
     * Sets player's name used in events thrown to the Controller
     *
     * @param actionSender contains the controller class to manage events
     */
    public ViewMessage(ActionSender actionSender) {
        this.actionSender = actionSender;
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
}
