package it.polimi.se2018.controller.network.server;

import org.junit.Test;

import static org.junit.Assert.*;

public class LoginResponsesEnumTest {
    @Test
    public void testValues() {
        assertEquals("Login ok",LoginResponsesEnum.LOGIN_OK.msg);
    }
}