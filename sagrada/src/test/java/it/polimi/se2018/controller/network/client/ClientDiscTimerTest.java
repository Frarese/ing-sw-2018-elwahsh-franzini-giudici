package it.polimi.se2018.controller.network.client;

import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import static org.junit.Assert.*;


public class ClientDiscTimerTest {
    private volatile AtomicInteger called;
    private ClientDiscTimer uut;
    private volatile boolean fail;
    private volatile AtomicBoolean failed;
    @Before
    public void setUp() {
        called=new AtomicInteger(0);
        uut=new ClientDiscTimer(new CommTest());
        fail=false;
        failed=new AtomicBoolean(false);
    }

    @Test(timeout=1000)
    public void testInit() {
        long timeout=1;
        uut.reschedule(timeout);
        while(!(called.get()>1));
        uut.stop();
        assertFalse(failed.get());
    }

    @Test(timeout=1000)
    public void testFail() {
        long timeout=1;
        fail=true;
        uut.reschedule(timeout);
        while(!failed.get());
        uut.stop();

        assertEquals(1,called.get());
    }

    private class CommTest extends Comm{
        @Override
        public boolean beginDisconnectedRoutines(){
            failed.set(true);
            return false;
        }

        @Override
        public Instant getLastSeen(){
            called.addAndGet(1);
            if(fail){
                return Instant.EPOCH;
            }
            Instant i=Instant.now().minusNanos(100);
            return i;
        }
    }
}