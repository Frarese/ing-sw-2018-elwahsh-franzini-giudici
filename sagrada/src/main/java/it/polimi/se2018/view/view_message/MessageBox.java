package it.polimi.se2018.view.view_message;

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

    @Override
    public void receiveMessagge( String sender, MessageTypes type, String message ) {

    }

    public void sendMessage( String destination, MessageTypes type, String message ){

    }
}