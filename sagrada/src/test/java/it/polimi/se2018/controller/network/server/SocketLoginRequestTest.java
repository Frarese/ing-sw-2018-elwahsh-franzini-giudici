package it.polimi.se2018.controller.network.server;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tester class for the SocketLoginRequest
 * @author Francesco Franzini
 */
public class SocketLoginRequestTest {
    /**
     * Tests that the object is correctly initialized
     */
    @Test
    public void testInit() {
        SocketLoginRequest uut=new SocketLoginRequest("usn","pw",true);
        assertEquals("usn",uut.username);
        assertEquals("usn",uut.username);
        assertTrue(uut.isNewUser);
        assertTrue(uut.isValid());
    }

    /**
     * Tests that illegal arguments are correctly detected
     */
    @Test(expected = IllegalArgumentException.class)
    public void testFail() {
        SocketLoginRequest uut=new SocketLoginRequest(null,"pw",true);
        assertNull(uut);
    }
}