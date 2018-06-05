package it.polimi.se2018.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A wrapper to {@link java.net.Socket} that ensures delivery and recognizes failures
 *
 * @author Francesco Franzini
 */
public class SafeSocket implements Runnable {
    public static final long DEFAULT_TIMEOUT = 1000;
    private Logger logger;
    private long timeout;
    private final Socket s;
    private final Queue<Serializable> inQueue;
    private ObjectInputStream inStr;
    private ObjectOutputStream outStr;
    private volatile SafeSocketACK lastACK;
    private Integer lastObjId;
    private Thread t;
    private boolean continua;

    /**
     * Creates a new SafeSocket and initializes a new {@link java.net.Socket} for it
     *
     * @param timeout the timeout(in milliseconds) for this SafeSocket
     * @throws IOException if an I/O error occurs
     */
    public SafeSocket(long timeout) throws IOException {
        this(new Socket(), timeout);
    }

    /**
     * Creates a new SafeSocket wrapper around a given(and already connected) {@link java.net.Socket}
     *
     * @param s       the connected socket to wrap
     * @param timeout the timeout(in milliseconds) for this SafeSocket
     * @throws IOException if an I/O error occurs
     */
    public SafeSocket(Socket s, long timeout) throws IOException {
        lastObjId = null;
        this.s = s;
        inQueue = new LinkedList<>();
        init();
        if (s.isConnected() && !s.isClosed()) {
            try {
                start();
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Error accessing socket's objects" + e.getMessage());
                throw e;
            }
        } else if (s.isClosed()) {
            throw new IllegalArgumentException("given Socket is closed");
        }
        this.timeout = timeout;
        logger.log(Level.FINE, "SafeSocket initialized");
    }

    /**
     * Getter for this SafeSocket's timeout
     *
     * @return the SafeSocket's timeout
     */
    public long getTimeout() {
        return this.timeout;
    }

    /**
     * Setter for this SafeSocket's timeout
     *
     * @param timeout the new timeout to use
     */
    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    /**
     * Connects to a host and begins to listen on the input stream
     *
     * @param host the hostname to connect to
     * @param port the port to use
     * @return true if the connection was a success, false otherwise or if the socket was already used in a connection
     */
    public boolean connect(String host, int port) {
        if (s.isConnected()) return false;
        boolean out = true;
        try {
            s.connect(new InetSocketAddress(host, port), (int) timeout);
            start();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error connecting to host " + e.getLocalizedMessage());
            out = false;
        }
        return out;
    }

    /**
     * Initializes the objects that will be used to handle the connection
     */
    private void init() {
        lastACK = null;
        this.logger = Logger.getGlobal();
    }

    /**
     * Starts this socket's listener
     *
     * @throws IOException if an error occurs
     */
    private void start() throws IOException {
        outStr = new ObjectOutputStream(s.getOutputStream());
        inStr = new ObjectInputStream(s.getInputStream());

        continua = true;
        t = new Thread(this);
        t.start();
    }

    /**
     * Sends a serializable object through the active connection and blocks execution until an ack is received or the network times out
     *
     * @param obj the serializable object to send
     * @return true if the ack is received
     */
    public boolean send(Serializable obj) {
        long time = System.currentTimeMillis();
        int objHash = hashObj(obj);
        try {
            synchronized (outStr) {
                outStr.writeObject(new SafeSocketDatagram(obj));
                outStr.flush();
            }
            while ((System.currentTimeMillis() - time) <= timeout) {
                synchronized (this) {
                    if (lastACK != null && lastACK.id == objHash) {
                        return true;
                    }
                }
                Thread.sleep(timeout / 100);
            }
            logger.log(Level.SEVERE, "Timed out while sending {0}", obj);
        } catch (IOException e) {
            if (!continua) {
                logger.log(Level.WARNING, "Interrupted while writing to safe socket" + e.getLocalizedMessage());
            } else {
                logger.log(Level.SEVERE, "Error writing to safe socket" + e.getLocalizedMessage());
            }

        } catch (InterruptedException e) {
            logger.log(Level.WARNING, "Interrupted while sending to safe socket" + e.getLocalizedMessage());
            Thread.currentThread().interrupt();
        }
        return false;
    }

    /**
     * Reads one object from the input queue, if no object is available it blocks execution until it can return something
     *
     * @return a Serializable object received from the other end or {@code null} if an error occurred
     * @throws InterruptedException if interrupted
     */
    public Serializable receive() throws InterruptedException {
        Serializable out;
        synchronized (inQueue) {
            while (inQueue.isEmpty()) {
                inQueue.wait();
            }
            out= inQueue.remove();
        }
        return out;
    }

    @Override
    public void run() {
        while (continua) {
            try {
                Serializable read = (Serializable) inStr.readObject();
                SafeSocketDatagram readDgram;
                if (read.getClass().equals(SafeSocketACK.class)) {
                    logger.log(Level.FINEST, "Received ack for {0}", ((SafeSocketACK) read).id);
                    synchronized (this) {
                        lastACK = (SafeSocketACK) read;
                    }
                    continue;
                }

                readDgram = (SafeSocketDatagram) read;
                if (!readDgram.id.equals(lastObjId)) {
                    synchronized (inQueue) {
                        inQueue.add(readDgram.payload);
                        inQueue.notifyAll();
                        lastObjId = readDgram.id;
                    }
                } else {
                    logger.log(Level.SEVERE, "Skipping on duplicated object {0}", read);
                }

                synchronized (outStr) {
                    outStr.writeObject(new SafeSocketACK(readDgram.id));
                    outStr.flush();
                }

            } catch (IOException e) {
                if (!continua) {
                    logger.log(Level.WARNING, "Interrupted while reading from safe socket" + e.getLocalizedMessage());
                } else {
                    logger.log(Level.SEVERE, "Error reading from safe socket" + e.getLocalizedMessage());
                    continua = false;
                }

            } catch (ClassNotFoundException | ClassCastException e) {
                logger.log(Level.WARNING, "Unknown class received to SafeSocket" + e.getLocalizedMessage());
            }
        }

    }

    /**
     * Hashes the given object, default method uses {@link java.lang.Object#hashCode()}
     *
     * @param obj the object to hash
     * @return the result of the hashing function
     */
    static Integer hashObj(Serializable obj) {
        return obj.hashCode();
    }


    /**
     * Closes this SafeSocket down
     *
     * @param force if true any pending operation will be interrupted
     */
    public void close(boolean force) {
        continua = false;
        if (force && t != null) {
            t.interrupt();
        }
        try {
            s.close();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error closing safe socket" + e.getLocalizedMessage());
        }
    }

    /**
     * Retrieves the Socket Address of the wrapped Socket
     * @return the Socket Address of the wrapped Socket
     */
    public SocketAddress getLocalSocketAddress() {
        return s.getLocalSocketAddress();
    }
}
