package it.polimi.se2018.events.messages;

import it.polimi.se2018.events.Event;

/**
 * Request for a change of connection type
 * @author Alì El Wahsh
 */
public class ChangeLayerRequest extends Event {

    private boolean toRMI;
    private int objectPort;
    private int requestPort;

    /**
     * ChangeLayerRequest's constructor
     * @param toRMI true if RMI is the new type of connection
     * @param objectPort  contains the object port number
     * @param requestPort contains the request port number
     */
    public ChangeLayerRequest(boolean toRMI, int objectPort, int requestPort)
    {
        this.toRMI = toRMI;
        this.objectPort = objectPort;
        this.requestPort = requestPort;
    }

    /**
     * Checks to what kind of connections the user is migrating
     * @return true for RMI, false for Socket
     */
    public boolean isToRMI() {
        return toRMI;
    }

    /**
     * Getter for object port number
     * @return object port number
     */
    public int getObjectPort() {
        return objectPort;
    }

    /**
     * Getter for request port number
     * @return request port number
     */
    public int getRequestPort() {
        return requestPort;
    }
}
