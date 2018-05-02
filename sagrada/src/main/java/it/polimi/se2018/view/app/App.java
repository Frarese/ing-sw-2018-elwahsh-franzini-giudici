package it.polimi.se2018.view.app;

import it.polimi.se2018.view.*;

/**
 * Abstract Class App represents the generic user interface (CLI or JavaFX)
 * @author Mathyas Giudici
 */

public abstract class App implements ControllerActionsInterface,ControllerToolCardActionsInterface {

    private boolean animationEnable;

    private ViewActions viewActions;

    private ViewToolCardActions viewToolCardActions;
}
