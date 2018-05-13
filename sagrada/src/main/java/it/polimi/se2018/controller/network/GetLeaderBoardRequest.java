package it.polimi.se2018.controller.network;

import it.polimi.se2018.controller.network.client.Comm;
import it.polimi.se2018.controller.network.client.CommUtilizer;
import it.polimi.se2018.controller.network.server.Client;
import it.polimi.se2018.controller.network.server.ServerMain;
import it.polimi.se2018.util.ScoreEntry;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Request for the update of the leaderboard
 * @author Francesco Franzini
 */
public class GetLeaderBoardRequest extends AbsReqServerLogic {
    private List<ScoreEntry> leaderboard;

    @Override
    public void clientHandle(Comm clientComm, CommUtilizer commUtilizer) {
        if(leaderboard==null)return;
        leaderboard=leaderboard.stream().sorted(Comparator.comparingInt(o->o.wins)).collect(Collectors.toList());
        commUtilizer.pushLeaderboard(leaderboard);
    }

    @Override
    public boolean checkValid() {
        return true;
    }

    @Override
    public void serverHandle(Client client, ServerMain server) {
        leaderboard=server.getRegisteredUsers();
        client.pushOutReq(this);
    }

}
