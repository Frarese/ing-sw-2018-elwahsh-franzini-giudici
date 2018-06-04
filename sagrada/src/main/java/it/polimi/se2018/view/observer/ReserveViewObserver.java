package it.polimi.se2018.view.observer;

import it.polimi.se2018.observable.ReserveView;
import it.polimi.se2018.view.app.App;

import java.util.Observable;
import java.util.Observer;

/**
 * ReserveView observable class
 *
 * @author Mathyas Giudici
 */

public class ReserveViewObserver extends ModelObserver implements Observer {

    public ReserveViewObserver(App app) {
        super(app);
    }

    @Override
    public void update(Observable o, Object arg) {
        ReserveView reserveView = (ReserveView) arg;
        this.app.getReserveViewCreator().setReserve(reserveView.getReserve());
    }
}
