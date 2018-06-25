package it.polimi.se2018.view.observer;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.model.IntColorPair;
import it.polimi.se2018.observable.ReserveView;
import it.polimi.se2018.view.app.App;
import it.polimi.se2018.view.app.CLIApp;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

/**
 * Tests for ReserveViewObserver class
 *
 * @author Mathyas Giudici
 */

public class ReserveViewObserverTest {

    @Test
    public void testUpdate() {
        App app = new CLIApp(null, null);

        IntColorPair[] reserve = new IntColorPair[3];
        reserve[0] = new IntColorPair(1, ColorModel.RED);
        reserve[1] = new IntColorPair(2, ColorModel.BLUE);
        reserve[2] = new IntColorPair(3, ColorModel.YELLOW);

        ReserveView reserveView = new ReserveView(reserve);

        ReserveViewObserver reserveViewObserver = new ReserveViewObserver(app);
        reserveViewObserver.update(reserveView, null);

        assertArrayEquals(reserveView.getReserve(), app.getReserveViewCreator().getReserve());
    }
}