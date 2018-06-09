package it.polimi.se2018.observable;

import it.polimi.se2018.model.IntColorPair;
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
                assertEquals(placementRights, ((PlayerView) arg).isFirstPlacementRights());
                assertEquals(cardRights, ((PlayerView) arg).isFirstCardRights());
            }
        }
    }

    private PlayerView playerView;

    private String playerName;
    private int playerID;
    private int playerFavours;
    private IntColorPair[][] playerTemplate;
    private IntColorPair[][] playerGrid;
    private boolean placementRights;
    private boolean cardRights;

    @Before
    public void testInit() {

        playerName = "Test";
        playerID = 1;
        playerFavours = 0;
        playerTemplate = new IntColorPair[1][1];
        playerGrid = new IntColorPair[2][2];
        placementRights = true;
        cardRights = true;

        playerView = new PlayerView(playerName, playerID);
        ObjectObserver objectObserver = new ObjectObserver();
        playerView.addObserver(objectObserver);
        playerView.setPlayerGrid(playerGrid);
        playerView.setPlayerTemplate(playerTemplate);


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
        playerGrid = new IntColorPair[5][5];
        playerView.setPlayerGrid(playerGrid);
    }

    @Test
    public void testIsPlacementRights() {
        assertEquals(placementRights, playerView.isFirstPlacementRights());
    }

    @Test
    public void testSetPlacementRights() {
        placementRights = false;
        playerView.setFirstPlacementRights(placementRights);
    }

    @Test
    public void testIsCardRights() {
        assertEquals(cardRights, playerView.isFirstCardRights());
    }

    @Test
    public void testSetCardRights() {
        cardRights = true;
        playerView.setFirstCardRights(cardRights);
    }
}