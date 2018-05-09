package it.polimi.se2018.controller.network.server;

/**
 * The server's login service abstract comm layer
 * @author Francesco Franzini
 */
abstract class ServerComm {
    protected ServerMain handler;

    /**
     * Creates a login service
     * @param handler the handler for the requests
     */
    ServerComm(ServerMain handler) {
        this.handler=handler;
    }

    /**
     * Attempts a login with the given parameters
     * @param usn username
     * @param pw password
     * @param isRecover flag to indicate that this is a connection recovery
     * @param register flag to indicate that this is a new user
     * @return an Object describing the result
     */
    abstract Object login(String usn, String pw, boolean isRecover, boolean register);

    /**
     * Attempts to delete a user
     * @param usn username
     * @param pw password
     * @return a textual representation of the outcome
     */
    abstract String delete(String usn, String pw);

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
        if(register && !handler.existsUsn(usn) && handler.createUser(usn, pw)){
            return LoginResponsesEnum.LOGIN_OK;
        }else{
            if(handler.existsUsn(usn)){
                if(handler.isPwCorrect(usn,pw)){
                    if(!handler.isUserLogged(usn)){
                        return LoginResponsesEnum.LOGIN_OK;
                    }
                }else{
                    return LoginResponsesEnum.WRONG_CREDENTIALS;
                }
            }else{
                return LoginResponsesEnum.USER_NOT_EXISTING;
            }
        }
        return LoginResponsesEnum.USER_ALREADY_EXISTS;
    }

}