package it.polimi.se2018.controller.network;


import it.polimi.se2018.controller.network.client.Comm;
import it.polimi.se2018.controller.network.client.CommUtilizer;
import it.polimi.se2018.controller.network.server.Client;
import it.polimi.se2018.controller.network.server.ServerMain;
import it.polimi.se2018.util.ScoreEntry;

import java.util.List;

/**
 * Request for the update of the logged users list
 * @author Francesco Franzini
 */
public class ListUsersRequest extends AbsReqServerLogic {
    private List<ScoreEntry> list;

    @Override
    public void serverHandle(Client client, ServerMain server) {
        this.list=server.getUserListCopy();
        client.pushOutReq(this);
    }

    @Override
    public void clientHandle(Comm clientComm, CommUtilizer commUtilizer) {
        if(list==null)return;
        commUtilizer.pushUserList(list);
    }

    @Override
    public boolean checkValid() {
        return true;
    }

    /**
     * Returns the List of {@link it.polimi.se2018.util.ScoreEntry}
     * @return the logged users List
     */
    public List<ScoreEntry> getList() {
        return list;
    }

    /**
     * Sets the List
     * @param list the list to transmit
     */
    public void setList(List<ScoreEntry> list){
        this.list=list;
    }

}
