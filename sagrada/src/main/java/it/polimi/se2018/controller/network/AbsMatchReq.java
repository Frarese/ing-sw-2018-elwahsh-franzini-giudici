package it.polimi.se2018.controller.network;

import it.polimi.se2018.util.MatchIdentifier;

/**
 * Represents a logic request concerning a match
 * @author Francesco Franzini
 */
public abstract class AbsMatchReq extends AbsReqServerLogic {
    public final MatchIdentifier matchId;

    public AbsMatchReq(MatchIdentifier match) {
        this.matchId=match;
    }


}
