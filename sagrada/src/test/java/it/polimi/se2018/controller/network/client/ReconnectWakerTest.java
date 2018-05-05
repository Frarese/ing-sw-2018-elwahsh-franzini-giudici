package it.polimi.se2018.controller.network.client;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ReconnectWakerTest {
    private volatile int attempts;
    private int threshold;
    private Comm testComm;
    private boolean purged;
    @Before
    public void setUp() {
        testComm=new TestComm();
        attempts=0;
        purged=false;
    }

    @After
    public void tearDown() {
        attempts=0;
    }

    @Test(timeout =2000)
    public void testNoSuccess() throws Exception{

        ReconnectWaker wk=new ReconnectWaker(testComm);
        threshold=6;
        wk.reschedule(100,5);
        while(attempts<5);
        assertEquals(5,attempts);
        assertTrue(purged);
    }

    @Test(timeout =2000)
    public void testSuccess() throws Exception{

        ReconnectWaker wk=new ReconnectWaker(testComm);
        threshold=3;
        wk.reschedule(100,5);
        while(attempts<3);
        assertEquals(3,attempts);
        assertTrue(!purged);
    }

    private class TestComm extends Comm{
        @Override
        public boolean tryRecover(boolean purgeOnFail){
            attempts++;
            if(attempts<threshold){
                if(purgeOnFail)purged=true;
                return false;
            }else return true;
        }
    }



}