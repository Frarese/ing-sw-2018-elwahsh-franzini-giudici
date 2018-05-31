package it.polimi.se2018.controller.network.server;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.InetAddress;

import static org.junit.Assert.*;

public class RMIServerTest {
    private RMIServer uut;
    private ServerMain s;
    private RMISession response;
    @Before
    public void setUp() throws Exception {
        File f=new File("users.csv");
        if(f.exists())assertTrue(f.delete());
        s=new ServerMain(0,0,0,"tt",InetAddress.getLocalHost(),null);

        s.createUser("test","pw");
    }

    @After
    public void tearDown(){
        uut.close();
    }

    @Test
    public void testRegister() throws Exception{
        uut=new RMIServer(s,10002,"test",InetAddress.getLocalHost());
        response=uut.login("test","pw",false,true);
        assertEquals(LoginResponsesEnum.USER_ALREADY_EXISTS,response.getLoginOutput());

        response=uut.login("test2","pw",false,true);
        assertEquals(LoginResponsesEnum.LOGIN_OK,response.getLoginOutput());

    }

    @Test
    public void testRecover() throws Exception{
        uut=new RMIServer(s,10003,"test",InetAddress.getLocalHost());
        response=uut.login("test","pw",true,false);
        assertEquals(LoginResponsesEnum.USER_NOT_LOGGED,response.getLoginOutput());

        uut.login("test","pw",false,false);
        response=uut.login("test","pw",true,false);
        assertEquals(LoginResponsesEnum.USER_ALREADY_LOGGED,response.getLoginOutput());

        response=uut.login("test","pw2",true,false);
        assertEquals(LoginResponsesEnum.WRONG_CREDENTIALS,response.getLoginOutput());
    }

    @Test
    public void testNormal() throws Exception{
        uut=new RMIServer(s,10004,"test",InetAddress.getLocalHost());
        response=uut.login("test","pw2",false,false);
        assertEquals(LoginResponsesEnum.WRONG_CREDENTIALS,response.getLoginOutput());

        response=uut.login("test","pw",false,false);
        assertEquals(LoginResponsesEnum.LOGIN_OK,response.getLoginOutput());

        response=uut.login("test","pw",false,false);
        assertEquals(LoginResponsesEnum.USER_ALREADY_LOGGED,response.getLoginOutput());

        response=uut.login("test2","pw2",false,false);
        assertEquals(LoginResponsesEnum.USER_NOT_EXISTING,response.getLoginOutput());
    }
}