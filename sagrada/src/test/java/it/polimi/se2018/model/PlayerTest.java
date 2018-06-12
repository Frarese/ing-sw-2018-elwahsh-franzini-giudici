package it.polimi.se2018.model;

import it.polimi.se2018.model.cards.PatternCard;
import it.polimi.se2018.model.cards.PrivateObjectiveCard;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class PlayerTest {

    private Player test;

    @Before
    public void initTest()
    {
        test = new Player("Gideon",40);
    }

    @Test
    public void testGetters()
    {
      assertEquals("Gideon",test.getName());
      assertNotNull(test.getGrid());
      assertEquals(40,test.getId());
    }

    @Test
    public void testSetters()
    {
        test.setPattern(new PatternCard("resources" + File.separator +"patterncard01.xml").getFrontSide());
        assertNotNull(test.getPattern());
        test.initFavourPoints();
        assertEquals(5,test.getFavourPoints());
        test.setFavourPoints(2);
        assertEquals(2,test.getFavourPoints());
        test.setPrivateObjective(new PrivateObjectiveCard(ColorModel.RED));
        assertEquals(0,test.getPrivateObjective().score(test));
    }

    @Test
    public void testRights()
    {
        assertTrue(test.canPlaceOnThisTurn(true));
        assertTrue(test.canPlaceOnThisTurn(false));
        assertTrue(test.canUseCardOnThisTurn(false));
        assertTrue(test.canUseCardOnThisTurn(true));
        test.setCardRights(true,true);
        test.setCardRights(false,true);
        test.setPlacementRights(true,true);
        test.setPlacementRights(false,true);
        assertTrue(test.canPlaceOnThisTurn(true));
        assertTrue(test.canPlaceOnThisTurn(false));
        assertTrue(test.canUseCardOnThisTurn(false));
        assertTrue(test.canUseCardOnThisTurn(true));
    }

}