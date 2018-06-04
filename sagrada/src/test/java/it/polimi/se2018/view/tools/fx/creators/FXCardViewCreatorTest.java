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

    @Before
    public void initTest() {
        url=null;
    }

    @Test
    public void testMakePrivateObjCard() {
        FXCardViewCreator cardViewCreator = new FXCardViewCreatorMock(new SingleCardView(1, 1), null, null);
        cardViewCreator.makeCard(1);
        assertEquals("/it/polimi/se2018/view/images/private_cards/privateObjective1.jpg",url);
    }

    @Test
    public void testMakePublicObjCard() {
        List<SingleCardView> publicCards = new ArrayList<>();
        publicCards.add(new SingleCardView(10, 1));
        publicCards.add(new SingleCardView(11, 1));

        FXCardViewCreator cardViewCreator = new FXCardViewCreatorMock(new SingleCardView(1,1), publicCards, null);
        cardViewCreator.makeCard(11);
        assertEquals("/it/polimi/se2018/view/images/public_cards/publicObjective1.jpg",url);

    }

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

        assertEquals("/it/polimi/se2018/view/images/tool_cards/toolCard1.jpg",url);
    }

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

    private class FXCardViewCreatorMock extends FXCardViewCreator{


        FXCardViewCreatorMock(SingleCardView privateObjectiveCard, List<SingleCardView> publicObjectiveCards, List<SingleCardView> toolCards) {
            super(privateObjectiveCard, publicObjectiveCards, toolCards);
        }

        @Override
        Image makeImage(String url){
            FXCardViewCreatorTest.this.url=url;
            return null;
        }
    }
}