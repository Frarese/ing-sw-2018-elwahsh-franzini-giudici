package it.polimi.se2018.events.messages;

import it.polimi.se2018.events.ServerMessageHandler;
public class ReadyView extends ServerMessage {

    public ReadyView()
    {
        this.description = "ReadyView";
    }

    @Override
    public void visit(ServerMessageHandler handler) {
        handler.handle(this);
    }
}
