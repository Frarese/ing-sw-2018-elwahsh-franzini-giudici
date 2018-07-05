package it.polimi.se2018.events.messages;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.model.dice.Die;
import it.polimi.se2018.model.dice.Reserve;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test for the ReserveStatus class
 * @author Alì El wahsh
 */
public class ReserveStatusTest {

    /**
     * Test for the class getters
     */
    @Test
    public void testGetter(){
        Reserve r=new Reserve();
        r.add(new Die(ColorModel.RED));
        ReserveStatus uut=new ReserveStatus(r);

        assertNotNull(uut.getDice());
    }
}