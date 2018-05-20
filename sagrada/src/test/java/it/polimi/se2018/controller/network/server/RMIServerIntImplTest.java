package it.polimi.se2018.controller.network.server;

import org.junit.Test;

import java.rmi.RemoteException;

import static org.junit.Assert.*;

public class RMIServerIntImplTest {

    @Test(expected = IllegalArgumentException.class)
    public void testInit() throws RemoteException {
        new RMIServerIntImpl(null);
    }

    @Test
    public void testEquals() throws RemoteException {
        RMIServer s=new RMIServer(null,10001,"testname");
        RMIServerIntImpl uut1=new RMIServerIntImpl(s);
        RMIServerIntImpl uut2=new RMIServerIntImpl(s);
        assertEquals(uut1,uut1);
        assertNotEquals(uut1,uut2);
    }
}