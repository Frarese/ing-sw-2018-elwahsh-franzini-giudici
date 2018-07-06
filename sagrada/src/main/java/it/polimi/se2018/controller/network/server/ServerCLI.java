package it.polimi.se2018.controller.network.server;

import it.polimi.se2018.controller.game.server.ServerControllerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Listens on the CLI for server commands
 * @author Francesco Franzini
 */
public class ServerCLI{

    /**
     * Creates a new server with the given parameters
     * @param objPort socket object port
     * @param reqPort socket request port
     * @param rmiPort rmi port
     * @param rmiIP rmi ip to use
     * @throws IOException if an error occurs
     */
    ServerCLI(int objPort,int reqPort, int rmiPort,InetAddress rmiIP) throws IOException {
        Logger logger = Logger.getGlobal();
        FileHandler handler = new FileHandler("serverLog.log");
        handler.setLevel(Level.INFO);
        logger.addHandler(handler);
        logger.setLevel(Level.ALL);
        logger.log(Level.INFO,"Initiating server startup");
        try {
            ServerMain serverMain = new ServerMain(objPort, reqPort, rmiPort, LoginResponsesEnum.RESOURCE_NAME.msg, rmiIP, new ServerControllerFactory());
            serverMain.buildServers();
        } catch (IOException e) {
            logger.log(Level.SEVERE,"Server init failed "+e.getMessage());
            throw e;
        }
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
            buildCLI(objPort,reqPort,rmiPort,rmiIp);
        } catch (IOException |IllegalArgumentException e) {
            Logger.getGlobal().log(Level.SEVERE,"Error initializing server {0}",e.getMessage());
        }
    }

    public static void buildCLI(int objPort,int reqPort, int rmiPort,InetAddress rmiIp) throws IOException{
        new ServerCLI(objPort,reqPort,rmiPort,rmiIp);
    }
}
