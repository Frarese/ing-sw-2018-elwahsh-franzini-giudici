package it.polimi.se2018.events.actions;

import it.polimi.se2018.controller.game.server.Round;
import it.polimi.se2018.controller.game.server.ServerController;
import it.polimi.se2018.controller.game.server.handlers.DiePlacementHandler;
import it.polimi.se2018.controller.game.server.handlers.RandomDice;
import it.polimi.se2018.controller.game.server.handlers.ToolCardsHandler;
import it.polimi.se2018.controller.network.server.MatchNetworkInterface;
import it.polimi.se2018.model.Board;
import it.polimi.se2018.model.Player;

/**
 * Handler for all the basic player's moves
 * @author Francesco Franzini, Alì El Wahsh
 */
public interface PlayerMoveHandler {

    /**
     * Handles the move
     * @param controller server controller
     * @param move move
     * @param currentPlayer the current player
     * @param board the Board
     * @param round the Round
     * @param networkInterface the Network Interface
     * @param randomDice Random Dice
     */
     static void handle(ServerController controller, PlayerMove move, Player currentPlayer, Board board, Round round, MatchNetworkInterface networkInterface, RandomDice randomDice){

        switch (move.toString()){
             case "Placement":
                 new Thread(
                     new DiePlacementHandler(currentPlayer,(DiePlacementMove)move,board.getReserve(),round.getFirstTurn(),networkInterface)
                     ,"Placement Handler" + round.getCurrentPlayer().hashCode()).start();
                 break;
            case "UseCard":

                ToolCardsHandler t = new ToolCardsHandler(
                        (UseToolCardMove)move,
                        currentPlayer,
                        board,
                        round.getFirstTurn(),
                        networkInterface,randomDice);
                controller.getInBus().addObserver(t);
                Thread handler=new Thread(t);
                handler.start();
                controller.registerHandler(handler);

                break;
            case "PassTurn":
                controller.newTurn();
                break;

            default:
                controller.getInBus().asyncPush(move);
        }






    }
}
