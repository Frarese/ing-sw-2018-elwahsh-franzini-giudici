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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
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
    private JavaFXStageProducer stageProducer;

    //Others
    private FXCardViewCreator fxCardViewCreator;
    private FXGridViewCreator fxGridViewCreator;
    private FXRoundTrackerViewCreator fxRoundTrackerViewCreator;
    private FXScoreViewCreator fxScoreViewCreator;

    /**
     * Class constructor
     */
    JavaFXApp() {
        this.stageProducer = new JavaFXStageProducer();
        try {
            this.stageProducer.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void animation(boolean enable) {
        this.animationEnable = enable;
    }

    @Override
    public void startLogin(boolean displayWelcome) {
        //TODO check displayWelcome
        stageProducer.getStage().setTitle("Sagrada Game");
        stageProducer.getStage().getIcons().add(new Image(JavaFXStageProducer.class.getResourceAsStream("/it/polimi/se2018/view/images/others/icon.png")));

        try {
            Parent root = FXMLLoader.load(stageProducer.getClass().getResource("fxml_files/start.fxml"));
            stageProducer.getStage().setScene(new Scene(root));
        } catch (IOException e) {
            Logger.getGlobal().log(Level.WARNING, "Non sono riuscito a caricare FXML");
        }

        stageProducer.getStage().show();
        stageProducer.getStage().setResizable(false);
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

    public void createLoginPage() {
        Scene scene = new Scene(new AnchorPane());

        stageProducer.getStage().setScene(scene);
    }
}
