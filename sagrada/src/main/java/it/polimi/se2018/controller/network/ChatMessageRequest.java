package it.polimi.se2018.controller.network;

import it.polimi.se2018.controller.network.client.Comm;
import it.polimi.se2018.controller.network.client.CommUtilizer;
import it.polimi.se2018.controller.network.server.Client;
import it.polimi.se2018.controller.network.server.ServerMain;

/**
 * Request that is used to send a chat message through the network
 * @author Francesco Franzini
 */
public class ChatMessageRequest extends AbsReqServerLogic {
    public final String sender;
    public final String destination;
    public final String msg;

    /**
     * Initializes this wrapper request with the given parameters
     * @param sender username of the sender
     * @param destination eventual username of the destination user
     * @param msg the message
     */
    public ChatMessageRequest(String sender, String destination, String msg) {
        this.destination=destination;
        this.sender=sender;
        this.msg=msg;
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