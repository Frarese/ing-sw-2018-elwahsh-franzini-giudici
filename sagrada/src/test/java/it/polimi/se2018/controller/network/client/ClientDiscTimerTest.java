package it.polimi.se2018.controller.network.client;

import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import static org.junit.Assert.*;

/**
 * Tester class for the Client disconnection timer
 * @author Francesco Franzini
 */
public class ClientDiscTimerTest {
    private volatile AtomicInteger called;
    private ClientDiscTimer uut;
    private volatile boolean fail;
    private AtomicBoolean failed;

    /**
     * Prepares the flags to be used and instantiates the tested object
     */
    @Before
    public void testSetUp() {
        called=new AtomicInteger(0);
        uut=new ClientDiscTimer(new CommTest());
        fail=false;
        failed=new AtomicBoolean(false);
    }

    /**
     * Tests if the timer checks for the last timestamp on the client
     */
    @Test(timeout=1000)
    public void testInit() {
        long timeout=50;
        uut.reschedule(timeout);
        while(!(called.get()>1));
        uut.stop();
        assertFalse(failed.get());
    }

    /**
     * Tests if the timer correctly calls the disconnection on the client
     */
    @Test(timeout=1000)
    public void testFail() {
        long timeout=1;
        fail=true;
        uut.reschedule(timeout);
        while(!failed.get());
        uut.stop();

        assertEquals(1,called.get());
    }

    /**
     * Mock class for Comm that intercepts the needed methods
     */
    private class CommTest extends Comm{
        @Override
        public void beginDisconnectedRoutines(){
            failed.set(true);
        }

        @Override
        public Instant getLastSeen(){
            called.addAndGet(1);
            if(fail){
                return Instant.EPOCH;
            }
            return Instant.now().minusNanos(100);
        }
    }
}