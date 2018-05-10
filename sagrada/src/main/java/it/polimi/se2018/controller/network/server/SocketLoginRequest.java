package it.polimi.se2018.controller.network.server;

import java.io.Serializable;

/**
 * Helper class that serializes and deserializes login requests
 * @author Francesco Franzini
 */
public class SocketLoginRequest implements Serializable {
    public final String username;
    public final String password;
    public final boolean isRecovery;
    public final boolean isNewUser;

    /**
     * Initializes this wrapper with the given parameters
     * @param username username
     * @param password password
     * @param isRecovery flag to indicate a recovery attempt
     * @param isNewUser flag to indicate a registration attempt
     */
    public SocketLoginRequest(String username, String password, boolean isRecovery, boolean isNewUser) {
        this.username = username;
        this.password = password;
        if(isRecovery&&isNewUser)throw new IllegalArgumentException("A user cannot be registering and reconnecting at the same time");
        this.isRecovery = isRecovery;
        this.isNewUser = isNewUser;
    }
}
