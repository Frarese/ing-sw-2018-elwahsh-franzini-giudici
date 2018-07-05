package it.polimi.se2018.events.messages;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.model.cards.PrivateObjectiveCard;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test for the PrivateObjectiveStatus class
 * @author Alì El wahsh
 */
public class PrivateObjectiveStatusTest {

    /**
     * Test for the class getters
     */
    @Test
    public void testGetters(){
        PrivateObjectiveCard p=new PrivateObjectiveCard(ColorModel.RED);

        PrivateObjectiveStatus uut=new PrivateObjectiveStatus(p);
        assertEquals(p.getId(),uut.getCardId());
    }

}