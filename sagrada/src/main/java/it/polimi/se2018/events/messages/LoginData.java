package it.polimi.se2018.events.messages;

import it.polimi.se2018.events.Event;

/**
 * User's login data after being inputed from the view
 * @author Alì El Wahsh
 */
public class LoginData extends Event {

    private String name;
    private String pass;
    private boolean newUser;
    private String host;
    private boolean isRMI;
    private int objectPort;
    private int requestPort;

    /**
     * LoginData's constructor
     * @param name user's name
     * @param password user's password
     * @param newUser true if it's a new user registration
     * @param host IP address
     * @param isRMI true if the user desire to use RMI
     * @param objectPort  contains the object port number
     * @param requestPort contains the request port number
     */
    public LoginData(String name, String password, boolean newUser, String host, boolean isRMI, int objectPort, int requestPort)
    {
        this.name = name;
        this.pass = password;
        this.newUser = newUser;
        this.host = host;
        this.isRMI = isRMI;
        this.objectPort = objectPort;
        this.requestPort = requestPort;
    }

    /**
     * Getter for user's name
     * @return user's name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for user's pass
     * @return user's pass
     */
    public String getPass() {
        return pass;
    }

    /**
     * Checks if it's a new user
     * @return true if it's a new user
     */
    public boolean isNewUser() {
        return newUser;
    }

    /**
     * Getter for host's IP address
     * @return IP address
     */
    public String getHost() {
        return host;
    }

    /**
     * Check if the user wants to use RMI or Socket
     * @return true for RMI, false otherwise
     */
    public boolean isRMI() {
        return isRMI;
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
