package it.polimi.se2018;

import it.polimi.se2018.controller.game.client.ActionSender;
import it.polimi.se2018.controller.game.client.ClientController;
import it.polimi.se2018.controller.network.server.ServerCLI;
import it.polimi.se2018.view.AppFactory;
import it.polimi.se2018.view.ViewActions;
import it.polimi.se2018.view.ViewMessage;
import it.polimi.se2018.view.ViewToolCardActions;
import it.polimi.se2018.view.app.App;

import java.net.InetAddress;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main Class used to init this application
 */
public class Main {
    /**
     * args[0] boolean to choose between client(true) and server(false)
     * args[1] boolean to choose between cli(false) and gui(true) if client, else rmi ip to use
     * args[2] int specifying rmi port
     * args[3] int specifying request port
     * args[4] int specifying object port
     * @param args arguments
     */
    public static void main(String[] args) throws Exception {
        if(Boolean.parseBoolean(args[0])){//Client
            Logger logger=Logger.getGlobal();
            ConsoleHandler handler=new ConsoleHandler();
            logger.addHandler(handler);
            logger.setLevel(Level.ALL);
            handler.setLevel(Level.ALL);
            if(args.length<2)throw new IllegalArgumentException("Not enough parameters");
            Boolean useGui=Boolean.parseBoolean(args[1]);
            ActionSender actionSender = new ActionSender();
            ViewActions viewActions = new ViewActions(actionSender);
            ViewToolCardActions viewToolCardActions = new ViewToolCardActions(actionSender);
            ViewMessage viewMessage = new ViewMessage(actionSender);
            App app = new AppFactory(useGui,viewActions,viewToolCardActions,viewMessage).getApp();
            ClientController controller = new ClientController(app);
            actionSender.setController(controller);
            app.startLogin(true);
            logger.log(Level.INFO,"Client up and running");

        }else{
            if(args.length<5)throw new IllegalArgumentException("Not enough parameters for server");
            InetAddress serverAddress=InetAddress.getByName(args[1]);
            Integer rmiPort=Integer.parseInt(args[2]);
            Integer requestPort=Integer.parseInt(args[3]);
            Integer objectPort=Integer.parseInt(args[4]);
            new ServerCLI(objectPort,requestPort,rmiPort,serverAddress);
        }
    }
}
