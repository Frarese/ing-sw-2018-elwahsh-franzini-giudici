package it.polimi.se2018.controller.network.server;

import it.polimi.se2018.controller.network.AbsReq;
import it.polimi.se2018.controller.network.ChangeCLayerRequest;
import it.polimi.se2018.controller.network.KeepAliveRequest;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.*;

/**
 * Tester class for the DisconnectChecker class
 * @author Francesco Franzini
 */
public class DisconnectCheckerTest {
    private DisconnectChecker uut;
    private AtomicBoolean warned;
    private AtomicBoolean zombied;
    private AtomicBoolean purge;
    private AtomicBoolean fail;
    private boolean old;
    private Field continuaF;
    private Field warnedF;
    private Method runTask;

    /**
     * Prepares the flags and objects to be used
     * @throws Exception if an error occurs
     */
    @Before
    public void setUp() throws Exception{
        warned=new AtomicBoolean(false);
        zombied=new AtomicBoolean(false);
        purge=new AtomicBoolean(false);
        fail=new AtomicBoolean(false);

        continuaF=DisconnectChecker.class.getDeclaredField("continua");
        continuaF.setAccessible(true);

        warnedF=DisconnectChecker.class.getDeclaredField("warned");
        warnedF.setAccessible(true);

        runTask=DisconnectChecker.class.getDeclaredMethod("runTask");
        runTask.setAccessible(true);
        old=false;
    }

    /**
     * Tests the initialization and stop procedures
     * @throws Exception if an error occurs
     */
    @Test
    public void testInitAndStop() throws Exception{
        long warningTimeout=2;
        long deathTimeout=2;
        long purgeTimeout=2;

        uut=new DisconnectChecker(warningTimeout,deathTimeout,purgeTimeout,new ClientTest());
        assertEquals(Boolean.FALSE,continuaF.get(uut));

        uut.reschedule();
        assertEquals(Boolean.TRUE,continuaF.get(uut));

        uut.stop();
        assertEquals(Boolean.FALSE,continuaF.get(uut));
    }

    /**
     * Tests the {@code resetWarned} method
     * @throws Exception if an error occurs
     */
    @Test
    public void testResetWarned() throws Exception{
        uut=new DisconnectChecker(1000,1000,1000,new ClientTest());
        assertFalse(((AtomicBoolean)warnedF.get(uut)).get());
        ((AtomicBoolean)warnedF.get(uut)).set(true);
        uut.resetWarned();
        assertFalse(((AtomicBoolean)warnedF.get(uut)).get());
    }

    /**
     * Tests the {@code schedule} method
     * @throws Exception if an error occurs
     */
    @Test
    public void testSchedule() throws Exception{
        uut=new DisconnectChecker(2,2,2,new ClientTest());
        continuaF.set(uut,true);
        old=true;
        runTask.invoke(uut);
        assertTrue(warned.get());
    }

    /**
     * Tests that the client is correctly purged if the network is down
     * @throws Exception if an error occurs
     */
    @Test
    public void testNetworkFailure() throws Exception{
        uut=new DisconnectChecker(2,2,2,new ClientTest());
        continuaF.set(uut,true);
        old=true;
        runTask.invoke(uut);
        runTask.invoke(uut);
        runTask.invoke(uut);
        assertTrue(purge.get());
    }

    /**
     * Tests that the client is correctly purged if the network goes down silently
     * @throws Exception if an error occurs
     */
    @Test
    public void testSilentFailure() throws Exception{
        fail.set(true);
        uut=new DisconnectChecker(2,2,2,new ClientTest());
        continuaF.set(uut,true);
        old=true;
        runTask.invoke(uut);
        runTask.invoke(uut);
        runTask.invoke(uut);
        assertTrue(purge.get());
    }

    /**
     * Mock client used to intercept method calls
     */
    private class ClientTest extends Client{
        final Instant i;
        ClientTest(){
            super("",null);
            i=Instant.now().minusNanos(100);
        }

        @Override
        public Instant lastSeen(){
            return (old)?Instant.EPOCH:i;
        }

        @Override
        public boolean sendReq(AbsReq req){
            if(req.getClass().equals(KeepAliveRequest.class)){
                warned.set(true);
            }
            return !fail.get();
        }

        @Override
        public void zombiefy(boolean notifyPlayers, ChangeCLayerRequest c){
            zombied.set(true);
        }

        @Override
        public void purge(boolean leaveMatch){
            purge.set(true);
        }

        @Override
        public boolean isZombie(){
            return zombied.get();
        }
    }

}