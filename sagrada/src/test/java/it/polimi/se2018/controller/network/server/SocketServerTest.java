package it.polimi.se2018.controller.network.server;

import it.polimi.se2018.util.SafeSocket;
import org.junit.Test;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class SocketServerTest {
    private SocketServer uut;

    @Test(expected = IllegalArgumentException.class)
    public void testFailArgs() throws IOException {
        uut=new SocketServer(null,-1,0);
    }

    @Test(expected = IOException.class)
    public void testFailInit() throws IOException {
        uut=new SocketServer(null,1,1);
    }

    @Test
    public void testCheckAndWrap() throws Exception{
        uut=new SocketServer(null,10006,10007);
        Method m=SocketServer.class.getDeclaredMethod("checkAndWrap", SafeSocket.class);
        m.setAccessible(true);
        SocketMock s=new SocketMock();
        assertNull(m.invoke(uut,s));
        s.invalidClass=false;
        assertNotNull(m.invoke(uut,s));
        s.invalidArgs=true;
        assertNull(m.invoke(uut,s));
        s.retNull=true;
        assertNull(m.invoke(uut,s));
        uut.close();
    }

    private class SocketMock extends SafeSocket{
        boolean invalidClass=true;
        boolean invalidArgs=false;
        boolean retNull=false;
        SocketMock() throws IOException {
            super(10);
        }

        @Override
        public Serializable receive() {
            if(retNull)return null;
            SocketLoginRequest sL=new SocketLoginRequest("test","test",true,false);

            try {
                Field f = SocketLoginRequest.class.getDeclaredField("isNewUser");
                f.setAccessible(true);
                if(invalidArgs)f.set(sL,true);
            } catch (Exception e) {
                return null;
            }
            return (invalidClass)?"test":sL;
        }

        @Override
        public void close(boolean force) {

        }

        @Override
        public boolean send(Serializable obj) {
            return true;
        }
    }
}