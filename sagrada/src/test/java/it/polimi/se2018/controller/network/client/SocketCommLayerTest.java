package it.polimi.se2018.controller.network.client;

import it.polimi.se2018.controller.network.KeepAliveRequest;
import it.polimi.se2018.controller.network.server.LoginResponsesEnum;
import it.polimi.se2018.util.SafeSocket;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class SocketCommLayerTest {
    private SocketCommLayer uut;
    private boolean fail;

    private boolean interrupt;
    private Method connectM;

    @Before
    public void setUp() throws Exception {
        SocTest s1 = new SocTest(false);
        SocTest s2 = new SocTest(false);
        uut=new SocketCommLayer(new Comm());

        fail=false;
        interrupt=false;

        Field soc1=uut.getClass().getDeclaredField("objSoc");
        soc1.setAccessible(true);
        soc1.set(uut, s1);

        Field soc2=uut.getClass().getDeclaredField("reqSoc");
        soc2.setAccessible(true);
        soc2.set(uut, s2);

        connectM=SocketCommLayer.class.getDeclaredMethod("connect", SafeSocket.class, SafeSocket.class, boolean.class, String.class, String.class, boolean.class);
        connectM.setAccessible(true);
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
    public void testFailConnection(){
        uut=new SocketCommLayer(new Comm());
        fail=true;
        String result=uut.establishCon("localhost",0,0,false," "," ",false);
        assertNotNull(result);
    }

    @Test
    public void testOkConnection() throws Exception{
        uut=new SocketCommLayer(new Comm());

        String result=(String)connectM.invoke(uut,new SocTest(false),new SocTest(false),false,"usn","pw",false);
        assertNull(result);
    }

    @Test
    public void testFailRequestSocket() throws Exception{
        uut=new SocketCommLayer(new Comm());
        String result=(String)connectM.invoke(uut,new SocTest(true),new SocTest(false),false,"usn","pw",false);
        assertNotNull(result);
    }

    @Test
    public void testFailObjectSocket() throws Exception{
        uut=new SocketCommLayer(new Comm());
        String result=(String)connectM.invoke(uut,new SocTest(false),new SocTest(true),false,"usn","pw",false);
        assertNotNull(result);
    }

    @Test
    public void testInterruptedConnection() throws Exception{
        uut=new SocketCommLayer(new Comm());
        interrupt=true;
        Thread t= new Thread(() -> {
            String result= null;
            try {
                result = (String)connectM.invoke(uut,new SocTest(false),new SocTest(false),false,"usn","pw",false);
            } catch (Exception e) {
                fail();
            }
            assertNotNull(result);
        });
        t.start();
        t.join();

    }

    @Test
    public void testDoubleCon(){
        assertEquals("Already logged",uut.establishCon(null,0,0,false,null,null,false));
    }

    private class SocTest extends SafeSocket{
        private final boolean failRec;
        SocTest(boolean failRec) throws IOException {
            super(0);
            this.failRec=failRec;
        }

        @Override
        public void close(boolean force){
        }

        @Override
        public boolean send(Serializable obj){
            return true;
        }

        @Override
        public boolean connect(String host, int port) {
            return !fail;
        }

        @Override
        public Serializable receive() throws InterruptedException{
            if(failRec)return LoginResponsesEnum.WRONG_CREDENTIALS;
            if(interrupt)throw new InterruptedException();
            return LoginResponsesEnum.LOGIN_OK;
        }
    }
}