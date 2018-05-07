package it.polimi.se2018.controller.network;

import it.polimi.se2018.controller.network.client.Comm;
import it.polimi.se2018.controller.network.client.CommUtilizer;
import it.polimi.se2018.controller.network.server.Client;
import it.polimi.se2018.controller.network.server.ServerMain;
import it.polimi.se2018.util.MessageTypes;

/**
 * Request that is used to send a chat message through the network
 * @author Francesco Franzini
 */
public class ChatMessageRequest extends AbsReqServerLogic {
    public final String sender;
    public final String destination;
    public final String msg;
    public final MessageTypes type;

    /**
     * Initializes this wrapper request with the given parameters
     * @param sender username of the sender
     * @param destination eventual username of the destination user
     * @param msg the message
     * @param type type of this message
     */
    public ChatMessageRequest(String sender, String destination, String msg, MessageTypes type) {
        this.destination=destination;
        this.sender=sender;
        this.msg=msg;
        this.type=type;
    }

    @Override
    public void clientHandle(Comm clientComm, CommUtilizer commUtilizer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void serverHandle(Client client, ServerMain server) {
        throw new UnsupportedOperationException();
    }


}