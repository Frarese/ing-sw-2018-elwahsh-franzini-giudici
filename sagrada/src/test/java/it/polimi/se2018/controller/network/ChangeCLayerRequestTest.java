package it.polimi.se2018.controller.network;

import it.polimi.se2018.controller.network.client.Comm;
import it.polimi.se2018.controller.network.client.CommUtilizer;
import it.polimi.se2018.controller.network.server.Client;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.assertTrue;

public class ChangeCLayerRequestTest {
    private ChangeCLayerRequest uut;
    private Field portField;
    private Client c;
    private boolean purged;
    private boolean called;

    @Before
    public void setUp() throws Exception {
        purged=false;
        called=false;
        uut=new ChangeCLayerRequest(true,1,2);
        portField=uut.getClass().getDeclaredField("reqPort");
        portField.setAccessible(true);
        c=new ClientMock();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInit() {
        uut=new ChangeCLayerRequest(false,0,0);
    }

    @Test
    public void testFail() throws IllegalAccessException {
        portField.set(uut,0);
        uut.serverHandle(null,null);
        uut.clientHandle(null,null);
    }

    @Test
    public void testServer(){
        uut.serverHandle(c,null);
        assertTrue(called);
    }

    @Test
    public void testClient(){
        uut.clientHandle(new CommMock(),null);
        assertTrue(purged);
        assertTrue(called);
    }

    private class ClientMock extends Client {

        ClientMock() {
            super("test", null);
        }

        @Override
        public void zombiefy(boolean notifyMatchPlayers, ChangeCLayerRequest cReq) {
            called=true;
        }
    }

    private class CommMock extends Comm {
        @Override
        public String login(String host, int requestPort, int objectPort, boolean isRecovery, String usn, String pw, boolean newUser, boolean useRMI, CommUtilizer utilizer) {
            called=true;
            return null;
        }

        @Override
        public void purgeComm() {
            purged=true;
        }
    }
}