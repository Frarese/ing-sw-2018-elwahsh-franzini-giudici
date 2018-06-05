package it.polimi.se2018.controller.network;

import it.polimi.se2018.controller.network.client.Comm;
import it.polimi.se2018.controller.network.client.CommUtilizer;
import it.polimi.se2018.controller.network.server.ServerVisitor;
import it.polimi.se2018.util.ScoreEntry;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Request for the update of the leaderboard
 * @author Francesco Franzini
 */
public class GetLeaderBoardRequest implements AbsReqServerLogic {
    private List<ScoreEntry> leaderboard;

    @Override
    public void clientHandle(Comm clientComm, CommUtilizer commUtilizer) {
        if(leaderboard==null)return;
        leaderboard=leaderboard.stream().sorted(Comparator.comparingInt(o->-o.wins)).collect(Collectors.toList());
        commUtilizer.pushLeaderboard(leaderboard);
    }

    @Override
    public boolean checkValid() {
        return true;
    }

    @Override
    public void serverVisit(ServerVisitor sV) {
        sV.handle(this);

    }

    /**
     * Setter for the leaderboard
     * @param leaderboard new leaderboard
     */
    public void setLeaderboard(List<ScoreEntry> leaderboard) {
        this.leaderboard= leaderboard;
    }

}
