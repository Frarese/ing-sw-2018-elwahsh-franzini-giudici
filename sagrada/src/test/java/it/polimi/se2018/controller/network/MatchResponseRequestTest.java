package it.polimi.se2018.controller.network;

import it.polimi.se2018.controller.network.server.*;
import it.polimi.se2018.util.MatchIdentifier;
import org.junit.Before;
import org.junit.Test;


import java.net.InetAddress;

/**
 * Tester class for the MatchResponseRequest
 * @author Francesco Franzini
 */
public class MatchResponseRequestTest {
    private MatchResponseRequest uut;
    private Client c;

    /**
     * Prepares the objects to be used
     * @throws Exception if an error occurs
     */
    @Before
    public void setUp() throws Exception{
        ServerMain s = new ServerMain(0, 0, 0, "", InetAddress.getLocalHost(), null);
        c=new Client("test", s);
    }

    /**
     * Tests that illegal arguments are correctly detected
     */
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidInit(){
        uut=new MatchResponseRequest(false,null);
    }

    /**
     * Tests the server method
     */
    @Test
    public void testServerHandleInvalid(){
        MatchIdentifier mId=new MatchIdentifier("us1","us2",null,null);
        uut=new MatchResponseRequest(false,mId);

        uut.clientHandle(null,null);
        uut.serverVisit(c.getServerVisitor());
    }

}