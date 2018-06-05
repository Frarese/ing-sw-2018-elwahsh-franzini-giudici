package it.polimi.se2018.events.actions;

import it.polimi.se2018.controller.game.server.Round;
import it.polimi.se2018.controller.game.server.ServerController;
import it.polimi.se2018.controller.game.server.handlers.DiePlacementHandler;
import it.polimi.se2018.controller.game.server.handlers.ToolCardHandlerFactory;
import it.polimi.se2018.controller.network.server.MatchNetworkInterface;
import it.polimi.se2018.model.Board;
import it.polimi.se2018.model.Player;

public interface PlayerMoveHandler {

    /**
     * Handles this move
     */
     static void handle(ServerController controller,PlayerMove move, Player currentPlayer, Board board, Round round, MatchNetworkInterface networkInterface){

        switch (move.toString()){
             case "Placement":
                 new Thread(
                     new DiePlacementHandler(currentPlayer,(DiePlacementMove)move,board.getReserve(),round.getFirstTurn(),networkInterface)
                     ,"Placement Handler" + round.getCurrentPlayer().hashCode()).start();
                 break;
            case "UseCard":
                new Thread(new ToolCardHandlerFactory()
                        .getCardHandler(controller.getInBus(),round.getCurrentPlayer(),
                                (UseToolCardMove)move,
                                board,
                                round.getFirstTurn(),
                                networkInterface), "CardHandler" + round.getCurrentPlayer().hashCode()).start();

                break;
            case "PassTurn":
                controller.newTurn();
                break;

            default:
                controller.getInBus().asyncPush(move);
        }






    }
}
