package it.polimi.se2018.view.tools.fx.creators;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.util.Pair;
import it.polimi.se2018.view.tools.fx.FXConstants;
import javafx.embed.swing.JFXPanel;
import javafx.scene.image.Image;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for FXDieViewCreator class
 *
 * @author Mathyas Giudici
 */

public class FXDieViewCreatorTest {

    @Before
    public void initTest(){
        new JFXPanel();
    }

    @Test
    public void makeDieTest() {
        FXDieViewCreator dieViewCreator = new FXDieViewCreator(FXConstants.GRID_CELL_DIM_VALUE);
        Image die = dieViewCreator.makeDie(new Pair<>(1, ColorModel.RED));
        Image aspect = new Image("/it/polimi/se2018/view/images/die/value_color/val1cRED.png",FXConstants.GRID_CELL_DIM_VALUE,FXConstants.GRID_CELL_DIM_VALUE,true,false);

        int error = 0;

        for (int x = 0; x < (int) die.getWidth(); x++) {
            for (int y = 0; y < (int) die.getHeight(); y++) {
                if (die.getPixelReader().getArgb(x, y) != aspect.getPixelReader().getArgb(x, y)) {
                    error++;
                }
            }
        }

        assertEquals(0,error);
    }
}