package it.polimi.se2018.controller.network.client;

import it.polimi.se2018.controller.network.AbsReq;
import it.polimi.se2018.controller.network.KeepAliveRequest;
import it.polimi.se2018.controller.network.server.LoginResponsesEnum;
import it.polimi.se2018.controller.network.server.RMISession;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.rmi.RemoteException;

import static org.junit.Assert.*;

/**
 * Tester class for the client RMI layer
 * @author Francesco Franzini
 */
public class RMICommLayerTest {
    private RMICommLayer uut;
    private boolean called;
    private RMISessionTest c;

    /**
     * Initializes the flags used and the object to test
     * @throws Exception if an error occurs
     */
    @Before
    public void testSetUp() throws Exception {
        called=false;
        c=new RMISessionTest();
        uut=new RMICommLayer(new Comm());
        Field f=uut.getClass().getDeclaredField("sessionObj");
        f.setAccessible(true);
        f.set(uut,c);
    }

    /**
     * Tests that the comm layer correctly checks if input is available
     */
    @Test
    public void testGetters() {
        assertEquals(KeepAliveRequest.class,uut.getReq().getClass());
        assertEquals("test",uut.getObj());

        assertTrue(uut.hasObj());
        assertTrue(uut.hasReq());
    }

    /**
     * Tests that the comm layer correctly ends the connection
     */
    @Test
    public void testEnd() {
        assertTrue(uut.endCon());
    }

    /**
     * Tests that the comm layer correctly sends out objects and requests
     */
    @Test
    public void testSenders(){
        uut.sendOutObj("test");
        assertTrue(called);
        called=false;
        uut.sendOutReq(new KeepAliveRequest());
        assertTrue(called);
    }

    /**
     * Tests that the comm layer handles all remote exceptions internally
     */
    @Test
    public void testRemoteEx(){
        c.fail=true;
        assertNull(uut.getReq());
        assertNull(uut.getObj());
        assertFalse(uut.hasObj());
        assertFalse(uut.hasReq());

        uut.sendOutObj("test");
        assertFalse(called);
        called=false;
        uut.sendOutReq(new KeepAliveRequest());
        assertFalse(called);
    }

    /**
     * Tests that it is not possible to connect if the session object is not null
     */
    @Test
    public void testDoubleCon(){
        assertEquals("Already logged",uut.establishCon(null,0,0,null,null,false));
    }

    /**
     * Mock class to intercept calls to the remote session object
     */
    private class RMISessionTest implements RMISession{
        boolean fail=false;
        @Override
        public boolean hasReq() throws RemoteException {
            if(fail)throw new RemoteException("test");
            return true;
        }

        @Override
        public AbsReq getReq() throws RemoteException {
            if(fail)throw new RemoteException("test");
            return new KeepAliveRequest();
        }

        @Override
        public boolean sendReq(AbsReq obj) throws RemoteException {
            if(fail)throw new RemoteException("test");
            called=true;
            return true;
        }

        @Override
        public boolean hasObj() throws RemoteException {
            if(fail)throw new RemoteException("test");
            return true;
        }

        @Override
        public Serializable getObj() throws RemoteException {
            if(fail)throw new RemoteException("test");
            return "test";
        }

        @Override
        public boolean sendObj(Serializable obj) throws RemoteException {
            if(fail)throw new RemoteException("test");
            called=true;
            return true;
        }

        @Override
        public boolean isTerminated() throws RemoteException {
            if(fail)throw new RemoteException("test");
            return false;
        }

        @Override
        public LoginResponsesEnum getLoginOutput() throws RemoteException {
            if(fail)throw new RemoteException("test");
            return null;
        }
    }
}