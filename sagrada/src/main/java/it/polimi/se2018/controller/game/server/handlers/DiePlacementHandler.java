package it.polimi.se2018.controller.game.server.handlers;

import it.polimi.se2018.controller.network.server.MatchNetworkInterface;
import it.polimi.se2018.events.actions.DiePlacementMove;
import it.polimi.se2018.events.messages.InvalidMove;
import it.polimi.se2018.events.messages.PlayerStatus;
import it.polimi.se2018.events.messages.ReserveStatus;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.dice.Reserve;

/**
 * Handler for generic die placement
 * @author Alì El Wahsh
 */
public class DiePlacementHandler implements Runnable {

    private final DiePlacementMove move;
    private final Player player;
    private final Reserve reserve;
    private final boolean firstTurn;
    private final MatchNetworkInterface network;

    /**
     * DiePlacementHandler's constructor
     * @param player player who's trying to place a die
     * @param move the placement move
     * @param reserve the reserve form which the die is taken
     * @param firstTurn true if it's a first turn placement, false otherwise
     * @param network network output for the result
     */
    public DiePlacementHandler(Player player, DiePlacementMove move, Reserve reserve, boolean firstTurn, MatchNetworkInterface network)
    {
        this.player = player;
        this.move = move;
        this.reserve = reserve;
        this.firstTurn = firstTurn;
        this.network = network;
    }

    /**
     * Sends the move to all client controller to notify the success
     */
    private void notifySuccess()
    {
        network.sendObj(new PlayerStatus(player,firstTurn));
        network.sendObj(new ReserveStatus(reserve));
    }

    /**
     * Sends the error to the player who was trying to place the die
     * @param error error message
     */
    private void notifyFailure(String error)
    {
        network.sendReq(new InvalidMove(move, error), player.getName());
    }

    @Override
    public void run() {
        if(!player.canPlaceOnThisTurn(firstTurn)) {
            notifyFailure("Hai già piazzato un dado");
            return;
        }
        String result =
                DiePlacementLogic.insertDie(
                        player,move.getHeight(),
                        move.getWidth(),
                        reserve.get(move.getDiePosition()),
                        move.isColorRestriction(),
                        move.isValueRestriction(),
                        move.isAdjacentRestriction());
        if(result == null) {
            player.getGrid().setDie(move.getHeight(),move.getWidth(),reserve.popDie(move.getDiePosition()));
            player.setPlacementRights(firstTurn,false);
            notifySuccess();
        }
        else{
            notifyFailure(result);
        }
    }
}
