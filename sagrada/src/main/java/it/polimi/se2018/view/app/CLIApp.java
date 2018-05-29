package it.polimi.se2018.view.app;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.observer.PlayerView;
import it.polimi.se2018.observer.ReserveView;
import it.polimi.se2018.observer.RoundTrackerView;
import it.polimi.se2018.util.MatchIdentifier;
import it.polimi.se2018.util.Pair;
import it.polimi.se2018.util.ScoreEntry;
import it.polimi.se2018.view.ViewActions;
import it.polimi.se2018.view.ViewMessage;
import it.polimi.se2018.view.ViewToolCardActions;
import it.polimi.se2018.view.tools.cli.command.*;
import it.polimi.se2018.view.tools.cli.creators.*;
import it.polimi.se2018.view.tools.cli.ui.CLIPrinter;
import it.polimi.se2018.view.tools.cli.ui.CLIReader;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class CLIApp that represents the command line interface
 *
 * @author Mathyas Giudici
 */

public class CLIApp extends App {

    /**
     * Player Information variables
     */
    private int ownerPlayerID;
    private String ownerPlayerName;
    private boolean useRMI;
    private boolean isYourTurn;


    private CLIPrinter printer;
    private CLIReader reader;
    private ArrayList<CLICommand> commands;
    private ArrayList<CLICommand> gameCommands;

    /**
     * Class constructor that creates CLICreators' objects and CLIui' objects
     *
     * @param viewActions         contains ViewActions class for View->Controller communication
     * @param viewToolCardActions contains ViewToolCardActions class for View->Controller communication (tool cards)
     * @param viewMessage         contains ViewMessage class for View->Controller communication (chat)
     */
    public CLIApp(ViewActions viewActions, ViewToolCardActions viewToolCardActions, ViewMessage viewMessage) {
        super(viewActions, viewToolCardActions, viewMessage);

        //Initializes Player Information
        this.ownerPlayerID = -1;
        this.ownerPlayerName = null;
        this.useRMI = false;
        this.isYourTurn = false;

        //Initializes CLI ui
        this.printer = new CLIPrinter();
        this.reader = new CLIReader(this.printer);

        //Initializes Arrays
        this.invites = new ArrayList<>();
        this.commands = new ArrayList<>();
        this.gameCommands = new ArrayList<>();
    }

    @Override
    public void animation(boolean enable) {
        this.animationEnable = enable;
    }

    @Override
    public void startLogin(boolean displayWelcome) {
        //Check if animation is enabled
        if (!this.animationEnable) {
            return;
        }

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
        //Check if animation is enabled
        if (!this.animationEnable) {
            return;
        }

        if (success) {
            printer.print("Login riuscito con successo!");

            this.commands.clear();
            this.commands.add(new CommandLogout(this));
            this.commands.add(new CommandChangeLayer(this));

            this.invites = new ArrayList<>();
            this.viewActions.askLobby();
        } else {
            printer.print("Login NON riuscito! Riprova.");
            this.startLogin(false);
        }
    }

    @Override
    public void changeLayerResult(boolean successRMI) {
        //Check if animation is enabled
        if (!this.animationEnable) {
            return;
        }

        if (successRMI) {
            printer.print("L'attuale layer e\' RMI.");
        } else {
            printer.print("L'attuale layer e\' Socket.");
        }

        //Call menu method
        this.menu();
    }

    @Override
    public void leaveMatchResult(boolean success) {
        //Check if animation is enabled
        if (!this.animationEnable) {
            return;
        }

        if (success) {
            printer.print("Match lasciato con successo.");
            this.viewActions.askLobby();
        } else {
            printer.print("Non sono riuscito a disconnettermi dal match.");

            //Call menu method
            this.menu();
        }
    }

    @Override
    public void logoutResult(boolean success) {
        //Check if animation is enabled
        if (!this.animationEnable) {
            return;
        }

        if (success) {
            printer.print("Logout riuscito con successo!");
            this.startLogin(true);
        } else {
            printer.print("Logout NON riuscito!");

            //Call menu method
            this.menu();
        }
    }

