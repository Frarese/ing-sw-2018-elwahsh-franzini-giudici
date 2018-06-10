package it.polimi.se2018.events.messages;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.model.dice.Die;
import it.polimi.se2018.model.dice.RoundTracker;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class RoundTrackStatusTest {


    @Test
    public void testGetters(){

        RoundTracker rT=new RoundTracker();
        List<Die> list=new ArrayList<>();
        list.add(new Die(ColorModel.RED));
        rT.addAll(list);
        RoundTrackStatus uut=new RoundTrackStatus(rT);

        uut.getDie(100,0);
        assertNotNull(uut.getDie(0,0));

        assertNotNull(uut.getRound(100));
        assertNotNull(uut.getRound(0));
        assertNotNull(uut.getDice()[0][0]);
        assertEquals(2,uut.round());
    }
}