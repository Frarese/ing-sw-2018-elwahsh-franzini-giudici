package it.polimi.se2018.controller.network.client;

import it.polimi.se2018.controller.network.AbsReq;
import it.polimi.se2018.controller.network.KeepAliveRequest;
import it.polimi.se2018.controller.network.server.LoginResponsesEnum;
import it.polimi.se2018.controller.network.server.RMISession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.rmi.RemoteException;

import static org.junit.Assert.*;

public class RMICommLayerTest {
    private RMICommLayer uut;
    boolean called;
    RMISessionTest c;
    @Before
    public void setUp() throws Exception {
        called=false;
        c=new RMISessionTest();
        uut=new RMICommLayer(new Comm());
        Field f=uut.getClass().getDeclaredField("sessionObj");
        f.setAccessible(true);
        f.set(uut,c);
    }

    @After
    public void tearDown() {
        called=false;
    }

    @Test
    public void testGetters() {
        assertEquals(KeepAliveRequest.class,uut.getReq().getClass());
        assertEquals("test",uut.getObj());

        assertTrue(uut.hasObj());
        assertTrue(uut.hasReq());
    }

    @Test
    public void testEnd() {
        assertTrue(uut.endCon());
    }

    @Test
    public void testSenders(){
        uut.sendOutObj("test");
        assertTrue(called);
        called=false;
        uut.sendOutReq(new KeepAliveRequest());
        assertTrue(called);
    }

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

    @Test
    public void testDoubleCon(){
        assertEquals("Already logged",uut.establishCon(null,0,0,false,null,null,false));
    }
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