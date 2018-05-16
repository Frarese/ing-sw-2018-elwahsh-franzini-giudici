package it.polimi.se2018.view.tools.cli.command;

import it.polimi.se2018.util.MatchIdentifier;
import it.polimi.se2018.view.app.CLIApp;

public class CommandCreateInvite extends CLICommand {

    public CommandCreateInvite(CLIApp app) {
        super("invite) crea una partita ed invita altri utenti", app);
    }

    @Override
    public void doAction() {
        //variables
        String player1;
        String player2 = null;
        String player3 = null;
        final String personMessage = "Inserire nome persona da invitare: ";

        //Add first player
        this.app.getPrinter().print("Creazione di un invito");
        this.app.getPrinter().print(personMessage);
        player1 = this.app.getReader().read();

        //Ask for second player
        this.app.getPrinter().print("Vuoi aggiungere un'altra persona?");
        if (this.app.getReader().chooseYes()) {
            this.app.getPrinter().print(personMessage);
            player2 = this.app.getReader().read();

            //Ask for third player
            this.app.getPrinter().print("Vuoi aggiungere un'altra persona?");
            if (this.app.getReader().chooseYes()) {
                this.app.getPrinter().print(personMessage);
                player3 = this.app.getReader().read();
            }
        }

        //Call View Action function
        this.app.getViewActions().pushInvite(new MatchIdentifier(this.app.getOwnerPlayerName(), player1, player2, player3));

        //Call menu method
        this.app.menu();
    }
}
