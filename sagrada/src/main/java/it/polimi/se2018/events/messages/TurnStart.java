package it.polimi.se2018.events.messages;

import it.polimi.se2018.events.ServerMessageHandler;

/**
 * Notifies players the beginning of a new turn
 */
public class TurnStart extends ServerMessage {

    private final String name;
    private final String oldPlayer;

    /**
     * TurnStart's constructor
     * @param oldPlayer old player
     * @param playerName new current player
     */
    public TurnStart(String oldPlayer, String playerName)
    {
        this.oldPlayer = oldPlayer;
         this.name = playerName;
         this.description = "TurnStart";
    }

    /**
     * Getter for current player's name
     * @return current player's name
     */
    public String getName() {
        return name;
    }


    /**
     * Getter for previous player
     * @return previous player
     */
    public String getOldPlayer() {
        return oldPlayer;
    }

    @Override
    public void visit(ServerMessageHandler handler) {
        handler.handle(this);
    }
}
