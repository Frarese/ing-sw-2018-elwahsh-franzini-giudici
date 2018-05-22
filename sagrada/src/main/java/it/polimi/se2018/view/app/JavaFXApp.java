package it.polimi.se2018.view.app;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.observer.PlayerView;
import it.polimi.se2018.observer.ReserveView;
import it.polimi.se2018.observer.RoundTrackerView;
import it.polimi.se2018.util.MatchIdentifier;
import it.polimi.se2018.util.Pair;
import it.polimi.se2018.util.ScoreEntry;
import it.polimi.se2018.view.tools.fx.creators.FXCardViewCreator;
import it.polimi.se2018.view.tools.fx.creators.FXGridViewCreator;
import it.polimi.se2018.view.tools.fx.creators.FXRoundTrackerViewCreator;
import it.polimi.se2018.view.tools.fx.creators.FXScoreViewCreator;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class JavaFXApp represents the GUI using JavaFX
 *
 * @author Mathyas Giudici
 */

public class JavaFXApp extends App {

    /**
     * Components for GUI
     */

    private FXCardViewCreator fxCardViewCreator;
    private FXGridViewCreator fxGridViewCreator;
    private FXRoundTrackerViewCreator fxRoundTrackerViewCreator;
    private FXScoreViewCreator fxScoreViewCreator;

    /**
     * Class constructor to initialize creators
     */
    JavaFXApp(String[] args) {
        this.openWindow(args);
    }

    @Override
    public void animation(boolean enable) {
        this.animationEnable = enable;
    }

    @Override
    public void startLogin(boolean displayWelcome) {
        //Control if animation is enabled
        if (!this.animationEnable) {
            return;
        }

        //Check if have to display welcome page
        if (displayWelcome) {

            //Variables
            FXMLLoader loader;
            Pane root;

            //Trying to load FXML
            try {
                loader = new FXMLLoader(getClass().getResource("fxmlFiles/start.fxml"));
                root = loader.load();

                //Create scene
                Platform.runLater(() -> {
                    JavaFXStageProducer.stage.setScene(new Scene(root));
                    JavaFXStageProducer.controller = loader.getController();
                    JavaFXStageProducer.stage.setResizable(false);
                });
            } catch (Exception e) {
                Logger.getGlobal().log(Level.WARNING, "Non sono riuscito a caricare FXML");
            }
        }


    }

    @Override
    public void loginResult(boolean success) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void changeLayerResult(boolean successRMI) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void leaveMatchResult(boolean success) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void logoutResult(boolean success) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void pullLeaderBoard(List<ScoreEntry> leaderBoard) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void pullInvitate(MatchIdentifier invite) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void askPattern(Pair<Integer, ColorModel>[][] pattern1, Pair<Integer, ColorModel>[][] pattern2, Pair<Integer, ColorModel>[][] pattern3, Pair<Integer, ColorModel>[][] pattern4) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void initGame(List<PlayerView> players, int yourPrivateObjectiveCard, int[] publicObjectiveCards, int[] toolCards, RoundTrackerView roundTracker) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void otherPlayerLeave(int playerID) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void otherPlayerReconnection(int playerID) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void startTurn(PlayerView player, ReserveView reserve, RoundTrackerView roundTracker) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void setDieResult(boolean result, String errorString) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void addUpdate(int playerID, int height, int width, int reserveIndex) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void useToolCardResult(boolean result, String errorString) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void useToolCardUpdate(int playerID, int card) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void passTurnResult(boolean result) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void passTurnUpdate(int playerID) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void gameEnd(List<ScoreEntry> scores) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void selectDieFromReserve() {
        throw new UnsupportedOperationException();

    }

    @Override
    public void selectNewValueForDie(int low, int high) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void updateReserve() {
        throw new UnsupportedOperationException();

    }

    @Override
    public void selectDieFromGrid() {
        throw new UnsupportedOperationException();

    }

    @Override
    public void setDieOnGrid(Pair die) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void selectDieFromRoundTracker() {
        throw new UnsupportedOperationException();

    }

    @Override
    public void selectFace(Pair die) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void selectDieFromGridByColor(ColorModel color) {
        throw new UnsupportedOperationException();

    }

    /**
     * Launches FX Application
     *
     * @param args contains the args passed at main
     */
    public void openWindow(String[] args) {
        Application.launch(JavaFXStageProducer.class, args);
        Platform.runLater(() -> JavaFXStageProducer.app = this);
    }
}
