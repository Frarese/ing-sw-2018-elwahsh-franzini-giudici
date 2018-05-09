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
import it.polimi.se2018.view.view_util.cli_interface.CLIPrinter;
import it.polimi.se2018.view.view_util.cli_interface.CLIReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class CLIApp represents the command line interface
 *
 * @author Mathyas Giudici
 */

public class CLIApp extends App {

    private CLIPrinter printer;
    private CLIReader reader;
    /**
     * Componets for CLI
     */
    private CLICardViewCreator cliCardViewCreator;
    private CLIGridViewCreator cliGridViewCreator;
    private CLIReserveViewCreator cliReserveViewCreator;
    private CLIScoreViewCreator cliScoreViewCreator;

    public CLIApp() {
        super();
        this.printer = new CLIPrinter();
        this.reader = new CLIReader();

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
    public void startLogin(boolean displayWelcome) {
        if (displayWelcome) {
            String welcome = "---------------------------------\n";
            welcome = welcome + "|         SAGRADA GAME          | \n";
            welcome = welcome + "---------------------------------\n";
            printer.print(welcome);
        }

        printer.print("Sei un nuovo utente? ");
        boolean newUser = reader.choise();

        printer.print("INSERIRE LE CREDENZIALI PER IL LOGIN");
        printer.print("Nome: ");
        String name = reader.read();
        printer.print("Password: ");
        String password = reader.read();

        printer.print("Inserire server: ");
        String server = reader.read();
        printer.print("Vuoi usare RMI connection? ");
        boolean isRMI = reader.choise();
        printer.print("Inserire object port: ");
        int objectPort = reader.readInt();
        int requestPort = -1;
        if(!isRMI){
            printer.print("Inserire request port: ");
            requestPort = reader.readInt();
        }

        this.viewActions.login(name,password,newUser,server,isRMI,objectPort,requestPort);
    }

    @Override
    public void loginResult(boolean success) {
        //Control if animation is enabled
        if (!this.animationEnable) {
            return;
        }

        if (success) {
            printer.print("Login riuscito con successo!");
        } else {
            printer.print("Login NON riuscito!");
        }
    }

    @Override
    public void changeLayerResult(boolean successRMI) {
        //Control if animation is enabled
        if (!this.animationEnable) {
            return;
        }

        if (successRMI) {
            printer.print("L'attuale layer è RMI.");
        } else {
            printer.print("L'attuale layer è Socket.");
        }
    }

    @Override
    public void leaveMatchResult(boolean success) {
        //Control if animation is enabled
        if (!this.animationEnable) {
            return;
        }

        if (success) {
            printer.print("Match lasciato con successo.");
        } else {
            printer.print("Non sono riuscito a disconnettermi dal match.");
        }
    }

    @Override
    public void logoutResult(boolean success) {
        //Control if animation is enabled
        if (!this.animationEnable) {
            return;
        }

        if (success) {
            printer.print("Logout riuscito con successo!");
        } else {
            printer.print("Logout NON riuscito!");
        }
    }

    @Override
    public void pullLeaderBoard(List leaderBoard) {
        //Control if animation is enabled
        if (!this.animationEnable) {
            return;
        }

        printer.print("Leader Board:");
        printer.print("____________________________");
        //TODO
    }

    @Override
    public void pullInvitate(List inviteList) {
        //Control if animation is enabled
        if (!this.animationEnable) {
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
