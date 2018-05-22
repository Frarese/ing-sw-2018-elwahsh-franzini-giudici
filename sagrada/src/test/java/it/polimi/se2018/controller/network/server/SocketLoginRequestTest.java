package it.polimi.se2018.controller.network.server;

import org.junit.Test;

import static org.junit.Assert.*;

public class SocketLoginRequestTest {
    @Test
    public void testInit() {
        SocketLoginRequest uut=new SocketLoginRequest("usn","pw",false,true);
        assertEquals("usn",uut.username);
        assertEquals("usn",uut.username);
        assertFalse(uut.isRecovery);
        assertTrue(uut.isNewUser);
        assertTrue(uut.isValid());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFail() {
        SocketLoginRequest uut=new SocketLoginRequest("usn","pw",true,true);
        assertNull(uut);
    }
}