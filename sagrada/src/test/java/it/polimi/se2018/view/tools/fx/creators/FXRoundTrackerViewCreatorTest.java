package it.polimi.se2018.view.tools.fx.creators;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.model.IntColorPair;
import org.junit.Test;

/**
 * Tests for FXRoundTrackerViewCreator class
 *
 * @author Mathyas Giudici
 */

public class FXRoundTrackerViewCreatorTest {

    @SuppressWarnings("unchecked")
    @Test(expected = ExceptionInInitializerError.class)
    public void testDisplay() {
        IntColorPair[][] roundT = new IntColorPair[9][10];
        roundT[0][0] = new IntColorPair(1,ColorModel.RED);
        roundT[0][1] = new IntColorPair(1,ColorModel.BLUE);
        roundT[1][0] = new IntColorPair(5,ColorModel.RED);

        FXRoundTrackerViewCreator roundTrackerViewCreator = new FXRoundTrackerViewCreator(3,roundT);

        roundTrackerViewCreator.display();
    }
}