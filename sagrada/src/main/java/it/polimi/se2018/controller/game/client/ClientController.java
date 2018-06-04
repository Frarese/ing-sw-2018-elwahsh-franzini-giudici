package it.polimi.se2018.controller.game.client;

import it.polimi.se2018.controller.network.client.CommUtilizer;

import it.polimi.se2018.util.MatchIdentifier;
import it.polimi.se2018.util.MessageTypes;
import it.polimi.se2018.util.ScoreEntry;
import it.polimi.se2018.view.app.App;

import java.io.Serializable;
import java.util.List;

/**
 * Client controller
 * This class works with the network layer and pushes the events on the upper layer
 */
public class ClientController implements CommUtilizer {

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
        throw new UnsupportedOperationException();
    }

    @Override
    public void pushLeaderboard(List<ScoreEntry> list) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void pushUserList(List<ScoreEntry> list) {
        throw new UnsupportedOperationException();
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
        throw new UnsupportedOperationException();
    }

}
