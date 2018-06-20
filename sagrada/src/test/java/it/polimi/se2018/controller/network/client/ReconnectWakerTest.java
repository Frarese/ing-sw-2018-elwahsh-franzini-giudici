package it.polimi.se2018.controller.network.client;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

/**
 * Tester class for the reconnect timer
 * @author Francesco Franzini
 */
public class ReconnectWakerTest {
    private Method runM;
    private ReconnectWaker uut;
    private boolean fail;
    private boolean purged;

    /**
     * Initializes the flags used and the object to be tested
     * @throws Exception if an error occurs
     */
    @Before
    public void testSetUp() throws Exception{
        fail=false;
        purged=false;
        uut=new ReconnectWaker(new TestComm());
        runM=ReconnectWaker.class.getDeclaredMethod("runTask");
        runM.setAccessible(true);
    }

    /**
     * Tests if the {@code isRunning} method works correctly
     */
    @Test
    public void testInit(){
        assertFalse(uut.isRunning());
        uut.reschedule(1000,5);
        assertTrue(uut.isRunning());
    }

    /**
     * Tests if a successful reconnection interrupts the timer
     */
    @Test
    public void testSuccess() throws Exception{
        uut.reschedule(1000,2);
        runM.invoke(uut);
        assertFalse(uut.isRunning());
        assertFalse(purged);
    }

    /**
     * Asserts if failed reconnection attempts lead to a correct purging of the Comm
     * @throws Exception if an error occurs
     */
    @Test
    public void testFail() throws Exception{
        uut.reschedule(1000,2);
        fail=true;

        runM.invoke(uut);
        assertTrue(uut.isRunning());
        assertFalse(purged);

        runM.invoke(uut);
        assertFalse(uut.isRunning());
        assertTrue(purged);

    }

    /**
     * Mock Comm used to intercept the call to {@code tryRecover}
     */
    private class TestComm extends Comm{
        @Override
        public boolean tryRecover(boolean purgeOnFail){
            if(fail){
                purged=purgeOnFail;
                return false;
            }
            return true;
        }
    }



}