package it.polimi.se2018.model.cards;

import it.polimi.se2018.model.Player;
import org.junit.Test;

import static org.junit.Assert.*;

public class ToolCardTest {
    private ToolTest test = new ToolTest();

    private class ToolTest extends ToolCard
    {

        private ToolTest()
        {
            this.id = 99;
            this.text = "Test";
        }

        @Override
        public boolean isUsable(Player player, boolean firstTurn) {
            return false;
        }
    }

    @Test
    public void testUsing()
    {
        Player player = new Player("Duncan", 420);
        player.setFavourPoints(3);
        assertFalse(test.isUsed());
        test.burnFavourPoints(player);
        test.updateUsed();
        assertTrue(test.isUsed());
        test.burnFavourPoints(player);
        assertEquals(0,player.getFavourPoints());
        assertFalse(test.isUsable(player,true));
    }

}