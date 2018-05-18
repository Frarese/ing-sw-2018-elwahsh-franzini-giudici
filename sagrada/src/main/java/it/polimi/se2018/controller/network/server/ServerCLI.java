package it.polimi.se2018.controller.network.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Listens on the CLI for server commands
 * @author Francesco Franzini
 */
class ServerCLI implements Runnable{
    private final ServerMain serverMain;
    private final Logger logger;

    /**
     * Creates a new server with the given parameters
     * @param objPort socket object port
     * @param reqPort socket request port
     * @param useDB true if a database is to be used for the user credentials validation
     * @param rmiPort rmi port
     * @param rmiName rmi name
     */
    public ServerCLI(int objPort,int reqPort, boolean useDB, int rmiPort, String rmiName) throws IOException {
        logger=Logger.getGlobal();
        logger.setUseParentHandlers(false);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        logger.addHandler(handler);
        logger.setLevel(Level.ALL);
        logger.log(Level.INFO,"Initiating server startup");
        try {
            this.serverMain=new ServerMain(objPort,reqPort,useDB,rmiPort,rmiName);
        } catch (IOException e) {
            logger.log(Level.SEVERE,"Server init failed "+e.getMessage());
            throw e;
        }
        Thread t = new Thread(this);
        t.start();
        logger.log(Level.INFO,"Server up and running");
    }


    public static void main(String[] args){
        if(args.length!=5){
            throw new IllegalArgumentException("Missing arguments");
        }
        int objPort=Integer.parseInt(args[0]);
        int reqPort=Integer.parseInt(args[1]);
        boolean useDB=Boolean.parseBoolean(args[2]);
        int rmiPort=Integer.parseInt(args[3]);
        String rmiName=args[4];
        try {
            new ServerCLI(objPort,reqPort,useDB,rmiPort,rmiName);
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
                        c.purge();
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
