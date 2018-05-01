package it.polimi.se2018.view.app;

import it.polimi.se2018.view.ViewActions;
import it.polimi.se2018.view.ControllerActionsInterface;
import it.polimi.se2018.view.ControllerToolCardActionsInterface;
import it.polimi.se2018.view.ViewToolCardActions;

/**
 * Abstract Class App represents the generic user interface (CLI or JavaFX)
 * @author Mathyas Giudici
 */

public abstract class App implements ControllerActionsInterface,ControllerToolCardActionsInterface {

    private boolean animationEnable;

    private ViewActions viewActions;

    private ViewToolCardActions viewToolCardActions;
}
