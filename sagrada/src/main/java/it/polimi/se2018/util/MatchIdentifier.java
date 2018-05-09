package it.polimi.se2018.util;


import java.io.Serializable;
import java.util.TreeSet;

/**
 * Wrapper class to identify a match
 * @author Francesco Franzini
 * */
public class MatchIdentifier implements Serializable {
    public static final String DEFAULT_SEPARATOR=":";
    public final int playerCount;
    public final String player0;
    public final String player1;
    public final String player2;
    public final String player3;

    /**
     * Initializes player names in alphabetic(asc) order.
     * Player names must be different
     *
     * @param player0 the first name
     * @param player1 the second name
     * @param player2 the third name({@code null} if not used)
     * @param player3 the fourth name({@code null} if not used)
     */
    public MatchIdentifier(String player0, String player1, String player2, String player3) {
        TreeSet<String> s =new TreeSet<>();
        if(player0==null || player1==null){
            throw new IllegalArgumentException();
        }
        s.add(player0);
        s.add(player1);
        if(player2==null){
            playerCount=2;
        }else{
            s.add(player2);
            if(player3==null){
                playerCount=3;
            }else{
                s.add(player3);
                playerCount=4;
            }
        }
        this.player0 = s.pollFirst();
        this.player1 = s.pollFirst();
        if(playerCount>2){
            this.player2 = s.pollFirst();
        }else{
            this.player2="";
        }
        if(playerCount==4){
            this.player3 = s.pollFirst();
        }else{
            this.player3="";
        }
    }

    @Override
    public String toString(){
        return player0+DEFAULT_SEPARATOR
                +player1+DEFAULT_SEPARATOR
                +player2+DEFAULT_SEPARATOR
                +player3;
    }

    /**
     * Finds the given client's position in this id
     * @param usn the username to put in
     * @return the index of the client, -1 if not valid
     */
    public int findPos(String usn){
        if(this.player0.equals(usn)){
            return 0;
        }else if(this.player1.equals(usn)){
            return 1;
        }else if(this.player2.equals(usn)){
            return 2;
        }else if(this.player3.equals(usn)){
            return 3;
        }
        return -1;
    }
}