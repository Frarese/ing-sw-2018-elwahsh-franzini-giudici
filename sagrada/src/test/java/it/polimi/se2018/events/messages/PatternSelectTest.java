package it.polimi.se2018.events.messages;

import it.polimi.se2018.model.IntColorPair;
import it.polimi.se2018.model.Pattern;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test for the PatternSelect class
 * @author Alì El wahsh
 */
public class PatternSelectTest {

    /**
     * Test for the class getters
     */
    @Test
    public void testGetters(){
        IntColorPair[][] array=new IntColorPair[4][5];
        Pattern p=new Pattern(array,"test",5);

        PatternSelect uut=new PatternSelect(p);

        assertEquals("test",uut.getName());
        assertEquals(5,uut.getFavourPoints());
        assertNotEquals(array,uut.getPattern());
    }
}