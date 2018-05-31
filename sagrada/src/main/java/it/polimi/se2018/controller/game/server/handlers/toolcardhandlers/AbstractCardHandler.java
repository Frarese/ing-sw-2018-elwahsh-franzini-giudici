package it.polimi.se2018.controller.game.server.handlers.toolcardhandlers;

import it.polimi.se2018.controller.network.server.MatchNetworkInterface;
import it.polimi.se2018.events.actions.UseToolCardMove;
import it.polimi.se2018.model.Board;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.cards.ToolCard;

/**
 * Generic card handler to be extended by specific ones
 */
public abstract class AbstractCardHandler implements Runnable {


    protected UseToolCardMove move;
    protected Player player;
    protected Board board;
    protected boolean firstTurn;
    protected MatchNetworkInterface network;
    protected ToolCard card;

    /**
     * AbstractCardHandler's constructor
     * @param player player to be handled
     * @param move move to be handled
     * @param board game board
     * @param firstTurn true if first turn, false otherwise
     * @param network network layer reference
     */
    public AbstractCardHandler(Player player, UseToolCardMove move, Board board, boolean firstTurn, MatchNetworkInterface network)
    {
        this.board = board;
        this.player = player;
        this.firstTurn = firstTurn;
        this.network = network;
        this.move = move;
    }


}
