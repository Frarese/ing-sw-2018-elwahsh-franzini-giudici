package it.polimi.se2018.controller.network;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.*;

/**
 * Tester class for the ThreadHandler class
 * @author Francesco Franzini
 */
public class ThreadHandlerTest {
    private boolean ok;
    private ThreadHandlerTestImpl uut;
    private boolean interrupt;
    private Field continuaF;

    /**
     * Prepares the flags and objects to be used
     * @throws Exception if an error occurs
     */
    @Before
    public void setUp() throws Exception{
        uut =new ThreadHandlerTestImpl();
        ok=true;
        interrupt=false;

        continuaF=ThreadHandler.class.getDeclaredField("continua");
        continuaF.setAccessible(true);
    }

    /**
     * Tests that the handler starts correctly
     */
    @Test
    public void testInit() {
        assertFalse(uut.isRunning());
        uut.start();
        assertTrue(uut.isRunning());
    }

    /**
     * Tests that the handler stops correctly
     * @throws Exception if an error occurs
     */
    @Test
    public void testStop() throws Exception{
        ok=false;
        uut.start();
        uut.stop();
        assertEquals(Boolean.FALSE,continuaF.get(uut));
    }

    /**
     * Tests that the handler forces a stop correctly
     * @throws Exception if an error occurs
     */
    @Test
    public void testForceStop() throws Exception{
        ok=false;
        uut.start();
        uut.forceStop();
        assertEquals(Boolean.FALSE,continuaF.get(uut));
    }

    /**
     * Tests that the handler reinitializes correctly
     */
    @Test
    public void testReinit() {
        assertFalse(uut.isRunning());
        uut.start();
        uut.forceStop();

        assertTrue(uut.start());
    }

    /**
     * Tests that the handler does not allow multiple starts
     */
    @Test
    public void testDoubleStart(){
        assertFalse(uut.isRunning());
        assertTrue(uut.start());
        assertTrue(uut.isRunning());
        assertFalse(uut.start());
    }

    /**
     * Tests the handler reaction to an interruption
     * @throws Exception if an error occurs
     */
    @Test
    public void testInterruptedEx() throws Exception{
        interrupt=true;
        continuaF.set(uut,true);
        Thread t=new Thread(uut);
        t.start();
        t.join();
    }

    /**
     * Mock implementation of ThreadHandler used to intercept method calls
     */
    private class ThreadHandlerTestImpl extends ThreadHandler{
        final AtomicBoolean blocked;
        private final String a;
        ThreadHandlerTestImpl(){
            blocked=new AtomicBoolean(false);
            a=" ";
        }
        @Override
        protected void methodToCall() throws InterruptedException {
            if(interrupt)throw new InterruptedException();
            if(!ok) {
                synchronized (a) {
                    blocked.set(true);
                    a.wait();
                    System.out.println("Hello");
                }
            }
        }
    }
}