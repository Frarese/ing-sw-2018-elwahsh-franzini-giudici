package it.polimi.se2018.controller.game.server.handlers;


import it.polimi.se2018.controller.network.server.MatchNetworkInterface;
import it.polimi.se2018.events.actions.UseToolCardMove;
import it.polimi.se2018.events.messages.InvalidMove;
import it.polimi.se2018.events.messages.PlayerStatus;
import it.polimi.se2018.events.messages.ReserveStatus;
import it.polimi.se2018.events.messages.RoundTrackStatus;
import it.polimi.se2018.model.Board;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.cards.toolcards.*;
import it.polimi.se2018.model.dice.Die;

/**
 * Handler for all tool cards effects
 * @author Alì El Wahsh
 */
public class ToolCardsHandler  {

    private final Board board;
    private final Player player;
    private final UseToolCardMove move;
    private final boolean firsTurn;
    private final MatchNetworkInterface network;
    private static final String NO_PERMISSION = "Non hai i permessi per usare questa carta";

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
        network.sendReq(new InvalidMove(move,error),player.getName());
    }

    /**
     * For each card checks its permission and executes its effect
     */
    public void checksEffect()
    {
        switch (move.getCardID())
        {
            case 21:
                if(new EglomiseBrush().isUsable(player,firsTurn))
                {
                    String result = DiePlacementLogic.insertDie(player,move.getHeight(),
                            move.getWidth(),
                            player.getGrid().getDie(move.getOriginalY(),
                                    move.getOriginalX()),
                            false,true,true);
                    if(result == null)
                    {
                        Die d = player.getGrid().setDie(move.getOriginalY(),move.getOriginalX(),null);
                        player.getGrid().setDie(move.getHeight(),move.getWidth(),d);
                        player.setCardRights(firsTurn,false);
                        network.sendObj(new PlayerStatus(player,firsTurn));
                    }
                    else
                        notifyFailure(result);
                }
                else notifyFailure(NO_PERMISSION);

                break;

            case 22:
                if(new CopperFoilBurnisher().isUsable(player,firsTurn))
                {
                    String result = DiePlacementLogic.insertDie(player,move.getHeight(),
                            move.getWidth(),
                            player.getGrid().getDie(move.getOriginalY(),
                                    move.getOriginalX()),
                            true,false,true);
                    if(result == null)
                    {
                        Die d = player.getGrid().setDie(move.getOriginalY(),move.getOriginalX(),null);
                        player.getGrid().setDie(move.getHeight(),move.getWidth(),d);
                        player.setCardRights(firsTurn,false);
                        network.sendObj(new PlayerStatus(player,firsTurn));
                    }
                    else
                        notifyFailure(result);
                }
                else notifyFailure(NO_PERMISSION);

                break;

            case 24:
                if(new LensCutter().isUsable(player,firsTurn) && board.getRoundTrack().lastFilledRound()>0)
                {
                   String result = CardEffects.reserveRoundTrackSwap(move.getReserveIndex(),
                           move.getRound(),
                           move.getDiePosition(),
                           board.getReserve(),
                           board.getRoundTrack());
                   if(result != null)
                   {
                       notifyFailure(result);
                   }
                   else
                   {
                       player.setCardRights(firsTurn,false);
                       network.sendObj(new RoundTrackStatus(board.getRoundTrack()));
                       network.sendObj(new ReserveStatus(board.getReserve()));
                       network.sendObj(new PlayerStatus(player,firsTurn));
                   }
                }
                else
            {
                notifyFailure(NO_PERMISSION);
            }
                break;

                case 25:
                    if(new FluxBrush().isUsable(player,firsTurn))
                    {
                        CardEffects.reRoll(false,board.getReserve(),board.getReserve().get(move.getReserveIndex()));
                        network.sendObj(new ReserveStatus(board.getReserve()));
                        player.setCardRights(firsTurn,false);
                        network.sendObj(new PlayerStatus(player,firsTurn));
                    }
                    else
                    {
                        notifyFailure(NO_PERMISSION);
                    }
                    break;

            case 26:
                if(new GlazingHammer().isUsable(player,firsTurn))
                {
                    CardEffects.reRoll(true,board.getReserve(),null);
                    player.setCardRights(firsTurn,false);
                    network.sendObj(new ReserveStatus(board.getReserve()));
                    network.sendObj(new PlayerStatus(player,firsTurn));
                }
                else {
                   notifyFailure(NO_PERMISSION);
                }
                break;
            case 27:

                break;
            case 28:
                if(new CorkBackedStraightedge().isUsable(player,firsTurn))
                {
                    String result = DiePlacementLogic.insertDie(player,
                            move.getHeight(),
                            move.getWidth(),
                            board.getReserve().get(move.getReserveIndex()),
                            true,true,false);
                    if(result != null)
                    {   player.getGrid().setDie(move.getHeight(),move.getWidth(),board.getReserve().popDie(move.getReserveIndex()));
                        player.setCardRights(firsTurn,false);
                        player.setPlacementRights(firsTurn,false);
                        network.sendObj(new ReserveStatus(board.getReserve()));
                        network.sendObj(new PlayerStatus(player,firsTurn));
                    }
                    else
                        notifyFailure(result);
                }
                else
                {
                    notifyFailure(NO_PERMISSION);
                }
                break;
            case 29:
                if (new GrindingStone().isUsable(player,firsTurn))
                {
                    CardEffects.changeValue(board.getReserve().get(move.getReserveIndex()),true,0);
                    player.setCardRights(firsTurn,false);
                    network.sendObj(new ReserveStatus(board.getReserve()));
                    network.sendObj(new PlayerStatus(player,firsTurn));
                }
                else notifyFailure(NO_PERMISSION);
                break;

            case 30:
                if(new FluxRemover().isUsable(player,firsTurn))
                {
                    CardEffects.reserveBagSwap(move.getReserveIndex(),board.getBag(),board.getReserve());
                    player.setCardRights(firsTurn,false);
                    network.sendObj(new ReserveStatus(board.getReserve()));
                    network.sendObj(new PlayerStatus(player,firsTurn));
                }
                else notifyFailure(NO_PERMISSION);
                break;
            default:


        }
    }

}

