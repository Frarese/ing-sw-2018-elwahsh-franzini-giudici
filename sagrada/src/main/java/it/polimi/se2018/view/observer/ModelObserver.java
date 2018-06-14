package it.polimi.se2018.view.observer;

import it.polimi.se2018.view.app.App;

import java.util.Observer;

/**
 * Abstract observable for model view classes
 *
 * @author Mathyas Giudici
 */

abstract class ModelObserver implements Observer {

    final App app;

    ModelObserver(App app) {
        this.app = app;
    }
}
