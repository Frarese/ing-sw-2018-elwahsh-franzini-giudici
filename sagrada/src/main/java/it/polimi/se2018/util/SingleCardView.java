package it.polimi.se2018.util;

/**
 * Simple class that contains a card and its properties
 *
 * @author Mathyas Giudici
 */

public class SingleCardView {
    public final int cardID;
    public final int cardCost;

    /**
     * Class Constructor
     *
     * @param cardID   contains the card's ID
     * @param cardCost contains the card's favours point cost
     */
    public SingleCardView(int cardID, int cardCost) {
        this.cardID = cardID;
        this.cardCost = cardCost;
    }
}
