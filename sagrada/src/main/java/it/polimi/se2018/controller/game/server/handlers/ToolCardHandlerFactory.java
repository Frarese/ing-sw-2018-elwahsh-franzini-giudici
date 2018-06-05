package it.polimi.se2018.controller.game.server.handlers;

import it.polimi.se2018.controller.game.EventBus;
import it.polimi.se2018.controller.game.server.handlers.toolcardhandlers.AbstractCardHandler;
import it.polimi.se2018.controller.network.server.MatchNetworkInterface;
import it.polimi.se2018.events.actions.UseToolCardMove;
import it.polimi.se2018.model.Board;
import it.polimi.se2018.model.Player;

/**
 * Factory for CardHandlers
 * Uses cardID to identify the right handler
 */
public class ToolCardHandlerFactory {

    public AbstractCardHandler getCardHandler(EventBus bus,Player player, UseToolCardMove move, Board board, boolean firstTurn, MatchNetworkInterface network) {
        throw new UnsupportedOperationException();
    }
}
