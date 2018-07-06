package it.polimi.se2018.controller.network.server;

import org.junit.Test;

import java.net.InetAddress;

/**
 * Tester class for the ServerCLI
 * @author Francesco Franzini
 */
public class ServerCLITest {

    /**
     * Tests that illegal arguments are correctly detected
     * @throws Exception if an error occurs
     */
    @Test(expected = IllegalArgumentException.class)
    public void testFailInit() throws Exception{
        new ServerCLI(-1,-1,-1,InetAddress.getLocalHost());
    }

    /**
     * Tests that illegal arguments are correctly detected
     * @throws Exception if an error occurs
     */
    @Test(expected = IllegalArgumentException.class)
    public void testFailMain() throws Exception{
        String[] args=new String[5];
        ServerCLI.main(args);
    }

    /**
     * Tests the {@code main} method
     * @throws Exception if an error occurs
     */
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