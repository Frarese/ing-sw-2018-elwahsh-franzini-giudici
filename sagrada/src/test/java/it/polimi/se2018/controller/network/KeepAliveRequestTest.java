package it.polimi.se2018.controller.network;

import it.polimi.se2018.controller.network.server.Client;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

/**
 * Tester class for the KeepAliveRequest
 * @author Francesco Franzini
 */
public class KeepAliveRequestTest {
    private KeepAliveRequest uut;
    private Field bounceField;
    private Client c;

    /**
     * Prepares the flags and the object to be tested
     * @throws Exception if an error occurs
     */
    @Before
    public void setUp() throws Exception {
        uut=new KeepAliveRequest();
        bounceField=KeepAliveRequest.class.getDeclaredField("bounced");
        bounceField.setAccessible(true);
        c=new Client("test",null);
    }

    /**
     * Tests that the object is correctly initialized
     * @throws Exception if an error occurs
     */
    @Test
    public void testInit() throws Exception {
        assertTrue(uut.checkValid());
        assertEquals(false,bounceField.get(uut));
    }

    /**
     * Tests that the server method is called correctly
     * @throws Exception if an error occurs
     */
    @Test
    public void testServerBounce() throws Exception {
        try{
            uut.serverVisit(c.getServerVisitor());
        }catch (NullPointerException ex){
            assertEquals(true,bounceField.get(uut));
        }
        uut.serverVisit(c.getServerVisitor());
    }

    /**
     * Tests that the client method is called correctly
     * @throws Exception if an error occurs
     */
    @Test
    public void testClientBounce() throws Exception {
        try{
            uut.clientHandle(null,null);
        }catch (NullPointerException ex){
            assertEquals(true,bounceField.get(uut));
        }
        uut.clientHandle(null,null);
    }
}