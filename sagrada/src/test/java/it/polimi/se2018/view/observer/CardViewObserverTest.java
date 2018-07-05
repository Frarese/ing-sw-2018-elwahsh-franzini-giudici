package it.polimi.se2018.view.observer;

import it.polimi.se2018.observable.CardView;
import it.polimi.se2018.util.SingleCardView;
import it.polimi.se2018.view.app.App;
import it.polimi.se2018.view.app.CLIApp;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Tests for CardViewObserver class
 *
 * @author Mathyas Giudici
 */

public class CardViewObserverTest {

    /**
     * Checks correct update of cards' state
     */
    @Test
    public void testUpdate() {
        App app = new CLIApp(null, null);

        SingleCardView privateObj = new SingleCardView(1, 0);
        List<SingleCardView> publicObj = new ArrayList<>();
        publicObj.add(new SingleCardView(10, 0));
        publicObj.add(new SingleCardView(15, 0));
        List<SingleCardView> tool = new ArrayList<>();
        tool.add(new SingleCardView(20, 0));
        tool.add(new SingleCardView(23, 0));

        CardView cardView = new CardView(privateObj, publicObj, tool);
        CardViewObserver cardViewObserver = new CardViewObserver(app);
        cardViewObserver.update(cardView, null);

        assertEquals(privateObj, app.getCardViewCreator().getPrivateObjectiveCard());
        assertArrayEquals(publicObj.toArray(), app.getCardViewCreator().getPublicObjectiveCards().toArray());
        assertArrayEquals(tool.toArray(), app.getCardViewCreator().getToolCards().toArray());
    }
}