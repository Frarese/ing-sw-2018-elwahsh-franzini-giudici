package it.polimi.se2018.controller.game.server.handlers;


import it.polimi.se2018.controller.network.server.MatchNetworkInterface;
import it.polimi.se2018.events.actions.*;
import it.polimi.se2018.events.messages.*;
import it.polimi.se2018.model.Board;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.cards.toolcards.GrozingPliers;


import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
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
    private static final String TOO_SLOW = "Troppo lento";
    private PlayerMove response;
    private CountDownLatch latch;
    private int cardPosition;
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
        boolean isInGame = false;
        for(int i = 0; i<3; i++)
        {
            if(board.getTool(i).getId() == move.getCardID())
            {
                isInGame = true;
                cardPosition = i;
            }
        }

        if(isInGame)
            switch (move.getCardID())
            {
                case 20:
                    grozingPliers();
                    break;
                default:
                notifyFailure("Carta inesistente");



            }
    }

    @Override
    public void run() {
        checksEffect();
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg!= null) {
            response = (PlayerMove) arg;
            latch.countDown();
        }
    }

    /**
     * Grzoing Pliers Crad effect
     */
    private void grozingPliers() {
        int index;
        int newValue;
        if (new GrozingPliers().isUsable(player, firsTurn)) {
            notifySuccess();
            index = askDieFromReserve();
            if(index >-1)
            {
                newValue = askNewValue(index);
                String result = CardEffects.changeValue(board.getReserve().get(index),false,newValue);
                network.sendReq(new ReserveStatus(board.getReserve()),player.getName());
                if(result == null)
                {
                        result = setDie(index);
                        if (result == null) {
                            player.setCardRights(firsTurn, false);
                            player.setPlacementRights(firsTurn, false);
                            board.getTool(cardPosition).burnFavourPoints(player);
                            board.getTool(cardPosition).updateUsed();
                            updateGameState();
                        } else
                            network.sendReq(new CardExecutionError(result), player.getName());
                }
                else
                    network.sendReq(new CardExecutionError(result),player.getName());
                }
        }
        else
            notifyFailure(NO_PERMISSION);
        }

    /**
     * Asks and wait for adie from the reserve
     * @return die's position inside the reserve
     */
    private int askDieFromReserve() {
        latch = new CountDownLatch(1);
        network.sendReq(new AskDieFromReserve(), player.getName());
        if (waitUpdate() && response.toString().equals("DieFromReserve")) {
            DieFromReserve dieFromReserve = (DieFromReserve) response;
            return dieFromReserve.getIndex();
        }
        network.sendReq(new CardExecutionError(TOO_SLOW),player.getName());
        return -1;
    }

    /**
     * Asks and wait for a new value for a die
     * @param index die's position inside the reserve
     * @return die' new value
     */
    private int askNewValue(int index)
    {
        latch = new CountDownLatch(1);
        int val1 = board.getReserve().get(index).getValue() -1;
        int val2 = board.getReserve().get(index).getValue() +1;
        network.sendReq(new AskNewValue(val1,val2), player.getName());
        if (waitUpdate() && response.toString().equals("NewValue")) {
            NewValue newValue = (NewValue) response;
            return newValue.getNewValue();
        }
        network.sendReq(new CardExecutionError(TOO_SLOW),player.getName());
        return -1;
    }

    /**
     * Asks for a die to be set
     * @param index die's position inside the reserve
     * @return Error message or null if no error occurred
     */
    private String setDie(int index)
    {
        latch = new CountDownLatch(1);
        network.sendReq(new SetDie(index), player.getName());
        if (waitUpdate() && response.toString().equals("DieSet")) {
            DieSet dieSet = (DieSet) response;
            String result = DiePlacementLogic.insertDie(player,dieSet.getH(),dieSet.getW(),board.getReserve().get(index),true,true,true);
            if(result == null)
            {
                player.getGrid().setDie(dieSet.getH(),dieSet.getW(),board.getReserve().popDie(index));
            }
            return result;
        }
        network.sendReq(new CardExecutionError(TOO_SLOW),player.getName());
        return "Qualcosa è andato storto" ;
    }


    /**
     * Waits for the requested response
     * @return true if a response has come, false otherwise
     */
    private boolean waitUpdate()
    {
        try {
            return latch.await(20, TimeUnit.SECONDS);
        } catch (InterruptedException e)
        {
            Logger.getGlobal().log(Level.WARNING, "Interrupted while waiting");
            Thread.currentThread().interrupt();
        }

        return false;
    }


    /**
     * Updates game stets on all clients
     */
    private void updateGameState()
    {
        network.sendObj(new CardInfo(board.getTools(),board.getObjectives()));
        network.sendObj(new PlayerStatus(player, firsTurn));
        network.sendObj(new ReserveStatus(board.getReserve()));
        network.sendObj(new RoundTrackStatus(board.getRoundTrack()));
    }

}

