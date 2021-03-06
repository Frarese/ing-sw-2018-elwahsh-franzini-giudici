package it.polimi.se2018.events.actions;

/**
 * Player choice of using a tool card during his/her turn
 * @author Alì El Wahsh
 */
public class UseToolCardMove extends PlayerMove {

    private final int cardID;

    /**
     * UseToolCardMove's constructor
     * @param name player's name
     * @param cardID ToolCard ID
     */
    public UseToolCardMove(String name, int cardID)
    {
        this.playerName = name;
        this.cardID = cardID;
        this.description = "UseCard";

    }

    /**
     * Getter for the card's ID
     * @return card ID
     */
    public int getCardID() {
        return cardID;
    }


}
