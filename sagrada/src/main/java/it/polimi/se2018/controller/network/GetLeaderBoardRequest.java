package it.polimi.se2018.controller.network;

import it.polimi.se2018.controller.network.client.Comm;
import it.polimi.se2018.controller.network.client.CommUtilizer;
import it.polimi.se2018.controller.network.server.Client;
import it.polimi.se2018.controller.network.server.ServerMain;

import java.util.List;

/**
 * Request for the update of the leaderboard
 * @author Francesco Franzini
 */
public class GetLeaderBoardRequest extends AbsReqServerLogic {
    private List leaderboard;

    @Override
    public void clientHandle(Comm clientComm, CommUtilizer commUtilizer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void serverHandle(Client client, ServerMain server) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the List of {@link it.polimi.se2018.util.ScoreEntry}
     * @return the logged users List
     */
    public List getLeaderboard() {
        return this.leaderboard;
    }

    /**
     * Sets the leaderboard
     * @param leaderboard the list to transmit
     */
    public void setLeaderboard(List leaderboard){
        this.leaderboard=leaderboard;
    }


}
