package it.polimi.se2018.controller.network;

import it.polimi.se2018.controller.network.server.ServerMain;
import it.polimi.se2018.util.MatchIdentifier;
import org.junit.Before;
import org.junit.Test;
import java.net.InetAddress;

public class MatchResponseRequestTest {
    private MatchResponseRequest uut;
    private ServerMain s;

    @Before
    public void setUp() throws Exception{
        s=new ServerMain(0,0,false,0,"a",InetAddress.getLocalHost(),null);

    }
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidInit(){
        uut=new MatchResponseRequest(false,null);
    }

    @Test
    public void testServerHandleInvalid(){
        MatchIdentifier mId=new MatchIdentifier("us1","us2",null,null);
        uut=new MatchResponseRequest(false,mId);
        uut.serverHandle(null,s);

    }


}