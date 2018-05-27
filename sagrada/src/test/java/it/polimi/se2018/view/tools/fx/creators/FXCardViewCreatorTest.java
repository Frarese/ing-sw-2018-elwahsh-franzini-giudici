package it.polimi.se2018.view.tools.fx.creators;

import javafx.embed.swing.JFXPanel;
import javafx.scene.image.Image;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

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
    public void makePrivateObjCardTest() {
        FXCardViewCreator cardViewCreator = new FXCardViewCreator(1, null, null);
        Image card = cardViewCreator.makeCard(1);
        Image aspect = new Image(new File("it/polimi/se2018/view/images/private_cards/privateObjective1.jpg").toURI().toString());

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
    public void makePublicObjCardTest() {
        int[] po = new int[3];
        po[0] = 15;
        po[1] = 11;
        FXCardViewCreator cardViewCreator = new FXCardViewCreator(1, po, null);
        Image card = cardViewCreator.makeCard(11);
        String url="/it/polimi/se2018/view/images/public_cards/publicObjective1.jpg";
        Image aspect = new Image(new File(url).toURI().toString());

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
    public void makeToolCardTest() {
        int[] po = new int[1];
        po[0] = 10;
        int[] tool = new int[3];
        tool[0] = 25;
        tool[1] = 21;
        FXCardViewCreator cardViewCreator = new FXCardViewCreator(1, po, tool);
        Image card = cardViewCreator.makeCard(21);
        String url="/it/polimi/se2018/view/images/tool_cards/toolCard1.jpg";
        Image aspect = new Image(new File(url).toURI().toString());

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
    public void makeFailTest() {
        int[] po = new int[1];
        int[] tool = new int[1];
        po[0] = 10;
        tool[0] = 25;
        FXCardViewCreator cardViewCreator = new FXCardViewCreator(1, po, tool);
        
        assertNull(cardViewCreator.makeCard(32));
    }
}