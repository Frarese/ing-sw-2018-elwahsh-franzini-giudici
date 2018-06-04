package it.polimi.se2018.controller.game.server;

import it.polimi.se2018.controller.game.EventBus;
import it.polimi.se2018.controller.game.server.handlers.DiePlacementHandler;
import it.polimi.se2018.controller.game.server.handlers.ToolCardHandlerFactory;
import it.polimi.se2018.controller.network.server.MatchController;
import it.polimi.se2018.controller.network.server.MatchNetworkInterface;
import it.polimi.se2018.events.actions.DiePlacementMove;
import it.polimi.se2018.events.actions.PassTurn;
import it.polimi.se2018.events.actions.PlayerMove;
import it.polimi.se2018.events.actions.UseToolCardMove;
import it.polimi.se2018.events.messages.PlayerStatus;
import it.polimi.se2018.events.messages.ReserveStatus;
import it.polimi.se2018.events.messages.RoundTrackStatus;
import it.polimi.se2018.events.messages.TurnStart;
import it.polimi.se2018.model.Board;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.util.MatchIdentifier;

import java.io.Serializable;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Principal class of the controller inside the server
 * @author Alì El Wahsh
 */
public class ServerController implements MatchController, Runnable {


    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<Player> offlinePlayers = new ArrayList<>();
    private Board board;
    private Round round;
    private EventBus inBus;
    private boolean done;
    private MatchNetworkInterface network;
    private Timer timer;
    private static final int TIME = 90000;

    /**
     * ServerController's constructor
     * @param mId Match identifier
     * @param network network output
     */
    ServerController(MatchIdentifier mId, MatchNetworkInterface network)
    {
        List<String> temp = new ArrayList<>();
        temp.add(mId.player0);
        temp.add(mId.player1);
        temp.add(mId.player2);
        temp.add(mId.player3);
        for(int i = 0; i<mId.playerCount; i++)
            players.add(new Player(temp.get(i), i));

        Collections.shuffle(players);
        this.board = new Board();
        this.round = new Round(this.players);
        this.inBus = new EventBus();
        new Thread(inBus, "eventBus" + mId.hashCode()).start();
        this.network = network;
    }


    private void startMatch()
    {
        board.rollDiceOnReserve(players.size());
        for(Player p: players)
            sendMatchStatus(p);

        network.sendObj(new TurnStart(round.getCurrentPlayer().getName()));
        timer = new Timer();
        timer.schedule(new TimeSUp(round.getCurrentPlayer().getName()),TIME);
    }

    /**
     * After the 90 seconds limit is reached ends the current player's turn
     */
    private class TimeSUp extends TimerTask
    {
        private String name;

        private TimeSUp(String name)
        {
            this.name = name;
        }

        @Override
        public void run() {
            if(name.equals(round.getCurrentPlayer().getName()))
            {
            newTurn();
            }
        }
    }

    @Override
    public void handleRequest(String sender, Serializable req) {

        for(Player p: players)
        {
            if(!offlinePlayers.contains(p) && p.getName().equals(sender))
                managePlayerMove((PlayerMove) req);
        }
    }

    @Override
    public void kill() {
        inBus.stopListening();
        done = true;
    }

    @Override
    public void userReconnected(String username) {
        for(Player p: players)
        {
            if(offlinePlayers.contains(p) && p.getName().equals(username)) {
                offlinePlayers.remove(p);
                sendMatchStatus(p);
            }
        }
    }

    @Override
    public void playerLeft(String username, boolean permanent) {
            for(Player p: players)
            {
                if(!offlinePlayers.contains(p) && p.getName().equals(username))
                    offlinePlayers.add(p);
            }
    }


    /**
     * Sets all the necessary flags for a new round and initialize the new order
     * If round 10 has passed, ends the game
     */
    private void manageNewRound()
    {
        if(round.getRoundNumber() <10) {
            for (Player p : players) {
                p.setPlacementRights(true, true);
                p.setPlacementRights(false, true);
                p.setCardRights(true, true);
                p.setCardRights(false, true);
            }
            round.prepareNextRound();
            board.putReserveOnRoundTrack();
            network.sendObj(new TurnStart(round.getCurrentPlayer().getName()));
            timer = new Timer();
            timer.schedule(new TimeSUp(round.getCurrentPlayer().getName()), TIME);
        }
            else
            {
                network.end(0,0,0,0);
                inBus.stopListening();
                done = true;
            }
    }

    /**
     * Sets a new turn and if the round is ended, calls manageNewRound
     * Notifies all player of the new turn
     */
    private synchronized void newTurn()
    {
        timer.cancel();
        round.nextTurn();
        if(round.getCurrentPlayer() == null)
            manageNewRound();
        else{
            network.sendObj(new TurnStart(round.getCurrentPlayer().getName()));
            timer = new Timer();
            timer.schedule(new TimeSUp(round.getCurrentPlayer().getName()),TIME);
        }
    }

    /**
     * Handles all player's move
     * If it's a placement it is handled by a DiePlacementHandler
     * If it's a tool card, by a CardHandler (called bya factory)
     * If it's a pass turn it ends the player turn
     * Else it will be handled by running handlers
     * @param move player move
     */
    private void managePlayerMove(PlayerMove move)
    {
        if(move.getPlayerName().equals(round.getCurrentPlayer().getName())) {
            switch (move.toString())
            {
                case "Placement":
                    DiePlacementMove placement = (DiePlacementMove) move;
                    new Thread(new DiePlacementHandler(round.getCurrentPlayer(),
                            placement,
                            board.getReserve(),
                            round.getFirstTurn(),
                            network),
                            "PlacementHandler" + round.getCurrentPlayer().hashCode()).start();
                    break;

                case "UseCard":
                    UseToolCardMove useCard = (UseToolCardMove) move;
                    new Thread(new ToolCardHandlerFactory()
                            .getCardHandler(round.getCurrentPlayer(),
                                    useCard,
                                    board,
                                    round.getFirstTurn(),
                                    network), "CardHandler" + round.getCurrentPlayer().hashCode()).start();
                    break;

                case "PassTurn":
                    newTurn();
                    break;


                default : inBus.asyncPush(move);



            }
        }
    }

    /**
     * Sends the status of the match to a single player
     * @param player player to be updated about the status of the match
     */
    private void sendMatchStatus(Player player)
    {
        network.sendReq(new ReserveStatus(board.getReserve()),player.getName());
        network.sendReq(new RoundTrackStatus(board.getRoundTrack()),player.getName());

        for(Player p: players)
            network.sendReq(new PlayerStatus(p),player.getName());
    }

    private void sendPatternCards()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void run() {

        sendPatternCards();

        while(!done)
        {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e)
            {
                Logger.getGlobal().log(Level.SEVERE, e.toString());
                Thread.currentThread().interrupt();
            }
        }
    }
}
