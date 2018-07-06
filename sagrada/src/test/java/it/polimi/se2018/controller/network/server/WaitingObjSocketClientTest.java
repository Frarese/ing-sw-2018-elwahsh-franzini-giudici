package it.polimi.se2018.controller.network.server;

import it.polimi.se2018.util.SafeSocket;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tester class for the WaitingObjSocketClient listener
 * @author Francesco Franzini
 */
public class WaitingObjSocketClientTest {

    /**
     * Tests that the object is correctly initialized
     * @throws Exception if an error occurs
     */
    @Test
    public void testInit() throws Exception{
        SafeSocket s=new SafeSocket(0);
        WaitingObjSocketClient uut=new WaitingObjSocketClient(s,"pw");
        assertEquals(s,uut.reqS);
        assertEquals("pw",uut.psw);
    }
}