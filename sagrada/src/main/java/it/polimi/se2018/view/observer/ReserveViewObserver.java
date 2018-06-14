package it.polimi.se2018.view.observer;

import it.polimi.se2018.observable.ReserveView;
import it.polimi.se2018.view.app.App;

import java.util.Observable;

/**
 * ReserveView observable class
 *
 * @author Mathyas Giudici
 */

public class ReserveViewObserver extends ModelObserver {

    public ReserveViewObserver(App app) {
        super(app);
    }

    @Override
    public void update(Observable o, Object arg) {
        ReserveView reserveView = (ReserveView) o;
        this.app.getReserveViewCreator().setReserve(reserveView.getReserve());
    }
}
