package it.polimi.se2018.view.message;

import it.polimi.se2018.util.MessageTypes;
import it.polimi.se2018.view.ControllerMessage;
import it.polimi.se2018.view.ViewMessage;

/**
 * Class to manage message
 *
 * @author Mathyas Giudici
 */

public class MessageBox implements ControllerMessage {

    private ViewMessage viewMessage;


    /**
     * Class constructor
     *
     * @param viewMessage contains ViewMessage class to View->Controller communication
     */
    public MessageBox(ViewMessage viewMessage) {
        this.viewMessage = viewMessage;
    }

    @Override
    public void receiveMessage(String sender, MessageTypes type, String message) {
        throw new UnsupportedOperationException();

    }

    /**
     * Send messages on network
     *
     * @param destination contains message's destination
     * @param type contains message's type
     * @param message contains message's text
     */
    public void sendMessage(String destination, MessageTypes type, String message) {
        throw new UnsupportedOperationException();
    }
}
