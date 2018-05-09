package it.polimi.se2018.controller.network.server;

import java.io.Serializable;

/**
 * An enumerator that holds the possible answers from a login attempt
 * @author Francesco Franzini
 */
public enum LoginResponsesEnum implements Serializable {
    RESOURCE_NAME("sagradaLogin"),
    WRONG_CREDENTIALS("Wrong Credentials"),
    USER_ALREADY_EXISTS("User already Exists"),
    USER_NOT_EXISTING("User does not exist"),
    LOGIN_OK("Login ok"),
    USER_ALREADY_LOGGED("User was already logged"),
    USER_NOT_LOGGED("User was not logged, cannot recover");

    public final String msg;
    LoginResponsesEnum(String msg){
        this.msg=msg;
    }
}
