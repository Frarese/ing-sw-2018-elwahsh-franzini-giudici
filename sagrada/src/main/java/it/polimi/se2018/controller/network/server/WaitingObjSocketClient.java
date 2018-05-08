package it.polimi.se2018.controller.network.server;

import it.polimi.se2018.util.SafeSocket;

/**
 * A wrapper for the objects concerning a half done socket login
 * @author Francesco Franzini
 */
class WaitingObjSocketClient {
    final SafeSocket reqS;
    final String usn;
    final String psw;
    final boolean isRecovery;

    /**
     * Initializes a wrapper with the given paramenters
     * @param reqS the request {@link it.polimi.se2018.util.SafeSocket}
     * @param usn username
     * @param psw password
     * @param isRecovery recovery flag
     */
    public WaitingObjSocketClient(SafeSocket reqS, String usn, String psw, boolean isRecovery) {
        this.reqS = reqS;
        this.usn = usn;
        this.psw = psw;
        this.isRecovery = isRecovery;
    }
}
