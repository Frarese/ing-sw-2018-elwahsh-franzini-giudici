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
        uut=new RMIServer(s,-1,"test",InetAddress.getLocalHost());
        response=uut.login("test","pw",true);
        assertEquals(LoginResponsesEnum.USER_ALREADY_EXISTS,response.getLoginOutput());

        response=uut.login("test2","pw",true);
        assertEquals(LoginResponsesEnum.LOGIN_OK,response.getLoginOutput());

    }

    @Test
    public void testRecover() throws Exception{

        uut=new RMIServer(s,-1,"test",InetAddress.getLocalHost());
        response=uut.login("test","pw",false);
        assertEquals(LoginResponsesEnum.LOGIN_OK,response.getLoginOutput());

        assertEquals(response.hashCode(),response.hashCode());

        uut.login("test","pw",false);
        response=uut.login("test","pw",false);
        assertEquals(LoginResponsesEnum.USER_ALREADY_LOGGED,response.getLoginOutput());

        s.getClient("test").zombiefy(false,null);

        response=uut.login("test","pw2",false);
        assertEquals(LoginResponsesEnum.WRONG_CREDENTIALS,response.getLoginOutput());

        response=uut.login("test","pw",false);
        assertEquals(LoginResponsesEnum.LOGIN_OK,response.getLoginOutput());
    }

    @Test
    public void testNormal() throws Exception{
        uut=new RMIServer(s,-1,"test",InetAddress.getLocalHost());
        response=uut.login("test","pw2",false);
        assertEquals(LoginResponsesEnum.WRONG_CREDENTIALS,response.getLoginOutput());

        response=uut.login("test","pw",false);
        assertEquals(LoginResponsesEnum.LOGIN_OK,response.getLoginOutput());

        response=uut.login("test","pw",false);
        assertEquals(LoginResponsesEnum.USER_ALREADY_LOGGED,response.getLoginOutput());

        response=uut.login("test2","pw2",false);
        assertEquals(LoginResponsesEnum.USER_NOT_EXISTING,response.getLoginOutput());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidPort() throws Exception{
        uut=new RMIServer(s,-1,"test",InetAddress.getLocalHost());
        uut.connect();
    }
}