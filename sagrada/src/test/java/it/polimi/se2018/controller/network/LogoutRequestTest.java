package it.polimi.se2018.controller.network;

import org.junit.Test;

public class LogoutRequestTest {

    @Test(expected = NullPointerException.class)
    public void testClient() {
        LogoutRequest uut=new LogoutRequest();
        uut.clientHandle(null,null);
    }
}