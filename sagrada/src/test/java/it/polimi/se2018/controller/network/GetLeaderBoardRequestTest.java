package it.polimi.se2018.controller.network;

import it.polimi.se2018.util.ScoreEntry;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GetLeaderBoardRequestTest {
    GetLeaderBoardRequest uut;

    @Before
    public void setUp() throws Exception {
        uut=new GetLeaderBoardRequest();
    }

    @Test(expected = NullPointerException.class)
    public void testInit() {
        assertTrue(uut.checkValid());
        uut.serverHandle(null,null);
    }

    @Test
    public void testClient() throws Exception {
        uut.clientHandle(null,null);
        Field f=uut.getClass().getDeclaredField("leaderboard");
        f.setAccessible(true);
        List<ScoreEntry> list=new ArrayList<>();
        list.add(new ScoreEntry("user1",100,200));
        list.add(new ScoreEntry("user2",100,300));
        try {
            f.set(uut,list);
            uut.clientHandle(null, null);
            fail();
        } catch (NullPointerException ex){
            assertTrue(isOrderedVersion(list, (List) f.get(uut)));
        }

    }

    @SuppressWarnings("unchecked")
    private boolean isOrderedVersion(List<ScoreEntry> l,List oL){
        if(!(l.containsAll(oL)&&oL.containsAll(l)))return false;
        ScoreEntry prev=null;
        for(Object e:oL){
            if(prev!=null){
                if(((ScoreEntry)e).wins>prev.wins){
                    return false;
                }
            }
            prev=(ScoreEntry)e;
        }
        return true;
    }
}