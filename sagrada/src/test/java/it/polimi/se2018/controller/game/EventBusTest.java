package it.polimi.se2018.controller.game;

import it.polimi.se2018.events.Event;
import it.polimi.se2018.events.actions.DiePlacementMove;
import org.junit.Before;
import org.junit.Test;

import java.util.Observable;
import java.util.Observer;

/**
 * Test for the EventBus class
 * @author Alì El wahsh
 */
public class EventBusTest {
    private final EventBus test = new EventBus();

    /**
     * Observer used fot testing
     */
    private class testObserver implements Observer
    {
        @Override
        public void update(Observable o, Object arg) {
            if(arg instanceof Event)
                test.stopListening();
        }
    }

    /**
     * Tests observation
     */
    @Before
    public void testInit()
    {
        test.addObserver(new testObserver());
    }


    /**
     * Test asyncPush and take
     */
    @Test
    public void testPushAndPop()
    {
        new Thread(test, "test").start();
        test.asyncPush(new DiePlacementMove(0,0,0,"",true,true,true));

    }

    /**
     * Test for interruption handling
     */
    @Test
    public void testInterrupt(){
        Thread t=new Thread(test, "test");
        t.start();
        t.interrupt();
    }



}