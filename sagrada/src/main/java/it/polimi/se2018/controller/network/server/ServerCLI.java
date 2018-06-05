package it.polimi.se2018.controller.network.server;

import it.polimi.se2018.controller.game.server.ServerControllerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Listens on the CLI for server commands
 * @author Francesco Franzini
 */
public class ServerCLI implements Runnable{
    private final ServerMain serverMain;
    private final Logger logger;

    /**
     * Creates a new server with the given parameters
     * @param objPort socket object port
     * @param reqPort socket request port
     * @param rmiPort rmi port
     * @param rmiIP rmi ip to use
     */
    public ServerCLI(int objPort,int reqPort, int rmiPort,InetAddress rmiIP) throws IOException {
        logger=Logger.getGlobal();
        logger.setUseParentHandlers(false);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.INFO);
        logger.addHandler(handler);
        logger.setLevel(Level.ALL);
        logger.log(Level.INFO,"Initiating server startup");
        try {
            this.serverMain=new ServerMain(objPort,reqPort,rmiPort,LoginResponsesEnum.RESOURCE_NAME.msg,rmiIP,new ServerControllerFactory());
            serverMain.buildServers();
        } catch (IOException e) {
            logger.log(Level.SEVERE,"Server init failed "+e.getMessage());
            throw e;
        }
        Thread t = new Thread(this);
        t.start();
        logger.log(Level.INFO,"Server up and running");
    }


    public static void main(String[] args) throws UnknownHostException {
        if(args.length!=4){
            throw new IllegalArgumentException("Missing arguments");
        }
        int objPort=Integer.parseInt(args[0]);
        int reqPort=Integer.parseInt(args[1]);
        int rmiPort=Integer.parseInt(args[2]);
        InetAddress rmiIp=InetAddress.getByName(args[3]);
        try {
            new ServerCLI(objPort,reqPort,rmiPort,rmiIp);
        } catch (IOException e) {
            System.exit(-1);
        }
    }

    @Override
    public void run() {
        BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
        while(!Thread.currentThread().isInterrupted()){
            try{
                String[] s=in.readLine().split(" ");
                switch (s[0]){
                    case "kick":
                        if(s.length<2){
                            logger.log(Level.WARNING,"A username must be supplied");
                            break;
                        }
                        Client c=serverMain.getClient(s[1]);
                        if(c==null){
                            logger.log(Level.INFO,"{0} is not logged",s[1]);
                            break;
                        }
                        c.purge(true);
                        break;
                    case "close":
                        logger.log(Level.INFO,"Closing down");
                        serverMain.closeDown();
                        Thread.currentThread().interrupt();
                        break;
                    default:
                        logger.log(Level.WARNING,"Unknown command {0}",s);
                }
            } catch (IOException e) {
                logger.log(Level.SEVERE,"Couldn't read from console input "+e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
    }
}
