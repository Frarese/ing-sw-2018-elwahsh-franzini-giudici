package it.polimi.se2018.events.messages;

import it.polimi.se2018.model.cards.ActiveObjectives;
import it.polimi.se2018.model.cards.ActiveTools;
import it.polimi.se2018.model.cards.ToolCard;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

/**
 * Test for the CardInfo class
 * @author Alì El wahsh
 */
public class CardInfoTest {

    /**
     * Test for the class getters
     */
    @Test
    public void testGetters() throws Exception{
        ActiveTools aT=new ActiveTools();
        ActiveObjectives aO=new ActiveObjectives();
        Field usedField=ToolCard.class.getDeclaredField("used");
        usedField.setAccessible(true);
        usedField.set(aT.getTool(0),true);

        CardInfo uut=new CardInfo(aT,aO);

        int[] tools=uut.getTools();
        int[] obj=uut.getObjectives();
        int[] tCost=uut.getToolCost();

        for(int i=0;i<3;i++){
            assertEquals(aT.getTool(i).getId(),tools[i]);
            assertEquals(aT.getTool(i).isUsed()?2:1,tCost[i]);
            assertEquals(aO.getObjective(i).getId(),obj[i]);
        }
    }
}