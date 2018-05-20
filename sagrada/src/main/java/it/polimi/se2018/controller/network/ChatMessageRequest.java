package it.polimi.se2018.controller.network;

import it.polimi.se2018.controller.network.client.Comm;
import it.polimi.se2018.controller.network.client.CommUtilizer;
import it.polimi.se2018.controller.network.server.Client;
import it.polimi.se2018.controller.network.server.ServerMain;
import it.polimi.se2018.util.MessageTypes;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        if(!checkValid())throw new IllegalArgumentException("Parameters invalid");
    }

    @Override
    public void clientHandle(Comm clientComm, CommUtilizer commUtilizer) {
        if(!checkValid())return;
        commUtilizer.pushChatMessage(msg,type,sender);
    }

    @Override
    public boolean checkValid() {
        return sender!=null && destination!=null && !destination.equals(sender) && msg!=null && type!=null;
    }

    @Override
    public void serverHandle(Client client, ServerMain server) {
        if(!checkValid())return;
        if(!sender.equals(client.usn)){
            Logger.getGlobal().log(Level.FINE,"Client attempted to cheat on username {0}",client.usn);
        }
        switch (type){
            case BROADCAST:
                throw new UnsupportedOperationException();
            case PM:
                Client d=server.getClient(destination);
                if(d!=null)d.pushOutReq(this);
                break;
            case MATCH:
                if(client.getMatch()==null)break;
                List<Client> dst=client.getMatch().getClients();
                dst.stream().filter(cl->(cl!=client)).forEach(cl->cl.pushOutReq(this));
        }
    }


}