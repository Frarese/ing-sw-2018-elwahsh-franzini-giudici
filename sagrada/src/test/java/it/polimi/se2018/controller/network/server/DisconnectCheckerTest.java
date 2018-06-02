package it.polimi.se2018.controller.network.server;

import it.polimi.se2018.controller.network.AbsReq;
import it.polimi.se2018.controller.network.ChangeCLayerRequest;
import it.polimi.se2018.controller.network.KeepAliveRequest;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.*;

public class DisconnectCheckerTest {
    private volatile DisconnectChecker uut;
    private volatile AtomicBoolean warned;
    private volatile AtomicBoolean zombied;
    private volatile AtomicBoolean purge;
    private volatile AtomicBoolean fail;
    private Field continuaF;
    private Field warnedF;

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
    }

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

    @Test
    public void testResetWarned() throws Exception{
        uut=new DisconnectChecker(1000,1000,1000,new ClientTest());
        assertFalse(((AtomicBoolean)warnedF.get(uut)).get());
        ((AtomicBoolean)warnedF.get(uut)).set(true);
        uut.resetWarned();
        assertFalse(((AtomicBoolean)warnedF.get(uut)).get());
    }

    @Test(timeout = 1000)
    public void testSchedule(){
        uut=new DisconnectChecker(2,2,2,new ClientTest());
        uut.reschedule();
        while(!warned.get());
    }

    @Test(timeout = 1000)
    public void testNetworkFailure(){
        uut=new DisconnectChecker(2,2,2,new ClientTest());
        uut.reschedule();
        while(!purge.get());

    }

    @Test(timeout = 1000)
    public void testSilentFailure(){
        fail.set(true);
        uut=new DisconnectChecker(2,2,2,new ClientTest());
        uut.reschedule();
        while(!purge.get());
    }

    private class ClientTest extends Client{
        final Instant i;
        ClientTest(){
            super("",null);
            i=Instant.now().minusNanos(100);
        }

        @Override
        public Instant lastSeen(){
            return i;
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