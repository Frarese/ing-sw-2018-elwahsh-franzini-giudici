package it.polimi.se2018.view.tools.fx.controller;

import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.assertNotEquals;

/**
 * Tests for ChangeLayerController class
 *
 * @author Mathyas Giudici
 */

public class ChangeLayerControllerTest {

    /**
     *  Tests part of ChangeLayer form validation
     *  Checks correct request port and object port validation
     *
     * @throws Exception if an error occurs during checks
     */
    @Test
    public void testChecks() throws Exception {
        ChangeLayerController changeLayerController = new ChangeLayerController();

        Method reqM = ChangeLayerController.class.getDeclaredMethod("checkRequestPort", String.class, StringBuilder.class);
        reqM.setAccessible(true);

        StringBuilder stringBuilder = new StringBuilder();
        reqM.invoke(changeLayerController, "-1", stringBuilder);
        assertNotEquals("", stringBuilder.toString());

        stringBuilder = new StringBuilder();
        reqM.invoke(changeLayerController, "test", stringBuilder);
        assertNotEquals("", stringBuilder.toString());

        stringBuilder = new StringBuilder();
        reqM.invoke(changeLayerController, "", stringBuilder);
        assertNotEquals("", stringBuilder.toString());

        Method objM = ChangeLayerController.class.getDeclaredMethod("checkObjectPort", String.class, StringBuilder.class);
        objM.setAccessible(true);

        stringBuilder = new StringBuilder();
        objM.invoke(changeLayerController, "-1", stringBuilder);
        assertNotEquals("", stringBuilder.toString());

        stringBuilder = new StringBuilder();
        objM.invoke(changeLayerController, "test", stringBuilder);
        assertNotEquals("", stringBuilder.toString());

        stringBuilder = new StringBuilder();
        objM.invoke(changeLayerController, "", stringBuilder);
        assertNotEquals("", stringBuilder.toString());
    }
}