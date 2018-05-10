package it.polimi.se2018.controller.network.server;

import it.polimi.se2018.controller.network.AbsReq;
import it.polimi.se2018.controller.network.ChangeCLayerRequest;
import it.polimi.se2018.controller.network.KeepAliveRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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

    @After
    public void tearDown() {
        
    }

    @Test(timeout = 1000)
    public void testInit() {
        long warningTimeout=1;
        long deathTimeout=1;
        long purgeTimeout=1;
        uut=new DisconnectChecker(warningTimeout,deathTimeout,purgeTimeout,new ClientTest());
        uut.reschedule();
        while(!purge.get());
        assertTrue(warned.get());
        assertTrue(zombied.get());
    }

    @Test(timeout = 1000)
    public void testFail() {
        long warningTimeout=1;
        long deathTimeout=1;
        long purgeTimeout=1;
        fail.set(true);
        uut=new DisconnectChecker(warningTimeout,deathTimeout,purgeTimeout,new ClientTest());
        uut.reschedule();
        while(!purge.get());
        assertFalse(warned.get());
        assertTrue(zombied.get());
    }

    private class ClientTest extends Client{
        Instant i;
        public ClientTest(){
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
        public void purge(){
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