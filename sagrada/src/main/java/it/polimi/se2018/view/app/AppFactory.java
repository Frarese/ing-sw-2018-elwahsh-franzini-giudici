package it.polimi.se2018.view.app;

/**
 * This Class represents the App maker.
 * Creates an App (CLI or JavaFX) according to the user's choise
 *
 * @author Mathyas Giudici
 */

public class AppFactory {

    private App app;

    /**
     * Constructor called to create an App
     *
     * @param useGui boolean value to chose if use CLI or FX app
     */
    public AppFactory(boolean useGui) {
        if (useGui) {
            try {
                this.app = new JavaFXApp();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            this.app = new CLIApp();
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
