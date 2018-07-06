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
import java.util.LinkedList;
import java.util.Queue;

import static org.junit.Assert.*;

/**
 * Tester class for the SafeSocket
 * @author Francesco Franzini
 */
public class SafeSocketTest {
    private SafeSocket uut;
    private Field sF;
    private Field queueF;
    private boolean connected;
    private boolean failStream;

    /**
     * Prepares the flags to be used
     * @throws Exception if an error occurs
     */
    @Before
    public void testSetUp() throws Exception {
        sF=SafeSocket.class.getDeclaredField("s");
        sF.setAccessible(true);
        connected=true;
        failStream=true;
        queueF=SafeSocket.class.getDeclaredField("inQueue");
        queueF.setAccessible(true);
    }

    /**
     * Tests the ACKDgram
     */
    @Test
    public void testACKDgram() {
        SafeSocketDatagram d=new SafeSocketDatagram("test");
        assertEquals(SafeSocket.hashObj("test"),d.id);
        assertEquals(SafeSocket.hashObj("test").longValue(),
        new SafeSocketACK(SafeSocket.hashObj("test")).id);
        assertEquals("test",d.payload);
    }

    /**
     * Tests that illegal arguments are correctly detected
     * @throws Exception if an error occurs
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArg() throws Exception{
        Socket s=new Socket();
        s.close();
        uut=new SafeSocket(s,10000);
    }

    /**
     * Tests the setter method and the basic initialization
     * @throws Exception if an error occurs
     */
    @Test
    public void testEmptyInit()throws Exception{
        uut=new SafeSocket(10000);
        uut.setTimeout(10);
        uut.close(true);
        assertEquals(10,uut.getTimeout());
        assertNull(uut.getLocalSocketAddress());
    }

    /**
     * Tests that invalid sockets are correctly detected
     * @throws Exception if an error occurs
     */
    @Test(expected = IOException.class)
    public void testFailInit()throws Exception{
        Socket s=new SocketMock();
        uut=new SafeSocket(s,10000);
    }

    /**
     * Tests a connection
     * @throws Exception if an error occurs
     */
    @Test
    public void testConnect() throws Exception{
        Socket s=new SocketMock();
        uut=new SafeSocket(10000);
        sF.set(uut,s);
        connected=false;
        assertFalse(uut.connect("localhost",20));
    }


    /**
     * Tests the {@code receive} method
     * @throws Exception if an error occurs
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testReceive() throws Exception{
        uut=new SafeSocket(10000);
        Queue<Serializable> q=(Queue<Serializable>)queueF.get(uut);
        q.add("test");
        assertEquals("test",uut.receive());
    }

    /**
     * Tests that the {@code send} method fails when an invalid socket is used
     * @throws Exception if an error occurs
     */
    @Test
    public void testStart()throws Exception{
        failStream=false;
        uut=new SafeSocket(new SocketMock(),100);

        assertFalse(uut.send("test"));
    }

    /**
     * Mock socket used to emulate the different errors that can come from a socket without using the network
     */
    private class SocketMock extends Socket{
        final InputStream in;
        final OutputStream out;
        final Queue<Integer> queue;
        SocketMock() {
            queue=new LinkedList<>();
            out=new OutputStream() {
                @Override
                public void write(int b) {
                    queue.add(b);
                }
            };

            in=new InputStream() {
                @Override
                public int read() {
                    Integer out;
                    while(true){
                        if((out=queue.poll())!=null){
                            break;
                        }
                    }
                    return out;
                }
            };
        }

        @Override
        public InputStream getInputStream() throws IOException {

            if(failStream){
                throw new IOException("Failed as requested");
            }
            return in;

        }

        @Override
        public OutputStream getOutputStream() throws IOException {
            if(failStream){
                throw new IOException("Failed as requested");
            }
            return out;
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