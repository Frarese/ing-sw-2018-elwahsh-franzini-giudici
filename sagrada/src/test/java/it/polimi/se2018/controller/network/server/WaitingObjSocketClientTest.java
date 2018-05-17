package it.polimi.se2018.controller.network.server;

import it.polimi.se2018.util.SafeSocket;
import org.junit.Test;

import static org.junit.Assert.*;

public class WaitingObjSocketClientTest {

    @Test
    public void testInit() throws Exception{
        SafeSocket s=new SafeSocket(0);
        WaitingObjSocketClient uut=new WaitingObjSocketClient(s,"pw",false);
        assertEquals(s,uut.reqS);
        assertEquals("pw",uut.psw);
        assertFalse(uut.isRecovery);
    }
}