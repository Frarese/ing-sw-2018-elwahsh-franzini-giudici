package it.polimi.se2018.view.tools.fx.creators;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.model.IntColorPair;
import org.junit.Test;


/**
 * Tests for FXReserveViewCreator class
 *
 * @author Mathyas Giudici
 */

public class FXReserveViewCreatorTest {

    @Test
    public void testDisplay(){
        IntColorPair[] reserve = new IntColorPair[9];
        reserve[0] = new IntColorPair(1, ColorModel.RED);
        reserve[1] = new IntColorPair(2, ColorModel.RED);
        reserve[2] = new IntColorPair(3, ColorModel.RED);
        reserve[3] = new IntColorPair(4, ColorModel.RED);
        reserve[4] = new IntColorPair(5, ColorModel.RED);
        reserve[5] = new IntColorPair(6, ColorModel.RED);
        reserve[6] = new IntColorPair(1, ColorModel.BLUE);
        reserve[7] = new IntColorPair(3, ColorModel.BLUE);
        reserve[8] = new IntColorPair(6, ColorModel.BLUE);
        new FXReserveViewCreator(reserve);
    }
}