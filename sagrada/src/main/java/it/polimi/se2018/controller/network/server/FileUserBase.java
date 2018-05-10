package it.polimi.se2018.controller.network.server;

import it.polimi.se2018.util.Pair;
import it.polimi.se2018.util.ScoreEntry;

import java.io.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * A file based implementation of the User Base
 *
 * @author Francesco Franzini
 */
class FileUserBase implements UserBase {
    private ConcurrentHashMap<String,Pair<String,ScoreEntry>> userMap;
    private final String filename;
    public static final String DEFAULT_FILENAME="users.csv";

    /**
     * Loads from file the User Base
     */
    FileUserBase(String filename) throws IOException {
        this.filename=filename;
        userMap=new ConcurrentHashMap<>();
        Logger logger=Logger.getGlobal();
        String read;
        try (BufferedReader br=new BufferedReader(new FileReader(filename))) {
            while ((read=br.readLine()) !=null) {
                String[] line=read.split(",");
                if(line.length==4){
                    String usn=line[0];
                    String pw=line[1];
                    Integer tot=this.parseInt(line[2]);
                    Integer wins=this.parseInt(line[3]);
                    if(tot==null || wins==null){
                        logger.log(Level.WARNING,"Found faulty user entry {0}",line);
                        continue;
                    }
                    userMap.put(usn,new Pair<>(pw,new ScoreEntry(usn,tot,wins)));
                    logger.log(Level.FINEST,"Loaded user {0}",line);
                }else{
                    logger.log(Level.WARNING,"Found faulty user entry {0}",line);
                }
            }
        }catch (FileNotFoundException e){
            logger.log(Level.INFO,"Creating new file user base");
            File f=new File(filename);
            if(!f.createNewFile())throw e;
        }catch (IOException e){
            logger.log(Level.SEVERE,"Couldn't open user base "+e.getMessage());
            throw e;
        }
    }

    @Override
    public boolean containsUser(String usn) {
        return userMap.containsKey(usn);
    }

    @Override
    public String getPw(String usn) {
        if(usn==null)return null;
        Pair<String,ScoreEntry> p=userMap.get(usn);
        if(p==null)return null;
        return p.getFirst();
    }

    @Override
    public boolean addUser(String usn, String pw) {
        if(usn==null || pw==null)return false;
        Pair<String,ScoreEntry> toAdd=new Pair<>(pw,new ScoreEntry(usn,0,0));
        Pair outcome= userMap.putIfAbsent(usn,toAdd);
        if(outcome==null){
            updatePersistent();
        }
        return outcome==null;
    }

    @Override
    public boolean removeUser(String usn) {
        if(usn==null)return false;
        return userMap.remove(usn)==null;
    }

    @Override
    public ScoreEntry getUserScore(String usn) {
        if(usn==null)return null;
        return userMap.get(usn).getSecond();
    }

    @Override
    public void alterUserScore(String usn, int dTot, int dWins) {
        if(usn==null)return;
        Pair<String,ScoreEntry> p=userMap.get(usn);
        if(p==null)return;
        ScoreEntry newEntry=new ScoreEntry(usn,p.getSecond().tot+dTot,p.getSecond().wins+dWins);
        userMap.put(usn,new Pair<>(p.getFirst(),newEntry));
        updatePersistent();
    }

    @Override
    public List<ScoreEntry> getLeaderBoard() {
        return userMap.values().stream().map(Pair::getSecond).collect(Collectors.toList());
    }

    private void updatePersistent(){
        File f=new File(filename);
        if(!f.exists()){
            try {
                if(!f.createNewFile())return;
            } catch (IOException e) {
                Logger.getGlobal().log(Level.SEVERE,"Error creating user file "+e.getMessage());
                return;
            }
        }
        try(PrintWriter pw = new PrintWriter(new File(filename))) {
            for(ConcurrentHashMap.Entry<String,Pair<String,ScoreEntry>> e:userMap.entrySet()){
                String usn=e.getKey()+",";
                String psw=e.getValue().getFirst()+",";
                String tot=e.getValue().getSecond().tot+",";
                String win=e.getValue().getSecond().wins+"\n";
                pw.write(usn+psw+tot+win);
            }
        } catch (FileNotFoundException e) {
            Logger.getGlobal().log(Level.SEVERE,"Error opening user file "+e.getMessage());
        }
    }

    /**
     * Parses an integer from a string
     * @param s string to parse
     * @return the integer value or {@code null} if an Exception is thrown
     */
    private Integer parseInt(String s){
        try{
            return Integer.parseInt(s);
        }catch(NumberFormatException e){
            return null;
        }
    }
}
