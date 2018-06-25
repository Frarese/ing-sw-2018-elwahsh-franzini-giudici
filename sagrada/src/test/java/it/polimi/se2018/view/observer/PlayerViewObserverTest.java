package it.polimi.se2018.view.observer;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.model.IntColorPair;
import it.polimi.se2018.observable.PlayerView;
import it.polimi.se2018.observable.ReserveView;
import it.polimi.se2018.observable.RoundTrackerView;
import it.polimi.se2018.view.ViewActions;
import it.polimi.se2018.view.app.App;
import it.polimi.se2018.view.app.CLIApp;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests for PlayerViewObserver class
 *
 * @author Mathyas Giudici
 */

public class PlayerViewObserverTest {

    @Test
    public void testUpdate() {
        String enter = System.lineSeparator();
        String message = "y" + enter + "TestName" + enter + "test" + enter + "test" + enter + "n" + enter + "80" + enter + "80" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));
        System.setOut(new PrintStream(new ByteArrayOutputStream()));

        App app = new CLIApp(new FakeViewActions(), null);
        app.startLogin(true);

        IntColorPair[][] template = new IntColorPair[4][5];
        template[0][0] = new IntColorPair(6, ColorModel.RED);
        template[3][4] = new IntColorPair(5, ColorModel.GREEN);

        IntColorPair[][] grid = new IntColorPair[4][5];
        grid[1][1] = new IntColorPair(1, ColorModel.BLUE);
        grid[2][2] = new IntColorPair(2, ColorModel.YELLOW);

        PlayerView playerView = new PlayerView("Test", 0);
        playerView.setPlayerName("TestName");
        playerView.setPlayerID(1);
        playerView.setPlayerFavours(5);
        playerView.setPlayerTemplate(template);
        playerView.setPlayerGrid(grid);
        playerView.setPlacementRights(true);
        playerView.setCardRights(false);

        List<PlayerView> playerViews = new ArrayList<>();
        playerViews.add(playerView);

        app.animation(false);
        app.initGame(playerViews, new ReserveView(new IntColorPair[1]), new RoundTrackerView(0, new IntColorPair[1][1]));

        PlayerViewObserver playerViewObserver = new PlayerViewObserver(app);
        playerViewObserver.update(playerView, null);

        assertEquals("TestName", app.getPlayers().get(0).getPlayerName());
        assertEquals(5, app.getPlayers().get(0).getPlayerFavours());

        for (int i = 0; i < template.length; i++) {
            for (int j = 0; j < template[i].length; j++) {
                assertEquals(template[i][j], app.getPlayers().get(0).getPlayerTemplate()[i][j]);
                assertEquals(template[i][j], app.getGridViewCreator().getGridPattern()[i][j]);
            }
        }

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                assertEquals(grid[i][j], app.getPlayers().get(0).getPlayerGrid()[i][j]);
                assertEquals(grid[i][j], app.getGridViewCreator().getGrid()[i][j]);
            }
        }

        assertTrue(app.getPlayers().get(0).isPlacementRights());
        assertFalse(app.getPlayers().get(0).isCardRights());

        System.setIn(System.in);
        System.setOut(System.out);
    }

    private class FakeViewActions extends ViewActions {
        FakeViewActions() {
            super(null);
        }

        @Override
        public String login(String name, String password, boolean newUser, String host, boolean isRMI, int objectPort, int requestPort) {
            return null;
        }

        @Override
        public void askLobby() {
            assert true;
        }

        @Override
        public void endInitGame() {
            assert true;
        }
    }
}