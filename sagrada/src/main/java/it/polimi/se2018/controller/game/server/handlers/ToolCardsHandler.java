package it.polimi.se2018.controller.game.server.handlers;


import it.polimi.se2018.controller.network.server.MatchNetworkInterface;
import it.polimi.se2018.events.actions.PlayerMove;
import it.polimi.se2018.events.actions.UseToolCardMove;
import it.polimi.se2018.events.messages.*;
import it.polimi.se2018.model.Board;
import it.polimi.se2018.model.Player;


import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handler for all tool cards effects
 * @author Alì El Wahsh
 */
public class ToolCardsHandler implements Runnable,Observer  {

    private final Board board;
    private final Player player;
    private final UseToolCardMove move;
    private final boolean firsTurn;
    private final MatchNetworkInterface network;
    private static final String NO_PERMISSION = "Non hai i permessi per usare questa carta";
    private PlayerMove response;

    /**
     * Constructor
     * @param move card move
     * @param player player using the card
     * @param board game board
     * @param firstTurn true if first turn, false otherwise
     * @param networkInterface network interface
     */
    public ToolCardsHandler(UseToolCardMove move, Player player, Board board, boolean firstTurn, MatchNetworkInterface networkInterface)
    {
        this.move = move;
        this.player = player;
        this.board = board;
        this.firsTurn = firstTurn;
        this.network = networkInterface;
    }

    /**
     * Notifies failure sending a invalid move message
     * @param error error message
     */
    private void notifyFailure(String error)
    {
        network.sendReq(new InvalidMove(move,error,false),player.getName());
    }

    /**
     * Notifies success sending a confirm move message
     */
    private void notifySuccess()
    {
        network.sendReq(new ConfirmMove(move,false),player.getName());
    }

    /**
     * For each card checks its permission and executes its effect
     */
    private void checksEffect()
    {
        switch (move.getCardID())
        {
            case 20:
                break;
            case 21:
                break;
            default: notifyFailure("NotImplementedError");


        }
    }

    @Override
    public void run() {
        checksEffect();
    }

    @Override
    public void update(Observable o, Object arg) {

        response = (PlayerMove) arg;
    }


    /**
     * Waits for the requested response
     * @param message requested response message
     */
    private synchronized void waitUpdate(String message)
    {
        while(response == null || !response.toString().equals(message)) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                Logger.getGlobal().log(Level.INFO, "Thread killed");
                Thread.currentThread().interrupt();
            }
        }
    }

}

