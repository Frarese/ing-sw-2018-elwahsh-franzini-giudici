package it.polimi.se2018.view.app;

import it.polimi.se2018.controller.game.client.ActionSender;
import it.polimi.se2018.util.MatchIdentifier;
import it.polimi.se2018.view.ViewActions;
import it.polimi.se2018.view.ViewToolCardActions;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests for App abstract class
 *
 * @author Mathyas Giudici
 */

public class AppTest {

    private CLIApp cliApp;

    /**
     * Creates the object to test
     */
    @Before
    public void testInit() {
        cliApp = new CLIApp(new ViewActions(new ActionSender()), new ViewToolCardActions(new ActionSender()));
    }

    /**
     * Test the search in players' array that is null
     */
    @Test
    public void testSearchPlayerViewByNameNull() {
        assertNull(this.cliApp.searchPlayerViewByName(null, "Test"));
    }

    /**
     * Test getter method of invites' list
     */
    @Test
    public void testGetInvites() {
        List<MatchIdentifier> invites = new ArrayList<>();
        invites.add(new MatchIdentifier("test1", "test2", null, null));
        invites.add(new MatchIdentifier("test4", "test3", null, null));


        cliApp.pullInvitate(new MatchIdentifier("test1", "test2", null, null));
        cliApp.pullInvitate(new MatchIdentifier("test4", "test3", null, null));

        assertArrayEquals(invites.toArray(), cliApp.getInvites().toArray());
    }

    /**
     * Tests getter method of the viewActions object
     */
    @Test
    public void testGetViewActions() {
        testInit();

        ViewActions instance = new ViewActions(new ActionSender());
        Class<?> myViewAction = instance.getClass();

        assertEquals(myViewAction, this.cliApp.getViewActions().getClass());

        Method methods[] = myViewAction.getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            assertEquals(methods[i], this.cliApp.getViewActions().getClass().getDeclaredMethods()[i]);
        }

        Field fields[] = myViewAction.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            assertEquals(fields[i], this.cliApp.getViewActions().getClass().getDeclaredFields()[i]);
        }
    }

    /**
     * Tests getter method of the viewAToolCardActions object
     */
    @Test
    public void testGetViewToolCardActions() {
        testInit();

        ViewToolCardActions instance = new ViewToolCardActions(new ActionSender());
        Class<?> myViewToolCardAction = instance.getClass();

        assertEquals(myViewToolCardAction, this.cliApp.getViewToolCardActions().getClass());

        Method methods[] = myViewToolCardAction.getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            assertEquals(methods[i], this.cliApp.getViewToolCardActions().getClass().getDeclaredMethods()[i]);
        }

        Field fields[] = myViewToolCardAction.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            assertEquals(fields[i], this.cliApp.getViewToolCardActions().getClass().getDeclaredFields()[i]);
        }
    }
}
