package it.polimi.se2018.util;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.Queue;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A wrapper to {@link java.net.Socket} that ensures delivery and recognizes failures
 * @author  Francesco Franzini
 */
public class SafeSocket implements Runnable {
    private Logger logger;
    private long timeout;
    private final Socket s;
    private final Queue<Serializable> inQueue;
    private ObjectInputStream inStr;
    private ObjectOutputStream outStr;
    private SafeSocketACK lastACK;
    private Thread t;
    private boolean continua;

    /**Creates a new SafeSocket and initializes a new {@link java.net.Socket} for it
     * @param timeout the timeout(in milliseconds) for this SafeSocket
     */
    public SafeSocket(long timeout) throws IOException {
        this(new Socket(),timeout);
    }

    /**
     * Creates a new SafeSocket wrapper around a given(and already connected) {@link java.net.Socket}
     * @param s the connected socket to wrap
     * @param timeout the timeout(in milliseconds) for this SafeSocket
     */
    public SafeSocket(Socket s, long timeout) throws IOException {
        this.s=s;
        inQueue=new LinkedList<>();
        init();
        if(s.isConnected() && !s.isClosed()){
            try {
                start();
            } catch (IOException e) {
                logger.log(Level.SEVERE,"Error accessing socket's objects"+e.getMessage());
                throw e;
            }
        }else if(s.isClosed()){
            throw new IllegalArgumentException("given Socket is closed");
        }
        this.timeout=timeout;
        logger.log(Level.FINE,"SafeSocket initialized");
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
     * @return true if the connection was a success, false otherwise or if the socket was already used in a connection
     */
    public boolean connect(String host, int port) {
        if(s.isConnected())return false;
        boolean out=true;
        try {
            s.connect(new InetSocketAddress(host,port),1000);
            start();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error connecting to host "+e.getLocalizedMessage());
            out=false;
        }
        return out;
    }

    /**
     * Initializes the objects that will be used to handle the connection
     */
    private void init()  {
        lastACK=null;
        this.logger=Logger.getGlobal();
    }

    /**
     * Starts this socket's listener
     * @throws IOException if an error occurs
     */
    private void start() throws IOException{
        outStr=new ObjectOutputStream(s.getOutputStream());
        inStr=new ObjectInputStream(s.getInputStream());

        continua=true;
        t=new Thread(this);
        t.start();
    }

    /**
     * Sends a serializable object through the active connection and blocks execution until an ack is received or the network times out
     * @param obj the serializable object to send
     * @return true if the ack is received
     */
    public boolean send(Serializable obj) {
        long time=System.currentTimeMillis();
        int objHash=hashObj(obj);
        try {
            synchronized (outStr) {
                outStr.writeObject(obj);
                outStr.flush();
            }
            while((System.currentTimeMillis()-time) <=timeout){
                    synchronized (this) {
                        if (lastACK!=null && lastACK.id==objHash) {
                            return true;
                        }
                    }
                    Thread.sleep(timeout/100);
                }
        } catch (IOException e) {
            if(!continua){
                logger.log(Level.WARNING,"Interrupted while writing to safe socket"+e.getLocalizedMessage());
            }else{
                logger.log(Level.SEVERE,"Error writing to safe socket"+e.getLocalizedMessage());
            }

        } catch (InterruptedException e) {
            logger.log(Level.WARNING,"Interrupted while sending to safe socket"+e.getLocalizedMessage());
            Thread.currentThread().interrupt();
        }

        return false;
    }

    /**
     * Reads one object from the input queue, if no object is available it blocks execution until it can return something
     * @return a Serializable object received from the other end or {@code null} if an error occurred
     * @throws InterruptedException if interrupted
     */
    public Serializable receive() throws InterruptedException {
        synchronized (inQueue){
            while(inQueue.isEmpty()){
                inQueue.wait();
            }
            return inQueue.remove();
        }
    }

    @Override
    public void run() {
        while(continua){
            try {
                Serializable read=(Serializable)inStr.readObject();
                if(read.getClass().equals(SafeSocketACK.class)){
                    synchronized (this) {
                        lastACK = (SafeSocketACK) read;
                    }
                }else{
                    synchronized (inQueue){
                        inQueue.add(read);
                        inQueue.notifyAll();
                    }
                    synchronized (outStr){
                        logger.log(Level.FINEST,"Received object, returning ack");
                        int objHash=hashObj(read);
                        outStr.writeObject(new SafeSocketACK(objHash));
                        outStr.flush();
                    }

                }
            } catch (IOException e) {
                if(!continua){
                    logger.log(Level.WARNING,"Interrupted while reading from safe socket"+e.getLocalizedMessage());
                }else{
                    logger.log(Level.SEVERE,"Error reading from safe socket"+e.getLocalizedMessage());
                    continua=false;
                }

            } catch (ClassNotFoundException e) {
                logger.log(Level.WARNING,"Unknown class received to SafeSocket"+e.getLocalizedMessage());
            }
        }

    }

    /**
     * Hashes the given object, default method uses {@link java.lang.Object#hashCode()}
     * @param obj the object to hash
     * @return  the result of the hashing function
     */
    private static int hashObj(Object obj) {
        return obj.hashCode();
    }


    /**
     * Closes this SafeSocket down
     * @param force if true any pending operation will be interrupted
     */
    public void close(boolean force){
        continua=false;
        if(force){
            t.interrupt();
        }
        try {
            s.close();
        } catch (IOException e) {
            logger.log(Level.SEVERE,"Error closing safe socket"+e.getLocalizedMessage());
        }
    }
}
