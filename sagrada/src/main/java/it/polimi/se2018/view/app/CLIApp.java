package it.polimi.se2018.view.app;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.observer.PlayerView;
import it.polimi.se2018.observer.ReserveView;
import it.polimi.se2018.observer.RoundTrackerView;
import it.polimi.se2018.util.MatchIdentifier;
import it.polimi.se2018.util.Pair;
import it.polimi.se2018.util.ScoreEntry;
import it.polimi.se2018.view.view_util.cli_creators.CLICardViewCreator;
import it.polimi.se2018.view.view_util.cli_creators.CLIGridViewCreator;
import it.polimi.se2018.view.view_util.cli_creators.CLIReserveViewCreator;
import it.polimi.se2018.view.view_util.cli_creators.CLIScoreViewCreator;
import it.polimi.se2018.view.view_util.cli_interface.CLIPrinter;
import it.polimi.se2018.view.view_util.cli_interface.CLIReader;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    /**
     * Class constructor
     */
    CLIApp() {
        super();
        this.printer = new CLIPrinter();
        this.reader = new CLIReader();

        this.invites = new ArrayList<>();
    }

    @Override
    public void animation(boolean enable) {
        this.animationEnable = enable;
    }

    @Override
    public void startLogin(boolean displayWelcome) {
        //Check if have to display welcome page
        if (displayWelcome) {
            String welcome = "---------------------------------\n";
            welcome = welcome + "|         SAGRADA GAME          | \n";
            welcome = welcome + "---------------------------------\n";
            printer.print(welcome);
        }

        //Ask for a new User
        printer.print("Sei un nuovo utente? ");
        boolean newUser = reader.choose();

        //Ask login information
        printer.print("INSERIRE LE CREDENZIALI PER IL LOGIN");
        printer.print("Nome: ");
        String name = reader.read();
        printer.print("Password: ");
        String password = reader.read();

        //Connection information
        printer.print("Inserire server: ");
        String server = reader.read();
        printer.print("Vuoi usare RMI connection? ");
        boolean isRMI = reader.choose();
        printer.print("Inserire object port: ");
        int objectPort = reader.readInt();
        int requestPort = -1;
        if (!isRMI) {
            printer.print("Inserire request port: ");
            requestPort = reader.readInt();
        }

        //Controller call
        this.viewActions.login(name, password, newUser, server, isRMI, objectPort, requestPort);
    }

    @Override
    public void loginResult(boolean success) {
        //Control if animation is enabled
        if (!this.animationEnable) {
            return;
        }

        if (success) {
            printer.print("Login riuscito con successo!");
            this.viewActions.askLobby();
        } else {
            printer.print("Login NON riuscito! Riprova.");
            this.startLogin(false);
        }
    }

    @Override
    public void changeLayerResult(boolean successRMI) {
        //Control if animation is enabled
        if (!this.animationEnable) {
            return;
        }

        if (successRMI) {
            printer.print("L'attuale layer e\' RMI.");
        } else {
            printer.print("L'attuale layer e\' Socket.");
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
    public void pullLeaderBoard(List<ScoreEntry> leaderBoard) {
        //Control if animation is enabled
        if (!this.animationEnable) {
            return;
        }

        printer.print("Leader Board (Utente, Punti, Vittorie):");
        printer.print("____________________________");
        for (int i = 0; i < leaderBoard.size(); i++) {
            printer.print(i + ") " +
                    leaderBoard.get(i).usn + ", " +
                    leaderBoard.get(i).tot + ", " +
                    leaderBoard.get(i).wins);
        }
        //TODO
        //Visualizzare primi 10, poi chiedere successivi?
        //Manca menus
    }

    @Override
    public void pullInvitate(MatchIdentifier invite) {
        //Add invite add list
        this.invites.add(invite);

        //Show to user
        printer.print("C'è un nuovo invito, lo vuoi visualizzare?");
        if (reader.choose()) {
            printer.print(invite.toString());
            printer.print("Vuoi partecipare?");
            if (reader.choose()){
                viewActions.acceptInvite(invite);
            }
        }
    }


    @Override
    public void askPattern(Pair<Integer, ColorModel>[][] pattern1, Pair<Integer, ColorModel>[][] pattern2, Pair<Integer, ColorModel>[][] pattern3, Pair<Integer, ColorModel>[][] pattern4) {
        //Control if animation is enabled
        if (!this.animationEnable) {
            return;
        }

        //Show patterns
        printer.print("Carta Schema 1:");
        this.cliGridViewCreator = new CLIGridViewCreator(null,pattern1);
        printer.printArray(cliGridViewCreator.display());
        printer.print("Carta Schema 2;");
        this.cliGridViewCreator = new CLIGridViewCreator(null,pattern2);
        printer.printArray(cliGridViewCreator.display());
        printer.print("Carta Schema 3:");
        this.cliGridViewCreator = new CLIGridViewCreator(null,pattern3);
        printer.printArray(cliGridViewCreator.display());
        printer.print("Carta Schema 4:");
        this.cliGridViewCreator = new CLIGridViewCreator(null,pattern4);
        printer.printArray(cliGridViewCreator.display());

        //Ask pattern
        int pattern = reader.choose(1, 4);
        switch (pattern) {
            case 1:
                viewActions.selectedPattern(pattern1);
                break;
            case 2:
                viewActions.selectedPattern(pattern2);
                break;
            case 3:
                viewActions.selectedPattern(pattern3);
                break;
            case 4:
                viewActions.selectedPattern(pattern4);
                break;
            default:
                Logger.getGlobal().log(Level.WARNING, "Qualcosa e\' andato storto nella selezione del pattern");
        }

    }

    @Override
    public void initGame(List<PlayerView> players, int yourPrivateObjectiveCard, int[] publicObjectiveCards, int[] toolCards, RoundTrackerView roundTracker) {
        this.players = players;
        this.cliCardViewCreator = new CLICardViewCreator(yourPrivateObjectiveCard, publicObjectiveCards, toolCards);

    }

    @Override
    public void otherPlayerLeave(int playerID) {
        printer.print(players.get(playerID).getPlayerName() + " ha lasciato il gioco");
    }

    @Override
    public void otherPlayerReconnection(int playerID) {
        printer.print(players.get(playerID).getPlayerName() + " e\' rientrato in gioco");
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
