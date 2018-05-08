package it.polimi.se2018.view.app;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.observer.PlayerView;
import it.polimi.se2018.observer.ReserveView;
import it.polimi.se2018.observer.RoundTrackerView;
import it.polimi.se2018.util.Pair;
import it.polimi.se2018.view.view_util.cli_creators.CLICardViewCreator;
import it.polimi.se2018.view.view_util.cli_creators.CLIGridViewCreator;
import it.polimi.se2018.view.view_util.cli_creators.CLIReserveViewCreator;
import it.polimi.se2018.view.view_util.cli_creators.CLIScoreViewCreator;

import java.util.ArrayList;
import java.util.List;

/**
 * Class CLIApp represents the command line interface
 *
 * @author Mathyas Giudici
 */

public class CLIApp extends App {

    /**
     * Componets for CLI
     */
    private CLICardViewCreator cliCardViewCreator;
    private CLIGridViewCreator cliGridViewCreator;
    private CLIReserveViewCreator cliReserveViewCreator;
    private CLIScoreViewCreator cliScoreViewCreator;

    public CLIApp() {
        super();
        this.animationEnable = true;
        this.cliCardViewCreator = new CLICardViewCreator();
        this.cliGridViewCreator = new CLIGridViewCreator();
        this.cliReserveViewCreator = new CLIReserveViewCreator();
        this.cliScoreViewCreator = new CLIScoreViewCreator();
    }

    @Override
    public void animation(boolean enable) {
        this.animationEnable = enable;
    }

    @Override
    public void loginResult(boolean success) {
        //Control if animation is enabled
        if(!this.animationEnable){
            return;
        }

        if(success){
            System.out.println("Login riuscito con successo!");
        }
        else{
            System.out.println("Login NON riuscito!");
        }
    }

    @Override
    public void changeLayerResult(boolean successRMI) {
        //Control if animation is enabled
        if(!this.animationEnable){
            return;
        }

        if(successRMI){
            System.out.println("L'attuale layer è RMI.");
        }
        else{
            System.out.println("L'attuale layer è Socket.");
        }
    }

    @Override
    public void leaveMatchResult(boolean success) {
        //Control if animation is enabled
        if(!this.animationEnable){
            return;
        }

        if(success){
            System.out.println("Match lasciato con successo.");
        }
        else{
            System.out.println("Non sono riuscito a disconnettermi dal match.");
        }
    }

    @Override
    public void logoutResult(boolean success) {
        //Control if animation is enabled
        if(!this.animationEnable){
            return;
        }

        if(success){
            System.out.println("Logout riuscito con successo!");
        }
        else{
            System.out.println("Logout NON riuscito!");
        }
    }

    @Override
    public void pullLeaderBoard(List leaderBoard) {
        //Control if animation is enabled
        if(!this.animationEnable){
            return;
        }

        System.out.println("Leader Board:");
        System.out.println("____________________________");
        //TODO
    }

    @Override
    public void pullInvitate(List inviteList) {
        //Control if animation is enabled
        if(!this.animationEnable){
            return;
        }
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
