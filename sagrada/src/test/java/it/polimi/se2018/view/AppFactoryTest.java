package it.polimi.se2018.view;

import it.polimi.se2018.view.app.CLIApp;
import it.polimi.se2018.view.app.JavaFXApp;
import javafx.application.Platform;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;

/**
 * Tests for AppFactory class
 *
 * @author Mathyas Giudici
 */

public class AppFactoryTest {

    @Test
    public void testCliGetApp() {
        AppFactory appFactory = new AppFactory(false, null, null, null);

        CLIApp instance = new CLIApp(null, null, null);
        Class<?> genericApp = instance.getClass();

        assertEquals(genericApp, appFactory.getApp().getClass());

        Method methods[] = genericApp.getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            assertEquals(methods[i], appFactory.getApp().getClass().getDeclaredMethods()[i]);
        }

        Field fields[] = genericApp.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            assertEquals(fields[i], appFactory.getApp().getClass().getDeclaredFields()[i]);
        }
    }

    @Test
    public void testFXGetApp() {
        String[] arg = new String[1];
        arg[0] = "gui";
        AppFactory appFactory = new AppFactory(true, null, null, null);

        JavaFXApp instance = new JavaFXApp(null, null, null);
        Class<?> genericApp = instance.getClass();

        Platform.exit();

        assertEquals(genericApp, appFactory.getApp().getClass());

        Method methods[] = genericApp.getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            assertEquals(methods[i], appFactory.getApp().getClass().getDeclaredMethods()[i]);
        }

        Field fields[] = genericApp.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            assertEquals(fields[i], appFactory.getApp().getClass().getDeclaredFields()[i]);
        }
    }
}