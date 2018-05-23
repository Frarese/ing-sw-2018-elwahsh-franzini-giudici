package it.polimi.se2018.controller.network;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.*;

public class ThreadHandlerTest {
    private boolean ok;
    private ThreadHandler a;
    @Before
    public void setUp(){
        a=new ThreadHandlerTestImpl();
        ok=true;
    }

    @After
    public void tearDown() {
        ok=true;
        a.forceStop();
    }

    @Test
    public void testInit() {

        assertFalse(a.isRunning());
        a.start();
        assertTrue(a.isRunning());
    }

    @Test(timeout= 1000)
    public void testStop() {

        assertFalse(a.isRunning());
        a.start();
        assertTrue(a.isRunning());
        a.stop();
        while(a.isRunning());
        assertFalse(a.isRunning());
    }

    @Test(timeout =1000)
    public void testForceStop() {

        assertFalse(a.isRunning());
        a.start();
        assertTrue(a.isRunning());
        ok=false;
        while(true){
            if(((ThreadHandlerTestImpl)a).blocked.get()){
               break;
            }
        }
        a.forceStop();
        while(true){
            if(!a.isRunning()){
                break;
            }
        }
    }

    @Test(timeout= 1000)
    public void testReinit() {

        assertFalse(a.isRunning());
        a.start();
        assertTrue(a.isRunning());
        a.stop();
        while(a.isRunning());
        assertFalse(a.isRunning());

        assertTrue(a.start());
    }
    @Test
    public void testDoubleStart(){
        assertFalse(a.isRunning());
        assertTrue(a.start());
        assertTrue(a.isRunning());
        assertFalse(a.start());

    }


    private class ThreadHandlerTestImpl extends ThreadHandler{
        int count;
        final AtomicBoolean blocked;
        final String a;
        ThreadHandlerTestImpl(){
            blocked=new AtomicBoolean(false);
            count=0;
            a=" ";
        }
        @Override
        protected void methodToCall() throws InterruptedException {
            if(ok){
                count++;
            }else{
                synchronized (a){
                    blocked.lazySet(true);
                    a.wait();
                }

            }
        }
    }
}