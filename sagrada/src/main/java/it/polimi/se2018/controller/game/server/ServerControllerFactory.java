package it.polimi.se2018.controller.game.server;

import it.polimi.se2018.controller.network.server.MatchController;
import it.polimi.se2018.controller.network.server.MatchControllerFactory;
import it.polimi.se2018.controller.network.server.MatchNetworkInterface;
import it.polimi.se2018.util.MatchIdentifier;

/**
 * Factory for Server controlled, called by the network
 * @author Alì El Wahsh
 */
public class ServerControllerFactory implements MatchControllerFactory {

    @Override
    public MatchController buildMatch(MatchIdentifier mId, MatchNetworkInterface network) {
        return new ServerController(mId, network);
    }
}
