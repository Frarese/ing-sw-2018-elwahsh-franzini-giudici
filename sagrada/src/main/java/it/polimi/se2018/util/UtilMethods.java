package it.polimi.se2018.util;

import java.util.Queue;

/**
 * A class containing helper static methods
 * @author Francesco Franzini
 */
public interface UtilMethods {

    /**
     * Concurrently pushes an object to the given queue and notifies Objects waiting on it
     * @param q the queue to use
     * @param <T> the type of the object to push
     */
    static <T>void pushAndNotifyTS(Queue<T> q,T obj){
        final Queue<T>queue=q;
        synchronized (queue){
            queue.add(obj);
            queue.notifyAll();
        }

    }

    /**
     * Concurrently pops an object from the given queue, and blocks execution until one is available
     * @param q the queue to use
     * @param <T> the type of the object to retrieve
     * @return the object that has been retrieved
     * @throws InterruptedException if the execution is interrupted while on wait
     */
    static <T> T waitAndPopTS(Queue<T> q) throws InterruptedException {
        final Queue<T>queue=q;
        synchronized (queue){
            while(queue.isEmpty())queue.wait();
            return queue.poll();
        }
    }

    /**
     * Concurrently checks if the given queue is empty
     * @param q the queue to check
     * @param <T> the type of the queue's objects
     * @return true if the queue is not empty
     */
    static <T> boolean checkNotEmpty(Queue<T> q) {
        final Queue<T>queue=q;
        synchronized (queue){
            return !q.isEmpty();
        }
    }
}
