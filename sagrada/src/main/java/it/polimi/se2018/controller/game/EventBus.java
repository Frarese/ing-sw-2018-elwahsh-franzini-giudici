package it.polimi.se2018.controller.game;


import it.polimi.se2018.events.Event;
import java.util.Observable;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Queue for all events incoming
 * quite useful for avoiding traffic problems
 * @author  Alì El Wahsh
 */
public class EventBus extends Observable implements  Runnable {

    private final BlockingQueue<Event> events = new LinkedBlockingQueue<>();
    private boolean done;

    /**
     * Pushes event inside the queue
     * @param event event to be pushed
     */
    public void asyncPush(Event event)
    {
        if(event != null)
            events.add(event);
    }

    /**
     * Notifies an event, so it will be handled by the controller
     * @param event event to be popped
     */
    private void publish(Event event)
    {
        setChanged();
        notifyObservers(event);

    }

    /**
     * Stops the execution of this bus
     */
    public void stopListening()
    {
        done = true;
    }

    @Override
    public void run() {
        try {
            while (!done) {
                publish(events.take());
                }
            } catch (InterruptedException e) {
                Logger.getGlobal().log(Level.SEVERE, e.toString());
                Thread.currentThread().interrupt();
            }
        }

}
