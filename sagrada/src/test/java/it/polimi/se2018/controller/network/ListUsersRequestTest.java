package it.polimi.se2018.controller.network;

import it.polimi.se2018.util.ScoreEntry;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ListUsersRequestTest {
    ListUsersRequest uut;
    List<ScoreEntry> list;
    @Before
    public void setUp() {
        uut=new ListUsersRequest();
        list=new ArrayList<>();
    }

    @Test
    public void testInit() {
        assertTrue(uut.checkValid());
        assertNull(uut.getList());
    }

    @Test
    public void testSet() {
        list=new ArrayList<>();
        uut.setList(list);
        assertEquals(list,uut.getList());
    }

    @Test(expected = NullPointerException.class)
    public void testClientHandle() {
        uut.setList(list);
        uut.clientHandle(null,null);
    }

    @Test(expected = NullPointerException.class)
    public void serverClientHandle() {
        uut.setList(list);
        uut.serverHandle(null,null);
    }
}