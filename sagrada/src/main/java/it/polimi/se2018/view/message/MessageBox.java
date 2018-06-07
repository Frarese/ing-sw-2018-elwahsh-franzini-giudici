package it.polimi.se2018.view.message;

import it.polimi.se2018.util.MessageTypes;
import it.polimi.se2018.view.ControllerMessage;
import it.polimi.se2018.view.ViewMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to manage message
 *
 * @author Mathyas Giudici
 */

public class MessageBox implements ControllerMessage {

    class Message {
        final String sender;
        final MessageTypes type;
        final String content;

        Message(String sender, MessageTypes type, String message) {
            this.sender = sender;
            this.type = type;
            this.content = message;
        }
    }

    private final ViewMessage viewMessage;

    private final List<Message> messages;


    /**
     * Class constructor
     *
     * @param viewMessage contains ViewMessage class to View->Controller communication
     */
    public MessageBox(ViewMessage viewMessage) {
        this.viewMessage = viewMessage;
        this.messages = new ArrayList<>();
    }

    @Override
    public void receiveMessage(String sender, MessageTypes type, String message) {
        messages.add(new Message(sender, type, message));
    }

    /**
     * Send messages on network
     *
     * @param destination contains message's destination
     * @param type        contains message's type
     * @param message     contains message's text
     */
    public void sendMessage(String destination, MessageTypes type, String message) {
        viewMessage.sendMessage(destination, type, message);
    }

    /**
     * Gets messages' list
     *
     * @return the messages' list
     */
    public List<Message> getMessages() {
        return messages;
    }
}
