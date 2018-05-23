package it.polimi.se2018.controller.network.client;

import it.polimi.se2018.controller.network.KeepAliveRequest;
import it.polimi.se2018.util.SafeSocket;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;

import static org.junit.Assert.*;

public class SocketCommLayerTest {
    private SocketCommLayer uut;

    @Before
    public void setUp() throws Exception {
        SocTest s1 = new SocTest();
        SocTest s2 = new SocTest();
        uut=new SocketCommLayer(new Comm());

        Field soc1=uut.getClass().getDeclaredField("objSoc");
        soc1.setAccessible(true);
        soc1.set(uut, s1);

        Field soc2=uut.getClass().getDeclaredField("reqSoc");
        soc2.setAccessible(true);
        soc2.set(uut, s2);
    }

    @Test
    public void testSender() {
        assertTrue(uut.sendOutObj("test"));
        assertTrue(uut.sendOutReq(new KeepAliveRequest()));
    }

    @Test
    public void testClose(){
        assertTrue(uut.close());
    }

    @Test
    public void testCleanup() throws Exception{
        uut.endCon();
        Field soc1=uut.getClass().getDeclaredField("objSoc");
        soc1.setAccessible(true);
        Field soc2=uut.getClass().getDeclaredField("reqSoc");
        soc2.setAccessible(true);
        assertNull(soc1.get(uut));
        assertNull(soc2.get(uut));
    }

    @Test
    public void testDoubleCon(){
        assertEquals("Already logged",uut.establishCon(null,0,0,false,null,null,false));
    }

    private class SocTest extends SafeSocket{
        SocTest() throws IOException {
            super(0);
        }

        @Override
        public void close(boolean force){
        }

        @Override
        public boolean send(Serializable obj){
            return true;
        }
    }
}