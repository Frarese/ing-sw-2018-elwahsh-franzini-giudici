package it.polimi.se2018.controller.network.server;

import java.io.IOException;

/**
 * The server's login service abstract comm layer
 * @author Francesco Franzini
 */
abstract class ServerComm {
    final ServerMain handler;

    /**
     * Creates a login service
     * @param handler the handler for the requests
     */
    ServerComm(ServerMain handler) {
        this.handler=handler;
    }


    /**
     * Connects this server
     * @throws IOException if an IO error occurs
     */
    abstract void connect() throws IOException;

    /**
     * Closes this login service
     */
    abstract void close();

    /**
     * Checks if the given info are eligible for a login attempt(does not lock any username)
     * @param usn username
     * @param pw password
     * @param isRecover flag to indicate that this is a connection recovery
     * @param register flag to indicate that this is a new user(if success registration is done)
     * @return the {@link it.polimi.se2018.controller.network.server.LoginResponsesEnum} that represents the result
     */
    LoginResponsesEnum tryLogin(String usn,String pw,boolean isRecover,boolean register){
        if(isRecover){
            if(!handler.isUserLogged(usn)){
                return LoginResponsesEnum.USER_NOT_LOGGED;
            }else if(handler.isPwCorrect(usn,pw)){
                return LoginResponsesEnum.LOGIN_OK;
            }else{
                return LoginResponsesEnum.WRONG_CREDENTIALS;
            }
        }

        if(register){
            if(handler.createUser(usn, pw)){
                return LoginResponsesEnum.LOGIN_OK;
            }
            return LoginResponsesEnum.USER_ALREADY_EXISTS;
        }

        if(!handler.existsUsn(usn)) {
            return LoginResponsesEnum.USER_NOT_EXISTING;
        }

        if(handler.isPwCorrect(usn,pw)){
            if(!handler.isUserLogged(usn)){
                return LoginResponsesEnum.LOGIN_OK;
            }
            return LoginResponsesEnum.USER_ALREADY_LOGGED;
        }else{
            return LoginResponsesEnum.WRONG_CREDENTIALS;
        }

    }

}