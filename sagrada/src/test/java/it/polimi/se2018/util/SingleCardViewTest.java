package it.polimi.se2018.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for SingleCardView class
 *
 * @author Mathyas Giudici
 */

public class SingleCardViewTest {

    @Test
    public void testClass() {
        SingleCardView singleCardView = new SingleCardView(1, 5);

        assertEquals(1, singleCardView.cardID);
        assertEquals(5, singleCardView.cardCost);
    }
}