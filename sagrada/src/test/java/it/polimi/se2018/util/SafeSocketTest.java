package it.polimi.se2018.util;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Queue;

import static org.junit.Assert.*;

public class SafeSocketTest {
    private SafeSocket uut;
    private Field sF;
    private Field queueF;
    private boolean connected;

    @Before
    public void testSetUp() throws Exception {
        sF=SafeSocket.class.getDeclaredField("s");
        sF.setAccessible(true);
        connected=true;

        queueF=SafeSocket.class.getDeclaredField("inQueue");
        queueF.setAccessible(true);
    }

    @Test
    public void testACKDgram() {
        SafeSocketDatagram d=new SafeSocketDatagram("test");
        assertEquals(SafeSocket.hashObj("test"),d.id);
        assertEquals("test",d.payload);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArg() throws Exception{
        Socket s=new Socket();
        s.close();
        uut=new SafeSocket(s,10000);
    }

    @Test
    public void testEmptyInit()throws Exception{
        uut=new SafeSocket(10000);
        uut.setTimeout(10);
        uut.close(true);
        assertEquals(10,uut.getTimeout());
        assertNull(uut.getLocalSocketAddress());
    }

    @Test(expected = IOException.class)
    public void testFailInit()throws Exception{
        Socket s=new SocketMock();
        uut=new SafeSocket(s,10000);
    }

    @Test
    public void testConnect() throws Exception{
        Socket s=new SocketMock();
        uut=new SafeSocket(10000);
        sF.set(uut,s);
        connected=false;
        assertFalse(uut.connect("localhost",20));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testReceive() throws Exception{
        uut=new SafeSocket(10000);
        Queue<Serializable> q=(Queue<Serializable>)queueF.get(uut);
        q.add("test");
        assertEquals("test",uut.receive());
    }

    private class SocketMock extends Socket{

        SocketMock() {
        }

        @Override
        public InputStream getInputStream() throws IOException {
            throw new IOException("Failed as requested");
        }

        @Override
        public OutputStream getOutputStream() throws IOException {
            throw new IOException("Failed as requested");
        }



        @Override
        public synchronized void close() throws IOException {
            throw new IOException("Failed as requested");
        }

        @Override
        public boolean isClosed() {
            return false;
        }

        @Override
        public boolean isConnected() {
            return connected;
        }

        @Override
        public void connect(SocketAddress endpoint,int timeout) {

        }
    }
}