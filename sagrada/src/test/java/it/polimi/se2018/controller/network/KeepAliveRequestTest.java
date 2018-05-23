package it.polimi.se2018.controller.network;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

public class KeepAliveRequestTest {
    private KeepAliveRequest uut;
    private Field bounceField;
    @Before
    public void setUp() throws NoSuchFieldException {
        uut=new KeepAliveRequest();
        bounceField=KeepAliveRequest.class.getDeclaredField("bounced");
        bounceField.setAccessible(true);

    }

    @Test
    public void testInit() throws IllegalAccessException {
        assertTrue(uut.checkValid());
        assertEquals(false,bounceField.get(uut));
    }

    @Test
    public void testServerBounce() throws IllegalAccessException {
        try{
            uut.serverHandle(null,null);
        }catch (NullPointerException ex){
            assertEquals(true,bounceField.get(uut));
        }
        uut.serverHandle(null,null);
    }

    @Test
    public void testClientBounce() throws IllegalAccessException {
        try{
            uut.clientHandle(null,null);
        }catch (NullPointerException ex){
            assertEquals(true,bounceField.get(uut));
        }
        uut.clientHandle(null,null);
    }
}