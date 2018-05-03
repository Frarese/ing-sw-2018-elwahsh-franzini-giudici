package it.polimi.se2018.util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

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
        serverSS.close(false);
        serverSS =null;

    }

    @Test
    public void testConnection() throws Exception {
        Thread t= new Thread(() -> {
            clientSS.connect("localhost",port);

            for(int i=0;i<5;i++){
                try {
                    Integer toCSend= i;
                    assertTrue(clientSS.send(toCSend));
                    assertEquals(toCSend, clientSS.receive());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

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

}