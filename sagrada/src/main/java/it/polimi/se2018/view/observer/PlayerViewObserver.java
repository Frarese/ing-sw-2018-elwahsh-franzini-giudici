package it.polimi.se2018.view.observer;

import it.polimi.se2018.observable.PlayerView;
import it.polimi.se2018.view.app.App;

import java.util.Observable;

/**
 * PlayerView observable class
 *
 * @author Mathyas Giudici
 */

public class PlayerViewObserver extends ModelObserver {

    public PlayerViewObserver(App app) {
        super(app);
    }

    @Override
    public void update(Observable o, Object arg) {
        PlayerView playerView = (PlayerView) o;
        PlayerState playerState = new PlayerState(playerView.getPlayerName(), playerView.getPlayerFavours(),
                playerView.getPlayerTemplate(), playerView.getPlayerGrid(), playerView.isPlacementRights(), playerView.isCardRights());

        //Search in player view and update
        for (int i = 0; i < this.app.getPlayers().size(); i++) {
            if (this.app.getPlayers().get(i).getPlayerName().equals(playerView.getPlayerName())) {
                this.app.getPlayers().remove(i);
                this.app.getPlayers().add(i, playerState);
            }
        }

        if (playerView.getPlayerName().equals(app.getOwnerPlayerName())) {
            app.getGridViewCreator().setGridPattern(playerView.getPlayerTemplate());
            app.getGridViewCreator().setGrid(playerView.getPlayerGrid());
        }
    }
}
