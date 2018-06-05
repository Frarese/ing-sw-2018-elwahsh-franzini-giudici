package it.polimi.se2018.controller.network;

import it.polimi.se2018.util.MatchIdentifier;

/**
 * Represents a logic request concerning a match
 * @author Francesco Franzini
 */
abstract class AbsMatchReq implements AbsReqServerLogic {
    public final MatchIdentifier matchId;

    AbsMatchReq(MatchIdentifier match) {
        this.matchId=match;
    }


}
