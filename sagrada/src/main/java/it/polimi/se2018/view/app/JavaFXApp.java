package it.polimi.se2018.view.app;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.model.IntColorPair;
import it.polimi.se2018.observable.CardView;
import it.polimi.se2018.observable.PlayerView;
import it.polimi.se2018.observable.ReserveView;
import it.polimi.se2018.observable.RoundTrackerView;
import it.polimi.se2018.util.MatchIdentifier;
import it.polimi.se2018.util.PatternView;
import it.polimi.se2018.view.ViewActions;
import it.polimi.se2018.view.ViewMessage;
import it.polimi.se2018.view.ViewToolCardActions;
import it.polimi.se2018.view.observer.*;
import it.polimi.se2018.view.tools.DieViewCreator;
import it.polimi.se2018.view.tools.fx.alert.AlertBox;
import it.polimi.se2018.view.tools.fx.alert.ChangeLayerBox;
import it.polimi.se2018.view.tools.fx.controller.LobbyController;
import it.polimi.se2018.view.tools.fx.controller.ShowCardsController;
import it.polimi.se2018.view.tools.fx.creators.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

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
    private boolean isYourTurn;


    /**
     * FXML loading attributes
     */
    private FXMLLoader loader;
    private Pane root;

    /**
     * Class constructor to initialize creators
     *
     * @param viewActions         contains ViewActions class for View->Controller communication
     * @param viewToolCardActions contains ViewToolCardActions class for View->Controller communication (tool cards)
     * @param viewMessage         contains ViewMessage class for View->Controller communication (chat)
     */
    public JavaFXApp(ViewActions viewActions, ViewToolCardActions viewToolCardActions, ViewMessage viewMessage) {
        super(viewActions, viewToolCardActions, viewMessage);

        //Initializes Player Information
        this.ownerPlayerName = null;
        this.useRMI = false;
        this.isYourTurn = false;

        this.cardViewCreator = new FXCardViewCreator();
        this.roundTrackerViewCreator = new FXRoundTrackerViewCreator();
        this.reserveViewCreator = new FXReserveViewCreator();
        this.gridViewCreator = new FXGridViewCreator(ColorModel.RED.toJavaFXColor());

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
                logFxmlLoadError(e.getMessage());
            }
        } else {
            //Trying to load FXML
            try {
                loader = new FXMLLoader(getClass().getResource("fxmlFiles/login.fxml"));
                root = loader.load();

                loadScene(true);
            } catch (Exception e) {
                logFxmlLoadError(e.getMessage());
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
            ChangeLayerBox.close();
            useRMI = true;
        } else {
            message = "Layer attuale: Socket";
            ChangeLayerBox.close();
            useRMI = false;
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

            loadScene(true);
        } catch (Exception e) {
            logFxmlLoadError(e.getMessage());
        }


        Platform.runLater(() -> {
            LobbyController lobbyController = (LobbyController) JavaFXStageProducer.getController();
            lobbyController.setTables();
        });
    }


    @Override
    public void askPattern(PatternView pattern1, PatternView pattern2, PatternView pattern3, PatternView pattern4, CardView cardView) {
        //Adds CardView Observer
        cardViewObserver = new CardViewObserver(this);
        cardView.addObserver(cardViewObserver);

        //Save current player state
        this.cardViewCreator.setPrivateObjectiveCard(cardView.getPrivateObjectiveCard());
        this.cardViewCreator.setPublicObjectiveCards(cardView.getPublicObjectiveCards());
        this.cardViewCreator.setToolCards(cardView.getToolCards());

        //Control if animation is enabled
        if (!this.animationEnable) {
            return;
        }

        //Trying to load FXML
        try {
            loader = new FXMLLoader(getClass().getResource("fxmlFiles/showCards.fxml"));
            root = loader.load();

            loadScene(true);
            ShowCardsController showCardsController = loader.getController();
            showCardsController.saveState(pattern1, pattern2, pattern3, pattern4);
            showCardsController.display();

        } catch (Exception e) {
            logFxmlLoadError(e.getMessage());
        }
    }

    @Override
    public void initGame(List<PlayerView> players, ReserveView reserveView, RoundTrackerView roundTrackerView) {
        //Adds PlayerView Observer
        for (PlayerView playerView : players) {
            PlayerViewObserver playerViewObserver = new PlayerViewObserver(this);
            playerView.addObserver(playerViewObserver);
            playerViewObserverList.add(playerViewObserver);
        }

        //Save current players states
        for (PlayerView playerView : players) {
            this.players.add(setState(playerView));
        }

        //Adds ReserveView Observer
        reserveViewObserver = new ReserveViewObserver(this);
        reserveView.addObserver(reserveViewObserver);

        //Save current reserve state
        this.reserveViewCreator.setReserve(reserveView.getReserve());

        //Adds RoundTrackerView Observer
        roundTrackerViewObserver = new RoundTrackerViewObserver(this);
        roundTrackerView.addObserver(roundTrackerViewObserver);

        //Save current round tracker state
        this.roundTrackerViewCreator.setRound(roundTrackerView.getRound());
        this.roundTrackerViewCreator.setRoundTracker(roundTrackerView.getRoundTracker());

        this.isYourTurn = false;

        //Trying to load FXML
        try {
            loader = new FXMLLoader(getClass().getResource("fxmlFiles/game.fxml"));
            root = loader.load();

            loadScene(true);
            JavaFXStageProducer.getStage().setMaximized(true);
        } catch (Exception e) {
            logFxmlLoadError(e.getMessage());
        }

        this.viewActions.endInitGame();
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
        throw new UnsupportedOperationException();
    }

    @Override
    public void setDieResult(boolean result, String errorString) {
        //Check if animation is enabled
        if (!this.animationEnable) {
            return;
        }

        //Print result
        if (result) {
            notifySimpleAlert("Dado piazzato!");
        } else {
            notifySimpleAlert("Non sei riuscito a piazzare il dado: " + errorString);
        }

    }

    @Override
    public void addUpdate(String playerName, int height, int width) {
        //Check if animation is enabled
        if (!this.animationEnable) {
            return;
        }

        //Check if is my ID
        if (playerName.equals(this.ownerPlayerName)) {
            this.setDieResult(true, null);
            return;
        }

        //Search information
        PlayerState playerView = this.searchPlayerViewByName(this.players, playerName);
        if (playerView != null) {
            //Print
            DieViewCreator dieViewCreator = new FXDieViewCreator(100);
            Platform.runLater(() -> AlertBox.display("Notifica", " ha posizionato il dado in posizione (" + height + "," + width + ").",
                    ((FXDieViewCreator) dieViewCreator).makeDie(playerView.getPlayerGrid()[height][width])));
        }
    }

    @Override
    public void useToolCardResult(boolean result, String errorString) {
        //Check if animation is enabled
        if (!this.animationEnable) {
            return;
        }

        //Print result
        if (result) {
            notifySimpleAlert("Effetto carta completato!");
        } else {
            notifySimpleAlert("Non sei riuscito ad usare la carta: " + errorString);
        }
    }

    @Override
    public void useToolCardUpdate(String playerName, int card) {
        //Check if animation is enabled
        if (!this.animationEnable) {
            return;
        }

        //Check if is my ID
        if (playerName.equals(this.ownerPlayerName)) {
            this.useToolCardResult(true, null);
            return;
        }

        //Search information
        PlayerState playerView = this.searchPlayerViewByName(this.players, playerName);
        if (playerView != null) {
            String player = playerView.getPlayerName();
            //Print
            notifySimpleAlert(player + " ha usato la carta: " + this.cardViewCreator.makeCard(card));
        }
    }

    @Override
    public void passTurnResult(boolean result) {
        //Check if animation is enabled
        if (!this.animationEnable) {
            return;
        }

        //Print result
        if (result) {
            notifySimpleAlert("Turno passato con successo!");
            this.isYourTurn = false;
        } else {
            notifySimpleAlert("Non sei riuscito a passare il turno");
        }

    }

    @Override
    public void passTurnUpdate(String playerName) {
        //Check if animation is enabled
        if (!this.animationEnable) {
            return;
        }

        //Check if is my ID
        if (playerName.equals(this.ownerPlayerName)) {
            this.passTurnResult(true);
            return;
        }

        //Search information
        PlayerState playerView = this.searchPlayerViewByName(this.players, playerName);
        if (playerView != null) {
            String player = playerView.getPlayerName();
            //Print
            notifySimpleAlert(player + " ha passato il suo turno!");
        }
    }

    @Override
    public void gameEnd(MatchIdentifier matchIdentifier, int player0, int player1, int player2, int player3) {
        //Check if animation is enabled
        if (!this.animationEnable) {
            return;
        }

        //Print
        this.scoreViewCreator = new FXScoreViewCreator();
        Platform.runLater(() -> JavaFXStageProducer.getStage().setScene(new Scene((VBox) scoreViewCreator.display(matchIdentifier, player0, player1, player2, player3))));
        this.isYourTurn = false;
    }

    @Override
    public void abortMatch() {
        this.viewActions.askLobby();
        this.isYourTurn = false;
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
    public void setDieOnGrid(IntColorPair die) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void selectDieFromRoundTracker() {
        throw new UnsupportedOperationException();

    }

    @Override
    public void selectFace(IntColorPair die) {
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
            JavaFXStageProducer.getStage().centerOnScreen();
        });
    }

    /**
     * Call and show an AlertBox
     *
     * @param message contains the message to show
     */
    private void notifySimpleAlert(String message) {
        Platform.runLater(() ->
                AlertBox.notifyBox(message)
        );
    }

    /**
     * Logs error message if FXML hasn't been loaded
     */
    public static void logFxmlLoadError(String error) {
        String message = "Non sono riuscito a caricare FXML: " + error;
        Logger.getGlobal().log(Level.WARNING, message);
    }
}