    @Override
    public void pullLeaderBoard(List<ScoreEntry> leaderBoard) {
        //Check if animation is enabled
        if (!this.animationEnable) {
            return;
        }

        this.leaderBoard = leaderBoard;

        this.commands.add(0, new CommandCreateInvite(this));
        this.commands.add(0, new CommandAutoComplete(this));
        this.commands.add(0, new CommandAcceptInvite(this));
        this.commands.add(0, new CommandShowInvites(this));
        this.commands.add(0, new CommandShowLeaderBoard(this));

        //Call menu method
        this.menu();
    }

    @Override
    public void pullInvitate(MatchIdentifier invite) {
        //Add invite add list
        this.invites.add(invite);
    }


    @Override
    public void askPattern(Pair<Integer, ColorModel>[][] pattern1, Pair<Integer, ColorModel>[][] pattern2, Pair<Integer, ColorModel>[][] pattern3, Pair<Integer, ColorModel>[][] pattern4) {
        //Check if animation is enabled
        if (!this.animationEnable) {
            return;
        }

        //Show patterns
        printer.print("Carta Schema 1:");
        this.gridViewCreator = new CLIGridViewCreator(null, pattern1, this.printer);
        printer.printArray(this.gridViewCreator.display());
        printer.print("Carta Schema 2;");
        this.gridViewCreator = new CLIGridViewCreator(null, pattern2, this.printer);
        printer.printArray(this.gridViewCreator.display());
        printer.print("Carta Schema 3:");
        this.gridViewCreator = new CLIGridViewCreator(null, pattern3, this.printer);
        printer.printArray(this.gridViewCreator.display());
        printer.print("Carta Schema 4:");
        this.gridViewCreator = new CLIGridViewCreator(null, pattern4, this.printer);
        printer.printArray(this.gridViewCreator.display());

        //Ask pattern
        int pattern = reader.chooseInRange(1, 4);
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
    }

    @Override
    public void initGame(List<PlayerView> players, int yourPrivateObjectiveCard, int[] publicObjectiveCards, int[] toolCards, RoundTrackerView roundTracker) {
        //Check if animation is enabled
        if (!this.animationEnable) {
            return;
        }

        //Command array set
        this.commands.clear();
        this.commands.add(new CommandWaitYourTurn(this));
        this.commands.add(new CommandLeaveMatch(this));
        this.commands.add(new CommandLogout(this));
        this.commands.add(new CommandChangeLayer(this));

        //Initialize elements
        this.players = players;
        this.cardViewCreator = new CLICardViewCreator(yourPrivateObjectiveCard, publicObjectiveCards, toolCards);
        this.roundTrackerViewCreator = new CLIRoundTrackerViewCreator(roundTracker.getRound(), roundTracker.getRoundTracker());
        this.reserveViewCreator = new CLIReserveViewCreator(null);

        PlayerView me = searchPlayerViewByName(players, this.ownerPlayerName);
        if (me != null) {
            this.ownerPlayerID = me.getPlayerID();
            this.gridViewCreator = new CLIGridViewCreator(me.getPlayerGrid(), me.getPlayerTemplate(), this.printer);
        }
        this.isYourTurn = false;

        //Print cards
        printer.print("Carta obiettivo privato: ");
        printer.print(this.cardViewCreator.makeCard(this.cardViewCreator.getPrivateObjectiveCard()));
        printer.print("Carte obiettivo pubblico: ");
        for (int el : this.cardViewCreator.getPublicObjectiveCards()) {
            this.cardViewCreator.makeCard(el);
        }
        printer.print("Carte utensili: ");
        for (int el : this.cardViewCreator.getToolCards()) {
            this.cardViewCreator.makeCard(el);
        }
        //Print Grid
        printer.print("La tua griglia:");
        printer.printArray(gridViewCreator.display());
        this.viewActions.endInitGame();
    }

    @Override
    public void otherPlayerLeave(int playerID) {
        //Check if animation is enabled
        if (!this.animationEnable) {
            return;
        }

        String message = players.get(playerID).getPlayerName() + " ha lasciato il gioco.";
        printer.print(message);
    }

