package it.polimi.se2018.model.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Model representation of a deck of cards
 * @param <E> A class that extends CardModel
 * @author Alì El Wahsh
 */
public class Deck<E extends CardModel> {
    private final ArrayList<E> deckOfCards;

    /**
     * Deck constructor
     * @param cards list of already built cards
     */
    public Deck(List<E> cards)
    {
        deckOfCards = new ArrayList<>(cards);
        Collections.shuffle(cards);
    }

    /**
     * Pop cards from the deck to a list
     * @param numberOfCards number of desired cards
     * @return the list of card with size &lt;= numberOfCards (if numberOfCards &gt; size())
     */
    public List<E> draw(int numberOfCards)
    {
        ArrayList<E> temp = new ArrayList<>();
        for(int i=0; i<numberOfCards && !deckOfCards.isEmpty(); i++)
        {
                temp.add(deckOfCards.remove(0));
        }
        return temp;
    }

    /**
     * Use the shuffle method from collection to ensure a random order
     */
    public void shuffle()
    {
        Collections.shuffle(deckOfCards);
    }
}
