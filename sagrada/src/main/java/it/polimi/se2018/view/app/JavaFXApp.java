package it.polimi.se2018.view.app;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.observer.PlayerView;
import it.polimi.se2018.util.MatchIdentifier;
import it.polimi.se2018.util.Pair;
import it.polimi.se2018.util.PatternView;
import it.polimi.se2018.util.ScoreEntry;
import it.polimi.se2018.view.ViewActions;
import it.polimi.se2018.view.ViewMessage;
import it.polimi.se2018.view.ViewToolCardActions;
import it.polimi.se2018.view.tools.fx.creators.FXCardViewCreator;
import it.polimi.se2018.view.tools.fx.creators.FXGridViewCreator;
import it.polimi.se2018.view.tools.fx.creators.FXRoundTrackerViewCreator;
import it.polimi.se2018.view.tools.fx.creators.FXScoreViewCreator;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
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
     * Player Information variables
     */
    private final int ownerPlayerID;
    private final String ownerPlayerName;
    private final boolean useRMI;
    private final boolean isYourTurn;

    /**
     * Components for GUI
     */
    private FXCardViewCreator fxCardViewCreator;
    private FXGridViewCreator fxGridViewCreator;
    private FXRoundTrackerViewCreator fxRoundTrackerViewCreator;
    private FXScoreViewCreator fxScoreViewCreator;

    /**
     * Class constructor to initialize creators
     *
     * @param viewActions         contains ViewActions class for View->Controller communication
     * @param viewToolCardActions contains ViewToolCardActions class for View->Controller communication (tool cards)
     * @param viewMessage         contains ViewMessage class for View->Controller communication (chat)
     * @param args                contains the arg passed at mains
     */
    public JavaFXApp(ViewActions viewActions, ViewToolCardActions viewToolCardActions, ViewMessage viewMessage, String[] args) {
        super(viewActions, viewToolCardActions, viewMessage);

        //Initializes Player Information
        this.ownerPlayerID = -1;
        this.ownerPlayerName = null;
        this.useRMI = false;
        this.isYourTurn = false;

        this.invites = new ArrayList<>();

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
                    JavaFXStageProducer.getStage().setScene(new Scene(root));
                    JavaFXStageProducer.setController(loader.getController());
                    JavaFXStageProducer.getStage().setResizable(false);
                });
            } catch (Exception e) {
                Logger.getGlobal().log(Level.WARNING, "Non sono riuscito a caricare FXML");
            }
        } else {
            //TODO
            Logger.getGlobal().log(Level.WARNING, "Login Operation");
        }


    }

    @Override
    public void loginResult(boolean success, String error) {
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
    public void createLobby() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void pullConnectedPlayers(List<ScoreEntry> players) {
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
    public void askPattern(PatternView pattern1, PatternView pattern2, PatternView pattern3, PatternView pattern4) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void initGame(List<PlayerView> players) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void otherPlayerLeave(String playerName) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void otherPlayerReconnection(String playerName) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void startTurn() {
        throw new UnsupportedOperationException();

    }

    @Override
    public void setDieResult(boolean result, String errorString) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void addUpdate(String playerName, int height, int width, int reserveIndex) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void useToolCardResult(boolean result, String errorString) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void useToolCardUpdate(String playerName, int card) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void passTurnResult(boolean result) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void passTurnUpdate(String playerName) {
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
    private void openWindow(String[] args) {
        Application.launch(JavaFXStageProducer.class, args);
        JavaFXStageProducer.setApp(this);
    }

    /**
     * Getter method for current player's ID
     *
     * @return the playerID
     */
    public int getOwnerPlayerID() {
        return ownerPlayerID;
    }

    /**
     * Getter method for current player's name
     *
     * @return the player's name
     */
    public String getOwnerPlayerName() {
        return ownerPlayerName;
    }

    /**
     * Getter method for boolean value that represents current type of connection
     *
     * @return boolean value that represents if user is using RMI connection
     */
    public boolean useRMI() {
        return useRMI;
    }
}