    @Override
    public void otherPlayerReconnection(int playerID) {
        //Check if animation is enabled
        if (!this.animationEnable) {
            return;
        }

        String message = players.get(playerID).getPlayerName() + " e\' rientrato in gioco.";
        printer.print(message);
    }

    @Override
    public void startTurn(PlayerView player, ReserveView reserve, RoundTrackerView roundTracker) {
        this.isYourTurn = true;
        //Check if animation is enabled
        if (!this.animationEnable) {
            return;
        }
        //Update game turn
        this.reserveViewCreator.setReserve(reserve.getReserve());
        this.roundTrackerViewCreator.setRoundTracker(roundTracker.getRoundTracker());

        //Call menu method
        this.menu();
    }

    @Override
    public void setDieResult(boolean result, String errorString) {
        //Check if animation is enabled
        if (!this.animationEnable) {
            return;
        }

        //Print result
        if (result) {
            printer.print("Dado piazzato!");
        } else {
            printer.print("Non sei riuscito a piazzare il dado: " + errorString);
        }

        //Call menu method
        this.menu();
    }

    @Override
    public void addUpdate(int playerID, int height, int width, int reserveIndex) {
        //Check if animation is enabled
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

        //Call menu method
        this.menu();
    }

    @Override
    public void useToolCardResult(boolean result, String errorString) {
        //Check if animation is enabled
        if (!this.animationEnable) {
            return;
        }

        //Print result
        if (result) {
            printer.print("Carta in uso.");
        } else {
            printer.print("Non sei riuscito ad usare la carta: " + errorString);
        }

        //Call menu method
        this.menu();
    }

    @Override
    public void useToolCardUpdate(int playerID, int card) {
        //Check if animation is enabled
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
            printer.print(player + " ha usato la carta: " + this.cardViewCreator.makeCard(card));
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
            printer.print("Turno passato con successo!");
            this.isYourTurn = false;

            //Call menu method
            this.menu();
        } else {
            printer.print("Non sei riuscito a passare il turno");
        }

