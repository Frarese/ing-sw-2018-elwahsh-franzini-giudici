package it.polimi.se2018.view.tools.fx.creators;

import it.polimi.se2018.util.SingleCardView;
import javafx.embed.swing.JFXPanel;
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

    @Before
    public void initTest() {
        new JFXPanel();
    }

    @Test
    public void testMakePrivateObjCard() {
        FXCardViewCreator cardViewCreator = new FXCardViewCreator(new SingleCardView(1, 1), null, null);
        Image card = cardViewCreator.makeCard(1);
        Image aspect = new Image("/it/polimi/se2018/view/images/private_cards/privateObjective1.jpg");

        int error = 0;

        for (int x = 0; x < (int) card.getWidth(); x++) {
            for (int y = 0; y < (int) card.getWidth(); y++) {
                if (card.getPixelReader().getArgb(x, y) != aspect.getPixelReader().getArgb(x, y)) {
                    error++;
                }
            }
        }
        assertEquals(0, error);
    }

    @Test
    public void testMakePublicObjCard() {
        List<SingleCardView> publicCards = new ArrayList<>();
        publicCards.add(new SingleCardView(10, 1));
        publicCards.add(new SingleCardView(11, 1));

        FXCardViewCreator cardViewCreator = new FXCardViewCreator(new SingleCardView(1,1), publicCards, null);
        Image card = cardViewCreator.makeCard(11);
        Image aspect = new Image("/it/polimi/se2018/view/images/public_cards/publicObjective1.jpg");

        int error = 0;

        for (int x = 0; x < (int) card.getWidth(); x++) {
            for (int y = 0; y < (int) card.getWidth(); y++) {
                if (card.getPixelReader().getArgb(x, y) != aspect.getPixelReader().getArgb(x, y)) {
                    error++;
                }
            }
        }
        assertEquals(0, error);
    }

    @Test
    public void testMakeToolCard() {
        List<SingleCardView> publicCards = new ArrayList<>();
        publicCards.add(new SingleCardView(10, 1));
        publicCards.add(new SingleCardView(11, 1));

        List<SingleCardView> toolCards = new ArrayList<>();
        toolCards.add(new SingleCardView(21, 1));
        toolCards.add(new SingleCardView(25, 1));

        FXCardViewCreator cardViewCreator = new FXCardViewCreator(new SingleCardView(1, 1), publicCards, toolCards);
        Image card = cardViewCreator.makeCard(21);
        Image aspect = new Image("/it/polimi/se2018/view/images/tool_cards/toolCard1.jpg");

        int error = 0;

        for (int x = 0; x < (int) card.getWidth(); x++) {
            for (int y = 0; y < (int) card.getWidth(); y++) {
                if (card.getPixelReader().getArgb(x, y) != aspect.getPixelReader().getArgb(x, y)) {
                    error++;
                }
            }
        }
        assertEquals(0, error);
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
}