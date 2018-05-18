package it.polimi.se2018.util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.Assert.*;

public class SafeSocketTest {
    private SafeSocket clientSS;
    private SafeSocket serverSS;
    private ServerSocket servSocket;
    private static final int port = 9999;
    private static final long timeout = 2000;

    @Before
    public void setUp() throws Exception {
        servSocket = new ServerSocket(port);
        clientSS = new SafeSocket(timeout);

    }

    @After
    public void tearDown() throws Exception {
        servSocket.close();
        servSocket = null;
        clientSS.close(false);
        clientSS = null;
        if(serverSS!=null){
            serverSS.close(true);
            serverSS =null;
        }

    }

    @Test
    public void testConnection() throws Exception {
        Thread t=makeTestThread(5);

        t.start();
        Socket s=servSocket.accept();
        serverSS=new SafeSocket(s,timeout);
        for(int j=0;j<5;j++){
            Integer toSend=j;
            assertTrue(serverSS.send(toSend));
            assertEquals(toSend, serverSS.receive());
        }
        t.join();
    }

    @Test
    public void testSetters() {
        clientSS.setTimeout(timeout);
        assertEquals(timeout,clientSS.getTimeout());
    }

    @Test
    public void testFailConnection() {
        clientSS.setTimeout(200);
        assertFalse(clientSS.connect("localhost",9998));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testClosed() throws Exception {
        Thread t=makeTestThread(0);

        t.start();
        Socket s=servSocket.accept();
        s.getInputStream().close();
        serverSS=new SafeSocket(s,timeout);
        serverSS.send(5);

        t.join();
    }

    @Test(expected = IOException.class)
    public void testFailStreams() throws Exception {
        Thread t=makeTestThread(0);

        t.start();
        Socket s=servSocket.accept();
        TestSocket wrapper=new TestSocket(s,true,true,false);
        serverSS=new SafeSocket(wrapper,timeout);

        t.join();
    }

    //Helper methods and classes
    private Thread makeTestThread(int tries){
        return new Thread(() -> {
            clientSS.connect("localhost",port);

            for(int i=0;i<tries;i++){
                try {
                    Integer toCSend= i;
                    assertTrue(clientSS.send(toCSend));
                    assertEquals(toCSend, clientSS.receive());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private class TestSocket extends Socket{
        private boolean failInStrGet;
        private boolean failOutStrGet;
        private boolean failClose;
        private Socket s;

        TestSocket(Socket s,boolean failInStrGet, boolean failOutStrGet, boolean failClose) {
            this.failInStrGet = failInStrGet;
            this.failOutStrGet = failOutStrGet;
            this.failClose = failClose;
            this.s=s;
        }

        @Override
        public InputStream getInputStream() throws IOException {

            if(failInStrGet){

                throw new IOException("Failed as requested");
            }
            return s.getInputStream();
        }

        @Override
        public OutputStream getOutputStream() throws IOException {
            if(failOutStrGet){
                throw new IOException("Failed as requested");
            }
            return s.getOutputStream();
        }



        @Override
        public synchronized void close() throws IOException {
            if(failClose){
                throw new IOException("Failed as requested");
            }
            s.close();
        }

        @Override
        public boolean isClosed() {
            return s.isClosed();
        }

        @Override
        public boolean isConnected() {
            return s.isConnected();
        }
    }
}