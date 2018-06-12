package it.polimi.se2018.events.messages;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.model.IntColorPair;
import it.polimi.se2018.model.Pattern;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.dice.Die;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerStatusTest {

    @Test
    public void testGetters(){
        IntColorPair[][] array=new IntColorPair[4][5];
        Pattern pat=new Pattern(array,"testp",5);
        Player p=new Player("test",0);
        p.setPattern(pat);
        p.setFavourPoints(5);
        p.getGrid().setDie(0,0,new Die(ColorModel.RED));
        PlayerStatus uut=new PlayerStatus(p,true);

        assertNotEquals(p.getGrid(),uut.getGrid());
        assertNotEquals(array,uut.getPattern());
        assertEquals(5,uut.getFavourPoints());
        assertEquals(0,uut.getId());
        assertEquals("test",uut.getName());

        assertTrue(uut.isCardRights());
        assertTrue(uut.isPlacementRights());

    }

}