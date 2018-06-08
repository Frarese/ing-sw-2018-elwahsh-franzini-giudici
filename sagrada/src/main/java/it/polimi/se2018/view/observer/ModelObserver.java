package it.polimi.se2018.view.observer;

import it.polimi.se2018.view.app.App;

/**
 * Abstract observable for model view classes
 *
 * @author Mathyas Giudici
 */

abstract class ModelObserver {

    final App app;

    ModelObserver(App app) {
        this.app = app;
    }
}
