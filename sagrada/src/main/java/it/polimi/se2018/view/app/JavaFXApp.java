package it.polimi.se2018.view.app;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.observable.CardView;
import it.polimi.se2018.observable.PlayerView;
import it.polimi.se2018.observable.ReserveView;
import it.polimi.se2018.observable.RoundTrackerView;
import it.polimi.se2018.util.MatchIdentifier;
import it.polimi.se2018.util.Pair;
import it.polimi.se2018.util.PatternView;
import it.polimi.se2018.util.ScoreEntry;
import it.polimi.se2018.view.ViewActions;
import it.polimi.se2018.view.ViewMessage;
import it.polimi.se2018.view.ViewToolCardActions;
import it.polimi.se2018.view.observer.PlayerState;
import it.polimi.se2018.view.tools.fx.alert.AlertBox;
import it.polimi.se2018.view.tools.fx.creators.FXCardViewCreator;
import it.polimi.se2018.view.tools.fx.creators.FXGridViewCreator;
import it.polimi.se2018.view.tools.fx.creators.FXRoundTrackerViewCreator;
import it.polimi.se2018.view.tools.fx.creators.FXScoreViewCreator;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
    private String ownerPlayerName;
    private boolean useRMI;
    private boolean isYourTurn;

    /**
     * Components for GUI
     */
    private FXCardViewCreator fxCardViewCreator;
    private FXGridViewCreator fxGridViewCreator;
    private FXRoundTrackerViewCreator fxRoundTrackerViewCreator;
    private FXScoreViewCreator fxScoreViewCreator;

    /**
     * FXML loading attributes
     */
    private FXMLLoader loader;
    private Pane root;

    /**
     * Class constructor to initialize creators
     *  @param viewActions         contains ViewActions class for View->Controller communication
     * @param viewToolCardActions contains ViewToolCardActions class for View->Controller communication (tool cards)
     * @param viewMessage         contains ViewMessage class for View->Controller communication (chat)
     */
    public JavaFXApp(ViewActions viewActions, ViewToolCardActions viewToolCardActions, ViewMessage viewMessage) {
        super(viewActions, viewToolCardActions, viewMessage);

        //Initializes Player Information
        this.ownerPlayerName = null;
        this.useRMI = false;
        this.isYourTurn = false;

        this.invites = new ArrayList<>();

        this.openWindow();
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
            //Trying to load FXML
            try {
                loader = new FXMLLoader(getClass().getResource("fxmlFiles/start.fxml"));
                root = loader.load();

                loadScene(false);
            } catch (Exception e) {
                logFxmlLoadError();
            }
        } else {
            //Trying to load FXML
            try {
                loader = new FXMLLoader(getClass().getResource("fxmlFiles/login.fxml"));
                root = loader.load();

                loadScene(true);
            } catch (Exception e) {
                logFxmlLoadError();
            }
        }
    }

    public void tryLogin(String name, boolean useRMI) {
        this.ownerPlayerName = name;
        this.useRMI = useRMI;
    }

    @Override
    public void loginResult(boolean success, String error) {
        //Check if animation is enabled
        if (!this.animationEnable) {
            return;
        }

        if (success) {
            notifySimpleAlert("Login riuscito con successo");
            this.viewActions.askLobby();
        } else {
            notifySimpleAlert("Login NON riuscito");
        }
    }

    @Override
    public void changeLayerResult(boolean successRMI) {
        //Check if animation is enabled
        if (!this.animationEnable) {
            return;
        }

        String message;
        if (successRMI) {
            message = "Layer attuale: RMI";
        } else {
            message = "Layer attuale: Socket";
        }

        notifySimpleAlert(message);
    }

    @Override
    public void leaveMatchResult(boolean success) {
        //Check if animation is enabled
        if (!this.animationEnable) {
            return;
        }

        if (success) {
            notifySimpleAlert("Match lasciato con successo.");
            this.viewActions.askLobby();
        } else {
            notifySimpleAlert("Non sono riuscito a disconnettermi dal match.");
        }
    }

    @Override
    public void logoutResult(boolean success) {
        //Check if animation is enabled
        if (!this.animationEnable) {
            return;
        }

        if (success) {
            notifySimpleAlert("Logout riuscito con successo!");
            this.startLogin(false);
        } else {
            notifySimpleAlert("Logout NON riuscito!");
        }
    }

    @Override
    public void createLobby() {
        //Check if animation is enabled
        if (!this.animationEnable) {
            return;
        }

        //Trying to load FXML
        try {
            loader = new FXMLLoader(getClass().getResource("fxmlFiles/lobby.fxml"));
            root = loader.load();

            loadScene(false);
        } catch (Exception e) {
            logFxmlLoadError();
        }
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
    public void askPattern(PatternView pattern1, PatternView pattern2, PatternView pattern3, PatternView pattern4, CardView cardView) {
        //Control if animation is enabled
        if (!this.animationEnable) {
            return;
        }

        //Trying to load FXML
        try {
            loader = new FXMLLoader(getClass().getResource("fxmlFiles/patternSelection.fxml"));
            root = loader.load();

            loadScene(true);
        } catch (Exception e) {
            logFxmlLoadError();
        }
    }

    @Override
    public void initGame(List<PlayerView> players, ReserveView reserveView, RoundTrackerView roundTrackerView) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void otherPlayerLeave(String playerName) {
        //Check if animation is enabled
        if (!this.animationEnable) {
            return;
        }

        PlayerState player = this.searchPlayerViewByName(players, playerName);
        String message = player.getPlayerName() + " ha lasciato il gioco.";
        notifySimpleAlert(message);
    }

    @Override
    public void otherPlayerReconnection(String playerName) {
        //Check if animation is enabled
        if (!this.animationEnable) {
            return;
        }

        PlayerState player = this.searchPlayerViewByName(players, playerName);
        String message = player.getPlayerName() + " è rientrato in gioco.";
        notifySimpleAlert(message);
    }

    @Override
    public void startTurn() {
        this.isYourTurn = true;
        //Check if animation is enabled
        if (!this.animationEnable) {
            return;
        }

        //TODO enable JavaFx FXML
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
    public void gameEnd(MatchIdentifier matchIdentifier, int player0, int player1, int player2, int player3) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void abortMatch() {

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
     */
    private void openWindow() {
        class FxAppLauncher implements Runnable {
            @Override
            public void run() {
                Application.launch(JavaFXStageProducer.class, new String[1]);
            }
        }

        Thread thread = new Thread(new FxAppLauncher());
        thread.start();

        JavaFXStageProducer.setApp(this);
    }

    /**
     * Load a new Scene in FX Application
     *
     * @param resizable sets resizable of windows
     */
    private void loadScene(boolean resizable) {
        Platform.runLater(() -> {
            JavaFXStageProducer.getStage().setScene(new Scene(root));
            JavaFXStageProducer.setController(loader.getController());
            JavaFXStageProducer.getStage().setResizable(resizable);
        });
    }

    /**
     * Call and show an AlertBox
     *
     * @param title   contains the alert box's title
     * @param message contains the message to show
     * @param image   @Nullable contains the image to show, if it's null AlertBox doesn't show image
     */
    private void showAlert(String title, String message, Image image) {
        Platform.runLater(() ->
                AlertBox.display(title, message, image)
        );
    }

    private void notifySimpleAlert(String message) {
        showAlert("Notifica", message, null);
    }

    /**
     * Logs error message if FXML hasn't been loaded
     */
    private void logFxmlLoadError() {
        Logger.getGlobal().log(Level.WARNING, "Non sono riuscito a caricare FXML");
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
