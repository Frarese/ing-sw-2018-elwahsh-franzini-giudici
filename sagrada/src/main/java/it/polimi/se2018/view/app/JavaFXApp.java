package it.polimi.se2018.view.app;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.observer.PlayerView;
import it.polimi.se2018.observer.ReserveView;
import it.polimi.se2018.observer.RoundTrackerView;
import it.polimi.se2018.util.Pair;
import it.polimi.se2018.view.view_util.fx_creators.FXCardViewCreator;
import it.polimi.se2018.view.view_util.fx_creators.FXGridViewCreator;
import it.polimi.se2018.view.view_util.fx_creators.FXRoundTrackerViewCreator;
import it.polimi.se2018.view.view_util.fx_creators.FXScoreViewCreator;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
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
     * Componets for GUI
     */
    private JavaFXStageProducer stageProducer;

    //Others
    private FXCardViewCreator fxCardViewCreator;
    private FXGridViewCreator fxGridViewCreator;
    private FXRoundTrackerViewCreator fxRoundTrackerViewCreator;
    private FXScoreViewCreator fxScoreViewCreator;

    public JavaFXApp() {
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
        stageProducer.getStage().getIcons().add(new Image(JavaFXStageProducer.class.getResourceAsStream("/it/polimi/se2018/view/view_img/others/icon.png")));

        try {
            Parent root = FXMLLoader.load(stageProducer.getClass().getResource("fxml_files/start.fxml"));
            stageProducer.getStage().setScene(new Scene(root));
        }catch (IOException e){
            Logger.getGlobal().log(Level.WARNING,"Non sono riuscito a caricare FXML");
        }

        stageProducer.getStage().show();
        stageProducer.getStage().setResizable(false);
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

    public void createLoginPage(){
        Scene scene = new Scene(new AnchorPane());

        stageProducer.getStage().setScene(scene);
    }
}
