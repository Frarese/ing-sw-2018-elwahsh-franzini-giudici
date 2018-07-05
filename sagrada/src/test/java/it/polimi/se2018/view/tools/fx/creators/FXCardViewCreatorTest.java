package it.polimi.se2018.view.tools.fx.creators;

import it.polimi.se2018.util.SingleCardView;
import javafx.scene.image.Image;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Tests for FXCardViewCreator class
 *
 * @author Mathyas Giudici
 */

public class FXCardViewCreatorTest {
    private String url;

    /**
     * Set url flag at null
     */
    @Before
    public void initTest() {
        url = null;
    }

    /**
     * Tests correct FX's private objective card creation
     */
    @Test
    public void testMakePrivateObjCard() {
        FXCardViewCreator cardViewCreator = new FXCardViewCreatorMock(new SingleCardView(1, 1), null, null);
        cardViewCreator.makeCard(1);
        assertEquals("/it/polimi/se2018/view/images/private_cards/privateObjective1.jpg", url);
    }

    /**
     * Tests correct FX's public objective card creation
     */
    @Test
    public void testMakePublicObjCard() {
        List<SingleCardView> publicCards = new ArrayList<>();
        publicCards.add(new SingleCardView(10, 1));
        publicCards.add(new SingleCardView(11, 1));

        FXCardViewCreator cardViewCreator = new FXCardViewCreatorMock(new SingleCardView(1, 1), publicCards, null);
        cardViewCreator.makeCard(11);
        assertEquals("/it/polimi/se2018/view/images/public_cards/publicObjective1.jpg", url);
    }

    /**
     * Tests correct FX's tool card creation
     */
    @Test
    public void testMakeToolCard() {
        List<SingleCardView> publicCards = new ArrayList<>();
        publicCards.add(new SingleCardView(10, 1));
        publicCards.add(new SingleCardView(11, 1));

        List<SingleCardView> toolCards = new ArrayList<>();
        toolCards.add(new SingleCardView(21, 1));
        toolCards.add(new SingleCardView(25, 1));

        FXCardViewCreator cardViewCreator = new FXCardViewCreatorMock(new SingleCardView(1, 1), publicCards, toolCards);
        cardViewCreator.makeCard(21);

        assertEquals("/it/polimi/se2018/view/images/tool_cards/toolCard1.jpg", url);
    }

    /**
     * Tests correct FX's card creation failure
     */
    @Test
    public void testMakeFail() {
        List<SingleCardView> publicCards = new ArrayList<>();
        publicCards.add(new SingleCardView(10, 1));
        publicCards.add(new SingleCardView(11, 1));

        List<SingleCardView> toolCards = new ArrayList<>();
        toolCards.add(new SingleCardView(21, 1));
        toolCards.add(new SingleCardView(25, 1));
        FXCardViewCreator cardViewCreator = new FXCardViewCreator(new SingleCardView(1, 1), publicCards, toolCards);

        assertNull(cardViewCreator.makeCard(32));
    }

    /**
     * Mock class of FXCardViewCreator object
     */
    private class FXCardViewCreatorMock extends FXCardViewCreator {


        FXCardViewCreatorMock(SingleCardView privateObjectiveCard, List<SingleCardView> publicObjectiveCards, List<SingleCardView> toolCards) {
            super(privateObjectiveCard, publicObjectiveCards, toolCards);
        }

        /**
         * Sets FXCardViewCreatorTest's flag with url passed
         *
         * @param url contain's image url
         * @return null
         */
        @Override
        Image makeImage(String url) {
            FXCardViewCreatorTest.this.url = url;
            return null;
        }
    }
}