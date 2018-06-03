package it.polimi.se2018.controller.network.server;

import org.junit.Test;

import java.net.InetAddress;
import java.rmi.RemoteException;

import static org.junit.Assert.*;

public class RMIServerIntImplTest {

    @Test(expected = IllegalArgumentException.class)
    public void testInit() throws RemoteException {
        new RMIServerIntImpl(null);
    }

    @Test
    public void testEquals() throws Exception {
        RMIServerMock s=new RMIServerMock(InetAddress.getLocalHost());
        RMIServerIntImpl uut1=new RMIServerIntImpl(s);
        RMIServerIntImpl uut2=new RMIServerIntImpl(s);
        assertEquals(uut1,uut1);
        assertNotEquals(uut1,uut2);

        assertNotEquals(uut1.hashCode(),uut2.hashCode());

        assertNull(uut1.login("test","pw",false,false));
    }

    private class RMIServerMock extends RMIServer{


        RMIServerMock(InetAddress ip) {
            super(null, -1, "testname", ip);
        }

        @Override
        RMISession login(String usn, String pw, boolean isRecover, boolean register) {
            return null;
        }
    }
}