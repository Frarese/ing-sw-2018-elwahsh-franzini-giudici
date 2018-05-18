package it.polimi.se2018.controller.network;

import org.junit.Test;

import static org.junit.Assert.*;

public class AbsReqTest {
    @Test
    public void testMethods() {
        AbsReq a=new AbsReqTester();
        a.serverHandle(null,null);
        a.clientHandle(null,null);
        assertFalse(a.checkValid());
    }

    private class AbsReqTester extends AbsReq{}
}