package it.polimi.se2018.controller.network.server;

import it.polimi.se2018.util.SafeSocket;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.ServerSocket;


import static org.junit.Assert.*;

/**
 * Tester class for the SocketServer class
 * @author Francesco Franzini
 */
public class SocketServerTest {
    private SocketServer uut;
    private ServerMock s;
    private SocketMock sM;
    private boolean failSocketCreation;

    /**
     * Prepares the objects to be used
     * @throws Exception if an error occurs
     */
    @Before
    public void setUp() throws Exception {
        s=new ServerMock();
        sM=new SocketMock();
        failSocketCreation=false;
        uut=new SocketServer(s,-1,-1);

    }

    /**
     * Tests that illegal arguments are correctly detected
     * @throws Exception if an error occurs
     */
    @Test(expected = IllegalArgumentException.class)
    public void testFailArgs() throws Exception {
        uut=new SocketServer(null,-1,0);
        uut.connect();
    }

    /**
     * Tests that connection fails towards invalid locations
     * @throws Exception if an error occurs
     */
    @Test(expected = IOException.class)
    public void testFailInit() throws Exception {
        uut=new SocketServer(null,1,1);
        uut.connect();
    }

    /**
     * Tests the {@code checkAndWrap} method
     * @throws Exception if an error occurs
     */
    @Test
    public void testCheckAndWrap() throws Exception{
        Method m=SocketServer.class.getDeclaredMethod("checkAndWrap", SafeSocket.class);
        m.setAccessible(true);

        assertNull(m.invoke(uut,sM));
        sM.invalidClass=false;
        assertNotNull(m.invoke(uut,sM));
        sM.invalidArgs=true;
        assertNull(m.invoke(uut,sM));
        sM.retNull=true;
        assertNull(m.invoke(uut,sM));
        uut.close();
    }

    /**
     * Tests the login method when failed
     * @throws Exception if an error occurs
     */
    @Test
    public void testFailLogin() throws Exception{

        Method loginM=SocketServer.class.getDeclaredMethod("listenLogin", SafeSocket.class);
        loginM.setAccessible(true);

        sM.invalidClass=false;
        loginM.invoke(uut,sM);
        assertTrue(sM.closed);

        s.logCheckResult=false;
        loginM.invoke(uut,sM);
        assertTrue(sM.closed);
    }

    /**
     * Tests the login method when successful
     * @throws Exception if an error occurs
     */
    @Test
    public void testOkLogin() throws Exception{

        Method loginM=SocketServer.class.getDeclaredMethod("listenLogin", SafeSocket.class);
        loginM.setAccessible(true);
        sM.invalidClass=false;
        s.logCheckResult=true;
        s.addCheckResult=true;

        loginM.invoke(uut,sM);
        assertFalse(sM.closed);
    }

    /**
     * Tests the socket completion when failed
     * @throws Exception if an error occurs
     */
    @Test
    public void testFailCompletion() throws Exception{

        Method completionM=SocketServer.class.getDeclaredMethod("listenCompletion", SafeSocket.class);
        completionM.setAccessible(true);

        sM.invalidClass=false;
        completionM.invoke(uut,sM);
        assertTrue(sM.closed);

        sM=new SocketMock();
        sM.invalidClass=false;
        testOkLogin();
        failSocketCreation=true;
        completionM.invoke(uut,sM);
        assertTrue(sM.closed);
    }

    /**
     * Tests the socket completion when successful
     * @throws Exception if an error occurs
     */
    @Test
    public void testOkCompletion() throws Exception{
        testOkLogin();

        Method completionM=SocketServer.class.getDeclaredMethod("listenCompletion", SafeSocket.class);
        completionM.setAccessible(true);

        sM.invalidClass=false;
        completionM.invoke(uut,sM);
        assertFalse(sM.closed);
    }

    /**
     * Tests the send method
     * @throws Exception if an error occurs
     */
    @Test
    public void testSocClientCommObj() throws Exception{
        SocClientComm socComm=new SocClientComm(null,new SocketMock(),null);
        assertTrue(socComm.sendObj("test"));
    }

    /**
     * Tests the loop call method
     * @throws Exception if an error occurs
     */
    @Test
    public void testLoopCall() throws Exception{
        Method loopM=SocketServer.class.getDeclaredMethod("loopCall", SafeSocket.class, boolean.class);
        loopM.setAccessible(true);
        sM.retNull=true;
        loopM.invoke(uut,sM,false);
        loopM.invoke(uut,sM,true);
    }

    /**
     * Tests the inner class
     * @throws Exception if an error occurs
     */
    @Test
    public void testInner() throws Exception{
        Field serverF=SocketServer.class.getDeclaredField("reqSSoc");
        try {
            uut.testInner(false);
            fail();
        }catch (NullPointerException e){
            //ok
        }
        serverF.setAccessible(true);
        ServerSocket serverSocket=new ServerSocket();
        serverSocket.close();
        serverF.set(uut,serverSocket);
        uut.testInner(true);
    }

    /**
     * Mock SafeSocket used to intercept method calls
     */
    private class SocketMock extends SafeSocket{
        boolean invalidClass=true;
        boolean invalidArgs=false;
        boolean retNull=false;
        boolean closed=false;
        SocketMock() throws IOException {
            super(10);
        }

        @Override
        public Serializable receive() {
            if(retNull)return null;
            SocketLoginRequest sL=new SocketLoginRequest("test","test",false);

            try {
                Field f = SocketLoginRequest.class.getDeclaredField("password");
                f.setAccessible(true);
                if(invalidArgs)f.set(sL,null);
            } catch (Exception e) {
                return null;
            }
            return (invalidClass)?"test":sL;
        }

        @Override
        public void close(boolean force) {
            closed=true;
        }

        @Override
        public boolean send(Serializable obj) {
            return true;
        }
    }

    /**
     * Mock server used to intercept method calls
     */
    private class ServerMock extends ServerMain{
        boolean logCheckResult=false;
        boolean addCheckResult=false;
        ServerMock() throws IOException {
            super(-1,-1,-1,"",null,null);
        }

        @Override
        boolean isUserLogged(String usn) {
            return logCheckResult;
        }

        @Override
        boolean isPwCorrect(String usn, String pw) {
            return true;
        }

        @Override
        boolean addClient(Client client) {
            return addCheckResult;
        }

        @Override
        boolean existsUsn(String usn) {
            return true;
        }

        @Override
        public Client getClient(String usn) {
            return new ClientMock();
        }
    }

    /**
     * Mock client used to intercept method calls
     */
    private class ClientMock extends Client{

        ClientMock(){
            super("test",s);
        }

        @Override
        boolean createSocketComm(SafeSocket reqSoc, SafeSocket objSoc) {
            return !failSocketCreation;
        }
    }
}