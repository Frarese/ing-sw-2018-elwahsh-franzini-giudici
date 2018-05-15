package it.polimi.se2018.view.view_util.cli_command;

import it.polimi.se2018.observer.PlayerView;
import it.polimi.se2018.view.app.CLIApp;

/**
 * Class to handle show favours request
 *
 * @author Mathyas Giudici
 */

public class CommandShowFavours extends CLICommand {

    public CommandShowFavours(CLIApp app) {
        super("fav) visualizza Punti favore", app);
    }

    @Override
    public void doAction() {
        this.app.getPrinter().print("Vuoi visualizzare i tuoi punti favore??");
        if (this.app.getReader().chooseYes()) {
            PlayerView me = this.app.searchPlayerViewById(this.app.getPlayers(), this.app.getOwnerPlayerID());
            this.app.getPrinter().print("Punti favore: " + me.getPlayerFavours());
        } else {
            for (int i = 0; i < this.app.getPlayers().size(); i++) {
                this.app.getPrinter().print(i + ") " + this.app.getPlayers().get(i).getPlayerName());
                this.app.getPrinter().print("Seleziona il giocatore");
                int player = this.app.getReader().choose(0, this.app.getPlayers().size() - 1);
                this.app.getPrinter().print("Punti favore: " + this.app.getPlayers().get(player).getPlayerFavours());
            }
        }

        //Call menu method
        this.app.menu();
    }
}
