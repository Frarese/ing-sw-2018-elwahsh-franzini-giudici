package it.polimi.se2018.controller.game.client;

import it.polimi.se2018.controller.network.client.CommUtilizer;

import it.polimi.se2018.observable.CardView;
import it.polimi.se2018.observable.PlayerView;
import it.polimi.se2018.observable.ReserveView;
import it.polimi.se2018.observable.RoundTrackerView;
import it.polimi.se2018.util.MatchIdentifier;
import it.polimi.se2018.util.MessageTypes;
import it.polimi.se2018.util.ScoreEntry;
import it.polimi.se2018.view.app.App;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Client controller
 * This class works with the network layer and pushes the events on the upper layer
 * @author Alì El Wahsh
 */
public class ClientController implements CommUtilizer,Runnable {

    private App app;
    private ArrayList<PlayerView> players = new ArrayList<>();
    private ReserveView reserve;
    private RoundTrackerView roundTrack;
    private CardView cards;
    private MatchIdentifier mId;

    public ClientController(App app)
    {
        this.app = app;
    }

    @Override
    public void receiveObject(Serializable obj) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void receiveRequest(Serializable req) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void abortMatch() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void notifyInvite(MatchIdentifier match) {
        app.pullInvitate(match);
    }

    @Override
    public void notifyMatchEnd(int playerScore0, int playerScore1, int playerScore2, int playerScore3) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void notifyMatchStart() {
        throw new UnsupportedOperationException();
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
        throw new UnsupportedOperationException();
    }

    @Override
    public void pushChatMessage(String msg, MessageTypes type, String source) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void notifyReconnect() {
        throw new UnsupportedOperationException();
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


    @Override
    public void run() {

        throw new UnsupportedOperationException();
    }

}
