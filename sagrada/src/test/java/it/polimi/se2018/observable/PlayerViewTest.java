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

    /**
     * Mock class to observe PlayerView
     */
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
    private IntColorPair[][] playerTemplate;
    private IntColorPair[][] playerGrid;
    private boolean placementRights;
    private boolean cardRights;

    /**
     * Creates a player and observes its PlayerView
     */
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

    /**
     * Tests getter method of player's name
     */
    @Test
    public void testGetPlayerName() {
        assertEquals(playerName, playerView.getPlayerName());
    }

    /**
     * Tests setter method of player's name with correct update
     */
    @Test
    public void testSetPlayerName() {
        playerName = "Test2";
        playerView.setPlayerName(playerName);
    }

    /**
     * Tests getter method of player's id
     */
    @Test
    public void testGetPlayerID() {
        assertEquals(playerID, playerView.getPlayerID());
    }

    /**
     * Tests setter method of player's id with correct update
     */
    @Test
    public void testSetPlayerID() {
        playerID = -1;
        playerView.setPlayerID(playerID);
    }

    /**
     * Tests getter method of player's favour points
     */
    @Test
    public void testGetPlayerFavours() {
        assertEquals(playerFavours, playerView.getPlayerFavours());
    }

    /**
     * Tests setter method of player's favour points with correct update
     */
    @Test
    public void testSetPlayerFavours() {
        playerFavours = 10;
        playerView.setPlayerFavours(playerFavours);
    }

    /**
     * Tests getter method of player's template
     */
    @Test
    public void testGetPlayerTemplate() {
        assertArrayEquals(playerTemplate, playerView.getPlayerTemplate());
    }

    /**
     * Tests setter method of player's template with correct update
     */
    @Test
    public void testSetPlayerTemplate() {
        playerTemplate = null;
        playerView.setPlayerTemplate(null);
    }

    /**
     * Tests getter method of player's grid
     */
    @Test
    public void testGetPlayerGrid() {
        assertArrayEquals(playerGrid, playerView.getPlayerGrid());
    }

    /**
     * Tests setter method of player's grid with correct update
     */
    @Test
    public void testSetPlayerGrid() {
        playerGrid = new IntColorPair[5][5];
        playerView.setPlayerGrid(playerGrid);
    }

    /**
     * Tests getter method of player's placement rights
     */
    @Test
    public void testIsPlacementRights() {
        assertEquals(placementRights, playerView.isPlacementRights());
    }

    /**
     * Tests setter method of player's placement rights with correct update
     */
    @Test
    public void testSetPlacementRights() {
        placementRights = false;
        playerView.setPlacementRights(placementRights);
    }

    /**
     * Tests getter method of player's card rights
     */
    @Test
    public void testIsCardRights() {
        assertEquals(cardRights, playerView.isCardRights());
    }

    /**
     * Tests setter method of player's card rights with correct update
     */
    @Test
    public void testSetCardRights() {
        cardRights = true;
        playerView.setCardRights(cardRights);
    }
}