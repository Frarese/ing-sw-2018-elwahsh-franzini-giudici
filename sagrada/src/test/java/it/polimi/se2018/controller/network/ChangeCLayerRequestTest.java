package it.polimi.se2018.controller.network;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

public class ChangeCLayerRequestTest {
    private ChangeCLayerRequest uut;
    private Field portField;

    @Before
    public void setUp() throws NoSuchFieldException {
        uut=new ChangeCLayerRequest(true,1,2);
        portField=uut.getClass().getDeclaredField("reqPort");
        portField.setAccessible(true);
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

    @Test(expected = NullPointerException.class)
    public void testServer(){
        uut.serverHandle(null,null);
    }

    @Test(expected = NullPointerException.class)
    public void testClient(){
        uut.clientHandle(null,null);
    }
}