        //Call menu method
        this.menu();
    }

    @Override
    public void passTurnUpdate(int playerID) {
        //Check if animation is enabled
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
        //Check if animation is enabled
        if (!this.animationEnable) {
            return;
        }

        //Print
        this.scoreViewCreator = new CLIScoreViewCreator();
        printer.printArray(scoreViewCreator.display(scores));

        //Call menu method
        this.isYourTurn = false;
        this.menu();
    }

    @Override
    public void selectDieFromReserve() {
        //Print and read operations
        printer.print("Seleziona un dado dalla riserva: ");
        printer.print(this.reserveViewCreator.display());
        int reserveIndex = reader.chooseInRange(0, this.reserveViewCreator.getReserve().length - 1);

        //Call ViewToolCardActions
        viewToolCardActions.selectedDieFromReserve(reserveIndex);
    }

    @Override
    public void selectNewValueForDie(int low, int high) {
        //Print and read operations
        printer.print("Seleziona il valore del dado tra " + low + " e " + high);
        int choice = reader.chooseBetweenTwo(low, high);

        //Call ViewToolCardActions
        viewToolCardActions.selectedValueForDie(choice);
    }

    @Override
    public void updateReserve() {
        //Print operation
        printer.print("Nuova riserva:");
        printer.print(reserveViewCreator.display());
    }

    @Override
    public void selectDieFromGrid() {
        //Print and read operation
        printer.print("Seleziona un dado dalla griglia (la numerazione parte da 0)");
        printer.printArray(gridViewCreator.display());
        int x = this.getCoordinateX();
        int y = this.getCoordinateY();

        //Call ViewToolCardActions
        viewToolCardActions.selectedDieFromGrid(x, y);
    }

    @Override
    public void setDieOnGrid(Pair<Integer, ColorModel> die) {
        CLIDieViewCreator dieCreator = new CLIDieViewCreator();

        //Print and read operation
        printer.print("Devi posizionare il dado: " + dieCreator.makeDie(die));
        printer.print("(la numerazione sulla griglia parte da 0)");
        printer.printArray(gridViewCreator.display());
        int x = this.getCoordinateX();
        int y = this.getCoordinateY();

        //Call ViewToolCardActions
        viewToolCardActions.selectedDieToGrid(x, y);
    }

    @Override
    public void selectDieFromRoundTracker() {
        //Print and read operation
        printer.printArray(roundTrackerViewCreator.display());
        printer.print("Inserire il numero del round da cui si vuole estrarre il dado:");
        int roundIndex = reader.chooseInRange(1, roundTrackerViewCreator.getRound() - 1) - 1;
        printer.print("Inserire il numero del dado nel round selezionato");
        int dieIndex = reader.chooseInRange(0, roundTrackerViewCreator.getRoundTracker()[roundIndex].length - 1);

        //Call ViewToolCardActions
        viewToolCardActions.selectedDieFromRoundTracker(roundIndex, dieIndex);
    }

    @Override
    public void selectFace(Pair<Integer, ColorModel> die) {
        CLIDieViewCreator dieCreator = new CLIDieViewCreator();

        //Print and read operation
        printer.print("Devi selezionare il nuovo valore del dado: " + dieCreator.makeDie(die));
        printer.print("Inserisci nuovo valore:");
        int value = reader.chooseInRange(1, 6);

        //Call ViewToolCardActions
        viewToolCardActions.selectedFace(value);
    }

    @Override
    public void selectDieFromGridByColor(ColorModel color) {
        //Print and read operation
        printer.print("Seleziona un dado dalla griglia che ha colore: " + color.toString());
        printer.print("(la numerazione parte da 0)");
        printer.printArray(gridViewCreator.display());
        int x = this.getCoordinateX();
        int y = this.getCoordinateY();

        //Call ViewToolCardActions
        viewToolCardActions.selectedDieFromGridByColor(x, y);
    }

    /**
     * Asks to user the x coordinate on grid (enumeration from 0 to 4)
     *
     * @return the x coordinate
     */
    public int getCoordinateX() {
        printer.print("Inserisci coordinata x:");
        return reader.chooseInRange(0, 4);
    }

    /**
     * Asks to user the y coordinate on grid (enumeration from 0 to 3)
     *
     * @return the y coordinate
     */
    public int getCoordinateY() {
        printer.print("Inserisci coordinata y:");
        return reader.chooseInRange(0, 3);
    }

    /**
     * Getter method for unique CLIPrinter
     *
     * @return the cli printer
     */
    public CLIPrinter getPrinter() {
        return printer;
    }

    /**
     * Getter method for unique CLIReader
     *
     * @return the cli reader
     */
    public CLIReader getReader() {
        return reader;
    }


    /**
     * To show the turn options during a turn
     */
    private void menuTurnControl() {
        //Checks menu's array
        if (commands == null) {
            Logger.getGlobal().log(Level.WARNING, "Ci sono problemi nella creazione del menu");
            return;
        }

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
        printArray.addAll(this.gameCommands);
        printArray.addAll(this.commands);

        printer.print("Comandi disponibili: ");
        for (int i = 0; i < printArray.size(); i++) {
            printer.print(i + "-" + printArray.get(i).display());
        }
        printer.print("Seleziona un comando: ");
        int actionID = reader.chooseInRange(0, printArray.size() - 1);
        printArray.get(actionID).doAction();
    }

    /**
     * To show basic menu options
     */
    private void menuControl() {

        //Checks menu's array
        if (commands == null) {
            Logger.getGlobal().log(Level.WARNING, "Ci sono problemi nella creazione del menu");
            return;
        }

        //Writes enable command
        printer.print("Comandi disponibili: ");
        for (int i = 0; i < this.commands.size(); i++) {
            printer.print(i + "-" + this.commands.get(i).display());
        }

        //Asks command to execute
        printer.print("Seleziona un comando: ");
        int actionID = reader.chooseInRange(0, this.commands.size() - 1);
        this.commands.get(actionID).doAction();
    }

    /**
     * Selects the right menu to show
     */
    public void menu() {
        if (this.isYourTurn) {
            this.menuTurnControl();
        } else {
            this.menuControl();
        }
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
