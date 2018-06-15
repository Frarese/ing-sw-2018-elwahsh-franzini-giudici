package it.polimi.se2018.controller.network.server;

import java.io.Serializable;

/**
 * Helper class that serializes and deserializes login requests
 * @author Francesco Franzini
 */
public class SocketLoginRequest implements Serializable {
    private static final String TO_MATCH ="^[a-zA-Z][a-zA-Z0-9]*";
    public final String username;
    public final String password;
    public final boolean isNewUser;

    /**
     * Initializes this wrapper with the given parameters
     * @param username username
     * @param password password
     * @param isNewUser flag to indicate a registration attempt
     */
    public SocketLoginRequest(String username, String password, boolean isNewUser) {
        this.username = username;
        this.password = password;
        this.isNewUser = isNewUser;
        if(!isValid())throw new IllegalArgumentException("Parameters cannot be null");
    }

    public final boolean isValid() {
        return username != null && password != null && username.matches(TO_MATCH) && password.matches(TO_MATCH);
    }
}
