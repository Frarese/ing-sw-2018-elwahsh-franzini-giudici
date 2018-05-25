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

    @Before
    public void setUp(){
        warned=new AtomicBoolean(false);
        zombied=new AtomicBoolean(false);
        purge=new AtomicBoolean(false);
        fail=new AtomicBoolean(false);
    }

    @Test(timeout = 1000)
    public void testInit() {
        long warningTimeout=2;
        long deathTimeout=2;
        long purgeTimeout=2;
        uut=new DisconnectChecker(warningTimeout,deathTimeout,purgeTimeout,new ClientTest());
        uut.reschedule();
        while(!purge.get());
        assertTrue(warned.get());
        assertTrue(zombied.get());
    }

    @Test(timeout = 1000)
    public void testFail() {
        long warningTimeout=2;
        long deathTimeout=2;
        long purgeTimeout=2;
        fail.set(true);
        uut=new DisconnectChecker(warningTimeout,deathTimeout,purgeTimeout,new ClientTest());
        uut.reschedule();
        while(!purge.get());
        assertFalse(warned.get());
        assertTrue(zombied.get());
    }

    @Test(timeout=1000)
    public void testResetWarned() throws Exception{
        long warningTimeout=2;
        long deathTimeout=2;
        long purgeTimeout=2;
        uut=new DisconnectChecker(warningTimeout,deathTimeout,purgeTimeout,new ClientTest());
        uut.reschedule();
        while(!warned.get());
        uut.stop();
        uut.resetWarned();
        Field f=DisconnectChecker.class.getDeclaredField("warned");
        f.setAccessible(true);
        assertFalse(((AtomicBoolean)f.get(uut)).get());
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
            if(zombied.get() || purge.get() ||warned.get()){
                fail("Repeated warning");
            }
            if(req.getClass().equals(KeepAliveRequest.class)){
                if(fail.get()){
                    return false;
                }
                warned.set(true);
            }else{
                fail("received: "+req.getClass().getName());
            }
            return true;
        }

        @Override
        public void zombiefy(boolean flag, ChangeCLayerRequest c){
            if(zombied.get() || purge.get()){
                fail("Error zombiefying");
            }
            if(flag){
                zombied.set(true);
            }
        }

        @Override
        public void purge(boolean leaveMatch){
            if(!zombied.get() || purge.get()){
                fail("Error purging");
            }
            purge.set(true);
        }

        @Override
        public boolean isZombie(){
            return zombied.get();
        }
    }

}