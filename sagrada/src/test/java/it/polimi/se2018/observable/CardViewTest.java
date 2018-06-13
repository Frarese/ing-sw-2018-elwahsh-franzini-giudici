package it.polimi.se2018.observable;

import it.polimi.se2018.events.messages.CardInfo;
import it.polimi.se2018.model.cards.ActiveObjectives;
import it.polimi.se2018.model.cards.ActiveTools;
import it.polimi.se2018.util.SingleCardView;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Tests for CardView class
 *
 * @author Mathyas Giudici
 */

public class CardViewTest {

    private class ObjectObserver implements Observer {

        @Override
        public void update(Observable o, Object arg) {
            if (arg instanceof CardView) {
                assertEquals(privateCard, ((CardView) arg).getPrivateObjectiveCard());
                assertArrayEquals(publicCards.toArray(), ((CardView) arg).getPublicObjectiveCards().toArray());
                assertArrayEquals(toolCards.toArray(), ((CardView) arg).getToolCards().toArray());
            }
        }
    }

    private SingleCardView privateCard;
    private List<SingleCardView> publicCards;
    private List<SingleCardView> toolCards;

    private CardView cardView;


    @Before
    public void testInit() {
        privateCard = new SingleCardView(1, 1);

        publicCards = new ArrayList<>();
        publicCards.add(new SingleCardView(10, 1));
        publicCards.add(new SingleCardView(17, 1));

        toolCards = new ArrayList<>();
        toolCards.add(new SingleCardView(20, 1));
        toolCards.add(new SingleCardView(30, 1));

        this.cardView = new CardView(privateCard, publicCards, toolCards);
        ObjectObserver objectObserver = new ObjectObserver();
        this.cardView.addObserver(objectObserver);
    }

    @Test
    public void testGetPrivateObjectiveCard() {
        assertEquals(privateCard, cardView.getPrivateObjectiveCard());
    }

    @Test
    public void testSetPrivateObjectiveCard() {
        privateCard = new SingleCardView(2, 1);
        cardView.setPrivateObjectiveCard(privateCard);
    }

    @Test
    public void testGetPublicObjectiveCards() {
        assertArrayEquals(publicCards.toArray(), cardView.getPublicObjectiveCards().toArray());
    }

    @Test
    public void testSetPublicObjectiveCards() {
        publicCards = new ArrayList<>();
        publicCards.add(new SingleCardView(12, 1));
        publicCards.add(new SingleCardView(15, 1));

        cardView.setPublicObjectiveCards(publicCards);
    }

    @Test
    public void testGetToolCards() {
        assertArrayEquals(toolCards.toArray(), cardView.getToolCards().toArray());
    }

    @Test
    public void testSetToolCards() {
        toolCards = new ArrayList<>();
        toolCards.add(new SingleCardView(20, 1));
        toolCards.add(new SingleCardView(30, 1));

        cardView.setToolCards(toolCards);
    }

    @Test
    public void testSetCardView() {
        List<SingleCardView> tools=cardView.getToolCards();
        ActiveTools aT=new ActiveTools();
        ActiveObjectives aO=new ActiveObjectives();
        cardView.setCardView(new CardInfo(aT,aO));
        assertNotEquals(tools,cardView.getToolCards());
    }
}