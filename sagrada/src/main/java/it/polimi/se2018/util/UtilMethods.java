package it.polimi.se2018.util;

import java.util.Queue;

/**
 * A class containing helper static methods
 * @author Francesco Franzini
 */
public interface UtilMethods {

    //Helper methods
    static <T>void pushAndNotifyTS(Queue<T> q,T obj){
        final Queue<T>queue=q;
        synchronized (queue){
            queue.add(obj);
            queue.notifyAll();
        }

    }

    static <T> T waitAndPopTS(Queue<T> q) throws InterruptedException {
        final Queue<T>queue=q;
        synchronized (queue){
            while(queue.isEmpty())queue.wait();
            return queue.poll();
        }
    }

    static <T> boolean checkEmpty(Queue<T> q) {
        final Queue<T>queue=q;
        synchronized (queue){
            return q.isEmpty();
        }
    }
}
