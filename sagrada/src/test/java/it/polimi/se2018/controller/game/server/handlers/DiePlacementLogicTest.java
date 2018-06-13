package it.polimi.se2018.controller.game.server.handlers;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.model.Pattern;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.cards.PatternCard;
import it.polimi.se2018.model.dice.Die;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class DiePlacementLogicTest {


    private final Pattern p = (new PatternCard("resources" + File.separator +"patterncard10.xml").getBackSide());
    private Player player;



    @Before
    public void initTest()
    {
        player = new Player("Gigino", 13);
        player.setPattern(p);
    }

    @Test
    public void testIsAdjacent()
    {

        player.getGrid().setDie(0,0, new DieMock(ColorModel.RED));
        assertNull(DiePlacementLogic.insertDie(player,
                0,
                1,
                new DieMock(ColorModel.BLUE,3),
                true,
                true,
                true));
        assertNotNull(DiePlacementLogic.insertDie(player,
                0,
                1,
                new DieMock(ColorModel.BLUE),
                true,
                true,
                true));
        assertNotNull(DiePlacementLogic.insertDie(player,
                2,
                0,
                new DieMock(ColorModel.BLUE),
                true,
                true,
                true));
    }

    @Test
    public void testAdjacentSameColor()
    {
        player.getGrid().setDie(0,1, new DieMock(ColorModel.RED));
        player.getGrid().setDie(1,0, new DieMock(ColorModel.RED));
        player.getGrid().setDie(1,2, new DieMock(ColorModel.VIOLET));
        player.getGrid().setDie(2,1, new DieMock(ColorModel.BLUE));
        assertNull(DiePlacementLogic.insertDie(player,
                1,
                1,
                new DieMock(ColorModel.YELLOW,2),
                true,
                true,
                true));
        assertNotNull(DiePlacementLogic.insertDie(player,
                1,
                1,
                new DieMock(ColorModel.RED),
                true,
                true,
                true));
    }

    @Test
    public void testEmptySpot()
    {
        assertNull(DiePlacementLogic.insertDie(player,
                0,
                0,
                new DieMock(ColorModel.BLUE),
                true,
                true,
                true));
        player.getGrid().setDie(0,0,new DieMock(ColorModel.RED));
        assertNotNull(DiePlacementLogic.insertDie(player,
                0,
                0,
                new DieMock(ColorModel.BLUE),
                false,
                false,
                false));
    }

    @Test
    public void testIsBorder()
    {
        assertNull(DiePlacementLogic.insertDie(player,
                0,
                0,
                new DieMock(ColorModel.BLUE),
                true,
                true,
                true));
        assertNotNull(DiePlacementLogic.insertDie(player,
                1,
                1,
                new DieMock(ColorModel.BLUE),
                true,
                true,
                true));
    }

    @Test
    public void testRightColor()
    {
        assertNull(DiePlacementLogic.insertDie(player,
                0,
                3,
                new DieMock(ColorModel.RED),
                true,
                true,
                true));
        assertNotNull(DiePlacementLogic.insertDie(player,
                0,
                3,
                new DieMock(ColorModel.BLUE),
                true,
                true,
                true));

    }

    @Test
    public void testRightValue()
    {
        Die d = new DieMock(ColorModel.RED);
        d.setFace(5);

        assertNull(DiePlacementLogic.insertDie(player,
                0,
                4,
                d,
                true,
                true,
                true));
        d.setFace(2);
        assertNotNull(DiePlacementLogic.insertDie(player,
                0,
                4,
                d,
                true,
                true,
                true));
    }
    private class DieMock extends Die{

        DieMock(ColorModel color) {
            super(color);
        }

        DieMock(ColorModel color, int value) {
            super(color);
            this.setFace(value);
        }

        @Override
        public synchronized void roll() {
            this.setFace(1);
        }
    }
}