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
     * Checks if in first position in mainArgs's array there is "gui" string.
     * If it's true starts a FXApp, in other cases start a CLIApp
     *
     * @param mainArgs contains the arg passed at mains
     */
    public AppFactory(String[] mainArgs, ViewActions viewActions, ViewToolCardActions viewToolCardActions, ViewMessage viewMessage) {
        boolean useGui = false;
        if (mainArgs != null && mainArgs[0] != null && mainArgs[0].equals("gui")) {
            useGui = true;
        }

        if (useGui) {
            this.app = new JavaFXApp(viewActions, viewToolCardActions, viewMessage, mainArgs);
        } else {
            this.app = new CLIApp(viewActions, viewToolCardActions, viewMessage);
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
