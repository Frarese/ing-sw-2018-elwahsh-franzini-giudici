package it.polimi.se2018.util;

import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Queue;
import java.io.Serializable;

/**
 * A wrapper to {@link java.net.Socket} that ensures delivery and recognizes failures
 * @author  Francesco Franzini
 */
public class SafeSocket implements Runnable {

    private long timeout;
    private final Socket s;
    private Queue inQueue;
    private ObjectInputStream inStr;
    private ObjectOutputStream outStr;
    private SafeSocketACK lastACK;
    private Thread t;
    private Boolean continua;
    private SafeSocketACK lastReceivedACK;

    /**Creates a new SafeSocket and initializes a new {@link java.net.Socket} for it
     * @param timeout the timeout(in milliseconds) for this SafeSocket
     */
    public SafeSocket(long timeout) {
        s=new Socket();
        this.timeout=timeout;
    }

    /**
     * Creates a new SafeSocket wrapper around a given {@link java.net.Socket}
     * @param s the socket to wrap
     * @param timeout the timeout(in milliseconds) for this SafeSocket
     */
    public SafeSocket(Socket s, long timeout) {
        this.s=s;
        this.timeout=timeout;
    }

    /**
     * Getter for this SafeSocket's timeout
     * @return the SafeSocket's timeout
     */
    public long getTimeout() {
        return this.timeout;
    }

    /**
     * Setter for this SafeSocket's timeout
     * @param timeout the new timeout to use
     */
    public void setTimeout(long timeout) {
        this.timeout=timeout;
    }

    /**
     * Connects to a host and begins to listen on the input stream
     * @param host the hostname to connect to
     * @param port the port to use
     * @return true if the connection was a success
     */
    public boolean connect(String host, int port) {
        throw new UnsupportedOperationException();
    }

    /**
     * Initializes the objects that will be used to handle the connection
     * @return true if no error is raised
     */
    private boolean init() {
        throw new UnsupportedOperationException();
    }

    /**
     * Sends a serializable object through the active connection and blocks execution until an ack is received or the network times out
     * @param obj the serializable object to send
     * @return true if the ack is received
     */
    public boolean send(Serializable obj) {
        throw new UnsupportedOperationException();
    }

    /**
     * Reads one object from the input queue, if no object is available it blocks execution until it can return something
     * @return a Serializable object received from the other end or {@code null} if an error occurred
     */
    public Serializable receive() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException();
    }

    /**
     * Hashes the given object, default method uses {@link java.lang.Object#hashCode()}
     * @param obj the object to hash
     * @return  the result of the hashing function
     */
    public static int hashObj(Object obj) {
        return obj.hashCode();
    }




}
