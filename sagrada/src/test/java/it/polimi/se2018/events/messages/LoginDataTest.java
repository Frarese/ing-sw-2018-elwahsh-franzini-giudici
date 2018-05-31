package it.polimi.se2018.events.messages;


import org.junit.Test;

import static org.junit.Assert.*;

public class LoginDataTest {

    private final LoginData test = new LoginData("Lollo","password",false,"127.0.0.0",false, 220,300);

    @Test
    public void testGetters()
    {
       assertEquals("Lollo",test.getName());
       assertEquals("password",test.getPass());
       assertFalse(test.isNewUser());
       assertEquals("127.0.0.0",test.getHost());
       assertFalse(test.isRMI());
       assertEquals(220,test.getObjectPort());
       assertEquals(300,test.getRequestPort());
    }

}