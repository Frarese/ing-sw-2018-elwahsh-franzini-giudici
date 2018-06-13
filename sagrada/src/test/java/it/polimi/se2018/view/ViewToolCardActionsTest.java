package it.polimi.se2018.view;

import it.polimi.se2018.controller.game.client.ActionSender;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for ViewToolCardActions class
 *
 * @author Mathyas Giudici
 */

public class ViewToolCardActionsTest {

    private boolean assertion;

    private ViewToolCardActions viewActions;

    private ActionSender actionSender;

    @Before
    public void testInit() {
        assertion = false;
    }

    @After
    public void testUltimateCheck() {
        assertTrue(assertion);
    }

    @Test
    public void testSelectedDieFromReserve() {
        actionSender = new FakeActionSender() {
            @Override
            public void selectedDieFromReserve(int index) {
                assertEquals(1, index);
                assertion = true;
            }
        };

        viewActions = new ViewToolCardActions(actionSender);
        viewActions.selectedDieFromReserve(1);
    }

    @Test
    public void testSelectedValueForDie() {
        actionSender = new FakeActionSender() {
            @Override
            public void selectedNewValueForDie(int newVal) {
                assertEquals(5, newVal);
                assertion = true;
            }
        };

        viewActions = new ViewToolCardActions(actionSender);
        viewActions.selectedValueForDie(5);
    }

    @Test
    public void testSelectedDieFromGrid() {
        actionSender = new FakeActionSender() {
            @Override
            public void selectedDieFromGrid(int h, int w) {
                assertEquals(1, w);
                assertEquals(2, h);
                assertion = true;
            }
        };

        viewActions = new ViewToolCardActions(actionSender);
        viewActions.selectedDieFromGrid(1, 2);
    }

    @Test
    public void testSelectedDieToGrid() {
        actionSender = new FakeActionSender() {
            @Override
            public void selectedDieToGrid(int h, int w) {
                assertEquals(1, w);
                assertEquals(2, h);
                assertion = true;
            }
        };

        viewActions = new ViewToolCardActions(actionSender);
        viewActions.selectedDieToGrid(1, 2);
    }

    @Test
    public void testSelectedDieFromRoundTracker() {
        actionSender = new FakeActionSender() {
            @Override
            public void selectedDieFromRoundTrack(int round, int diePosition) {
                assertEquals(1, round);
                assertEquals(2, diePosition);
                assertion = true;
            }
        };

        viewActions = new ViewToolCardActions(actionSender);
        viewActions.selectedDieFromRoundTracker(1, 2);
    }

    @Test
    public void testSelectedFace() {
        actionSender = new FakeActionSender() {
            @Override
            public void selectedNewValueForDie(int newVal) {
                assertEquals(5, newVal);
                assertion = true;
            }
        };

        viewActions = new ViewToolCardActions(actionSender);
        viewActions.selectedFace(5);
    }

    @Test
    public void testSelectedDieFromGridByColor() {
        actionSender = new FakeActionSender() {
            @Override
            public void selectedDieFromGrid(int h, int w) {
                assertEquals(3, h);
                assertEquals(4, w);
                assertion = true;
            }
        };

        viewActions = new ViewToolCardActions(actionSender);
        viewActions.selectedDieFromGridByColor(4, 3);
    }

    private abstract class FakeActionSender extends ActionSender {
        FakeActionSender() {

        }
    }
}