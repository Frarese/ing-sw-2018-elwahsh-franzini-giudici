package it.polimi.se2018.controller.game.server;

import it.polimi.se2018.controller.game.EventBus;
import it.polimi.se2018.controller.game.server.handlers.DiePlacementHandler;
import it.polimi.se2018.controller.network.server.MatchController;
import it.polimi.se2018.controller.network.server.MatchNetworkInterface;
import it.polimi.se2018.events.Event;
import it.polimi.se2018.events.actions.DiePlacementMove;
import it.polimi.se2018.events.actions.PlayerMove;
import it.polimi.se2018.model.Board;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.util.MatchIdentifier;

import java.io.Serializable;
import java.util.*;

/**
 * Principal class of the controller inside the server
 * @author Alì El Wahsh
 */
public class ServerController implements MatchController,Observer {


    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<Player> offlinePlayers = new ArrayList<>();
    private Board board;
    private Round round;
    private EventBus inBus;
    private boolean done;
    private MatchNetworkInterface network;

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

        board = new Board();
        this.round = new Round(this.players);
        this.inBus = new EventBus();
        new Thread(inBus, "eventBus").start();
        this.network = network;
    }

    @Override
    public void handleRequest(String sender, Serializable req) {

        for(Player p: players)
        {
            if(!offlinePlayers.contains(p) && p.getName().equals(sender))
                inBus.asyncPush((Event) req);
        }
    }

    @Override
    public void kill() {
        done = true;
    }

    @Override
    public void userReconnected(String username) {
        for(Player p: players)
        {
            if(offlinePlayers.contains(p) && p.getName().equals(username))
                offlinePlayers.remove(p);
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

    @Override
    public void update(Observable o, Object arg) {

        if(arg instanceof PlayerMove)
        {
            managePlayerMove((PlayerMove) arg);
        }
        else if(arg instanceof Event)
        {
            manageEvent((Event) arg);
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
            board.getRoundTrack().addAll(board.getReserve().popAllDice());
        }
            else
            {
                // call GameEnd
               done = true;
            }
    }

    /**
     * Sets a new turn and if the round is ended, calls manageNewRound
     */
    public void newTurn()
    {
        round.nextTurn();
        if(round.getCurrentPlayer() == null)
            manageNewRound();
    }

    /**
     * Calculates the total score for a single player
     * @param player player to be scored
     * @return player's score
     */
    private int totalScore(Player player)
    {
        throw new UnsupportedOperationException();
    }


    /**
     * Handles all player's move
     * @param move player move
     */
    private void managePlayerMove(PlayerMove move)
    {
        if(move.getPlayerID() == round.getCurrentPlayer().getId())
        {
            if(move instanceof DiePlacementMove)
            {
                DiePlacementMove placement = (DiePlacementMove) move;
                new Thread(new DiePlacementHandler(round.getCurrentPlayer(),placement,board.getReserve(),round.getFirstTurn(), network),"PlacementHandler").start();
            }
            else
            {
                throw new UnsupportedOperationException();
            }
        }
    }

    /**
     * Handles non game events, like disconnections
     * @param event non game event
     * */
    private void manageEvent(Event event)
    {
        throw new UnsupportedOperationException();
    }

}
