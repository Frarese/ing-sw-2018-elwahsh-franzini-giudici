package it.polimi.se2018.controller.network;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserReconnectedRequestTest {
    private UserReconnectedRequest uut;


    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgument() {
        uut=new UserReconnectedRequest(null);
    }

    @Test(expected = NullPointerException.class)
    public void testInit() {
        uut=new UserReconnectedRequest("test");
        assertTrue(uut.checkValid());
        uut.clientHandle(null,null);
    }
}