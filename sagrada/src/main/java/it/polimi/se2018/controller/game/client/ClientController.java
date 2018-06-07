package it.polimi.se2018.controller.game.client;

import it.polimi.se2018.controller.network.client.CommUtilizer;

import it.polimi.se2018.events.Event;

import it.polimi.se2018.events.messages.ServerMessageHandler;
import it.polimi.se2018.observable.CardView;
import it.polimi.se2018.observable.PlayerView;
import it.polimi.se2018.observable.ReserveView;
import it.polimi.se2018.observable.RoundTrackerView;
import it.polimi.se2018.util.MatchIdentifier;
import it.polimi.se2018.util.MessageTypes;
import it.polimi.se2018.util.Pair;
import it.polimi.se2018.util.ScoreEntry;
import it.polimi.se2018.view.app.App;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Client controller
 * This class works with the network layer and pushes the events on the upper layer
 * @author Alì El Wahsh
 */
public class ClientController implements CommUtilizer {

    private String localPlayer;
    private App app;
    private ArrayList<PlayerView> players = new ArrayList<>();
    private ReserveView reserve = new ReserveView(new Pair[9]);
    private RoundTrackerView roundTrack = new RoundTrackerView(0,new Pair[9][10]);
    private CardView cards;
    private MatchIdentifier mId;


    public ClientController(App app)
    {
        this.app = app;
    }

    @Override
    public void receiveObject(Serializable obj) {
        ServerMessageHandler.handle(this,(Event) obj, app,players,reserve,roundTrack,cards);
    }

    @Override
    public void receiveRequest(Serializable req) {
        ServerMessageHandler.handle(this,(Event) req, app,players,reserve,roundTrack,cards);
    }

    @Override
    public void abortMatch() {
        app.abortMatch();
    }

    @Override
    public void notifyInvite(MatchIdentifier match) {
        app.pullInvitate(match);
    }

    @Override
    public void notifyMatchEnd(int playerScore0, int playerScore1, int playerScore2, int playerScore3) {
        app.gameEnd(mId,playerScore0,playerScore1,playerScore2,playerScore3);
    }

    @Override
    public void notifyMatchStart() {

        players.add(new PlayerView(mId.player0,0));
        players.add(new PlayerView(mId.player1,1));
        if(mId.playerCount >2)
            players.add(new PlayerView(mId.player2,2));
        if(mId.playerCount >3)
            players.add(new PlayerView(mId.player3,3));
        app.initGame(players,reserve,roundTrack);


    }

    @Override
    public void notifyUserLeft(String usn) {
        app.otherPlayerLeave(usn);
    }

    @Override
    public void pushLeaderboard(List<ScoreEntry> list) {
        app.pullLeaderBoard(list);
    }

    @Override
    public void pushUserList(List<ScoreEntry> list) {
        app.pullConnectedPlayers(list);
    }

    @Override
    public void notifyCommDropped() {
        app.startLogin(false);
        cleanUp();
    }

    @Override
    public void pushChatMessage(String msg, MessageTypes type, String source) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void notifyReconnect() {
        Logger.getGlobal().log(Level.INFO, "Network experiencing troubles but reconnected");
    }

    @Override
    public void notifyUserReconnected(String usn) {
        app.otherPlayerReconnection(usn);
    }

    /**
     * Setter for mId
     * @param id new id
     */
    public void setMId(MatchIdentifier id)
    {
        mId = id;
    }

    /**
     * Sets local player
     * @param player LocalPlayer
     */
    public void setLocalPlayer(String player) {
        this.localPlayer = player;
    }

    /**
     * Getter for LocalPlayer
     * @return localPlayer's name
     */
    public String getLocalPlayer() {
        return localPlayer;
    }

    /**
     * Resets model view
     */
    private void cleanUp()
    {
        players = new ArrayList<>();
        reserve = new ReserveView(new Pair[9]);
        roundTrack = new RoundTrackerView(0,new Pair[9][10]);
        cards = new CardView(null,null,null);
    }


}
