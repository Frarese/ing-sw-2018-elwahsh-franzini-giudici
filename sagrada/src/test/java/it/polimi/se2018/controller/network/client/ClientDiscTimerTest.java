package it.polimi.se2018.controller.network.client;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
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
    private Field continuaF;
    private Method runT;

    /**
     * Prepares the flags to be used and instantiates the tested object
     * @throws Exception if an error occurs
     */
    @Before
    public void testSetUp() throws Exception{
        called=new AtomicInteger(0);
        uut=new ClientDiscTimer(new CommTest());
        fail=false;
        failed=new AtomicBoolean(false);

        continuaF=ClientDiscTimer.class.getDeclaredField("continua");
        continuaF.setAccessible(true);

        runT=ClientDiscTimer.class.getDeclaredMethod("runTask");
        runT.setAccessible(true);
    }

    /**
     * Tests that the object has been correctly initialized
     * @throws Exception if an error occurs
     */
    @Test
    public void testInit() throws Exception{
        long timeout=50;
        uut.reschedule(timeout);
        assertTrue((Boolean)continuaF.get(uut));
        uut.stop();
    }

    /**
     * Tests that the task works correctly
     * @throws Exception if an error occurs
     */
    @Test
    public void testTask() throws Exception {
        continuaF.set(uut, Boolean.TRUE);
        runT.invoke(uut);
        assertFalse(failed.get());

        fail=true;
        runT.invoke(uut);
        assertTrue(failed.get());
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