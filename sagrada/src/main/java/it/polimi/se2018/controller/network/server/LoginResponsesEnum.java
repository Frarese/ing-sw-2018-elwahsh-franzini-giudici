package it.polimi.se2018.controller.network.server;

public enum LoginResponsesEnum {
    RESOURCE_NAME("sagradaLogin"),
    WRONG_CREDENTIALS("Wrong Credentials"),
    USER_ALREADY_EXISTS("User already Exists"),
    LOGIN_OK("Login ok"),
    USER_NOT_LOGGED("User was not logged, cannot recover");

    public final String msg;
    LoginResponsesEnum(String msg){
        this.msg=msg;
    }
}
