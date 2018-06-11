package it.polimi.se2018.controller.network.server;

import org.junit.Test;

import java.net.InetAddress;

public class ServerCLITest {

    @Test(expected = IllegalArgumentException.class)
    public void testFailInit() throws Exception{
        new ServerCLI(-1,-1,-1,InetAddress.getLocalHost());
    }
}