package it.polimi.se2018.events.messages;

import it.polimi.se2018.events.ServerMessageHandler;
public class ReadyView extends ServerMessage {
    public final String playerName;
    public ReadyView(String username)
    {
        this.playerName = username;
        this.description = "ReadyView";
    }

    @Override
    public void visit(ServerMessageHandler handler) {
        handler.handle(this);
    }
}
