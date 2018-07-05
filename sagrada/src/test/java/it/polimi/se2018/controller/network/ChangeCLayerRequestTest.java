package it.polimi.se2018.controller.network;

import it.polimi.se2018.controller.network.client.Comm;
import it.polimi.se2018.controller.network.client.CommUtilizer;
import it.polimi.se2018.controller.network.server.Client;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tester class for the ChangeLayerRequest
 * @author Francesco Franzini
 */
public class ChangeCLayerRequestTest {
    private ChangeCLayerRequest uut;
    private Field portField;
    private Client c;
    private boolean purged;
    private boolean called;

    /**
     * Prepares the flags to be used and the object to test
     * @throws Exception if an error occurs
     */
    @Before
    public void setUp() throws Exception {
        purged=false;
        called=false;
        uut=new ChangeCLayerRequest(true,1,2);
        portField=uut.getClass().getDeclaredField("reqPort");
        portField.setAccessible(true);
        c=new ClientMock();
    }

    /**
     * Tests that invalid arguments are correctly detected
     */
    @Test(expected = IllegalArgumentException.class)
    public void testInit() {
        uut=new ChangeCLayerRequest(false,0,0);
    }

    /**
     * Checks that an invalid request is not processed
     * @throws IllegalAccessException if an error occurs
     */
    @Test
    public void testFail() throws IllegalAccessException {
        portField.set(uut,0);
        uut.serverVisit(c.getServerVisitor());
        uut.clientHandle(null,null);
        assertFalse(purged);
    }

    /**
     * Checks that the visitor is correctly called
     */
    @Test
    public void testServer(){
        uut.serverVisit(c.getServerVisitor());
        assertTrue(called);
    }

    /**
     * Tests that the client method is correctly called
     */
    @Test
    public void testClient(){
        uut.clientHandle(new CommMock(),null);
        assertTrue(purged);
        assertTrue(called);
    }

    /**
     * Mock client used to intercept method calls
     */
    private class ClientMock extends Client {

        ClientMock() {
            super("test", null);
        }

        @Override
        public void zombiefy(boolean notifyMatchPlayers, ChangeCLayerRequest cReq) {
            called=true;
        }
    }

    /**
     * Mock Comm used to intercept method calls
     */
    private class CommMock extends Comm {
        @Override
        public String login(String host, int requestPort, int objectPort, String usn, String pw, boolean newUser, boolean useRMI, CommUtilizer utilizer) {
            called=true;
            return null;
        }

        @Override
        public void purgeComm() {
            purged=true;
        }
    }
}