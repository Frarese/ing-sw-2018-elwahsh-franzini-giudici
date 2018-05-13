package it.polimi.se2018.view.app;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.observer.PlayerView;
import it.polimi.se2018.observer.ReserveView;
import it.polimi.se2018.observer.RoundTrackerView;
import it.polimi.se2018.util.MatchIdentifier;
import it.polimi.se2018.util.Pair;
import it.polimi.se2018.util.ScoreEntry;
import it.polimi.se2018.view.view_util.cli_command.*;
import it.polimi.se2018.view.view_util.cli_creators.*;
import it.polimi.se2018.view.view_util.cli_interface.CLIPrinter;
import it.polimi.se2018.view.view_util.cli_interface.CLIReader;

import java.util.ArrayList;
import java.util.Collection;
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
    private ArrayList<CLICommand> commands;
    private ArrayList<CLICommand> gameCommands;

    /**
     * Class constructor
     */
    CLIApp() {
        super();
        this.printer = new CLIPrinter();
        this.reader = new CLIReader();

        this.invites = new ArrayList<>();
        this.commands = new ArrayList<>();
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
        boolean newUser = reader.chooseYes();

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
        boolean isRMI = reader.chooseYes();
        printer.print("Inserire object port: ");
        int objectPort = reader.readInt();
        int requestPort = -1;
        if (!isRMI) {
            printer.print("Inserire request port: ");
            requestPort = reader.readInt();
        }

        //Save player name
        this.ownerPlayerName = name;

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
            this.commands.add(new CommandLogout(this));
            this.commands.add(new CommandChangeLayer(this));
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
            this.viewActions.askLobby();
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
            this.startLogin(true);
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
        if (reader.chooseYes()) {
            printer.print(invite.toString());
            printer.print("Vuoi partecipare?");
            if (reader.chooseYes()) {
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
        this.gridViewCreator = new CLIGridViewCreator(null, pattern1);
        printer.printArray(this.gridViewCreator.display());
        printer.print("Carta Schema 2;");
        this.gridViewCreator = new CLIGridViewCreator(null, pattern2);
        printer.printArray(this.gridViewCreator.display());
        printer.print("Carta Schema 3:");
        this.gridViewCreator = new CLIGridViewCreator(null, pattern3);
        printer.printArray(this.gridViewCreator.display());
        printer.print("Carta Schema 4:");
        this.gridViewCreator = new CLIGridViewCreator(null, pattern4);
        printer.printArray(this.gridViewCreator.display());

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
                Logger.getGlobal().log(Level.WARNING, "Qualcosa e\' andato storto nella selezione del pattern.");
        }
        this.menuControl();
    }

    @Override
    public void initGame(List<PlayerView> players, int yourPrivateObjectiveCard, int[] publicObjectiveCards, int[] toolCards, RoundTrackerView roundTracker) {
        //Control if animation is enabled
        if (!this.animationEnable) {
            return;
        }

        //Initialize elements
        this.players = players;
        this.cardViewCreator = new CLICardViewCreator(yourPrivateObjectiveCard, publicObjectiveCards, toolCards);
        this.roundTrackerViewCreator = new CLIRoundTrackerViewCreator(roundTracker.getRound(), roundTracker.getRoundTracker());
        PlayerView me = searchPlayerViewByName(players, this.ownerPlayerName);
        if (me != null) {
            this.ownerPlayerID = me.getPlayerID();
            this.gridViewCreator = new CLIGridViewCreator(me.getPlayerGrid(), me.getPlayerTemplate());
        }

        //Print cards
        printer.print("Carta obiettivo privato: ");
        printer.print(this.cardViewCreator.makeCart(this.cardViewCreator.getPrivateObjectiveCard()));
        printer.print("Carte obiettivo pubblico: ");
        for (int el : this.cardViewCreator.getPublicObjectiveCards()) {
            this.cardViewCreator.makeCart(el);
        }
        printer.print("Carte utensili: ");
        for (int el : this.cardViewCreator.getToolCards()) {
            this.cardViewCreator.makeCart(el);
        }
        //Print Grid
        printer.print("La tua griglia:");
        printer.printArray(gridViewCreator.display());
        this.viewActions.endInitGame();
    }

    @Override
    public void otherPlayerLeave(int playerID) {
        //Control if animation is enabled
        if (!this.animationEnable) {
            return;
        }

        printer.print(players.get(playerID).getPlayerName() + " ha lasciato il gioco.");
    }

    @Override
    public void otherPlayerReconnection(int playerID) {
        //Control if animation is enabled
        if (!this.animationEnable) {
            return;
        }

        printer.print(players.get(playerID).getPlayerName() + " e\' rientrato in gioco.");
    }

    @Override
    public void startTurn(PlayerView player, ReserveView reserve, RoundTrackerView roundTracker) {
        //Control if animation is enabled
        if (!this.animationEnable) {
            return;
        }
        //Update game turn
        this.reserveViewCreator.setReserve(reserve.getReserve());
        this.roundTrackerViewCreator.setRoundTracker(roundTracker.getRoundTracker());

        //Call menuTurn method
        this.menuTurnControl();
    }

    @Override
    public void setDieResult(boolean result, String errorString) {
        //Control if animation is enabled
        if (!this.animationEnable) {
            return;
        }

        //Print result
        if (result) {
            printer.print("Dado piazzato!");
        } else {
            printer.print("Non sei riuscito a piazzare il dado: " + errorString);
        }

        this.menuTurnControl();
    }

    @Override
    public void addUpdate(int playerID, int height, int width, int reserveIndex) {
        //Control if animation is enabled
        if (!this.animationEnable) {
            return;
        }

        //Check if is my ID
        if (playerID == this.ownerPlayerID) {
            return;
        }

        //Search information
        PlayerView playerView = this.searchPlayerViewById(this.players, playerID);
        if (playerView != null) {
            //Print
            printer.print(playerView.getPlayerName() + " ha posizionato il dado: " + this.reserveViewCreator.pickDie(reserveIndex) + " in posizione (" + height + "," + width + ").");
        }

    }

    @Override
    public void useToolCardResult(boolean result, String errorString) {
        //Control if animation is enabled
        if (!this.animationEnable) {
            return;
        }

        //Print result
        if (result) {
            printer.print("Carta in uso.");
        } else {
            printer.print("Non sei riuscito ad usare la carta: " + errorString);
        }

        this.menuTurnControl();
    }

    @Override
    public void useToolCardUpdate(int playerID, int card) {
        //Control if animation is enabled
        if (!this.animationEnable) {
            return;
        }

        //Check if is my ID
        if (playerID == this.ownerPlayerID) {
            return;
        }

        //Search information
        PlayerView playerView = this.searchPlayerViewById(this.players, playerID);
        if (playerView != null) {
            String player = playerView.getPlayerName();
            //Print
            printer.print(player + " ha usato la carta: " + this.cardViewCreator.makeCart(card));
        }
    }

    @Override
    public void passTurnResult(boolean result) {
        //Control if animation is enabled
        if (!this.animationEnable) {
            return;
        }

        //Print result
        if (result) {
            printer.print("Turno passato con successo!");
        } else {
            printer.print("Non sei riuscito a passare il turno");
        }
    }

    @Override
    public void passTurnUpdate(int playerID) {
        //Control if animation is enabled
        if (!this.animationEnable) {
            return;
        }

        //Check if is my ID
        if (playerID == this.ownerPlayerID) {
            return;
        }

        //Search information
        PlayerView playerView = this.searchPlayerViewById(this.players, playerID);
        if (playerView != null) {
            String player = playerView.getPlayerName();
            //Print
            printer.print(player + " ha passato il suo turno!");
        }
    }

    @Override
    public void gameEnd(List<ScoreEntry> scores) {
        //Control if animation is enabled
        if (!this.animationEnable) {
            return;
        }

        //Print
        this.scoreViewCreator = new CLIScoreViewCreator();
        printer.printArray(scoreViewCreator.display(scores));
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
    public void setDieOnGrid(Pair<Integer, ColorModel> die) {

    }

    @Override
    public void selectDieFromRoundTracker() {

    }

    @Override
    public void selectFace(Pair<Integer, ColorModel> die) {

    }

    @Override
    public void selectDieFromGridByColor(ColorModel color) {

    }

    /**
     * Getter method
     *
     * @return the cli printer
     */
    public CLIPrinter getPrinter() {
        return printer;
    }

    /**
     * Getter method
     *
     * @return the cli reader
     */
    public CLIReader getReader() {
        return reader;
    }



    /**
     * To show the turn options during a turn
     */
    public void menuTurnControl() {
        //Search PlayerView
        PlayerView me = this.searchPlayerViewByName(this.players, this.ownerPlayerName);

        //Clear gameCommands
        this.gameCommands.clear();

        //Add new Commands
        this.gameCommands.add(new CommandShowGrid(this));
        this.gameCommands.add(new CommandShowFavours(this));
        this.gameCommands.add(new CommandShowRoundTracker(this));

        if (me.hasPlacementRights()) {
            this.gameCommands.add(new CommandAddDie(this));
        }
        if (me.hasCardRights()) {
            this.gameCommands.add(new CommandUseTool(this));
        }

        this.gameCommands.add(new CommandPassTurn(this));

        //Print options
        ArrayList<CLICommand> printArray = new ArrayList<>();
        printArray.addAll((Collection<? extends CLICommand>) gameCommands.clone());
        printArray.addAll((Collection<? extends CLICommand>) commands.clone());

        printer.print("Comandi disponibili: ");
        for(int i =0; i< printArray.size(); i++){
            printer.print(i + "-" + printArray.get(i).display());
        }
        printer.print("Seleziona un comando");
        int actionID = reader.choose(0,printArray.size()-1);
        printArray.get(actionID).doAction();
    }

    /**
     * To show basic menu options
     */
    public void menuControl() {
        printer.print("Comandi disponibili: ");
        for(int i =0; i< this.commands.size(); i++){
            printer.print(i + "-" + this.commands.get(i).display());
        }
        printer.print("Seleziona un comando");
        int actionID = reader.choose(0,this.commands.size()-1);
        this.commands.get(actionID).doAction();
    }
}
