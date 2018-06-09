package it.polimi.se2018.observable;

import it.polimi.se2018.model.IntColorPair;
import org.junit.Before;
import org.junit.Test;

import java.util.Observable;
import java.util.Observer;

import static org.junit.Assert.assertArrayEquals;

/**
 * Tests for ReserveView class
 *
 * @author Mathyas Giudici
 */

public class ReserveViewTest {

    private class ObjectObserver implements Observer {

        @Override
        public void update(Observable o, Object arg) {
            if (arg instanceof ReserveView) {
                assertArrayEquals(reserve, ((ReserveView) arg).getReserve());
            }
        }
    }

    private IntColorPair[] reserve;

    private ReserveView reserveView;

    @Before
    public void testInit() {
        reserve = new IntColorPair[1];

        reserveView = new ReserveView(reserve);
        ObjectObserver objectObserver = new ObjectObserver();
        reserveView.addObserver(objectObserver);
    }

    @Test
    public void testGetReserve() {
        assertArrayEquals(reserve, reserveView.getReserve());
    }

    @Test
    public void testSetReserve() {
        reserve = new IntColorPair[10];
        reserveView.setReserve(reserve);
    }
}