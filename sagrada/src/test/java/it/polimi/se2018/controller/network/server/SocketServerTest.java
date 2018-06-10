package it.polimi.se2018.controller.network.server;

import it.polimi.se2018.util.SafeSocket;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;


import static org.junit.Assert.*;

public class SocketServerTest {
    private SocketServer uut;
    private ServerMock s;
    private SocketMock sM;
    private boolean failSocketCreation;

    @Before
    public void setUp() throws Exception {
        s=new ServerMock();
        sM=new SocketMock();
        failSocketCreation=false;

    }

    @Test(expected = IllegalArgumentException.class)
    public void testFailArgs() throws IOException {
        uut=new SocketServer(null,-1,0);
        uut.connect();
    }

    @Test(expected = IOException.class)
    public void testFailInit() throws IOException {
        uut=new SocketServer(null,1,1);
        uut.connect();
    }

    @Test
    public void testCheckAndWrap() throws Exception{
        uut=new SocketServer(null,-1,-1);
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

    @Test
    public void testFailLogin() throws Exception{
        uut=new SocketServer(s,-1,-1);

        Method loginM=SocketServer.class.getDeclaredMethod("listenLogin", SafeSocket.class);
        loginM.setAccessible(true);

        sM.invalidClass=false;
        loginM.invoke(uut,sM);
        assertTrue(sM.closed);

        s.logCheckResult=false;
        sM.recovery=false;
        loginM.invoke(uut,sM);
        assertTrue(sM.closed);
    }

    @Test
    public void testOkLogin() throws Exception{
        uut=new SocketServer(s,-1,-1);

        Method loginM=SocketServer.class.getDeclaredMethod("listenLogin", SafeSocket.class);
        loginM.setAccessible(true);
        sM.invalidClass=false;
        s.logCheckResult=true;
        s.addCheckResult=true;

        loginM.invoke(uut,sM);
        assertFalse(sM.closed);
    }

    @Test
    public void testFailCompletion() throws Exception{
        uut=new SocketServer(s,-1,-1);

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

    @Test
    public void testOkCompletion() throws Exception{
        testOkLogin();

        Method completionM=SocketServer.class.getDeclaredMethod("listenCompletion", SafeSocket.class);
        completionM.setAccessible(true);

        sM.invalidClass=false;
        completionM.invoke(uut,sM);
        assertFalse(sM.closed);
    }


    @Test
    public void testSocClientCommObj() throws Exception{
        SocClientComm socComm=new SocClientComm(null,new SocketMock(),null);
        assertTrue(socComm.sendObj("test"));
    }
    private class SocketMock extends SafeSocket{
        boolean invalidClass=true;
        boolean invalidArgs=false;
        boolean retNull=false;
        boolean closed=false;
        boolean recovery=true;
        SocketMock() throws IOException {
            super(10);
        }

        @Override
        public Serializable receive() {
            if(retNull)return null;
            SocketLoginRequest sL=new SocketLoginRequest("test","test",recovery,false);

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
            closed=true;
        }

        @Override
        public boolean send(Serializable obj) {
            return true;
        }
    }

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