package it.polimi.se2018.view.tools.fx.creators;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.util.Pair;
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
        Pair<Integer, ColorModel>[][] roundT = new Pair[10][2];
        roundT[0][0] = new Pair<>(1,ColorModel.RED);
        roundT[0][1] = new Pair<>(1,ColorModel.BLUE);
        roundT[1][0] = new Pair<>(5,ColorModel.RED);

        FXRoundTrackerViewCreator roundTrackerViewCreator = new FXRoundTrackerViewCreator(3,roundT);

        roundTrackerViewCreator.display();
    }
}