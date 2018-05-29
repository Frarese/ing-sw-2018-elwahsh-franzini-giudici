package it.polimi.se2018.controller.network.server;

import it.polimi.se2018.util.MatchIdentifier;

/**
 * Factory interface used to produce Match Controllers
 * @author Francesco Franzini
 */
public interface MatchControllerFactory {
    /**
     * Builds a {@link it.polimi.se2018.controller.network.server.MatchController}
     * @param mId the Id to use
     * @param network the {@link it.polimi.se2018.controller.network.server.MatchNetworkInterface} to use
     * @return the controller that was built
     */
    MatchController buildMatch(MatchIdentifier mId,MatchNetworkInterface network);
}
