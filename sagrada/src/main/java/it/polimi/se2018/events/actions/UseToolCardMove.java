package it.polimi.se2018.events.actions;

/**
 * Player choice of using a tool card during his/her turn
 * @author Alì El Wahsh
 */
public class UseToolCardMove extends PlayerMove {

    private final int cardID;
    private int reserveIndex;
    private int newValue;
    private int originalX;
    private int originalY;
    private int height;
    private int width;
    private int round;
    private int diePosition;

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

    public int getReserveIndex() {
        return reserveIndex;
    }

    public int getNewValue() {
        return newValue;
    }

    public int getOriginalX() {
        return originalX;
    }

    public int getOriginalY() {
        return originalY;
    }

    public int getRound() {
        return round;
    }

    public int getDiePosition() {
        return diePosition;
    }

    public void setReserveIndex(int reserveIndex) {
        this.reserveIndex = reserveIndex;
    }

    public void setNewValue(int newValue) {
        this.newValue = newValue;
    }

    public void setOriginalX(int originalX) {
        this.originalX = originalX;
    }

    public void setOriginalY(int originalY) {
        this.originalY = originalY;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public void setDiePosition(int diePosition) {
        this.diePosition = diePosition;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
