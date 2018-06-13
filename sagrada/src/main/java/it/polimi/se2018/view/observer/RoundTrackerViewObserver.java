package it.polimi.se2018.view.observer;

import it.polimi.se2018.observable.RoundTrackerView;
import it.polimi.se2018.view.app.App;

import java.util.Observable;
import java.util.Observer;

/**
 * RoundTrackerView observable class
 *
 * @author Mathyas Giudici
 */

public class RoundTrackerViewObserver extends ModelObserver implements Observer {

    public RoundTrackerViewObserver(App app) {
        super(app);
    }

    @Override
    public void update(Observable o, Object arg) {
        RoundTrackerView roundTrackerView = (RoundTrackerView) o;
        this.app.getRoundTrackerViewCreator().setRound(roundTrackerView.getRound());
        this.app.getRoundTrackerViewCreator().setRoundTracker(roundTrackerView.getRoundTracker());
    }
}
