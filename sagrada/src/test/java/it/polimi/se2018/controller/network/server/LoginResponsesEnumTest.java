package it.polimi.se2018.controller.network.server;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tester class for the LoginResponseEnum class
 * @author Francesco Franzini
 */
public class LoginResponsesEnumTest {

    /**
     * Tests that the value is correct
     */
    @Test
    public void testValues() {
        assertEquals("Login ok",LoginResponsesEnum.LOGIN_OK.msg);
    }
}