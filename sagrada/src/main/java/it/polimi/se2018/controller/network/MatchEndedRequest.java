package it.polimi.se2018.controller.network;

import it.polimi.se2018.controller.network.client.Comm;
import it.polimi.se2018.controller.network.client.CommUtilizer;
import it.polimi.se2018.controller.network.server.ServerVisitor;
import it.polimi.se2018.util.MatchIdentifier;

/**
 * Request used to notify that a match has ended and to transmit the results
 * @author Francesco Franzini
 */
public class MatchEndedRequest extends AbsMatchReq {
    public final int playerScore0;
    public final int playerScore1;
    public final int playerScore2;
    public final int playerScore3;

    /**
     * Initializes this request with the given parameter
     * @param matchId the {@link it.polimi.se2018.util.MatchIdentifier} of the match
     * @param playerScore0 player0's score
     * @param playerScore1 player1's score
     * @param playerScore2 player2's score
     * @param playerScore3 player3's score
     */
    public MatchEndedRequest(MatchIdentifier matchId,int playerScore0, int playerScore1, int playerScore2, int playerScore3) {
        super(matchId);
        this.playerScore0=playerScore0;
        this.playerScore1=playerScore1;
        this.playerScore2=playerScore2;
        this.playerScore3=playerScore3;
        if(!checkValid())throw new IllegalArgumentException("Invalid parameters");
    }

    @Override
    public void serverVisit(ServerVisitor sV) {
        sV.handle(this);
    }

    @Override
    public void clientHandle(Comm clientComm, CommUtilizer commUtilizer) {
        commUtilizer.notifyMatchEnd(playerScore0,playerScore1,playerScore2,playerScore3);
    }

    @Override
    public boolean checkValid() {
        return matchId!=null && playerScore3>=0 && playerScore2>=0 && playerScore1>=0 && playerScore0>=0;
    }



}
