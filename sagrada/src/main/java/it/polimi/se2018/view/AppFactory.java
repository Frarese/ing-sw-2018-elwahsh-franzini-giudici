package it.polimi.se2018.view;

import it.polimi.se2018.view.app.App;
import it.polimi.se2018.view.app.CLIApp;
import it.polimi.se2018.view.app.JavaFXApp;

/**
 * This Class represents the App maker.
 * Creates an App (CLI or JavaFX) according to the user's choice
 *
 * @author Mathyas Giudici
 */

public class AppFactory {

    private final App app;

    /**
     * Constructor called to create an App
     *
     * @param isGui               contains boolean value to choose between cli or gui
     * @param viewActions         contains ViewActions class for View-&gt;Controller communication
     * @param viewToolCardActions contains ViewToolCardActions class for View-&gt;Controller communication (tool cards)
     */
    public AppFactory(boolean isGui, ViewActions viewActions, ViewToolCardActions viewToolCardActions) {
        if (isGui) {
            this.app = new JavaFXApp(viewActions, viewToolCardActions);
        } else {
            this.app = new CLIApp(viewActions, viewToolCardActions);
        }
    }

    /**
     * Use to call App outside view
     *
     * @return current App reference
     */
    public App getApp() {
        return app;
    }
}
