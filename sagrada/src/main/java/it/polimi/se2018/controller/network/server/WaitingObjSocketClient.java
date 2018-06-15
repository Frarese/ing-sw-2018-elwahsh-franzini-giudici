package it.polimi.se2018.controller.network.server;

import it.polimi.se2018.util.SafeSocket;

/**
 * A wrapper for the objects concerning a half done socket login
 * @author Francesco Franzini
 */
class WaitingObjSocketClient {
    final SafeSocket reqS;
    final String psw;

    /**
     * Initializes a wrapper with the given parameters
     * @param reqS the request {@link it.polimi.se2018.util.SafeSocket}
     * @param psw password
     */
    WaitingObjSocketClient(SafeSocket reqS, String psw) {
        this.reqS = reqS;
        this.psw = psw;
    }
}
