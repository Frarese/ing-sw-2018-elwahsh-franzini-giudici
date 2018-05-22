package it.polimi.se2018.controller.game;

import it.polimi.se2018.model.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class PlayersOrderTest {

    private PlayersOrder test;
    @Before
    public  void initTest() {
        Player p1 = new Player("Malcador", 300);
        Player p2 = new Player("Mortarion", 14);
        ArrayList<Player> temp = new ArrayList<>();
        temp.add(p1);
        temp.add(p2);
        test = new PlayersOrder(temp);
    }

    @Test
    public void testOrder()
    {
        Iterator<Player> temp = test.iterator();
        assertEquals("Malcador",temp.next().getName());
        assertEquals("Mortarion",temp.next().getName());
        assertEquals("Mortarion",temp.next().getName());
        assertEquals("Malcador",temp.next().getName());
        assertFalse(temp.hasNext());
    }

    @Test
    public void testNextRound()
    {
        test.prepareNextRound();
        Iterator<Player> temp = test.iterator();
        assertEquals("Mortarion",temp.next().getName());
        assertEquals("Malcador",temp.next().getName());
        assertEquals("Malcador",temp.next().getName());
        assertEquals("Mortarion",temp.next().getName());
        assertFalse(temp.hasNext());
    }
}
