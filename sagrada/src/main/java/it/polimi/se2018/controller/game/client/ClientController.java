package it.polimi.se2018.controller.game.client;

import it.polimi.se2018.controller.network.client.CommUtilizer;
import it.polimi.se2018.events.Event;
import it.polimi.se2018.events.messages.PatternSelect;
import it.polimi.se2018.events.messages.ServerMessageHandler;
import it.polimi.se2018.model.IntColorPair;
import it.polimi.se2018.observable.CardView;
import it.polimi.se2018.observable.PlayerView;
import it.polimi.se2018.observable.ReserveView;
import it.polimi.se2018.observable.RoundTrackerView;
import it.polimi.se2018.util.*;
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
    private final App app;
    private ArrayList<PlayerView> players;
    private ReserveView reserve;
    private RoundTrackerView roundTrack;
    private CardView cards;
    private MatchIdentifier mId;
    private ArrayList<PatternView> patternsReceived;



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
        Logger.getGlobal().log(Level.WARNING,"Aborting match as requested from server");
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
        cleanUp();
        players.add(new PlayerView(mId.player0,0));
        players.add(new PlayerView(mId.player1,1));
        if(mId.playerCount >2)
            players.add(new PlayerView(mId.player2,2));
        if(mId.playerCount >3)
            players.add(new PlayerView(mId.player3,3));

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
        app.logoutResult(true);
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
     * Adds pattern to patternsReceived and if the pattern quota is reached it calls the view
     * @param patternSelect pattern sent by the server
     */
    public void addPatternView(PatternSelect patternSelect)
    {
        PatternView pattern = new PatternView(patternSelect.getName(),patternSelect.getFavourPoints(),patternSelect.getPattern());
        if(!patternsReceived.contains(pattern))
        {
            patternsReceived.add(pattern);
            if(patternsReceived.size() == 4)
                app.askPattern(patternsReceived.get(0),patternsReceived.get(1),patternsReceived.get(2),patternsReceived.get(3),cards);
        }
    }

    /**
     * Resets model view
     */
    private void cleanUp()
    {
        players = new ArrayList<>();
        reserve = new ReserveView(new IntColorPair[9]);
        roundTrack = new RoundTrackerView(0,new IntColorPair[9][10]);
        cards = new CardView(null,null,null);
        patternsReceived=new ArrayList<>();
    }


}
