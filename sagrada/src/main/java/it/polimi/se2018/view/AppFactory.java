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
     * @param useGui   boolean value to chose if use CLI or FX app
     * @param mainArgs contains the arg passed at mains
     */
    public AppFactory(boolean useGui, String[] mainArgs) {
        if (useGui) {
            this.app = new JavaFXApp(new ViewActions(), new ViewToolCardActions(), new ViewMessage(), mainArgs);
        } else {
            this.app = new CLIApp(new ViewActions(), new ViewToolCardActions(), new ViewMessage());
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
