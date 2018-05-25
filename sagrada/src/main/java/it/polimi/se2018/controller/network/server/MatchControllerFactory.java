package it.polimi.se2018.controller.network.server;

import it.polimi.se2018.util.MatchIdentifier;

public interface MatchControllerFactory {
    MatchController buildMatch(MatchIdentifier mId,MatchNetworkInterface network);
}
