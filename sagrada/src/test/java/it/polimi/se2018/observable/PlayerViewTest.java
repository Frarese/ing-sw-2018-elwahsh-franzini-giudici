package it.polimi.se2018.observable;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.util.Pair;
import org.junit.Before;
import org.junit.Test;

import java.util.Observable;
import java.util.Observer;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Tests for PlayerView class
 *
 * @author Mathyas Giudici
 */

public class PlayerViewTest {

    private class ObjectObserver implements Observer {

        @Override
        public void update(Observable o, Object arg) {
            if (arg instanceof PlayerView) {
                assertEquals(playerName, ((PlayerView) arg).getPlayerName());
                assertEquals(playerID, ((PlayerView) arg).getPlayerID());
                assertEquals(playerFavours, ((PlayerView) arg).getPlayerFavours());
                assertArrayEquals(playerTemplate, ((PlayerView) arg).getPlayerTemplate());
                assertArrayEquals(playerGrid, ((PlayerView) arg).getPlayerGrid());
                assertEquals(placementRights, ((PlayerView) arg).isPlacementRights());
                assertEquals(cardRights, ((PlayerView) arg).isCardRights());
            }
        }
    }

    private PlayerView playerView;

    private String playerName;
    private int playerID;
    private int playerFavours;
    private Pair<Integer, ColorModel>[][] playerTemplate;
    private Pair<Integer, ColorModel>[][] playerGrid;
    private boolean placementRights;
    private boolean cardRights;

    @Before
    public void testInit() {

        playerName = "Test";
        playerID = 1;
        playerFavours = 0;
        playerTemplate = new Pair[1][1];
        playerGrid = new Pair[2][2];
        placementRights = true;
        cardRights = false;

        playerView = new PlayerView(playerName, playerID, playerFavours, playerTemplate, playerGrid, true, false);
        ObjectObserver objectObserver = new ObjectObserver();
        playerView.addObserver(objectObserver);

    }

    @Test
    public void testGetPlayerName() {
        assertEquals(playerName, playerView.getPlayerName());
    }

    @Test
    public void testSetPlayerName() {
        playerName = "Test2";
        playerView.setPlayerName(playerName);
    }

    @Test
    public void testGetPlayerID() {
        assertEquals(playerID, playerView.getPlayerID());
    }

    @Test
    public void testSetPlayerID() {
        playerID = -1;
        playerView.setPlayerID(playerID);
    }

    @Test
    public void testGetPlayerFavours() {
        assertEquals(playerFavours, playerView.getPlayerFavours());
    }

    @Test
    public void testSetPlayerFavours() {
        playerFavours = 10;
        playerView.setPlayerFavours(playerFavours);
    }

    @Test
    public void testGetPlayerTemplate() {
        assertArrayEquals(playerTemplate, playerView.getPlayerTemplate());
    }

    @Test
    public void testSetPlayerTemplate() {
        playerTemplate = null;
        playerView.setPlayerTemplate(null);
    }

    @Test
    public void testGetPlayerGrid() {
        assertArrayEquals(playerGrid, playerView.getPlayerGrid());
    }

    @Test
    public void testSetPlayerGrid() {
        playerGrid = new Pair[5][5];
        playerView.setPlayerGrid(playerGrid);
    }

    @Test
    public void testIsPlacementRights() {
        assertEquals(placementRights, playerView.isPlacementRights());
    }

    @Test
    public void testSetPlacementRights() {
        placementRights = false;
        playerView.setPlacementRights(placementRights);
    }

    @Test
    public void testIsCardRights() {
        assertEquals(cardRights, playerView.isCardRights());
    }

    @Test
    public void testSetCardRights() {
        cardRights = true;
        playerView.setCardRights(cardRights);
    }
}