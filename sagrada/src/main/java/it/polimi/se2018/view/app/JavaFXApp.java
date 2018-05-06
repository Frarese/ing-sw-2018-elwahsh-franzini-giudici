package it.polimi.se2018.view.app;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.observer.PlayerView;
import it.polimi.se2018.observer.ReserveView;
import it.polimi.se2018.observer.RoundTrackerView;
import it.polimi.se2018.util.Pair;
import it.polimi.se2018.view.ViewActions;
import it.polimi.se2018.view.ViewToolCardActions;
import it.polimi.se2018.view.view_util.fx_creators.FXCardViewCreator;
import it.polimi.se2018.view.view_util.fx_creators.FXGridViewCreator;
import it.polimi.se2018.view.view_util.fx_creators.FXRoundTrackerViewCreator;
import it.polimi.se2018.view.view_util.fx_creators.FXScoreViewCreator;

import java.util.ArrayList;
import java.util.List;

/**
 * Class JavaFXApp represents the GUI using JavaFX
 *
 * @author Mathyas Giudici
 */

public class JavaFXApp extends App {

    /**
     * Componets for GUI
     */
    private JavaFXStageProducer stageProducer;
    private FXCardViewCreator fxCardViewCreator;
    private FXGridViewCreator fxGridViewCreator;
    private FXRoundTrackerViewCreator fxRoundTrackerViewCreator;
    private FXScoreViewCreator fxScoreViewCreator;

    public JavaFXApp() {
    }

    @Override
    public void animation(boolean enable) {

    }

    @Override
    public void loginResult(boolean success) {

    }

    @Override
    public void changeLayerResult(boolean successRMI) {

    }

    @Override
    public void leaveMatchResult(boolean success) {

    }

    @Override
    public void logoutResult(boolean success) {

    }

    @Override
    public void pullLeaderBoard(List leaderBoard) {

    }

    @Override
    public void pullInvitate(List inviteList) {

    }

    @Override
    public void pullConnectedUsers(List users) {

    }

    @Override
    public void askPattern(Pair pattern1, Pair pattern2, Pair pattern3, Pair pattern4) {

    }

    @Override
    public void initGame(List players, int yourPrivateObjectiveCard, ArrayList publicObjectiveCards, ArrayList toolCards, RoundTrackerView roundTracker) {

    }

    @Override
    public void otherPlayerLeave(int playerID) {

    }

    @Override
    public void otherPlayerReconnection(int playerID) {

    }

    @Override
    public void startTurn(PlayerView player, ReserveView reserve, RoundTrackerView roundTracker) {

    }

    @Override
    public void setDieResult(boolean result, String errorString) {

    }

    @Override
    public void cannotUseReserve() {

    }

    @Override
    public void addUpdate(int playerID, int height, int width, int reserveIndex) {

    }

    @Override
    public void useToolCardResult(boolean result, String errorString) {

    }

    @Override
    public void useToolCardUpdate(int playerID, int card) {

    }

    @Override
    public void passTurnResult(boolean result) {

    }

    @Override
    public void passTurnUpdate(int playerID) {

    }

    @Override
    public void gameEnd(ArrayList scores) {

    }

    @Override
    public void selectDieFromReserve() {

    }

    @Override
    public void selectNewValueForDie(int up, int down) {

    }

    @Override
    public void updateReserve() {

    }

    @Override
    public void selectDieFromGrid() {

    }

    @Override
    public void setDieOnGrid(Pair die) {

    }

    @Override
    public void selectDieFromRoundTracker() {

    }

    @Override
    public void selectFace(Pair die) {

    }

    @Override
    public void selectDieFromGridByColor(ColorModel color) {

    }
}
