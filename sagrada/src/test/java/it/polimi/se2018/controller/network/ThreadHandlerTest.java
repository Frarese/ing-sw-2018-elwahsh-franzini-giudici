package it.polimi.se2018.controller.network;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.*;

public class ThreadHandlerTest {
    private boolean ok;
    private ThreadHandlerTestImpl uut;
    private boolean interrupt;
    private Field continuaF;

    @Before
    public void setUp() throws Exception{
        uut =new ThreadHandlerTestImpl();
        ok=true;
        interrupt=false;

        continuaF=ThreadHandler.class.getDeclaredField("continua");
        continuaF.setAccessible(true);
    }

    @After
    public void tearDown() {
        ok=true;
    }

    @Test
    public void testInit() {
        assertFalse(uut.isRunning());
        uut.start();
        assertTrue(uut.isRunning());
    }

    @Test
    public void testStop() throws Exception{
        ok=false;
        uut.start();
        uut.stop();
        assertEquals(Boolean.FALSE,continuaF.get(uut));
    }

    @Test
    public void testForceStop() throws Exception{
        ok=false;
        uut.start();
        uut.forceStop();
        assertEquals(Boolean.FALSE,continuaF.get(uut));
    }

    @Test
    public void testReinit() {
        assertFalse(uut.isRunning());
        uut.start();
        uut.forceStop();

        assertTrue(uut.start());
    }

    @Test
    public void testDoubleStart(){
        assertFalse(uut.isRunning());
        assertTrue(uut.start());
        assertTrue(uut.isRunning());
        assertFalse(uut.start());
    }

    @Test
    public void testInterruptedEx() throws Exception{
        interrupt=true;
        continuaF.set(uut,true);
        Thread t=new Thread(uut);
        t.start();
        t.join();
    }


    private class ThreadHandlerTestImpl extends ThreadHandler{
        private int count;
        final AtomicBoolean blocked;
        private final String a;
        ThreadHandlerTestImpl(){
            blocked=new AtomicBoolean(false);
            count=0;
            a=" ";
        }
        @Override
        protected void methodToCall() throws InterruptedException {
            if(interrupt)throw new InterruptedException();
            if(ok){
                count++;
            }else{
                synchronized (a){
                    blocked.set(true);
                    a.wait();
                    System.out.println("Hello");
                }

            }
        }
    }
}