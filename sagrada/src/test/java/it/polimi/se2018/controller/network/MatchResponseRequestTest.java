package it.polimi.se2018.controller.network;

import it.polimi.se2018.controller.network.server.*;
import it.polimi.se2018.util.MatchIdentifier;
import org.junit.Before;
import org.junit.Test;


import java.net.InetAddress;

public class MatchResponseRequestTest {
    private MatchResponseRequest uut;
    private Client c;

    @Before
    public void setUp() throws Exception{
        ServerMain s = new ServerMain(0, 0, 0, "", InetAddress.getLocalHost(), null);
        c=new Client("test", s);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidInit(){
        uut=new MatchResponseRequest(false,null);
    }

    @Test
    public void testServerHandleInvalid(){
        MatchIdentifier mId=new MatchIdentifier("us1","us2",null,null);
        uut=new MatchResponseRequest(false,mId);

        uut.clientHandle(null,null);
        uut.serverVisit(c.getServerVisitor());
    }


}