package it.polimi.se2018.controller.network.server;

import org.junit.Test;

import java.net.InetAddress;

public class ServerCLITest {

    @Test(expected = IllegalArgumentException.class)
    public void testFailInit() throws Exception{
        new ServerCLI(-1,-1,-1,InetAddress.getLocalHost());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFailMain() throws Exception{
        String[] args=new String[5];
        ServerCLI.main(args);
    }

    @Test
    public void testMain() throws Exception{
        String[] args=new String[4];
        args[0]="-1";
        args[1]="-1";
        args[2]="-1";
        args[3]="localhost";
        ServerCLI.main(args);
    }
}